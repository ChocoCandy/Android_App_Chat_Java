package android.app.appchat;

import android.app.appchat.activity.LoginActivity;
import android.app.appchat.activity.UpdateInfoActivity;
import android.app.appchat.model.ChatMsg;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    View view;
    TextView txtProfileName;
    FloatingActionButton btnNRoom;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ImageView imgCloseDrawer;
    CircleImageView imgProfile;
    RecyclerView recyclerView;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseRecyclerAdapter recyclerAdapter;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        myRef = database.getReference().child("Msg");
        firebaseStorage = FirebaseStorage.getInstance();

        editText = findViewById(R.id.edtRoomName);

        btnNRoom = findViewById(R.id.btnNewChat);
        btnNRoom.setOnClickListener(v -> {

            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String msg = editText.getText().toString();
            database.getReference().child("Msg").push().setValue(new ChatMsg(timeStamp, firebaseUser.getDisplayName(), msg));
            editText.setText("");
        });

        recyclerView = findViewById(R.id.rvChatRoom);

        navigationView = findViewById(R.id.navView);
        drawerLayout = findViewById(R.id.drawerLayout);
        view = navigationView.getHeaderView(0);

        txtProfileName = view.findViewById(R.id.txtProfileName);
        String currentUser = firebaseUser.getDisplayName();
        txtProfileName.setText(currentUser);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Msg")
                .limitToLast(50);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Toast.makeText(MainActivity.this, snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        };

        query.addChildEventListener(childEventListener);

        recyclerView.setAdapter(recyclerAdapter);

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.MnuLogout) {
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            return true;
        });


        imgProfile = view.findViewById(R.id.profileImg);
        try {
            storageReference = firebaseStorage.getReference().child("Profile Image").child("202412448_492580548644578_3550300539347730779_n.jpg");
            File tmp = File.createTempFile("img", "png");
            storageReference.getFile(tmp)
                    .addOnSuccessListener(taskSnapshot -> {
                        Bitmap bitmap = BitmapFactory.decodeFile(tmp.getPath());
                        imgProfile.setImageBitmap(bitmap);
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imgProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UpdateInfoActivity.class);
            startActivity(intent);
            finishAndRemoveTask();
        });

        imgCloseDrawer = view.findViewById(R.id.imgCloseDrawer);
        imgCloseDrawer.setOnClickListener(v -> drawerLayout.closeDrawers());

    }//onCreate

    @Override
    protected void onStart() {
            super.onStart();
        FirebaseApp.initializeApp(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}