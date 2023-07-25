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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signin extends AppCompatActivity {
    TextView textView;
    TextInputEditText editTextEmail, editTextPassword, confirmPassword;
    Button buttonLogin;
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
        setContentView(R.layout.activity_signin);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.sign_id);
        editTextPassword = findViewById(R.id.sign_pass);
        buttonLogin = findViewById(R.id.btn_sign);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.nowSignup);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String sign_id, sign_pass;
                mAuth = FirebaseAuth.getInstance();
                sign_id = editTextEmail.getText().toString();
                sign_pass = editTextPassword.getText().toString();

                if (TextUtils.isEmpty(sign_id)){
                    Toast.makeText(Signin.this, "이메일을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(sign_pass)){
                    Toast.makeText(Signin.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(sign_id, sign_pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.VISIBLE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Signin.this, "로그인 실패",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}