package com.lumisdinos.gameoffifteen.ui.dialog

import android.app.AlertDialog
import android.content.Context

fun getAlertDialogFull(
    context: Context,
    action: String,
    additional: String,
    listener: DialogListener,
    title: String,
    message: String,
    btnPositive: String,
    btnNegative: String,
    btnNeutral: String
): AlertDialog {

    val builder = AlertDialog.Builder(context)
    with(builder)
    {
        setTitle(title)
        setMessage(message)
        if (!btnPositive.isEmpty()) {
            setPositiveButton(btnPositive) { _, id ->
                listener.onPositiveDialogClick(
                    listOf(
                        action,
                        additional
                    )
                )
            }
        }
        if (!btnNegative.isEmpty()) {
            setNegativeButton(btnNegative) { _, id -> listener.onNegativeDialogClick(listOf(action)) }
        }
        if (!btnNeutral.isEmpty()) {
            setNeutralButton(btnNeutral) { _, id -> listener.onNeutralDialogClick(listOf(action)) }
        }
    }
    val dialog = builder.create()
    dialog.setCancelable(false)
    dialog.setCanceledOnTouchOutside(false)
    return dialog
}

fun getAlertDialog(
    context: Context,
    action: String,
    listener: DialogListener,
    title: String,
    message: String,
    btnPositive: String,
    btnNeutral: String
): AlertDialog {
    return getAlertDialogFull(
        context,
        action,
        "",
        listener,
        title,
        message,
        btnPositive,
        "",
        btnNeutral
    )
}

fun getAlertDialog(
    context: Context,
    action: String,
    additional: String,
    listener: DialogListener,
    title: String,
    message: String,
    btnPositive: String,
    btnNeutral: String
): AlertDialog {
    return getAlertDialogFull(
        context,
        action,
        additional,
        listener,
        title,
        message,
        btnPositive,
        "",
        btnNeutral
    )
}

fun getAlertDialog(
    context: Context,
    action: String,
    listener: DialogListener,
    title: String,
    message: String,
    btnPositive: String
): AlertDialog {
    return getAlertDialogFull(context, action, "", listener, title, message, btnPositive, "", "")
}

