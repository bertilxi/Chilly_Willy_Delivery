package dam.isi.frsf.utn.edu.ar.delivery.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.koushikdutta.async.future.FutureCallback;

import java.io.ByteArrayOutputStream;

import dam.isi.frsf.utn.edu.ar.delivery.R;
import dam.isi.frsf.utn.edu.ar.delivery.model.Review;
import dam.isi.frsf.utn.edu.ar.delivery.service.DataService;

public class ReviewActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imageViewReview;
    Bitmap imageBitmap;
    Boolean cameraAvailable;
    Review review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageViewReview = (ImageView) findViewById(R.id.ivPhoto);
        Button addPicButton = (Button) findViewById(R.id.btnAddImage);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar_review);

        PackageManager pm = ReviewActivity.this.getPackageManager();
        cameraAvailable = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
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
        EditText etComment = (EditText) findViewById(R.id.etComment);
        review.setComment(etComment.getText().toString());
        if(cameraAvailable && imageBitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] byteArrayImage = baos.toByteArray();
            String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
            review.setImg(encodedImage);
        }
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar_review);
        review.setRating(ratingBar.getNumStars());
        DataService dataService = new DataService(this);
        try {
            dataService.addReview(review).setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    Log.d("MIRAME", "onAddReviewCompleted: "+ (result == null ? "FALLÓ" : "ÉXITO"));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
