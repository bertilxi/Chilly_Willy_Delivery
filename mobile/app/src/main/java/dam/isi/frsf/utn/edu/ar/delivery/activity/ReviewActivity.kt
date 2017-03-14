package dam.isi.frsf.utn.edu.ar.delivery.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import dam.isi.frsf.utn.edu.ar.delivery.R
import dam.isi.frsf.utn.edu.ar.delivery.model.Review
import dam.isi.frsf.utn.edu.ar.delivery.service.DataService
import java.io.ByteArrayOutputStream

class ReviewActivity : AppCompatActivity() {
    internal var imageViewReview: ImageView? = null
    internal var imageBitmap: Bitmap? = null
    internal var cameraAvailable: Boolean? = null
    internal var review: Review? = null
    internal var addPicButton: Button? = null
    internal val REQUEST_IMAGE_CAPTURE = 1
    internal val PERMISSION_REQUEST_CAMERA = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        imageViewReview = findViewById(R.id.ivPhoto) as ImageView
        addPicButton = findViewById(R.id.btnAddImage) as Button

        val pm = packageManager
        cameraAvailable = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CAMERA)
            }
        }

        if ((!cameraAvailable!!)) {
            imageViewReview?.visibility = View.INVISIBLE
            addPicButton?.visibility = View.INVISIBLE
            return
        }
        addPicButton?.setOnClickListener { dispatchTakePictureIntent() }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CAMERA -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size < 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    imageViewReview?.visibility = View.INVISIBLE
                    addPicButton?.visibility = View.INVISIBLE
                    return
                }
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val extras = data.extras
            imageBitmap = extras.get("data") as Bitmap
            imageViewReview?.setImageBitmap(imageBitmap)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_review_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.send_review -> {
                sendReview()
                finish()
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun sendReview() {
        review = Review()
        val etComment = findViewById(R.id.etComment) as EditText
        review!!.comment = etComment.text.toString()
        if (cameraAvailable!! && imageBitmap != null) {
            val baos = ByteArrayOutputStream()
            imageBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val byteArrayImage = baos.toByteArray()
            val encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT)
            review!!.img = encodedImage
        }
        val ratingBar = findViewById(R.id.ratingBar_review) as RatingBar
        review?.rating = ratingBar.numStars
        val dataService = DataService(applicationContext)
        try {
            dataService.addReview(review!!).setCallback { _, result -> Log.d("MIRAME", "onAddReviewCompleted: " + if (result == null) "FALLÓ" else "ÉXITO") }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        imageBitmap = savedInstanceState?.getParcelable<Bitmap>(getString(R.string.photo_key))
        imageViewReview?.setImageBitmap(imageBitmap)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("photo key", imageBitmap)
    }

}
