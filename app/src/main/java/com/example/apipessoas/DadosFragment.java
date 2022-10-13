package com.example.apipessoas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DadosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DadosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    EditText etDados;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DadosFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DadosFragment newInstance(String param1, String param2) {
        DadosFragment fragment = new DadosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_dados, container, false);
        etDados = view.findViewById(R.id.etDados);
        etDados.setText(mParam1);

        return view;
    }
}