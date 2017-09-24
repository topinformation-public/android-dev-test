package br.com.ciraolo.desafiogithub.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import br.com.ciraolo.desafiogithub.R;
import br.com.ciraolo.desafiogithub.models.Item;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class RepositoriosRecyclerViewAdapter extends RecyclerView.Adapter<RepositoriosRecyclerViewAdapter.ViewHolder> {
    private final Activity mActivity;
    //================================================================================
    // PROPERTIES
    //================================================================================
    private List<Item> mDataset;

    //================================================================================
    // CONSTRUCTOR
    //================================================================================
    // Provide a suitable constructor (depends on the kind of dataset)
    public RepositoriosRecyclerViewAdapter(Activity activity) {
        mDataset = new ArrayList<>();
        mActivity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_view_repositorios, parent, false);

        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Item repositorio = mDataset.get(position);
        holder.txvNomeRepositorio.setText(repositorio.getName());
        holder.txvDescricaoRepositorio.setText(repositorio.getDescription());
        holder.txvQtdeBranchesRepositorio.setText(String.format("%d", repositorio.getForksCount()));
        holder.txvQtdeFavoritosRepositorio.setText(String.format("%d", repositorio.getStargazersCount()));
        holder.txvUsuarioRepositorio.setText(repositorio.getOwner().getLogin());
        holder.txvNomeAutorRepositorio.setText(repositorio.getOwner().getType());

        Glide.with(mActivity)
                .load(repositorio.getOwner().getAvatarUrl())
                .override(75, 75)
                .fitCenter()
                .bitmapTransform(new CropCircleTransformation(mActivity))
                .error(R.mipmap.ic_launcher_round)
                .sizeMultiplier(0.5f)
                .into(holder.imgUsuarioRepositorio);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void updateList(List<Item> newlist) {
        mDataset = new ArrayList<>();
        mDataset.addAll(newlist);
        notifyDataSetChanged();
    }

    public List<Item> getDataset() {
        return mDataset;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {

        //PROPERTIES
        @BindView(R.id.txvNomeRepositorio)
        TextView txvNomeRepositorio;
        @BindView(R.id.txvDescricaoRepositorio)
        TextView txvDescricaoRepositorio;
        @BindView(R.id.txvQtdeBranchesRepositorio)
        TextView txvQtdeBranchesRepositorio;
        @BindView(R.id.txvQtdeFavoritosRepositorio)
        TextView txvQtdeFavoritosRepositorio;
        @BindView(R.id.imgUsuarioRepositorio)
        ImageView imgUsuarioRepositorio;
        @BindView(R.id.txvUsuarioRepositorio)
        TextView txvUsuarioRepositorio;
        @BindView(R.id.txvNomeAutorRepositorio)
        TextView txvNomeAutorRepositorio;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
