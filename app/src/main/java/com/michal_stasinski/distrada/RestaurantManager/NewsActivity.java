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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.michal_stasinski.distrada.Menu.MenuActivity.BaseMenu;
import com.michal_stasinski.distrada.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewsActivity extends BaseMenu{


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
        currentActivity = 0;
        choicetActivity = 0;
        mStorage = FirebaseStorage.getInstance().getReference();
        mPostTitle = (EditText) findViewById(R.id.postTitle);
        mPostDesc = (EditText) findViewById(R.id.postDesc);
        mSubmiteBtn = (Button) findViewById(R.id.submit_post_btn);

        mSecetedImage = (ImageButton) findViewById(R.id.imageSelect);

        mProgress = new ProgressDialog(this);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("blogs");

        RelativeLayout background = (RelativeLayout) findViewById(R.id.main_frame_pizza);
        background.setBackgroundResource(R.mipmap.piec_view);


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
                    DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
                    String date = df.format(Calendar.getInstance().getTime());
                    String date1 = df1.format(Calendar.getInstance().getTime());


                    DatabaseReference newPost = mDatabase.push();
                    newPost.child("title").setValue(titleVal.toString());
                    newPost.child("news").setValue(descVal.toString());
                    newPost.child("date").setValue(date.toString());
                    newPost.child("rank").setValue(date1.toString());
                    // dodanie urla obrazka ...zamienic na stringa!!

                    newPost.child("imageUrl").setValue(downLoadUri.toString());

                    mProgress.dismiss();
                }
            });
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        TextView toolBarTitle = (TextView) findViewById(R.id.toolBarTitle);
        toolBarTitle.setText("WYŚLIJ WIADOMOŚĆ");
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
