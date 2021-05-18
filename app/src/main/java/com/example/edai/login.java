package com.example.edai;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity
{

    EditText emailId, password;
    Button btnlogin;
    TextView accSignup;
    FirebaseAuth mFirebaseAuth;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth= FirebaseAuth.getInstance();
        emailId = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnlogin = findViewById(R.id.btnlogin);
        accSignup = findViewById(R.id.notRegistered);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null)
                {
                    Toast.makeText(login.this, "You are logged in!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(login.this, scanqr.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(login.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pass = password.getText().toString();
                if (email.isEmpty())
                {
                    emailId.setError("Please enter your email id.");
                    emailId.requestFocus();
                }
                else if (pass.isEmpty())
                {
                    password.setError("Please enter your password.");
                    password.requestFocus();
                }
                else if (email.isEmpty() && pass.isEmpty())
                {
                    Toast.makeText(login.this,"Fields are empty!",Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && pass.isEmpty()))
                {
                    mFirebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful())
                            {
                                Toast.makeText(login.this,"Login Error, Please Login Again.",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Intent intTOHome = new Intent(login.this, scanqr.class);
                                startActivity(intTOHome);
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(login.this,"Error occurred",Toast.LENGTH_SHORT).show();
                }

            }
        });

        accSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(login.this, signup.class);
                startActivity(intSignUp);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
