package br.com.ciraolo.desafiogithub.retrofit;

import br.com.ciraolo.desafiogithub.models.ListRepositoriesResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by henriqueciraolo on 21/09/17.
 */

public interface API {
    @GET("repositories?q=language:Java&sort=stars&page=1")
    Call<ListRepositoriesResponse> listRepositories();
}
