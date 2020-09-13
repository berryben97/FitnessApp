package com.example.ben.fitnessapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class AccountActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    Button btnLogin;
    TextView textToSignUp;
    ProgressBar progressBar;

    /*FirebaseDatabase database;
    DatabaseReference users;*/

    FirebaseAuth mAuth;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent1 = new Intent(AccountActivity.this,MainMenuActivity.class);
                    startActivity(intent1);
                    return true;
                case R.id.navigation_planner:
                    Intent intent2 = new Intent(AccountActivity.this,PlannerActivity.class);
                    startActivity(intent2);
                    return true;
                case R.id.navigation_account:
                    //mTextMessage.setText("Account");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        textToSignUp = (TextView) findViewById(R.id.textToSignup);

        textToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this,SignUpActivity.class));
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        //get firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        /*database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");*/

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        progressBar =(ProgressBar) findViewById(R.id.progressbar);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*signIn(edtUsername.getText().toString(),
                        edtPassword.getText().toString());*/
                String email = edtEmail.getText().toString();
                final String password =edtPassword.getText().toString();

                if (TextUtils.isEmpty(email)){
                    edtEmail.setError("Please enter email!");
                    edtEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    edtPassword.setError("Please enter password!");
                    edtPassword.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(AccountActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()){
                                    if (password.length() < 6){
                                        edtPassword.setError("Password is minimum 6 characters");
                                        edtPassword.requestFocus();
                                    }
                                    else {
                                        Toast.makeText(AccountActivity.this, "Email or Password is not recognized!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    finish();
                                    Intent intent = new Intent(AccountActivity.this, ProfileActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() !=null){
            finish();
            startActivity(new Intent(this,ProfileActivity.class));
        }
    }

    //old method of login in with firebase realtime database
/*    private void signIn(final String username, final String password) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(username).exists()) {
                    if (!username.isEmpty()) {
                        User login = dataSnapshot.child(username).getValue(User.class);
                        if (login.getPassword().equals(password)) {
                            Toast.makeText(AccountActivity.this, "Successfully Logged In.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AccountActivity.this, "Username or Password is wrong.", Toast.LENGTH_SHORT).show();
                        }
                    }else
                        Toast.makeText(AccountActivity.this, "Username is empty.", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(AccountActivity.this, "Username does not exists.", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/
}
