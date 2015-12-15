package com.myittraing.android.fbsampletest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

/**
 * Created by DJ on 12/7/2015.
 */
public class Second extends MainActivity {
    private static int RESULT_LOAD_IMG = 2;
    String imgDecodableString;
    ShareDialog shareDialog;
     EditText editText;
    Button upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse);

        final EditText editText = (EditText) findViewById(R.id.etcaption);
        editText.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        }));

    }


    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        galleryIntent.putExtra("crop", "true");
        galleryIntent.putExtra("scale", true);
        galleryIntent.putExtra("scaleUpIfNeeded", true);
        galleryIntent.putExtra("max-width", 170);
        galleryIntent.putExtra("max-height", 160);
        galleryIntent.putExtra("aspectX", 1);
        galleryIntent.putExtra("aspectY",1);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//requestcode is constant passed to determine which activity we hering back from
        //result code is code the activity we called executed ok or refuse
        //intent data is any data that result return
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK    ///everything processes successfully  and we are hearing back from image gallery
                    && null != data) {
                // Get the Image from data

                Bundle extras = data.getExtras();
                //get the cropped bitmap
                final Bitmap thePic = extras.getParcelable("data");

                ImageView imgView = (ImageView) findViewById(R.id.imgView);
                imgView.setImageBitmap(thePic);
                final EditText editText = (EditText) findViewById(R.id.etcaption);
                editText.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editText.setText("");
                    }
                }));
                // Set the Image in ImageView after decoding the String
                //imgView.setImageBitmap(BitmapFactory  //BitmapFactory to convert it to the Bimap object.//get the bitmap from the stream
                //        .decodeFile(imgDecodableString));
                Button button= (Button) findViewById(R.id.btnupload1);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharePhoto photo = new SharePhoto.Builder()
                                .setBitmap(thePic)
                                .setCaption(editText.getText().toString())
                                .build();

                        SharePhotoContent content = new SharePhotoContent.Builder()
                                .addPhoto(photo)
                                .build();
                        //shareDialog.show(content);
                        ShareApi.share(content, null);
                        Toast.makeText(Second.this,"Photo uploaded successfully",Toast.LENGTH_SHORT).show();
                    }
                });


            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }


}
