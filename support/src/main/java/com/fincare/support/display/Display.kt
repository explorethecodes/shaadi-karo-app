package com.fincare.support.display

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentActivity

fun nightMode(isNightMode: Boolean) {
    if (isNightMode){
    } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}

fun FragmentActivity.fullScreen(isFullScreen: Boolean){
    if (isFullScreen){
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}

fun FragmentActivity.darkForeground(statusBarColor: Int, navigationBarColor: Int){
    darkForeground(this,statusBarColor,navigationBarColor)
}
fun darkForeground(activity: Activity, statusBarColor: Int, navigationBarColor: Int) {
    darkForeground(activity)
    activity.window.statusBarColor = statusBarColor
    activity.window.navigationBarColor = navigationBarColor
}

fun FragmentActivity.darkForeground(){
    darkForeground(this)
}
fun darkForeground(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var flags = activity.window.decorView.systemUiVisibility // get current flag
        flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // add LIGHT_STATUS_BAR to flag
        activity.window.decorView.systemUiVisibility = flags

        //                window.setFlags(
//                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        //                window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}

fun FragmentActivity.lightForeground(){
    lightForeground(this)
}
fun FragmentActivity.lightForeground(statusBarColor: Int, navigationBarColor: Int) {
    lightForeground(this,statusBarColor,navigationBarColor)
}

fun lightForeground(activity: Activity, statusBarColor: Int, navigationBarColor: Int){
    lightForeground(activity)
    activity.window.statusBarColor = statusBarColor
    activity.window.navigationBarColor = navigationBarColor
}

fun lightForeground(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var flags = activity.window.decorView.systemUiVisibility // get current flag
        flags =
            flags xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // use XOR here for remove LIGHT_STATUS_BAR from flags
        activity.window.decorView.systemUiVisibility = flags
    }
}