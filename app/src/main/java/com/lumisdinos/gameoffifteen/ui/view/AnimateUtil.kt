package com.lumisdinos.gameoffifteen.ui.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Handler
import android.view.View
import android.view.animation.*
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.lumisdinos.gameoffifteen.R
import com.lumisdinos.gameoffifteen.common.AppConfig.fragWidth
import com.lumisdinos.gameoffifteen.data.Constants.DURATION_FULL
import com.lumisdinos.gameoffifteen.data.Constants.DURATION_TO_CENTER
import com.lumisdinos.gameoffifteen.data.Constants.DURATION_TO_DOWN
import com.lumisdinos.gameoffifteen.data.Constants.DURATION_TO_SIDE
import com.lumisdinos.gameoffifteen.data.Constants.ROTATE_END
import com.lumisdinos.gameoffifteen.data.Constants.ROTATE_START
import com.lumisdinos.gameoffifteen.data.Constants.SCALE_END
import com.lumisdinos.gameoffifteen.data.Constants.SCALE_START
import java.util.*
import javax.inject.Inject


class AnimateUtil @Inject constructor() {

    fun animateDice(diceImage: ImageView, isLeft: Boolean) {

        val emptySpaceInDice = diceImage.width / 6F
        val takeAway = diceImage.layoutParams.width / 2

        val initialX = diceImage.left + diceImage.layoutParams.width / 2.toFloat()

        val sideX = if (isLeft) {
            diceImage.layoutParams.width / 2.toFloat() - emptySpaceInDice
        } else {
            fragWidth - diceImage.layoutParams.width / 2.toFloat() + emptySpaceInDice
        }

        val initialY = diceImage.top + diceImage.layoutParams.height / 2.toFloat()
        var middleY = initialY + diceImage.layoutParams.height.toFloat()
        var downY = initialY + diceImage.layoutParams.height * 2.toFloat()
        if (isLeft) {
            val bias =
                diceImage.context.resources.getDimensionPixelSize(R.dimen.dice_right_margin_top)
            middleY += bias
            downY += bias
        }

        val rotateStart = if (isLeft) {
            ROTATE_END
        } else {
            ROTATE_START
        }
        val rotateEnd = if (isLeft) {
            ROTATE_START
        } else {
            ROTATE_END
        }


        val animatorYDown = ValueAnimator.ofFloat(initialY, downY)
        animatorYDown.addUpdateListener { animation ->
            diceImage.y = animation.animatedValue as Float - takeAway
        }
        animatorYDown.duration = DURATION_TO_DOWN
        animatorYDown.interpolator = AccelerateInterpolator()


        val animatorXToSide = ValueAnimator.ofFloat(initialX, sideX)
        animatorXToSide.addUpdateListener { animation ->
            diceImage.x = animation.animatedValue as Float - takeAway
        }
        animatorXToSide.duration = DURATION_TO_SIDE
        animatorXToSide.interpolator = LinearOutSlowInInterpolator()


        val animatorYUpWithSide = ValueAnimator.ofFloat(downY, middleY)
        animatorYUpWithSide.addUpdateListener { animation ->
            diceImage.y = animation.animatedValue as Float - takeAway
        }
        animatorYUpWithSide.duration = DURATION_TO_SIDE
        animatorYUpWithSide.interpolator = DecelerateInterpolator()


        val animatorXToCenter = ValueAnimator.ofFloat(sideX, initialX)
        animatorXToCenter.addUpdateListener { animation ->
            diceImage.x = animation.animatedValue as Float - takeAway
        }
        animatorXToCenter.duration = DURATION_TO_CENTER
        animatorXToCenter.interpolator = DecelerateInterpolator()


        val animatorYUpWithCenter = ValueAnimator.ofFloat(middleY, initialY)
        animatorYUpWithCenter.addUpdateListener { animation ->
            diceImage.y = animation.animatedValue as Float - takeAway
        }
        animatorYUpWithCenter.duration = DURATION_TO_CENTER
        animatorYUpWithCenter.interpolator = BounceInterpolator();


        val anivatorRotate = ObjectAnimator.ofFloat(
            diceImage,
            "rotation", rotateStart, rotateEnd
        )
        anivatorRotate.duration = DURATION_TO_DOWN + DURATION_TO_SIDE + DURATION_TO_CENTER  * 2

        AnimatorSet().apply {
            play(animatorXToSide).after(animatorYDown)
            play(animatorXToCenter).after(animatorXToSide)
            play(animatorYUpWithCenter).after(animatorXToCenter)
            play(animatorXToSide).with(animatorYUpWithSide)
            play(anivatorRotate)
            start()
        }

        Handler().postDelayed(
            {
                val drawableResource = when (Random().nextInt(6) + 1) {
                    1 -> R.drawable.dice_1
                    2 -> R.drawable.dice_2
                    3 -> R.drawable.dice_3
                    4 -> R.drawable.dice_4
                    5 -> R.drawable.dice_5
                    else -> R.drawable.dice_6
                }
                diceImage.setImageResource(drawableResource)
            }, DURATION_TO_DOWN + DURATION_TO_SIDE
        )
    }


    fun animateCells(cell: View) {

        val anivatorRotateFirst = ObjectAnimator.ofFloat(
            cell,
            "rotation", ROTATE_START, ROTATE_END
        )
        anivatorRotateFirst.duration = DURATION_FULL / 2

        val anivatorRotateSecond = ObjectAnimator.ofFloat(
            cell, "rotation", ROTATE_END, ROTATE_START
        )
        anivatorRotateSecond.duration = DURATION_FULL / 2

        val scaleYDown = ObjectAnimator.ofFloat(cell, "scaleY", SCALE_START, SCALE_END)
        scaleYDown.duration = DURATION_FULL / 2
        val scaleXDown = ObjectAnimator.ofFloat(cell, "scaleX", SCALE_START, SCALE_END)
        scaleXDown.duration = DURATION_FULL / 2

        val scaleYUp = ObjectAnimator.ofFloat(cell, "scaleY", SCALE_END, SCALE_START)
        scaleYUp.duration = DURATION_FULL / 2
        val scaleXUp = ObjectAnimator.ofFloat(cell, "scaleX", SCALE_END, SCALE_START)
        scaleXUp.duration = DURATION_FULL / 2

        val animatorSet = AnimatorSet()
        animatorSet.play(anivatorRotateSecond).after(anivatorRotateFirst)
        animatorSet.play(scaleYUp).after(scaleYDown)
        animatorSet.play(scaleXUp).after(scaleXDown)
        animatorSet.start()
    }


    fun moveCell(
        cell: View,
        left: Int,
        top: Int,
        duration: Long,
        layoutParams: RelativeLayout.LayoutParams
    ) {
        val isMovingX = top == cell.top
        val animatorSet = AnimatorSet()

        if (isMovingX) {
            val startX = cell.left.toFloat()
            val endX = left.toFloat()

            val animatorX = ValueAnimator.ofFloat(startX, endX)
            animatorX.addUpdateListener { animation ->
                layoutParams.marginStart = (animation.animatedValue as Float).toInt()
                cell.layoutParams = layoutParams
            }
            animatorX.duration = duration
            animatorX.interpolator = LinearOutSlowInInterpolator()

            animatorSet.play(animatorX)
        } else {
            val startY = cell.top.toFloat()
            val endY = top.toFloat()

            val animatorY = ValueAnimator.ofFloat(startY, endY)
            animatorY.addUpdateListener { animation ->
                layoutParams.topMargin = (animation.animatedValue as Float).toInt()
                cell.layoutParams = layoutParams
            }
            animatorY.duration = duration
            animatorY.interpolator = LinearOutSlowInInterpolator();

            animatorSet.play(animatorY)
        }

        animatorSet.start()
    }

}