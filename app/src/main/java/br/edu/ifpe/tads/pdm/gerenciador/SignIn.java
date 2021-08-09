package br.edu.ifpe.tads.pdm.gerenciador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    private EditText edEmail;
    private EditText edPassword;
    private FirebaseAuthListener authListener;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();

        edEmail = findViewById(R.id.edit_email);
        edPassword = findViewById(R.id.edit_password);
        Button btnSignup = findViewById(R.id.btn_signup);
        Button btnSignIn = findViewById(R.id.btn_signin);

        btnSignIn.setOnClickListener(this::buttonSignInClick);
        btnSignup.setOnClickListener(this::buttonSignUpClick);

        this.mAuth = FirebaseAuth.getInstance();
        this.authListener = new FirebaseAuthListener(this);
    }

    public void buttonSignUpClick(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void buttonSignInClick(View view) {
        String login = edEmail.getText().toString();
        String passwd = edPassword.getText().toString();
        if (login.isEmpty() || passwd.isEmpty()) return;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(login, passwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            singIn();
                        }else {
                            Toast.makeText(SignIn.this, "SIGN IN ERROR!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void singIn() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authListener);
    }
}