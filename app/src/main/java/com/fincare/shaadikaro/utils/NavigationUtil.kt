package com.fincare.shaadikaro.utils

import android.app.Activity
import android.content.Intent
import com.fincare.support.images.PhotoActivity

fun Activity.startPhotoActivity(imageUrl : String){
    val intent = Intent(this, PhotoActivity::class.java)
    intent.putExtra("image_url",imageUrl)
    startActivity(intent)
}