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

    private ArrayList<Produtos> produtosArrayList;
    private ArrayList<Produtos> produtosArrayListFull; // Lista de produtos completa (para filtragem)
    private Context context;

    public ProdutosAdapter(ArrayList<Produtos> produtosArrayList, Context context) {
        this.produtosArrayList = produtosArrayList;
        this.produtosArrayListFull = new ArrayList<>(produtosArrayList); // Copia da lista completa
        this.context = context;
    }

    @NonNull
    @Override
    public ProdutosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecycleLayoutBinding binding = RecycleLayoutBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ProdutosViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutosViewHolder holder, int position) {
        Produtos produto = produtosArrayList.get(position);
        holder.bind(produto);
    }

    @Override
    public int getItemCount() {
        return produtosArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return produtosFilter;
    }

    private Filter produtosFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Produtos> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(produtosArrayListFull); // Lista completa se o filtro estiver vazio
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Produtos produto : produtosArrayListFull) {
                    if (produto.getNome().toLowerCase().contains(filterPattern)) {
                        filteredList.add(produto); // Adiciona produtos que correspondem ao padrão de filtro
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            produtosArrayList.clear();
            produtosArrayList.addAll((List) results.values);
            notifyDataSetChanged(); // Notifica o RecyclerView sobre a mudança nos dados
        }
    };

    // Método para atualizar a lista de produtos exibida no RecyclerView após filtragem
    public void filterList(ArrayList<Produtos> filteredList) {
        produtosArrayList = filteredList;
        notifyDataSetChanged();
    }

    static class ProdutosViewHolder extends RecyclerView.ViewHolder {
        private RecycleLayoutBinding binding;

        ProdutosViewHolder(RecycleLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Produtos produto) {
            binding.containerImg.setBackgroundResource(produto.getImg());
            binding.RecycleNome.setText(produto.getNome());
            binding.RecyclePreco.setText(String.valueOf(produto.getPreco()));
            binding.RecycleQuantidade.setText(String.valueOf(produto.getQuantidadeEmestoque()));
        }
    }
}