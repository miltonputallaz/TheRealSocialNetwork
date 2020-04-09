package com.sanicorporation.therealsocialnetwork.activities.add_post


import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.sanicorporation.therealsocialnetwork.R
import com.sanicorporation.therealsocialnetwork.databinding.ActivityAddPostBinding
import com.sanicorporation.therealsocialnetwork.utils.FileCreator
import com.sanicorporation.therealsocialnetwork.utils.PhotoModifier
import kotlinx.android.synthetic.main.activity_add_post.*
import kotlinx.android.synthetic.main.image_capture_dialog.view.*
import java.io.File


class AddPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPostBinding

    private var addPostViewModel: AddPostViewModel = AddPostViewModel()

    val REQUEST_IMAGE_CAPTURE = 1
    var imageFile: File? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_post)
        setUpBinding()
        setUpToolbar()
        setUpCameraButton()
    }

    private fun setUpCameraButton() {
        val packageManager = packageManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val isEnable = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
            addPostViewModel.setCameraState(isEnable)
        } else {
            addPostViewModel.setCameraState(false)

        }
    }

    private fun setUpBinding(){
        binding.viewmodel = addPostViewModel
        binding.handler = this
        binding.lifecycleOwner = this
    }

    private fun setUpToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        onBackPressed()
        return true
    }

    private val sucessFullHandler: () -> Unit = {
        try {
            showSuccessfulAddedDialog()
        } catch (exception: WindowManager.BadTokenException){}

    }

    private val errorHandler: () -> Unit ={

    }

    private val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        goToMain()
    }

    fun onClickShare(){
        addPostViewModel.performAddPost(sucessFullHandler, errorHandler)
    }

    fun onClickCamera(){
        showCameraDialog()
    }

    private fun showCameraDialog() {
        lateinit var photoDialog: Dialog

        val builder = AlertDialog.Builder(this)
        // Get the layout inflater
        val inflater = layoutInflater;
        var view = inflater.inflate(R.layout.image_capture_dialog, null)
        view.select_image_button.setOnClickListener{
            photoDialog.dismiss()
            selectImage()
        }
        view.take_photo_button.setOnClickListener{
            photoDialog.dismiss()
            takePhoto()
        }
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
        photoDialog = builder.create()
        photoDialog.show()

    }


    private fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                imageFile = FileCreator.createImageFile(getExternalFilesDir(Environment.DIRECTORY_PICTURES))
                // Continue only if the File was successfully created
                imageFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    private fun selectImage(){
        imageFile
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            rotateIfItsNeccesary()
            if (imageFile!!.exists()) {
                addPostViewModel.setImagePath(imageFile!!.absolutePath)
            }
        }
    }

    private fun rotateIfItsNeccesary() {
        val exitInterface = ExifInterface(imageFile!!.path);
        val orientation = exitInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(90.0f);
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(180.0f);
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(270.0f);
            else -> setPostImage(BitmapFactory.decodeFile(imageFile!!.absolutePath))
        }

    }

    private fun setPostImage(bitmap: Bitmap) {
        post_image.setImageBitmap(bitmap)
    }

    private fun rotateImage(degrees: Float){
       setPostImage(PhotoModifier.rotateImage(degrees,imageFile!!))
    }


    private fun goToMain(){
        onBackPressed()
    }

    private fun showSuccessfulAddedDialog(){
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle(R.string.addpost_successful_added_title)
            setMessage(R.string.addpost_successful_added_message)
            setPositiveButton(R.string.addpost_successful_added_button, DialogInterface.OnClickListener(function = positiveButtonClick))
            setCancelable(false)
            show()
        }
    }



}
