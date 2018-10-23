package com.example.shiva.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private EditText inputEmail,inputPassword;
    private ProgressBar progressBar;
    private Button btnLogin,btnSignUp,btnResetPwd;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        inputEmail=(EditText)findViewById(R.id.sign_in_email);
        inputPassword=(EditText)findViewById(R.id.sign_in_pwd);
        btnLogin=(Button)findViewById(R.id.button_login_in);
        btnResetPwd=(Button)findViewById(R.id.btn_reset_password);
        btnSignUp=(Button)findViewById(R.id.sign_up_button);
        progressBar=(ProgressBar)findViewById(R.id.progressBarSignIn);

        auth=FirebaseAuth.getInstance();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
            }
        });

        btnResetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,ResetPassword.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String signInEmail=inputEmail.getText().toString();
                final String signInPwd=inputPassword.getText().toString();

                if(TextUtils.isEmpty(signInEmail)){
                    Toast.makeText(getApplicationContext(),"Enter email id!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(signInPwd)){
                    Toast.makeText(getApplicationContext(),"Enter Password!",Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.signInWithEmailAndPassword(signInEmail,signInPwd)
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                //IF LOGIN IS SUCCESSFUL OR BOT
                                if(!task.isSuccessful()) {
                                    //IF THERE IS AN ERROR THEN TO DISPLAY IT
                                    if (signInPwd.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(SignInActivity.this, getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
                                    }
                                } else{
                                    startActivity(new Intent(SignInActivity.this,MainActivity.class));
                                    finish();


                                }
                            }
                        });
            }
        });


    }
}
