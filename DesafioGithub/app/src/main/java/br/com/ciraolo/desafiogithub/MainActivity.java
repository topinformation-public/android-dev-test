package br.com.ciraolo.desafiogithub;

import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import br.com.ciraolo.desafiogithub.adapter.RepositoriosRecyclerViewAdapter;
import br.com.ciraolo.desafiogithub.models.Item;
import br.com.ciraolo.desafiogithub.models.ListRepositoriesResponse;
import br.com.ciraolo.desafiogithub.retrofit.ListarRepositoriosServices;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ListarRepositoriosServices.ListarRepositoriosCallback, ListarRepositoriosServices.ServiceFailureCallback, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.srlAtualizar)
    SwipeRefreshLayout srlAtualizar;

    @BindView(R.id.rcvRepositorios)
    RecyclerView rcvRepositorios;

    private RepositoriosRecyclerViewAdapter rcvAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        configurarLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        obterDados();
    }

    private void configurarLayout() {
        //Setup Toolbar
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        //Setup RecyclerView
        rcvRepositorios.setHasFixedSize(true);

        LinearLayoutManager rcvLayoutManager = new LinearLayoutManager(this);
        rcvRepositorios.setLayoutManager(rcvLayoutManager);

        //ADAPTER
        rcvAdapter = new RepositoriosRecyclerViewAdapter(this);
        rcvRepositorios.setAdapter(rcvAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvRepositorios.addItemDecoration(dividerItemDecoration);

        srlAtualizar.setOnRefreshListener(this);
    }

    private void obterDados() {
        srlAtualizar.post(new Runnable() {
            @Override
            public void run() {
                srlAtualizar.setRefreshing(true);
            }
        });

        ListarRepositoriosServices services = new ListarRepositoriosServices(this);
        services.listarRepositorios(this);
    }

    @Override
    public void onListarRepositoriosSuccess(ListRepositoriesResponse body) {
        srlAtualizar.setRefreshing(false);
        rcvAdapter.updateList(body.getItems());
    }

    @Override
    public void onServiceError() {
        srlAtualizar.setRefreshing(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.titulo_erro)
                .setMessage(R.string.mensagem_erro_desconhecido)
                .setPositiveButton(R.string.botao_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        if (!isFinishing()) builder.create().show();
    }

    @Override
    public void onNoConnection() {
        srlAtualizar.setRefreshing(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.titulo_sem_conexao)
                .setMessage(R.string.mensagem_erro_sem_conexao)
                .setPositiveButton(R.string.botao_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        if (!isFinishing()) builder.create().show();
    }

    @Override
    public void onRefresh() {
        obterDados();
    }
}
