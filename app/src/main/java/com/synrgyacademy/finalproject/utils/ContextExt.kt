package com.synrgyacademy.finalproject.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import com.synrgyacademy.finalproject.R

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, duration).show()

fun Context.showAlert(title: String, message: String, positiveCallback: (DialogInterface) -> Unit) {
    val builder = AlertDialog.Builder(this)
    with(builder) {
        setTitle(title)
        setMessage(message)
        setPositiveButton(getString(R.string.text_ya)) { dialog, _ ->
            positiveCallback(dialog)
            dialog.cancel()
        }
        setNegativeButton(getString(R.string.text_batal)) { dialog, _ ->
            dialog.cancel()
        }
    }

    val alertDialog = builder.create()
    alertDialog.setCanceledOnTouchOutside(true)
    alertDialog.show()
}