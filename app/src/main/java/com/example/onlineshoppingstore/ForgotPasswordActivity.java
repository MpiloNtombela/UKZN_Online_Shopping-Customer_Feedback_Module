package com.example.onlineshoppingstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlineshoppingstore.feedback.repo.DBHelper;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ImageButton submit; /** submit changes **/
    private EditText userName; /** Current Username **/
    private EditText newPass; /** new password **/
    private EditText conPass;/** confirm new password **/
    /** Will need a new variable for database authentication **/
    DBHelper myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide(); /*** code allows for full screen use **/

        submit = (ImageButton) findViewById(R.id.submitButton);
        userName = findViewById(R.id.UsernameTxt);
        newPass = findViewById(R.id.NewPassTxt);
        conPass = findViewById(R.id.ConfirmPassTxt);
        myDB = new DBHelper(this);

        Intent intent = getIntent();
        userName.setText(intent.getStringExtra("userName"));

        submit.setOnClickListener(new View.OnClickListener() {
            /** code for the submit button
             to create new password for user **/
            @Override
            public void onClick(View view) {

                String uName = userName.getText().toString();
                String passNew = newPass.getText().toString();
                String passCon = conPass.getText().toString();


                Boolean validUser = myDB.checkusername(uName);
                if(validUser == true) {


                    if (passNew.equals(passCon)) {

                        Boolean checkpasswordupdate = myDB.updatepassword(uName, passNew);


                        if (checkpasswordupdate == true) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(ForgotPasswordActivity.this, "Password Updated Successfully.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "Password Not Updated Successfully.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Password did not match.", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(ForgotPasswordActivity.this, "Invalid Credentials.", Toast.LENGTH_SHORT).show();
                }




//                if(TextUtils.isEmpty(uName))
//                {
//                    Toast.makeText(ForgotPasswordActivity.this, "Please enter your registered username.", Toast.LENGTH_SHORT).show();
//                    userName.setError("Username is incorrect.");
//                    userName.requestFocus();
//                }
//                else if(!uName.equals("admin")) /** placeholder test for username field, needs to be modified to fit check database for correct usernames**/
//                {
//                    Toast.makeText(ForgotPasswordActivity.this, "Please enter a valid username.", Toast.LENGTH_SHORT).show();
//                    userName.setError("User does not exist.");
//                    userName.requestFocus();
//                }
//                else /** this piece of code is to check password requirements are met. When database is connected this will be changed such that the valid
//                 passwords entered will update in the database table. **/
//                {
//                    if(passNew.equals(passCon) && !TextUtils.isEmpty(passNew))
//                    {
//                        Toast.makeText(ForgotPasswordActivity.this, "Password successfully changed.", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(ForgotPasswordActivity.this, MainActivity.class));
//                    }
//                    else if (TextUtils.isEmpty(passNew))
//                    {
//                        Toast.makeText(ForgotPasswordActivity.this, "Please enter a new password.", Toast.LENGTH_SHORT).show();
//                        newPass.setError("Password field is empty.");
//                        newPass.requestFocus();
//                    }
//                    else if (TextUtils.isEmpty(passCon) || !passCon.equals(passNew))
//                    {
//                        Toast.makeText(ForgotPasswordActivity.this, "Please enter the same password.", Toast.LENGTH_SHORT).show();
//                        conPass.setError("Password did not match.");
//                        conPass.requestFocus();
//                    }
//
//                }

            }
        });

    }


}