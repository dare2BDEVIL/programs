package com.example.shiva.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private EditText resetEmail;
    private Button reseetBtn;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        resetEmail=(EditText)findViewById(R.id.reset_email);
        reseetBtn=(Button)findViewById(R.id.btn_reset_password);
        progressBar=(ProgressBar)findViewById(R.id.progressBarReset);
        auth=FirebaseAuth.getInstance();

        reseetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getEmail=resetEmail.getText().toString().trim();
                if(TextUtils.isEmpty(getEmail)){
                    Toast.makeText(getApplicationContext(),"Enter your Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(getEmail)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetPassword.this, "we have sent you instructions to reset your password", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(ResetPassword.this, "Failed To sent you instructions", Toast.LENGTH_SHORT).show();

                                }
                                progressBar.setVisibility(View.GONE);
                            }

                        });
                }

        });
    }
}
