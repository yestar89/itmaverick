package kr.co.company.it_maverick.Sign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;



import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

import kr.co.company.it_maverick.MainActivity;
import kr.co.company.it_maverick.R;

public class Signup extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +
                    "(?=.*[a-zA-Z])" +
                    "(?=.*[!@#$%^&]{1,})" +
                    "(?=.*\\S+$)" +
                    ".{8}" +
                    "$");
    TextInputEditText editTextEmail, editTextPassword, confirmPassword;

    Button buttonReg;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    private TextInputLayout etEmailLayout;
    private TextInputLayout etPwLayout;
    private TextInputLayout etRepwLayout;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
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
//        editTextEmail = findViewById(R.id.et_email);
//        editTextPassword = findViewById(R.id.et_pw);
//        confirmPassword = findViewById(R.id.et_repw);
        buttonReg = findViewById(R.id.btn_reg);
        progressBar = findViewById(R.id.progressBar);
        etEmailLayout = findViewById(R.id.et_email_lay);
        etPwLayout = findViewById(R.id.et_pw_lay);
        etRepwLayout = findViewById(R.id.et_repw_lay);

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String et_email, et_pw, et_repw;
                et_email = etEmailLayout.getEditText().getText().toString().trim();
                et_pw = etPwLayout.getEditText().getText().toString().trim();
                et_repw = etRepwLayout.getEditText().getText().toString().trim();

                if (!validateEmail()) {
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                // 비밀번호 유효성 검사
                if (!validatePassword()) {
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                // 비밀번호 재검사
                if (!validateRepassword()) {
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                // 비밀번호 재입력 검사
                if (!et_pw.equals(et_repw)) {
                    progressBar.setVisibility(View.GONE);
                    etRepwLayout.setError("비밀번호가 일치하지 않습니다.");
                    return;
                }

                // 파이어베이스 회원가입 처리
                mAuth.createUserWithEmailAndPassword(et_email, et_pw)
                        .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // 회원가입 성공
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(Signup.this, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show();

                                    // 회원가입 성공 시 메인 액티비티로 이동
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // 회원가입 실패
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(Signup.this, "회원가입에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private boolean validateEmail() {
        String emailInput = etEmailLayout.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            etEmailLayout.setError("이메일을 입력하세요.");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            etEmailLayout.setError("올바른 이메일 형식이 아닙니다.");
            return false;
        } else {
            etEmailLayout.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = etPwLayout.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            etPwLayout.setError("비밀번호를 입력하세요.");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            etPwLayout.setError("비밀번호는 영문, 숫자, 특수문자를 포함한 8자리 이상");
            return false;
        } else {
            etPwLayout.setError(null);
            return true;
        }
    }

    private boolean validateRepassword() {
        String repasswordInput = etRepwLayout.getEditText().getText().toString().trim();
        String passwordInput = etPwLayout.getEditText().getText().toString().trim();

        if (repasswordInput.isEmpty()) {
            etRepwLayout.setError("비밀번호를 다시 입력하세요.");
            return false;
        } else if (!repasswordInput.equals(passwordInput)) {
            etRepwLayout.setError("비밀번호가 일치하지 않습니다.");
            return false;
        } else {
            etRepwLayout.setError(null);
            return true;
        }
    }
}
