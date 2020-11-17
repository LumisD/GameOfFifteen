package com.lumisdinos.gameoffifteen.ui.dialog

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lumisdinos.gameoffifteen.R
import com.lumisdinos.gameoffifteen.domain.repos.PuzzleLogicRepository.Companion.ACTION_CONGRATULATIONS

fun showMaterialAlertDialog(
    context: Context,
    action: String
) {
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

    MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(context.getString(R.string.ok)) { _, _ -> }
        .show()
}
