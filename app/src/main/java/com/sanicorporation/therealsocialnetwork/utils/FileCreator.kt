package com.sanicorporation.therealsocialnetwork.utils

import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class FileCreator {
    companion object {
        fun createImageFile(externalFileDir: File?): File?{
            val storageDir: File? = externalFileDir
            return try {
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())

                createTempFile("JPEG_${timeStamp}_", /* prefix */
                    ".jpg", /* suffix */
                    externalFileDir /* directory */)
            } catch (ex: IOException) {
                // Error occurred while creating the File

                null
            }
        }

        private fun createTempFile(prefix: String, suffix: String, directory: File?): File{
            return File.createTempFile(prefix, suffix, directory)
        }
    }

}