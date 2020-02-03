package com.elyeproj.blurimage

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.RadioButton
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.elyeproj.blurimage.bluralgo.BlurBasic
import com.elyeproj.blurimage.bluralgo.BlurBox
import com.elyeproj.blurimage.bluralgo.BlurEngine
import com.elyeproj.blurimage.bluralgo.JavaBlurProcess
import kotlinx.android.synthetic.main.activity_blur_image_basic.*
import kotlin.system.measureTimeMillis


class BlurImageBasicActivity : AppCompatActivity() {

    private var blurEngine: BlurEngine = BlurBasic()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blur_image_basic)

        (blur_method.getChildAt(0) as RadioButton).isChecked = true

        blur_method.setOnCheckedChangeListener { _, checkedId ->
            when (findViewById<RadioButton>(checkedId).id) {
                R.id.radio_blur_basic -> blurEngine = BlurBasic()
                R.id.radio_blur_box -> blurEngine = BlurBox()
                R.id.radio_blur_optimized_stack -> blurEngine = JavaBlurProcess()
            }
        }

        btn_blur.setOnClickListener {
            val radius = seekbar_radius.progress
            val measureTime = measureTimeMillis {
                img_view.setImageBitmap(
                    blurEngine.blur((img_view.drawable as BitmapDrawable).bitmap, radius)
                )
            }

            txt_result.text = "Time $measureTime ms with Radius: $radius using ${blurEngine.getType()}"
        }

        btn_reset.setOnClickListener {
            img_view.setImageResource(R.drawable.spring_in_lake_tekapo)
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
