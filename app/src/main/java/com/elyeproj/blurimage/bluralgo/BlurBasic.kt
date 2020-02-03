package com.elyeproj.blurimage.bluralgo

import android.graphics.Bitmap
import android.graphics.Color

class BlurBasic {

    fun blur(image: Bitmap, radius: Int): Bitmap {
        val w = image.width
        val h = image.height
        val currentPixels = IntArray(w * h)
        val newPixels = IntArray(w * h)
        image.getPixels(currentPixels, 0, w, 0, 0, w, h)

        blurProcess(w, h, currentPixels, newPixels, radius)

        return Bitmap.createBitmap(newPixels, w, h, Bitmap.Config.ARGB_8888)
    }

    fun blurProcess (
        w: Int,
        h: Int,
        currentPixels: IntArray,
        newPixels: IntArray,
        radius: Int
    ) {
        for (col in 0 until w) {
            for (row in 0 until h) {
                newPixels[row * w + col] = getSurroundAverage(currentPixels, col, row, h, w, radius)
            }
        }
    }

    private fun getSurroundAverage(
        currentPixels: IntArray,
        col: Int,
        row: Int,
        h: Int,
        w: Int,
        radius: Int
    ): Int {
        val originalPixel = currentPixels[row * w + col]
        val a: Int = originalPixel shr 24 and 0xFF
        val rOrig = originalPixel ushr 16 and 0xFF
        val gOrig = originalPixel ushr 8 and 0xFF
        val bOrig = originalPixel and 0xFF

        var rSum = rOrig
        var gSum = gOrig
        var bSum = bOrig

        for (y in (row - radius..row + radius)) {
            for (x in col - radius..col + radius) {
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

        val denominator = (radius * 2 + 1) * (radius * 2 + 1)

        return  ((a and 0xff) shl 24) or
                ((rSum / denominator) and 0xff shl 16) or
                ((gSum / denominator) and 0xff shl 8) or
                ((bSum / denominator) and 0xff)    }
}
