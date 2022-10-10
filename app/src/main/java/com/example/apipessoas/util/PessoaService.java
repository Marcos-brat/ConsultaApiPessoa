package com.example.apipessoas.util;

import androidx.core.view.accessibility.AccessibilityEventCompat;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface PessoaService {

    @GET("/api/")
    Call<Pessoa.Root> buscaPessoa(@Query("gender") String gender, @Query("nat") String nacionalidade);
}
