package com.fincare.support.alerts

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.fincare.support.R
import com.fincare.support.views.toast

fun FragmentActivity.noInternetAlert(callback: (AlertCallbacks) -> Unit){
    noInternetAlert(this) {
        when (it) {
            AlertCallbacks.TRY_AGAIN -> {
                callback(AlertCallbacks.TRY_AGAIN)
            }
            AlertCallbacks.QUIT -> {
                callback(AlertCallbacks.QUIT)
            }
        }
    }
}

fun noInternetAlert(context: Context, callback : (AlertCallbacks) -> Unit) {
    val dialogBuilder = AlertDialog.Builder(context).create()
    val inflater: LayoutInflater = LayoutInflater.from(context)

    val dialogView: View = inflater.inflate(R.layout.alert_no_internet, null)
    dialogView.setBackgroundColor(context.resources.getColor(android.R.color.transparent))

    val image: ImageView = dialogView.findViewById(R.id.id_image) as ImageView
    val positive: Button = dialogView.findViewById(R.id.id_positive) as Button
    val negative: Button = dialogView.findViewById(R.id.id_negative) as Button

    image.setOnClickListener {
        context.toast("Uh oh ! I can't jump & play :(")
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