package com.sanicorporation.therealsocialnetwork.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class PhotoModifier {

    companion object {
        fun rotateImage(degrees: Float, file: File): Bitmap{
            val matrix = Matrix();
            matrix.postRotate(degrees);
            val bitmap = BitmapFactory.decodeFile(file!!.absolutePath)
            val newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                matrix, true);
            val outputString = ByteArrayOutputStream()
            newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputString)
            val bitmaData = outputString.toByteArray()
            val fos = FileOutputStream(file)
            fos.write(bitmaData)
            fos.flush()
            fos.close()
            return newBitmap
        }
    }
}