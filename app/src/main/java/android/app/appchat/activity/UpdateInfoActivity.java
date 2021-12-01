package android.app.appchat.activity;

import android.app.appchat.MainActivity;
import android.app.appchat.R;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateInfoActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user;

    TextView txtEmail, txtUri;
    EditText edtName;
    CircleImageView imgProfile;
    ImageView imgChange;

    Button btnSave, btnCancel;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        edtName = findViewById(R.id.edtChangeDisplayName);
        imgChange = findViewById(R.id.imgChooseImg);
        imgProfile = findViewById(R.id.imgProfile);

        txtUri = findViewById(R.id.txtUri);
        txtEmail = findViewById(R.id.txtMail);


        txtEmail.setText(user.getEmail());
        edtName.setText(user.getDisplayName());

        btnCancel = findViewById(R.id.btnCancelChange);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateInfoActivity.this, MainActivity.class);
                startActivity(intent);
                finishAndRemoveTask();
            }
        });




        btnSave = findViewById(R.id.btnSaveChange);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edtName.getText().toString();

                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .setPhotoUri(uri)
                            .build();

                    user.updateProfile(profileChangeRequest)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(UpdateInfoActivity.this, uri.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

               Intent intent = new Intent(UpdateInfoActivity.this, MainActivity.class);
               startActivity(intent);
               finishAndRemoveTask();

            }
        });//btnsave

        imgChange.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            Intent i = Intent.createChooser(intent, "File");
            startActivityForResult(i, 1000);
//                Uri uri = intent.getData();
//                String path = uri.getPath();
//                txtUri.setText(path);
//
//                imgProfile.setImageBitmap(BitmapFactory.decodeFile(path));
//                Toast.makeText(UpdateInfoActivity.this, path, Toast.LENGTH_SHORT).show();
        });
    }//onCreate




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uri = data.getData();
        try {
            InputStream inputStream = null;
            inputStream = getContentResolver().openInputStream(data.getData());
            Bitmap image = BitmapFactory.decodeFile(String.valueOf(inputStream));
            imgProfile.setImageURI(uri);
            txtUri.setText(data.getData().toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}