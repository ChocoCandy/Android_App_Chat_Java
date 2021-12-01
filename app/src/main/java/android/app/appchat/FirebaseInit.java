package android.app.appchat;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class FirebaseInit extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
