package com.sanicorporation.therealsocialnetwork.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class PhotoModifier {

    companion object {

        fun rotateIfItsNeccesary(file: File, completionHandler: ()-> Unit) {
            val exitInterface = ExifInterface(file.path);
            val orientation = exitInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(90.0f, file, completionHandler);
                ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(180.0f, file, completionHandler);
                ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(270.0f, file, completionHandler);
                else -> completionHandler()
            }

        }

        private fun rotateImage(degrees: Float, file: File, completionHandler: ()-> Unit){
            val matrix = Matrix();
            matrix.postRotate(degrees);
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            val newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height,
                matrix, true);
            val outputString = ByteArrayOutputStream()
            newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputString)
            val bitmapData = outputString.toByteArray()
            val fos = FileOutputStream(file)
            fos.write(bitmapData)
            fos.flush()
            fos.close()
            completionHandler()
        }
    }
}