package com.example.apipessoas;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etGenero, etNacionalidade, etDados;
    private Button btPesquisar;
    private ImageView foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etGenero = findViewById(R.id.etGenero);
        etNacionalidade = findViewById(R.id.etNacionalidade);
        btPesquisar = findViewById(R.id.btPesquisar);
        foto = findViewById(R.id.imageView);
        etDados = findViewById(R.id.etDados);
        btPesquisar.setOnClickListener(e->{buscarPessoa();});

    }

    private void buscarPessoa() {

        etGenero.getText().toString().toLowerCase(Locale.ROOT);
        etNacionalidade.getText().toString().toLowerCase(Locale.ROOT);

        if(!etGenero.getText().toString().isEmpty() || !etNacionalidade.getText().toString().isEmpty())
        {
            Call<Pessoa.Root> call=new RetrofitConfig().getPessoaService().buscaPessoa(etGenero.getText().toString(), etNacionalidade.getText().toString());
            call.enqueue(new Callback<Pessoa.Root>() {
                @Override
                public void onResponse(Call<Pessoa.Root> call, Response<Pessoa.Root> response) {
                    Pessoa.Root pessoa = response.body();

                    new ImageLoadTask(pessoa.results.get(0).picture.medium, foto).execute();

                    etDados.setText("Nome: "+pessoa.results.get(0).name.first+"\n" +
                            "Idade: "+pessoa.results.get(0).dob.age+"\nGenero: "+pessoa.results.get(0).gender
                            +"\nNacionalidade: "+pessoa.results.get(0).location.country);
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