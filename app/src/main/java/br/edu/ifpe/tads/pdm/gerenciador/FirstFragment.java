package br.edu.ifpe.tads.pdm.gerenciador;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import br.edu.ifpe.tads.pdm.gerenciador.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ArrayList<String> filas;
    private static final String [] arrFilas = {"TCC", "Att"};

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        filas = new ArrayList<String>(Arrays.asList(arrFilas));
        binding = FragmentFirstBinding.inflate(inflater, container, false);

        binding.listaFilas.setAdapter(new ArrayAdapter<String>(
                binding.getRoot().getContext(), R.layout.list_filas, R.id.fila_name, filas));

        binding.listaFilas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}