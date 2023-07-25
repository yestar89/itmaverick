package kr.co.company.it_maverick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword, confirmPassword;
    Button buttonReg;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.et_email);
        editTextPassword = findViewById(R.id.et_pw);
        confirmPassword = findViewById(R.id.et_repw);
        buttonReg = findViewById(R.id.btn_reg);
        progressBar = findViewById(R.id.progressBar);


        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String et_email, et_pw, et_repw;
                mAuth = FirebaseAuth.getInstance();
                et_email = editTextEmail.getText().toString();
                et_pw = editTextPassword.getText().toString();
                et_repw = confirmPassword.getText().toString();

                if (TextUtils.isEmpty(et_email)){
                    Toast.makeText(Signup.this, "이메일을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(et_pw)){
                    Toast.makeText(Signup.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(et_repw)){
                    Toast.makeText(Signup.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(et_email, et_pw)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.VISIBLE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(Signup.this, "회원가입 성공",
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Signup.this, "회원가입 실패.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }
}