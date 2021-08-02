package br.edu.ifpe.tads.pdm.gerenciador;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

import br.edu.ifpe.tads.pdm.gerenciador.databinding.FragmentFirstBinding;
import br.edu.ifpe.tads.pdm.gerenciador.model.Queue;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ArrayList<String> queues;
    private Queue queue;
    private static final String [] arrQueue = {"TCC","Att"};
    private DatabaseReference drQueue;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        queues = new ArrayList<String>(Arrays.asList(arrQueue));
        binding = FragmentFirstBinding.inflate(inflater, container, false);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase fbDB = FirebaseDatabase.getInstance();
        drQueue = fbDB.getReference("Queue/"+mAuth.getCurrentUser().getUid());

        binding.listaFilas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

//        updateQueue(binding.listaFilas);

        binding.listaFilas.setAdapter(new ArrayAdapter<String>(
                binding.getRoot().getContext(), R.layout.list_filas, R.id.fila_name, queues));

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void updateQueue(ListView list){
        drQueue.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                queue = dataSnapshot.getValue(Queue.class);
                queues.add(queue.getName());
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}