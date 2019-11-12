package com.derich.travelmantics;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class FirebaseUtils {
    private static final int RC_SIGN_IN = 123;
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    private static FirebaseUtils mFirebaseUtils;
    public static FirebaseAuth mFirebaseAuth;
    private static Activity caller;
    public static FirebaseAuth.AuthStateListener mAuthListener;
    public static ArrayList<TravelDeal> mDeals;
    private FirebaseUtils(){}

    public static void openFbReference(String ref, final Activity callerActivity){
        if (mFirebaseUtils == null) {
            mFirebaseUtils = new FirebaseUtils();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseAuth = FirebaseAuth.getInstance();
            caller = callerActivity;
            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged( FirebaseAuth firebaseAuth) {
                    FirebaseUtils.signIn();
                    Toast.makeText(callerActivity.getBaseContext(), "Welcome Back!", Toast.LENGTH_LONG).show();
                }
            };
        }
        mDeals = new ArrayList<TravelDeal>();
        mDatabaseReference = mFirebaseDatabase.getReference().child(ref);

    }
    public static void signIn(){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());


// Create and launch sign-in intent
        caller.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }
    public static void attachListener(){
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }
    public static void detachListener(){
        mFirebaseAuth.removeAuthStateListener(mAuthListener);
    }
}
