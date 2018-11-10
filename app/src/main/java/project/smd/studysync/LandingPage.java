package project.smd.studysync;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class LandingPage extends AppCompatActivity {

    //--- UI ---
    private Toolbar toolbar;
    EditText EmailInput;
    EditText PasswordInput;
    TextView signupPage;
    Button Signin;

    //--- DB ---
    DatabaseReference usersDB;
    FirebaseDatabase database;
    FirebaseAuth auth;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        // --- Firebase ---
        database = FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        //--- UI Initializations ---
        EmailInput = (EditText) findViewById(R.id.email);
        PasswordInput = (EditText) findViewById(R.id.editText_password);
        Signin = (Button) findViewById(R.id.signinButton);
        signupPage= (TextView)findViewById(R.id.gotosignup) ;


        //--- Toolbar ---
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("StudySync");



        //--- If the user is signed in or not already -----

        if(auth.getCurrentUser()!=null) {
            // --- Signed in User ---
            Intent intent = new Intent(LandingPage.this, AboutPage.class);
            startActivity(intent);
            Log.v("Announcement","Registred User Login");
        }
        else {
            // --- Non signed in user ---
            Signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (EmailInput.getText().toString().equals("") || PasswordInput.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Fill the Information!", Toast.LENGTH_SHORT).show();
                    }

                    if(!PasswordInput.getText().toString().equals("") && !EmailInput.getText().toString().equals(""))
                    {
                        String email = EmailInput.getText().toString();
                        final String password = PasswordInput.getText().toString();

                        Log.v("Announcement",email);
                        Log.v("Announcement",password);

                        auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(LandingPage.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {


                                        //------ If user is Registered or Not -------

                                        if (!task.isSuccessful()) {
                                            // there was an error
                                            Log.v("Anncouncement", "Registered User Found");
                                            Intent intent = new Intent(LandingPage.this, AboutPage.class);
                                            startActivity(intent);
                                        } else {
                                            Log.v("Anncouncement", "User not Registered  ");
                                            Toast.makeText(getApplicationContext(), "Invalid User -  Please Sign Up", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                    }
                }
            });

        }


        signupPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPage.this, SignUp.class);
                startActivity(intent);
            }
        });



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

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu_landing_page, menu);
        return true;
    }
}
