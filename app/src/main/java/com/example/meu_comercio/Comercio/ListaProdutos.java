package com.example.meu_comercio.Comercio;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meu_comercio.Modelo.Produtos;
import com.example.meu_comercio.R;
import com.example.meu_comercio.adapter.ProdutosAdapter;
import com.example.meu_comercio.databinding.ActivityListaProdutosBinding;

import java.util.ArrayList;

public class ListaProdutos extends AppCompatActivity {

    private ActivityListaProdutosBinding binding;
    private final ArrayList<Produtos> produtosArrayList = new ArrayList<>();
    private ProdutosAdapter produtosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListaProdutosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerViewProduto = binding.RecycleViewProdutos;
        recyclerViewProduto.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewProduto.setHasFixedSize(true);
        produtosAdapter = new ProdutosAdapter(produtosArrayList, this);
        recyclerViewProduto.setAdapter(produtosAdapter);
        getProduto();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_filter, menu);

        MenuItem searchFilter = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchFilter.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                produtosAdapter.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void getProduto() {
        Produtos produto2 = new Produtos(
                R.drawable.tenis,
                2,
                "Tênis",
                20,
                159.99
        );
        produtosArrayList.add(produto2);

        Produtos produto3 = new Produtos(
                R.drawable.vestido,
                3,
                "Vestido",
                7,
                179.99
        );
        produtosArrayList.add(produto3);

        Produtos produto4 = new Produtos(
                R.drawable.jeans,
                4,
                "Calça Jeans Feminina",
                12,
                299.99
        );
        produtosArrayList.add(produto4);

        Produtos produto5 = new Produtos(
                R.drawable.bermuda,
                5,
                "Bermuda",
                16,
                69.99
        );
        produtosArrayList.add(produto5);

        Produtos produto6 = new Produtos(
                R.drawable.camiseta,
                6,
                "Camiseta",
                23,
                39.99
        );
        produtosArrayList.add(produto6);

        Produtos produto7 = new Produtos(
                R.drawable.roupadefrio,
                7,
                "Moletom",
                30,
                229.99
        );
        produtosArrayList.add(produto7);


        produtosAdapter.notifyDataSetChanged(); // Notifica o adaptador que os dados mudaram
    }
}
