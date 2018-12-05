package com.cumulations.signatureapp;

import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.kyanogen.signatureview.SignatureView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Bitmap bitmap;
    String path;
    private static final String IMAGE_DIRECTORY = "/signatureDemo";
    SignatureView signatureView;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signatureView = findViewById(R.id.signature_view);
        imageView = findViewById(R.id.imageView);
    }

    public void clear(View view) {
        signatureView.clearCanvas();
        imageView.setImageURI(null);
    }

    public void save(View view) {
        bitmap = signatureView.getSignatureBitmap();

        ContextWrapper wrapper = new ContextWrapper(getApplicationContext());
        File file = wrapper.getDir("Images", MODE_PRIVATE);
        file = new File(file, "Signature" + ".jpg");
        try {
            OutputStream stream = null;
            stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Uri savedImageURI = Uri.parse(file.getAbsolutePath());
        imageView.setImageURI(null);
        imageView.setImageURI(savedImageURI);
        Toast.makeText(wrapper, "Image saved in internal storage.\n" + savedImageURI, Toast.LENGTH_SHORT).show();
    }
}