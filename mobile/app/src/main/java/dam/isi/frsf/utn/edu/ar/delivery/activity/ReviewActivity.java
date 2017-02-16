package dam.isi.frsf.utn.edu.ar.delivery.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.koushikdutta.async.future.FutureCallback;

import java.io.ByteArrayOutputStream;

import dam.isi.frsf.utn.edu.ar.delivery.R;
import dam.isi.frsf.utn.edu.ar.delivery.model.Review;
import dam.isi.frsf.utn.edu.ar.delivery.service.DataService;

public class ReviewActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PERMISSION_REQUEST_CAMERA = 1;
    ImageView imageViewReview;
    Bitmap imageBitmap;
    Boolean cameraAvailable;
    Review review;
    Button addPicButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageViewReview = (ImageView) findViewById(R.id.ivPhoto);
        addPicButton = (Button) findViewById(R.id.btnAddImage);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar_review);

        PackageManager pm = ReviewActivity.this.getPackageManager();
        cameraAvailable = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            }
        }

        if (!cameraAvailable) {
            imageViewReview.setVisibility(View.INVISIBLE);
            addPicButton.setVisibility(View.INVISIBLE);
            return;
        }
        addPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length < 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    imageViewReview.setVisibility(View.INVISIBLE);
                    addPicButton.setVisibility(View.INVISIBLE);
                    return;

                }
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageViewReview.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_review_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send_review:
                sendReview();
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sendReview() {
        review = new Review();
        EditText etComment = (EditText) findViewById(R.id.etComment);
        review.setComment(etComment.getText().toString());
        if (cameraAvailable && imageBitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] byteArrayImage = baos.toByteArray();
            String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
            review.setImg(encodedImage);
        }
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar_review);
        review.setRating(ratingBar.getNumStars());
        DataService dataService = new DataService(getApplicationContext());
        try {
            dataService.addReview(review).setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    Log.d("MIRAME", "onAddReviewCompleted: " + (result == null ? "FALLÓ" : "ÉXITO"));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.photo_key))) {
            imageBitmap = savedInstanceState.getParcelable(getString(R.string.photo_key));
            imageViewReview.setImageBitmap(imageBitmap);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("photo key", imageBitmap);
    }
}
