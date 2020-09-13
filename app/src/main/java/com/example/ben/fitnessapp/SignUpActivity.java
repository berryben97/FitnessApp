package com.example.ben.fitnessapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ben.fitnessapp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText edtEmail,edtFullname, edtPassword, edtHeight, edtWeight;
    Button btnSignUp;
    TextView textToLogin;
    ProgressBar progressBar;

/*    FirebaseDatabase database;
    DatabaseReference users;*/

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

/*        database = FirebaseDatabase.getInstance(); //This is method was registering users with normal realtime database
        users = database.getReference("Users");*/

        mAuth = FirebaseAuth.getInstance();

        float heightValue;
        float weightValue;

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtFullname = (EditText) findViewById(R.id.edtFullname);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtHeight = (EditText) findViewById(R.id.edtHeight);
        edtWeight = (EditText) findViewById(R.id.edtWeight);
        textToLogin = (TextView) findViewById(R.id.textToLogin);
        progressBar =(ProgressBar) findViewById(R.id.progressbar);

        edtEmail.requestFocus();

        textToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, AccountActivity.class));
            }
        });

        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String heightStr = edtHeight.getText().toString();
                final String weightStr = edtWeight.getText().toString();
                final String email = edtEmail.getText().toString().trim();
                final String password = edtPassword.getText().toString().trim();
                final String fullname = edtFullname.getText().toString();

                if (TextUtils.isEmpty(email)){
                    edtEmail.setError("Please enter email!");
                    edtEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(fullname)){
                    edtFullname.setError("Please enter your full name!");
                    edtFullname.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    edtPassword.setError("Please enter password!");
                    edtPassword.requestFocus();
                    return;
                }
                if (password.length() < 6){
                    edtPassword.setError("Password is too short, enter minimum 6 characters!");
                    edtPassword.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(heightStr)){
                    edtHeight.setError("Please enter height!");
                    edtHeight.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(weightStr)){
                    edtWeight.setError("Please enter weight!");
                    edtWeight.requestFocus();
                    return;
                }
                /*User Obj= new User();*/

                //bmi calculation
                    float heightValue = Float.parseFloat(heightStr) / 100;
                    float weightValue = Float.parseFloat(weightStr);
                    float bmi = weightValue / (heightValue * heightValue);
                    final String currentBMI = String.format("%.2f",bmi);
                    /*Obj.setBMI(bmi):*/
                    final String calorie = "";

                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    User user = new User (
                                        fullname,
                                        email,
                                        currentBMI,
                                            calorie
                                    );

                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(SignUpActivity.this, "Successfully registered.", Toast.LENGTH_LONG).show();
                                            }
                                            else{
                                                Toast.makeText(SignUpActivity.this, "Failed to register.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                                else{
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                        Toast.makeText(SignUpActivity.this, "User already registered!", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                    /*final User user = new User(edtUsername.getText().toString(),
                            edtPassword.getText().toString(),
                            Obj.getBMI());*/

/*                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(user.getUsername()).exists())
                                Toast.makeText(SignUpActivity.this, "The Username is already existed", Toast.LENGTH_SHORT).show();
                            else {
                                users.child(user.getUsername()).setValue(user);
                                Toast.makeText(SignUpActivity.this, "Successfully Register.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });*/

            }
        });
    }
}