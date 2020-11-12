package com.lumisdinos.gameoffifteen.ui.dialog

import android.app.AlertDialog
import android.content.Context
import com.lumisdinos.gameoffifteen.R
import com.lumisdinos.gameoffifteen.domain.repos.PuzzleLogicRepository.Companion.ACTION_CONGRATULATIONS

fun getAlertDialog(
    context: Context,
    action: String,
    listener: DialogListener
): AlertDialog {

    val title: String
    val message: String
    when (action) {

        ACTION_CONGRATULATIONS -> {
            title = context.getString(R.string.winner)
            message = context.getString(R.string.congratulations_you_solved_it)
        }
        else -> {//ACTION_UNSOLVABLE
            title = context.getString(R.string.finish)
            message = context.getString(R.string.sorry_unsolvable)
        }

    }

    val builder = AlertDialog.Builder(context)
    with(builder)
    {
        setTitle(title)
        setMessage(message)
        setPositiveButton(context.getString(R.string.ok)) { _, id ->
            listener.onPositiveDialogClick(
                listOf(
                    action
                )
            )
        }
    }
    val dialog = builder.create()
    dialog.setCancelable(false)
    dialog.setCanceledOnTouchOutside(false)
    return dialog
}
