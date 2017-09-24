package br.com.ciraolo.desafiogithub.retrofit;

import java.net.HttpURLConnection;

import br.com.ciraolo.desafiogithub.models.ListRepositoriesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by henriqueciraolo on 21/09/17.
 */

public class ListarRepositoriosServices {

    private ServiceFailureCallback failureCallback;

    public ListarRepositoriosServices(ServiceFailureCallback failureCallback) {
        this.failureCallback = failureCallback;
    }

    public void listarRepositorios(final ListarRepositoriosCallback successCallback) {
        Call<ListRepositoriesResponse> call = GeradorDeServicos.getAPI().listRepositories();
        call.enqueue(new Callback<ListRepositoriesResponse>() {
            @Override
            public void onResponse(Call<ListRepositoriesResponse> call, Response<ListRepositoriesResponse> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    successCallback.onListarRepositoriosSuccess(response.body());
                } else {
                    failureCallback.onServiceError();
                }
            }

            @Override
            public void onFailure(Call<ListRepositoriesResponse> call, Throwable t) {
                t.printStackTrace();
                failureCallback.onNoConnection();
            }
        });
    }


    public interface ServiceFailureCallback {
        void onServiceError();

        void onNoConnection();
    }

    public interface ListarRepositoriosCallback {
        void onListarRepositoriosSuccess(ListRepositoriesResponse body);
    }

}
