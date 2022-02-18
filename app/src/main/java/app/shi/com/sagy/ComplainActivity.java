package app.shi.com.sagy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.mvc.imagepicker.ImagePicker;

public class ComplainActivity extends AppCompatActivity {
    ImageView  imageView;
    ImageButton camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain2);
        imageView = findViewById(R.id.imageview);
        camera = findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.pickImage(ComplainActivity.this, "Select your image:");
            }
        });
    }

    public void submitclick(View view) {



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        // TODO do something with the bitmap
        imageView.setImageBitmap(bitmap);
    }

    public void cameraclick(View view) {

    }
}
