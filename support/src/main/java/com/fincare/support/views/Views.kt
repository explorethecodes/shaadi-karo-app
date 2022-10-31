package com.fincare.support.views

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun View.show(){
    visibility = View.VISIBLE
}

fun View.hide(){
    visibility = View.GONE
}

fun View.invisible(){
    visibility = View.INVISIBLE
}

fun show(views: List<View>) {
    views.forEach {
        it.show()
    }
}

fun hide(views: List<View>) {
    views.forEach {
        it.hide()
    }
}

fun View.toggleVisibility(){
    if (visibility == View.VISIBLE){
        hide()
    } else {
        show()
    }
}

fun View.enable(){
    isEnabled = true
}

fun View.disable(){
    isEnabled = false
}

fun Context.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG ).show()
}

fun View.snackbar(message: String){
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}

fun View.setMargins(left: Int?, top: Int?, right: Int?, bottom: Int?) {
    if (layoutParams is MarginLayoutParams) {
        val p = layoutParams as MarginLayoutParams

        var leftMargin = p.leftMargin
        var topMargin = p.topMargin
        var rightMargin = p.rightMargin
        var bottomMargin = p.bottomMargin

        left?.let {
            leftMargin = it
        }
        top?.let {
            topMargin = it
        }
        right?.let {
            rightMargin = it
        }
        bottom?.let {
            bottomMargin = it
        }

        p.setMargins(leftMargin,topMargin,rightMargin,bottomMargin)

        requestLayout()
    }
}

fun View.resetMargin(){
    setMargins(null,null,null,null)
}

fun View.clearData(){
    clearData(this.context,this)
}

fun clearData(context: Context, view : View) {
    try {
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child = view.getChildAt(i)
                clearData(context,view)
            }
        } else if (view is TextView) {
            view.text = ""
        } else if (view is ImageView){
            view.setImageResource(0)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

// slide the view from below itself to the current position
fun slideUp(view: View) {
    view.visibility = View.VISIBLE
    val animate = TranslateAnimation(
        0f,  // fromXDelta
        0f,  // toXDelta
        view.height.toFloat(),  // fromYDelta
        0f
    ) // toYDelta
    animate.duration = 500
    animate.fillAfter = true
    view.startAnimation(animate)
}


// slide the view from its current position to below itself
fun slideDown(view: View) {
    val animate = TranslateAnimation(
        0f,  // fromXDelta
        0f,  // toXDelta
        0f,  // fromYDelta
        view.height.toFloat()
    ) // toYDelta
    animate.duration = 500
    animate.fillAfter = true
    view.startAnimation(animate)
}