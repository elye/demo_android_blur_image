package com.elyeproj.blurimage.bluralgo

import android.graphics.Bitmap
import android.graphics.Color

class BlurBasic {

    fun blur(image: Bitmap): Bitmap {
        val w = image.width
        val h = image.height
        val currentPixels = IntArray(w * h)
        val newPixels = IntArray(w * h)
        image.getPixels(currentPixels, 0, w, 0, 0, w, h)

        for (col in 0 until w) {
            for (row in 0 until h) {
                newPixels[row * w + col] = getSurroundAverage(currentPixels, col, row, h, w)
            }
        }

        return Bitmap.createBitmap(newPixels, w, h, Bitmap.Config.ARGB_8888)
    }

    private fun getSurroundAverage(
        currentPixels: IntArray,
        col: Int,
        row: Int,
        h: Int,
        w: Int
    ): Int {
        val originalPixel = currentPixels[row * w + col]
        val a: Int = originalPixel shr 24 and 0xFF
        val rOrig = originalPixel ushr 16 and 0xFF
        val gOrig = originalPixel ushr 8 and 0xFF
        val bOrig = originalPixel and 0xFF

        var rSum = rOrig
        var gSum = gOrig
        var bSum = bOrig

        for (y in (row - 1..row + 1)) {
            for (x in col - 1..col + 1) {
                if (y < 0 || y > h - 1 || x < 0 || x > w - 1) {
                    // Add the original value if it is outside the image boundary
                    rSum += rOrig; gSum += gOrig; bSum += bOrig
                } else if (y == row && x == col) {
                    // Don't do anything, as we have already added once up there.
                } else {
                    val sidePixel = currentPixels[y * w + x]
                    rSum += sidePixel ushr 16 and 0xFF
                    gSum += sidePixel ushr 8 and 0xFF
                    bSum += sidePixel and 0xFF
                }
            }
        }

        return Color.argb(a, rSum / 9, gSum / 9, bSum / 9)
    }
}
