package com.example.meu_comercio.Comercio;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meu_comercio.Modelo.Produtos;
import com.example.meu_comercio.R;
import com.example.meu_comercio.adapter.ProdutosAdapter;

import java.util.ArrayList;

public class ListaProdutos extends AppCompatActivity {

    private ArrayList<Produtos> produtosArrayList;
    private ProdutosAdapter produtosAdapter;

    EditText action_search;
    ImageButton searchButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);

        // Inicializa a lista de produtos
        produtosArrayList = new ArrayList<>();

        // Popula a lista de produtos (exemplo)
        populateProdutos();

        // Configura o RecyclerView
        RecyclerView recyclerViewProduto = findViewById(R.id.RecycleViewProdutos);
        recyclerViewProduto.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewProduto.setHasFixedSize(true);

        // Inicializa o adaptador com a lista completa de produtos
        produtosAdapter = new ProdutosAdapter(produtosArrayList, this);
        recyclerViewProduto.setAdapter(produtosAdapter);

        // Configuração da pesquisa
        action_search = findViewById(R.id.action_search);
        searchButton = findViewById(R.id.SearchButton);
        searchButton.setOnClickListener(v -> performSearch());


    }

    private void populateProdutos() {
        // Adiciona produtos de exemplo à lista
        produtosArrayList.add(new Produtos(R.drawable.tenis, 2, "Tênis", 20, 159.99));
        produtosArrayList.add(new Produtos(R.drawable.vestido, 3, "Vestido", 7, 179.99));
        produtosArrayList.add(new Produtos(R.drawable.jeans, 4, "Calça Jeans Feminina", 12, 299.99));
        produtosArrayList.add(new Produtos(R.drawable.bermuda, 5, "Bermuda", 16, 69.99));
        produtosArrayList.add(new Produtos(R.drawable.camiseta, 6, "Camiseta", 23, 39.99));
        produtosArrayList.add(new Produtos(R.drawable.roupadefrio, 7, "Moletom", 30, 229.99));

        // Adicione mais produtos conforme necessário
    }

    private void performSearch() {
        String query = action_search.getText().toString().toLowerCase().trim();

        if (query.isEmpty()) {
            // Se o campo de pesquisa estiver vazio, exibe uma mensagem
            Toast.makeText(this, "Digite um termo para pesquisar", Toast.LENGTH_SHORT).show();
        } else {
            // Filtra a lista de produtos com base no termo de pesquisa
            ArrayList<Produtos> filteredList = new ArrayList<>();

            for (Produtos produto : produtosArrayList) {
                if (produto.getNome().toLowerCase().contains(query)) {
                    filteredList.add(produto);
                }
            }

            // Atualiza o adaptador com a lista filtrada
            produtosAdapter.filterList(filteredList);

            // Se não houver resultados, você pode exibir uma mensagem ou lidar de outra forma
            if (filteredList.isEmpty()) {
                Toast.makeText(this, "Nenhum resultado encontrado para '" + query + "'", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void telaPagamento(View view){
        Intent in = new Intent(ListaProdutos.this, TelaPagamento.class);
        startActivity(in);
    }

    public void voltarTelaPrincipal(View view) {
        Intent in = new Intent(ListaProdutos.this, ListaProdutos.class);
        startActivity(in);
    }
}