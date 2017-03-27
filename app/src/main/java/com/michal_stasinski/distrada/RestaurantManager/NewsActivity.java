package com.michal_stasinski.distrada.RestaurantManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.michal_stasinski.distrada.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewsActivity extends AppCompatActivity {


    private ImageButton mSecetedImage;
    private EditText mPostTitle;
    private EditText mPostDesc;
    private Button mSubmiteBtn;
    private Uri mImageUri;

    //referencja do storgafe firebase
    private StorageReference mStorage;

    //referencja do bazy w firebase
    private DatabaseReference mDatabase;

    private ProgressDialog mProgress;

    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_post);

        mStorage = FirebaseStorage.getInstance().getReference();
        mPostTitle = (EditText) findViewById(R.id.postTitle);
        mPostDesc = (EditText) findViewById(R.id.postDesc);
        mSubmiteBtn = (Button) findViewById(R.id.submit_post_btn);

        mSecetedImage = (ImageButton) findViewById(R.id.imageSelect);

        mProgress = new ProgressDialog(this);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("blogs");



        mSecetedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");

                startActivityForResult(galleryIntent,GALLERY_REQUEST);
            }
        });

        mSubmiteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });
    }

    private void startPosting() {


        // pobrać text zamienic na stringa i usnunać białe znaki trim()
        final String titleVal= mPostTitle.getText().toString().trim();
        final String descVal= mPostDesc.getText().toString().trim();


        // jeśli title val nie jest pusty i descVal nie jest pusty i image nie jest nul to
        if(!TextUtils.isEmpty(titleVal) && !TextUtils.isEmpty(descVal) && mSecetedImage !=null ){

            mProgress.setMessage("Wysyłam post");
            mProgress.show();

            //StorageReference filepath = mStorage.child("Blog_image").child(mImageUri.getLastPathSegment());
            StorageReference filepath = mStorage.child("images").child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downLoadUri = taskSnapshot.getDownloadUrl();

                    DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                    String date = df.format(Calendar.getInstance().getTime());

                    DatabaseReference newPost = mDatabase.push();
                    newPost.child("title").setValue(titleVal);
                    newPost.child("news").setValue(descVal);
                    newPost.child("date").setValue(date);

                    // dodanie urla obrazka ...zamienic na stringa!!

                    newPost.child("imageUrl").setValue(downLoadUri.toString());

                    mProgress.dismiss();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode==RESULT_OK){
            mImageUri  = data.getData();

            mSecetedImage.setImageURI( mImageUri);
        }
    }

}
