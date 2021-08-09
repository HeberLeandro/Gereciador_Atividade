package br.edu.ifpe.tads.pdm.gerenciador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import br.edu.ifpe.tads.pdm.gerenciador.model.Queue;
import br.edu.ifpe.tads.pdm.gerenciador.model.Task;
import br.edu.ifpe.tads.pdm.gerenciador.model.User;

public class AddQueue extends AppCompatActivity {


    private EditText edName;
    private EditText edDescription;
    private Button btnCreate;
    private DatabaseReference drQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_queue);
        getSupportActionBar().hide();

        edName = findViewById(R.id.edit_queue_name);
        edDescription = findViewById(R.id.edit_queue_description);
        btnCreate = findViewById(R.id.btn_create);

        btnCreate.setOnClickListener(this::createQueue);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase fbDB = FirebaseDatabase.getInstance();
        drQueue = fbDB.getReference("Queue/"+mAuth.getCurrentUser().getUid());
    }

    private void createQueue(View view) {
        String Qname = edName.getText().toString();
        String Qdesc = edDescription.getText().toString();

        Queue queue = new Queue(Qname, Qdesc, MainActivity.user, new ArrayList<Task>());

        drQueue.push().setValue(queue);
        this.finish();
    }

}