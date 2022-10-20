package com.example.apipessoas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentResultOwner;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apipessoas.util.ImageLoadTask;
import com.example.apipessoas.util.Pessoa;
import com.example.apipessoas.util.RetrofitConfig;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etDados;
    private TextView tvGeneroSelected, tvNacionalidadeSelected;
    private Button btPesquisar, btFoto, btDados;
    private ImageView foto;
    private Spinner spGenero, spNacionalidade;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private static Fragment fDados;
    private static Fragment fFoto = FotoFragment.newInstance("","");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btPesquisar = findViewById(R.id.btPesquisar);
        foto = findViewById(R.id.imageView);
        etDados = findViewById(R.id.etDados);
        btPesquisar.setOnClickListener(e -> {
            buscarPessoa();
        });
        spGenero = findViewById(R.id.spGenero);
        spNacionalidade = findViewById(R.id.spNacionalidade);
        tvGeneroSelected = findViewById(R.id.textView2);
        tvNacionalidadeSelected = findViewById(R.id.textView3);
        btFoto = findViewById(R.id.btfragFoto);
        btDados = findViewById(R.id.btFragDados);

        btFoto.setOnClickListener(e->trocaFragment(fFoto));
        btDados.setOnClickListener(e->trocaFragment(fDados));

        fm=getSupportFragmentManager();
        ft=fm.beginTransaction();
        ft.add(R.id.frameLayout,new DadosFragment());
        ft.commit();



        final List<String> vetGenero = new ArrayList<String>();
        vetGenero.add("female");
        vetGenero.add("male");
        final List<String> vetNacionalidade = new ArrayList<String>();
        vetNacionalidade.add("au");
        vetNacionalidade.add("br");
        vetNacionalidade.add("ca");
        vetNacionalidade.add("ch");
        vetNacionalidade.add("de");
        vetNacionalidade.add("dk");
        vetNacionalidade.add("es");
        vetNacionalidade.add("fi");
        vetNacionalidade.add("fr");
        vetNacionalidade.add("gb");
        vetNacionalidade.add("ie");
        vetNacionalidade.add("in");
        vetNacionalidade.add("ir");
        vetNacionalidade.add("mx");
        vetNacionalidade.add("nl");
        vetNacionalidade.add("no");
        vetNacionalidade.add("nz");
        vetNacionalidade.add("rs");
        vetNacionalidade.add("tr");
        vetNacionalidade.add("ua");
        vetNacionalidade.add("us");


        ArrayAdapter arrayGenero = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, vetGenero);
        ArrayAdapter arrayNacionalidade = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, vetNacionalidade);
        spGenero.setAdapter(arrayGenero);
        spNacionalidade.setAdapter(arrayNacionalidade);

        spGenero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tvGeneroSelected.setText(vetGenero.get(i));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spNacionalidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tvNacionalidadeSelected.setText(vetNacionalidade.get(i));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


    private void trocaFragment(Fragment fFoto) {
        if(fDados != null) {
            ft = fm.beginTransaction();
            ft.replace(R.id.frameLayout, fFoto);
            ft.commit();
        }
    }


    private void buscarPessoa() {

        tvGeneroSelected.getText().toString();
        tvNacionalidadeSelected.getText().toString();

        if(!tvGeneroSelected.getText().toString().isEmpty() || !tvNacionalidadeSelected.getText().toString().isEmpty())
        {
            Call<Pessoa.Root> call=new RetrofitConfig().getPessoaService().buscaPessoa(tvGeneroSelected.getText().toString(), tvNacionalidadeSelected.getText().toString());
            call.enqueue(new Callback<Pessoa.Root>() {
                @Override
                public void onResponse(Call<Pessoa.Root> call, Response<Pessoa.Root> response) {
                    Pessoa.Root pessoa = response.body();
                    fFoto = FotoFragment.newInstance(pessoa.results.get(0).picture.large,"");

                    fDados = DadosFragment.newInstance("            Dados Pessoais Encontrados \n\nNome: "+pessoa.results.get(0).name.first+"\n" +
                            "Idade: "+pessoa.results.get(0).dob.age+"\nGenero: "+pessoa.results.get(0).gender
                            +"\nNacionalidade: "+pessoa.results.get(0).location.country,"");

                    trocaFragment(fDados);
                }

                @Override
                public void onFailure(Call<Pessoa.Root> call, Throwable t) {
                    etDados.setText(t.toString());
                }
            });
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(),"Dados preenchidos incorretamente!!!",Toast.LENGTH_LONG);
            toast.show();
        }


    }
}