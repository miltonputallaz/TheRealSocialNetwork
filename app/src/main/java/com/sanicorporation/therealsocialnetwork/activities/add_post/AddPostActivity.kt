package com.sanicorporation.therealsocialnetwork.activities.add_post


import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.sanicorporation.therealsocialnetwork.R
import com.sanicorporation.therealsocialnetwork.activities.BaseActivity
import com.sanicorporation.therealsocialnetwork.databinding.ActivityAddPostBinding
import com.sanicorporation.therealsocialnetwork.utils.FileUtils
import com.sanicorporation.therealsocialnetwork.utils.PhotoModifier
import kotlinx.android.synthetic.main.activity_add_post.*
import kotlinx.android.synthetic.main.image_capture_dialog.view.*
import java.io.File


class AddPostActivity : BaseActivity() {

    private val READ_EXT_STORAGE_REQUEST = 2
    private lateinit var binding: ActivityAddPostBinding

    private var addPostViewModel: AddPostViewModel = AddPostViewModel()

    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_IMAGE_PICK = 2
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
        progress_bar.bringToFront()
        addPostViewModel.performAddPost(sucessFullHandler, errorHandler)
    }

    fun onClickCamera(){
        showCameraDialog()
    }

    private fun showCameraDialog() {
        lateinit var photoDialog: Dialog
        val builder = AlertDialog.Builder(this)
        var view = layoutInflater.inflate(R.layout.image_capture_dialog, null)
        view.select_image_button.setOnClickListener{
            photoDialog.dismiss()
            checkReadPermission()
        }
        view.take_photo_button.setOnClickListener{
            photoDialog.dismiss()
            takePhoto()
        }
        builder.setView(view)
        photoDialog = builder.create()
        photoDialog.show()
    }

    private fun checkReadPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showReadExternalStorageExpl()
            } else {
                // No explanation needed, we can request the permission.
                requestReadExternalPermission()

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            selectImage()
        }

    }

    private fun requestReadExternalPermission(){
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            READ_EXT_STORAGE_REQUEST)
    }

    private fun showReadExternalStorageExpl() {
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle(R.string.permission_needded_title)
            setMessage(R.string.read_external_storage_permission)
            setPositiveButton(R.string.permission_needed_ok_button, DialogInterface.OnClickListener { dialog, which ->
                requestReadExternalPermission()
            })
            setCancelable(false)
            show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            READ_EXT_STORAGE_REQUEST -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    selectImage()
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

            else -> {
                // Ignore all other requests.
            }
        }
    }


    private fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                imageFile = FileUtils.createImageFile(getExternalFilesDir(Environment.DIRECTORY_PICTURES))
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
        Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI).also {
            it.type = "image/*"
            it.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(it, REQUEST_IMAGE_PICK)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == REQUEST_IMAGE_CAPTURE){

                val completionHandler: () -> Unit = {setPostImage(BitmapFactory.decodeFile(imageFile!!.absolutePath))}

                addPostViewModel.setImagePath(imageFile!!.absolutePath)
                PhotoModifier.rotateIfItsNeccesary(imageFile!!,completionHandler)

            } else if(requestCode == REQUEST_IMAGE_PICK) {
                data?.data?.let {
                    FileUtils.getRealPath(this,it)?.let {
                        addPostViewModel.setImagePath(it)
                        setPostImage(BitmapFactory.decodeFile(it))
                    }
                }
            }
        }

    }







    private fun setPostImage(bitmap: Bitmap) {
        addPostViewModel.setImageBitmap(bitmap)
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
