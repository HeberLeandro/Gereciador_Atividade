package br.edu.ifpe.tads.pdm.gerenciador;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import br.edu.ifpe.tads.pdm.gerenciador.databinding.ActivityMainBinding;
import br.edu.ifpe.tads.pdm.gerenciador.model.Queue;
import br.edu.ifpe.tads.pdm.gerenciador.model.User;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseAuthListener authListener;
    private DatabaseReference drUser;
    public static User user;
    private DatabaseReference drQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        this.mAuth = FirebaseAuth.getInstance();
        this.authListener = new FirebaseAuthListener(this);

        FirebaseDatabase fbDB = FirebaseDatabase.getInstance();

        FirebaseUser fbUser = mAuth.getCurrentUser();
        drUser = fbDB.getReference("users/" + fbUser.getUid());


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        drUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User tempUser = dataSnapshot.getValue(User.class);
                if (tempUser != null) {
                    MainActivity.this.user = tempUser;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavHostFragment navHostFragment =
                        (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                NavController navController = navHostFragment.getNavController();

                //Call activity to create a Queue
                if (navController.getCurrentDestination().getId() == R.id.FirstFragment) {
                    Intent intent = new Intent(MainActivity.this, AddQueue.class);
                    startActivity(intent);
                }
                //Call activity to create a Task (if is already inside in a queue) //TODO
                else if (navController.getCurrentDestination().getId() == R.id.SecondFragment) {
                    Snackbar.make(view, "Second Fragment", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    Snackbar.make(view,"Fail", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sign_out) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                mAuth.signOut();
            } else {
                Toast.makeText(MainActivity.this, "Erro!", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
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