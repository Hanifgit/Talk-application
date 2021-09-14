package com.example.chatspatial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class RegisterActivity extends AppCompatActivity {
    private FirebaseUser currentUser;
    private Button RegisterButton;
    private EditText userEmail,UserPassword;
    private TextView AlreadyHaveAccount;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        InitializeFields();

        AlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendUserToLoginActivity();
            }
        });
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });
    }

    private void CreateNewAccount() {
        String resEmail = userEmail.getText().toString();
        String resPassword = UserPassword.getText().toString();
        if(TextUtils.isEmpty(resEmail)){
            userEmail.setError("Email is required");
            Toast.makeText(this, "Please enter email..", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(resPassword)){
            UserPassword.setError("Password is required");
            Toast.makeText(this, "Please enter password..", Toast.LENGTH_SHORT).show();
        }else{
            loadingBar.setTitle("Creating a new account");
            loadingBar.setMessage("Please Wait,while we are creating new account for you..");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(resEmail,resPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                sendUserToLoginActivity();
                                Toast.makeText(RegisterActivity.this,"Account Create Successfully..",Toast.LENGTH_LONG);
                                loadingBar.dismiss();
                            }else{
                                Toast.makeText(RegisterActivity.this,"Account Create Unsuccessfully..",Toast.LENGTH_LONG);
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }

    private void InitializeFields() {
        RegisterButton = findViewById(R.id.register_button);
        userEmail = findViewById(R.id.register_email);
        UserPassword = findViewById(R.id.register_password);
        AlreadyHaveAccount = findViewById(R.id.already_have_account);
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);
    }

    private void sendUserToLoginActivity() {
        Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(loginIntent);
    }
}