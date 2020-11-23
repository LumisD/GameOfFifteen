package com.lumisdinos.gameoffifteen.ui.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.core.view.children
import com.lumisdinos.gameoffifteen.common.util.intToStr
import com.lumisdinos.gameoffifteen.common.AppConfig.cell15Size
import com.lumisdinos.gameoffifteen.data.Constants.DURATION_MOVE_CELL
import com.lumisdinos.gameoffifteen.data.Constants.FOUR_CELLS_IN_ROW
import com.lumisdinos.gameoffifteen.data.Constants.TWO_SIDES
import com.lumisdinos.gameoffifteen.databinding.CellBinding
import javax.inject.Inject


class DragUtil @Inject constructor() {

    private lateinit var callBack: (cellIndex: Int, zeroIndex: Int) -> Unit

    private var zeroIndex = -1
    private var gridMargin = 0
    private var cellMargin = 0


    fun insertCellsInLLayout(
        numbers: List<Int>,
        layout: RelativeLayout?,
        cellMovedHandler: (cellIndex: Int, zeroIndex: Int) -> Unit,
        layoutInflater: LayoutInflater,
        gridMargin: Int,
        cellMargin: Int,
        animateUtil: AnimateUtil
    ) {
        if (layout == null) return

        insertCellsInLLayout2(
            numbers, layout, cellMovedHandler,
            layoutInflater,
            gridMargin,
            cellMargin,
            animateUtil
        )
        layout.children.forEach {
            animateUtil.animateCells(it)
        }

    }


    @SuppressLint("ClickableViewAccessibility")
    private fun insertCellsInLLayout2(
        numbers: List<Int>,
        layout: RelativeLayout?,
        cellMovedHandler: (cellIndex: Int, zeroIndex: Int) -> Unit,
        layoutInflater: LayoutInflater,
        gridMargin: Int,
        cellMargin: Int,
        animateUtil: AnimateUtil
    ) {
        if (layout == null) return

        callBack = cellMovedHandler
        this.gridMargin = gridMargin
        this.cellMargin = cellMargin

        layout.removeAllViews()

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

            textView.setOnClickListener { view ->
                if (!isPossibleToMoveCell(view.tag as Int)) return@setOnClickListener
                alignAndPlaceClickedView(view, animateUtil)
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


    private fun alignAndPlaceClickedView(viewClicked: View, animateUtil: AnimateUtil) {
        (viewClicked.layoutParams as RelativeLayout.LayoutParams?)?.let {
            val leftTop = getLeftTop(zeroIndex)
            val left = leftTop.first
            val top = leftTop.second
            animateUtil.moveCell(viewClicked, left, top, DURATION_MOVE_CELL, it)
            callBackAndSwapZeroIndexWithTag(viewClicked)
        }
    }


    private fun callBackAndSwapZeroIndexWithTag(viewDragged: View) {
        callBack?.let { it(viewDragged.tag as Int, zeroIndex) }
        viewDragged.tag = zeroIndex.also { zeroIndex = viewDragged.tag as Int }
    }


    private fun getLeftTop(index: Int): Pair<Int, Int> {
        val row = index / FOUR_CELLS_IN_ROW
        val column = index % FOUR_CELLS_IN_ROW
        val left = gridMargin + (column * TWO_SIDES + 1) * cellMargin + column * cell15Size
        val top = gridMargin + (row * TWO_SIDES + 1) * cellMargin + row * cell15Size
        return Pair(left, top)
    }

}

