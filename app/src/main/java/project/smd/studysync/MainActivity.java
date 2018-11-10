package project.smd.studysync;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseReference usersDB;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //===================
        //User Authentication
        //===================

        /*if(auth.getCurrentUser()!=null)
        {
            //AlreadyLogged in user
            Log.v("Announcement","Registred User Login");
        }
        else
        {
            //New User
        }*/


        //Firebase configuration
        database = FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword("test@test.com", "testuser")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            // there was an error
                            Log.v("Anncouncement", "User not added ");

                        } else {
                            Log.v("Anncouncement", "User not signin ");

                        }
                    }
                });

        //Users Table for storing users data
        //name
        //password
        //email
        //ArrayList of connected-classes(class)
        usersDB= database.getReference("Users");
        ArrayList <String> c= new ArrayList<String>();
        c.add("one");
        c.add("two");
        User test= new User("TestUser","123333","test email",c);
        usersDB.child("001").setValue(test);

        usersDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot coursesnapshot : dataSnapshot.getChildren()) {
                    User usr= coursesnapshot.getValue(User.class);
                    Log.v("Announcement", usr.getUsername());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }      });



        //==========
        //Signup Area
        //===========

        String email= "test@test.com" ;
        String password="testuser";
        //Email password taken as input
        /*auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.v("Anncouncement", "User added ");
                        } else {
                            Log.v("Anncouncement", "User not added ");
                        }
                    }
                }); */






    }





}
