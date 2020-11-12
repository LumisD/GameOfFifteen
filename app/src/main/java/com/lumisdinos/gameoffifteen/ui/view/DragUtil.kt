package com.lumisdinos.gameoffifteen.ui.view

import android.annotation.SuppressLint
import android.content.ClipData
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.lumisdinos.gameoffifteen.common.util.intToStr
import com.lumisdinos.gameoffifteen.common.AppConfig.cell15Size
import com.lumisdinos.gameoffifteen.data.Constants.FOUR_CELLS_IN_ROW
import com.lumisdinos.gameoffifteen.data.Constants.TWO_SIDES
import com.lumisdinos.gameoffifteen.databinding.CellBinding
import javax.inject.Inject


class DragUtil @Inject constructor() {

    private lateinit var callBack: (cellIndex: Int, zeroIndex: Int) -> Unit

    private var xFromLayout = -1
    private var yFromLayout = -1
    private var zeroIndex = -1
    private var gridMargin = 0
    private var cellMargin = 0
    private var viewTouched: View? = null
    private var dragParams: RelativeLayout.LayoutParams? = null


    @SuppressLint("ClickableViewAccessibility")
    fun insertCellsInLLayout(
        numbers: List<Int>,
        layout: RelativeLayout?,
        cellMovedHandler: (cellIndex: Int, zeroIndex: Int) -> Unit,
        layoutInflater: LayoutInflater,
        gridMargin: Int,
        cellMargin: Int

    ) {
        if (layout == null) return
        setInitialState()
        callBack = cellMovedHandler
        this.gridMargin = gridMargin
        this.cellMargin = cellMargin

        layout.removeAllViews()

        layout.setOnDragListener(layoutDragListener)

        for (i in numbers.indices) {
            val number = numbers[i]
            if (number == 0) {
                zeroIndex = i
                continue
            }
            val textView = CellBinding.inflate(layoutInflater).root
            textView.text = intToStr(number)
            textView.width = cell15Size
            textView.height = cell15Size
            textView.tag = i

            textView.setOnTouchListener { vTouch, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {

                    if (!isPossibleToMoveCell(vTouch.tag as Int)) return@setOnTouchListener false

                    val dragData = ClipData.newPlainText("", "")
                    val myShadow = View.DragShadowBuilder(vTouch)
                    ViewCompat.startDragAndDrop(vTouch, dragData, myShadow, vTouch, 0)

                    viewTouched = vTouch
                    viewTouched?.setOnDragListener(dragListener)

                    return@setOnTouchListener true
                } else {
                    return@setOnTouchListener false
                }
            }

            layout.addView(textView)

            val leftTop = getLeftTop(i)
            val left = leftTop.first
            val top = leftTop.second
            val params = textView.layoutParams as RelativeLayout.LayoutParams
            params.setMargins(left, top, cellMargin, cellMargin)

            textView.layoutParams = params
            textView.invalidate()
        }
    }


    private fun isPossibleToMoveCell(cellIndex: Int): Boolean {
        if (zeroIndex + FOUR_CELLS_IN_ROW == cellIndex || zeroIndex - FOUR_CELLS_IN_ROW == cellIndex) return true
        if (zeroIndex / FOUR_CELLS_IN_ROW == cellIndex / FOUR_CELLS_IN_ROW &&
            (zeroIndex + 1 == cellIndex || zeroIndex - 1 == cellIndex)
        ) return true
        return false
    }


    private fun setInitialState() {
        xFromLayout = -1
        yFromLayout = -1
        zeroIndex = -1
        viewTouched = null
        dragParams = null
    }


    private val dragListener = View.OnDragListener { vDrag, eventDrag ->
        val view = eventDrag.localState as TextView


        when (eventDrag.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                dragParams = view.layoutParams as RelativeLayout.LayoutParams?
                vDrag.visibility = View.INVISIBLE
                true
            }

            DragEvent.ACTION_DRAG_ENDED -> {

                if (xFromLayout > -1) {
                    alignAndPlaceDraggedView(vDrag)
                }
                xFromLayout = -1
                yFromLayout = -1

                vDrag.visibility = View.VISIBLE
                view.invalidate()

                viewTouched?.setOnDragListener(null)
                true
            }
            else -> {
                false
            }
        }
    }


    private fun alignAndPlaceDraggedView(viewDragged: View) {
        dragParams?.let {

            val leftTop = getLeftTop(zeroIndex)
            val left = leftTop.first
            val top = leftTop.second
            val right = left + cell15Size
            val bottom = top + cell15Size

            if (xFromLayout in (left + 1) until right &&
                yFromLayout in (top + 1) until bottom) {
                it.marginStart = left
                it.topMargin = top

                callBackAndSwapZeroIndexWithTag(viewDragged)
            }

            viewDragged.layoutParams = it
        }
    }


    private fun callBackAndSwapZeroIndexWithTag(viewDragged: View) {
        callBack?.let { it(viewDragged.tag as Int, zeroIndex) }
        viewDragged.tag = zeroIndex.also { zeroIndex = viewDragged.tag as Int }
    }


    private val layoutDragListener = View.OnDragListener { _, eventDrag ->

        when (eventDrag.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                true
            }
            DragEvent.ACTION_DROP -> {
                xFromLayout = eventDrag.x.toInt()
                yFromLayout = eventDrag.y.toInt()
                true
            }
            else -> {
                false
            }
        }
    }


    private fun getLeftTop(index: Int): Pair<Int, Int> {
        val row = index / FOUR_CELLS_IN_ROW
        val column = index % FOUR_CELLS_IN_ROW
        val left = gridMargin + (column * TWO_SIDES + 1) * cellMargin + column * cell15Size
        val top = gridMargin + (row * TWO_SIDES + 1) * cellMargin + row * cell15Size
        return Pair(left, top)
    }

}

