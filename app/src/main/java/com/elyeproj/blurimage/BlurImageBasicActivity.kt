package com.elyeproj.blurimage

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elyeproj.blurimage.bluralgo.BlurBasic
import kotlinx.android.synthetic.main.activity_blur_image_basic.*


class BlurImageBasicActivity : AppCompatActivity() {

    private val blurEngine = BlurBasic()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blur_image_basic)

        btn_blur.setOnClickListener {
            img_view.setImageBitmap(blurEngine.blur((img_view.drawable as BitmapDrawable).bitmap))
        }
    }
}
