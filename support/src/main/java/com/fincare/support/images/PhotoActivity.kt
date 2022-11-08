package com.fincare.support.images

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fincare.support.databinding.ActivityPhotoBinding
import com.fincare.support.views.hide
import com.fincare.support.views.show

class PhotoActivity: AppCompatActivity() {

    lateinit var binding : ActivityPhotoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        create()
    }

    private fun create() {
        val imageUrl = intent.getStringExtra("image_url").toString()

        imageProgress(true)
        binding.idImage.loadImage(imageUrl){
            imageProgress(false)
            viewImage(binding.idImage,binding.idRoot)
        }

        binding.idBack.setOnClickListener {
            finish()
        }
    }

    private fun imageProgress(isLoading : Boolean){
        if (isLoading){
            binding.idProgressBar.show()
        } else {
            binding.idProgressBar.hide()
        }
    }
}