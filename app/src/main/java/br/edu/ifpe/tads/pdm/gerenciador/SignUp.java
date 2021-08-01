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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.edu.ifpe.tads.pdm.gerenciador.model.User;

public class SignUp extends AppCompatActivity {

    private EditText edName;
    private EditText edEmail;
    private EditText edPassword;
    private Button btnSignup;

    private FirebaseAuthListener authListener;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edName = findViewById(R.id.edit_name);
        edEmail = findViewById(R.id.edit_email);
        edPassword = findViewById(R.id.edit_password);
        btnSignup = findViewById(R.id.btn_signup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSignUpClick(v);
            }
        });

        this.mAuth = FirebaseAuth.getInstance();
        this.authListener = new FirebaseAuthListener(this);

    }

    public void buttonSignUpClick(View view) {
        final String name = edName.getText().toString();
        final String email = edEmail.getText().toString();
        final String password = edPassword.getText().toString();

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String msg = task.isSuccessful() ? "SIGN UP OK!":
                                "SIGN UP ERROR!";
                        Toast.makeText(SignUp.this, msg,
                                Toast.LENGTH_SHORT).show();

                        if (task.isSuccessful()) {
                            User tempUser = new User(name, email);
                            DatabaseReference drUsers = FirebaseDatabase.
                                    getInstance().getReference("users");
                            drUsers.child(mAuth.getCurrentUser().getUid()).
                                    setValue(tempUser);
                        }

                    }
                });
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