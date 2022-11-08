package com.fincare.support.alerts

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.fincare.support.R
import com.fincare.support.views.toggleVisibility

fun FragmentActivity.somethingWentWrongAlert(exception: Exception, callback: (AlertCallbacks) -> Unit){
    somethingWentWrongAlert(this,exception){
        when(it){
            AlertCallbacks.TRY_AGAIN -> {
                callback(AlertCallbacks.TRY_AGAIN)
            }
            AlertCallbacks.QUIT -> {
                callback(AlertCallbacks.QUIT)
            }
        }
    }
}

fun somethingWentWrongAlert(context: Context, exception: Exception, callback : (AlertCallbacks) -> Unit) {
    val dialogBuilder = AlertDialog.Builder(context).create()
    val inflater: LayoutInflater = LayoutInflater.from(context)

    val dialogView: View = inflater.inflate(R.layout.alert_something_went_wrong, null)
    dialogView.setBackgroundColor(context.resources.getColor(android.R.color.transparent))

    val positive: Button = dialogView.findViewById(R.id.id_positive) as Button
    val negative: Button = dialogView.findViewById(R.id.id_negative) as Button

    val image : ImageView = dialogView.findViewById(R.id.id_logo_image) as ImageView
    val reason : TextView = dialogView.findViewById(R.id.id_reason) as TextView

    reason.text = exception.toString()

    image.setOnClickListener {
        reason.toggleVisibility()
    }
    positive.setOnClickListener {
        dialogBuilder.dismiss()
        callback(AlertCallbacks.TRY_AGAIN)
    }

    negative.setOnClickListener {
        dialogBuilder.dismiss()
        callback(AlertCallbacks.QUIT)
    }

    dialogBuilder.setView(dialogView)
    dialogBuilder.setCancelable(false)
    dialogBuilder.show()
}