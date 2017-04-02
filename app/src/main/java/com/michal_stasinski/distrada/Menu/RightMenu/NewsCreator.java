package com.michal_stasinski.distrada.Menu.RightMenu;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.michal_stasinski.distrada.Menu.BaseMenu;
import com.michal_stasinski.distrada.R;

import org.json.JSONArray;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewsCreator extends BaseMenu {


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

        setContentView(R.layout.base_menu);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.right_news_creator);
        View inflated = stub.inflate();

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
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
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
        final String titleVal = mPostTitle.getText().toString().trim();
        final String descVal = mPostDesc.getText().toString().trim();

        Log.i("TAG", "GGGGG______________________" + mImageUri);
        // jeśli title val nie jest pusty i descVal nie jest pusty i image nie jest nul to
        if (!TextUtils.isEmpty(titleVal) && !TextUtils.isEmpty(descVal) && mImageUri != null) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NewsCreator.this);
            alertDialogBuilder.setTitle("UWAGA");
            alertDialogBuilder
                    .setMessage("Na pewno chcesz wysłać wiadomość?")
                    .setCancelable(false)
                    .setPositiveButton("TAK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
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
                    })
                    .setNegativeButton("NIE", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NewsCreator.this);
            alertDialogBuilder.setTitle("UWAGA");
            alertDialogBuilder
                    .setMessage("Nie wypełniłeś wszystkich wymaganych pół lub nie dodałeś zdjęcia")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, close
                            // current activity

                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
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

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            mImageUri = data.getData();

            mSecetedImage.setImageURI(mImageUri);
        }
    }

}
