package com.example.meu_comercio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meu_comercio.Modelo.Produtos;
import com.example.meu_comercio.databinding.RecycleLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class ProdutosAdapter extends RecyclerView.Adapter<ProdutosAdapter.ProdutosViewHolder> implements Filterable {

    private final ArrayList<Produtos> produtosArrayList;
    private final ArrayList<Produtos> fullProdutosArrayList;
    private final Context context;

    public ProdutosAdapter(ArrayList<Produtos> produtosArrayList, Context context) {
        this.produtosArrayList = produtosArrayList;
        this.context = context;
        this.fullProdutosArrayList = new ArrayList<>(produtosArrayList);
    }

    @NonNull
    @Override
    public ProdutosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecycleLayoutBinding listProduto = RecycleLayoutBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ProdutosViewHolder(listProduto);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutosViewHolder holder, int position) {
        Produtos produto = produtosArrayList.get(position);
        holder.binding.containerImg.setBackgroundResource(produto.getImg());
        holder.binding.RecycleNome.setText(produto.getNome());
        holder.binding.RecyclePreco.setText(String.valueOf(produto.getPreco()));
        holder.binding.RecycleQuantidade.setText(String.valueOf(produto.getQuantidadeEmestoque()));
    }

    @Override
    public int getItemCount() {
        return produtosArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private final Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Produtos> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(fullProdutosArrayList);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Produtos produto : fullProdutosArrayList) {
                    if (produto.getNome().toLowerCase().contains(filterPattern)) {
                        filteredList.add(produto);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            produtosArrayList.clear();
            produtosArrayList.addAll((ArrayList<Produtos>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public static class ProdutosViewHolder extends RecyclerView.ViewHolder {
        RecycleLayoutBinding binding;

        public ProdutosViewHolder(RecycleLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
