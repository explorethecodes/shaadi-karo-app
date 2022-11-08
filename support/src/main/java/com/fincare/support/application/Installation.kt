package com.fincare.support.application

import android.content.Context
import android.content.pm.PackageManager

fun isFirstInstall(context: Context): Boolean {
    return try {
        val firstInstallTime =
            context.packageManager.getPackageInfo(context.packageName, 0).firstInstallTime
        val lastUpdateTime =
            context.packageManager.getPackageInfo(context.packageName, 0).lastUpdateTime
        firstInstallTime == lastUpdateTime
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        true
    }
}

fun isInstallFromUpdate(context: Context): Boolean {
    return try {
        val firstInstallTime =
            context.packageManager.getPackageInfo(context.packageName, 0).firstInstallTime
        val lastUpdateTime =
            context.packageManager.getPackageInfo(context.packageName, 0).lastUpdateTime
        firstInstallTime != lastUpdateTime
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        false
    }
}