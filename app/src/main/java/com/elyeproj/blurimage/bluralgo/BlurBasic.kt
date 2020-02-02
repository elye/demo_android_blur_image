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

        if (row == 0 || col == 0) {
            rSum += rOrig; gSum += gOrig; bSum += bOrig
        } else {
            val topLeftPixel = currentPixels[(row - 1) * w + (col - 1)]
            rSum += topLeftPixel ushr 16 and 0xFF
            gSum += topLeftPixel ushr 8 and 0xFF
            bSum += topLeftPixel and 0xFF
        }

        if (row == 0) {
            rSum += rOrig; gSum += gOrig; bSum += bOrig
        } else {
            val topPixel = currentPixels[(row - 1) * w + (col)]
            rSum += topPixel ushr 16 and 0xFF
            gSum += topPixel ushr 8 and 0xFF
            bSum += topPixel and 0xFF
        }

        if (row == 0 || col == w - 1) {
            rSum += rOrig; gSum += gOrig; bSum += bOrig
        } else {
            val topRightPixel = currentPixels[(row - 1) * w + (col + 1)]
            rSum += topRightPixel ushr 16 and 0xFF
            gSum += topRightPixel ushr 8 and 0xFF
            bSum += topRightPixel and 0xFF
        }

        if (col == 0) {
            rSum += rOrig; gSum += gOrig; bSum += bOrig
        } else {
            val leftPixel = currentPixels[(row) * w + (col - 1)]
            rSum += leftPixel ushr 16 and 0xFF
            gSum += leftPixel ushr 8 and 0xFF
            bSum += leftPixel and 0xFF
        }

        if (col == w - 1) {
            rSum += rOrig; gSum += gOrig; bSum += bOrig
        } else {
            val rightPixel = currentPixels[(row) * w + (col + 1)]
            rSum += rightPixel ushr 16 and 0xFF
            gSum += rightPixel ushr 8 and 0xFF
            bSum += rightPixel and 0xFF
        }

        if (row == h - 1 || col == 0) {
            rSum += rOrig; gSum += gOrig; bSum += bOrig
        } else {
            val bottomLeftPixel = currentPixels[(row + 1) * w + (col - 1)]
            rSum += bottomLeftPixel ushr 16 and 0xFF
            gSum += bottomLeftPixel ushr 8 and 0xFF
            bSum += bottomLeftPixel and 0xFF
        }

        if (row == h - 1) {
            rSum += rOrig; gSum += gOrig; bSum += bOrig
        } else {
            val bottomPixel = currentPixels[(row + 1) * w + (col)]
            rSum += bottomPixel ushr 16 and 0xFF
            gSum += bottomPixel ushr 8 and 0xFF
            bSum += bottomPixel and 0xFF
        }

        if (row == h - 1 || col == w - 1) {
            rSum += rOrig; gSum += gOrig; bSum += bOrig
        } else {
            val bottomRightPixel = currentPixels[(row + 1) * w + (col + 1)]
            rSum += bottomRightPixel ushr 16 and 0xFF
            gSum += bottomRightPixel ushr 8 and 0xFF
            bSum += bottomRightPixel and 0xFF
        }

        return Color.argb(a, rSum/9, gSum/9, bSum/9)
    }
}
