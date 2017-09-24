package br.com.ciraolo.desafiogithub.retrofit;

import br.com.ciraolo.desafiogithub.Constantes;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by henriqueciraolo on 21/09/17.
 */

public class GeradorDeServicos {
    public static API getAPI() {
        return new Retrofit.Builder()
                .baseUrl(Constantes.Urls.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(API.class);
    }
}
