package br.edu.ifpe.tads.pdm.gerenciador;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.Arrays;

import br.edu.ifpe.tads.pdm.gerenciador.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private ArrayList<String> att;
    private static final String [] arrAtt = {"fazer resumo", "fazer reconhecimento de digistos"};

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        att = new ArrayList<String>(Arrays.asList(arrAtt));
        binding = FragmentSecondBinding.inflate(inflater, container, false);

        binding.listaAtividades.setAdapter(new ArrayAdapter<String>(
                binding.getRoot().getContext(), R.layout.list_filas, R.id.fila_name, att));

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