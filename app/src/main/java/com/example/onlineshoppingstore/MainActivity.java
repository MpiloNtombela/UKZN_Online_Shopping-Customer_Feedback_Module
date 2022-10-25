package com.example.onlineshoppingstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlineshoppingstore.feedback.model.User;
import com.example.onlineshoppingstore.feedback.repo.DBHelper;
import com.example.onlineshoppingstore.feedback.repo.SessionManager;

public class MainActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    private ImageButton login, forgotPassword, signup;
    private EditText Username, Password; //changed User to Username
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = SessionManager.getInstance(this.getApplication());
        getSupportActionBar().hide(); /*** code allows for full screen use **/

        if (sessionManager.isLoggedIn()) {
            // Intent intent = new Intent(MainActivity.this, HomeActivity.class); //

            // because there is no home or profile activity, we will use order complete activity instead
            Intent intent = new Intent(MainActivity.this, OrderCompleteActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }

        /***
         * Code still to be written
         * checks to verify that the user exists and that the details correlate
         * ***/
        login = (ImageButton) findViewById(R.id.LoginButton);
        db = new DBHelper(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /***
                 * Functions to check user exists (database needs to be connected)
                 * Moves to next Activity (to be created by next Team)
                 * ***/
                checkUserDetails();

            }
        });

        /***
         * Opens new ForgotPassword Activity
         * Activity allows users to reset their password
         * ***/
        forgotPassword = (ImageButton) findViewById(R.id.forgotPasswordButton);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openForgotPassword();

            }
        });

        /***
         * Opens new Signup Activity
         * Activity allows users to sign up for the app
         * ***/
        signup = (ImageButton) findViewById(R.id.signUpButton);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignupActivity();

            }
        });
    }

    public void openForgotPassword(){
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    public void openSignupActivity(){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void checkUserDetails() {

        Username = (EditText) findViewById(R.id.USERNAME);
        String username = Username.getText().toString(); //changed User to Username

        Password = (EditText) findViewById(R.id.PASSWORD);
        String password = Password.getText().toString();

        if(username.equals("")|| password.equals("")){
            Toast.makeText(this, "Fill all the fields.", Toast.LENGTH_SHORT).show();
        }
        else {

            User user = db.getUser(username, password);
            if (user.exists()) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                sessionManager.createLoginSession(user.getId(), user.getUsername());
                Intent intent = new Intent(this, OrderCompleteActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        }






//        /***
//         * Code to be written for retrieving values from database
//         * To be done by Database team
//         * example provided at bottom of page
//         * ***/
//
//        if (user.equals("admin") && password.equals("admin")) {
//            /***
//             * further code needs to be written to check whether text fields match
//             * database entries before login button can properly execute
//             * code provided is just a placeholder to show functionality
//             * ***/
//            executeLogin();
//            /***
//             * see executeLogin() function to move to new Activity (needs to be created)
//             * ***/
//
//        } else {
//            Toast.makeText(this, "Invalid Username or Password! \nTry Again", Toast.LENGTH_SHORT).show();
//
//        }
    }

    public void executeLogin() {
        /***
         * NewActivity is a placeholder Activity to be created by next interface team
         * commented out to prevent breaking the code

        Intent intent = new Intent(MainActivity.this, NewActivity.class);
            startActivity(intent);
        ***/
        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
    }


    /***
     * Example of code to access entries in database
     *
     * DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users/");
     *     ref.addListenerForSingleValueEvent(new ValueEventListener() {
     *         @Override
     *         public void onDataChange(@NonNull DataSnapshot snapshot) {
     *             for (DataSnapshot ds : snapshot.getChildren()) {
     *
     *                 users_from_database = (String) ds.child("username").getValue();
     *
     *                 username_list.add(users_from_database);
     *                 StringBuilder stringBuilder = new StringBuilder();
     *                 for (String s : username_list) {
     *                     stringBuilder.append(s + "\n");
     *                 }
     *                 Log.d("ZI", stringBuilder.toString());
     *
     *             }
     *
     *         }
     *
     *        }
     * ***/
}