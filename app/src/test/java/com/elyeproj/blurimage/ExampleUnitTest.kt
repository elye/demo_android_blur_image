package com.elyeproj.blurimage

import com.elyeproj.blurimage.bluralgo.BlurBasic
import com.elyeproj.blurimage.bluralgo.BlurBox
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun box_and_basic_match() {
        val blurBox = BlurBox()
        val blurBasic = BlurBasic()

        val width = 5
        val height = 4
        val data = intArrayOf(100, 2, 200, 4, 150, 6, 70, 8, 9, 105, 115, 123, 13, 214, 15, 160, 17, 18, 190, 20)
        val newBoxPixels = IntArray( width * height)
        val newBasicPixels = IntArray(width * height)

        blurBox.blurProcess(width, height, data, newBoxPixels, 1)
        blurBasic.blurProcess(width, height, data, newBasicPixels, 1)

        // Just check the center and skip all the border, since they are calculated border differently
        // due to different adjacent value for both approach. The other pixel should be identical
        for (y in 1 until height - 1) {
            for (x in 1 until width - 1) {
                assertEquals(newBoxPixels[y * width + x], newBasicPixels[y * width + x])
            }
        }
    }
}
