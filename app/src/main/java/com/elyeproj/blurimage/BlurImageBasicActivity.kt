package com.elyeproj.blurimage

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.elyeproj.blurimage.bluralgo.BlurBasic
import kotlinx.android.synthetic.main.activity_blur_image_basic.*
import kotlin.system.measureTimeMillis


class BlurImageBasicActivity : AppCompatActivity() {

    private val blurEngine = BlurBasic()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blur_image_basic)

        btn_blur.setOnClickListener {
            val radius = seekbar_radius.progress
            val measureTime = measureTimeMillis {
                img_view.setImageBitmap(
                    blurEngine.blur((img_view.drawable as BitmapDrawable).bitmap, radius)
                )
            }

            txt_result.text = "Time $measureTime ms with Radius: $radius"
        }

        seekbar_radius.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, value: Int, p2: Boolean) {
                txt_radius.text = "Radius: $value"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }
}
