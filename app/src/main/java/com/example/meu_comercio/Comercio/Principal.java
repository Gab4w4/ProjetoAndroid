package com.example.meu_comercio.Comercio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.meu_comercio.R;
import com.example.meu_comercio.Util.ConfiguracaoBD;
import com.google.firebase.auth.FirebaseAuth;

public class Principal extends AppCompatActivity {

    private FirebaseAuth auth;
    // Instância do FirebaseAuth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Habilita layout de borda a borda
        setContentView(R.layout.activity_principal);
        // Define o layout da atividade
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            // Ajusta o padding para suportar áreas de recorte da tela
            return insets;
        });
        auth = ConfiguracaoBD.Firebaseautenticacao();
        // Obtém a instância do FirebaseAuth



    }

    // Método para deslogar o usuário
    public void deslogar(View view) {
        try {
            auth.signOut(); // Encerra a sessão do usuário autenticado
            finish(); // Fecha a atividade atual
        } catch (Exception e) {
            e.printStackTrace(); // Imprime o rastreamento completo da pilha de execução da exceção
        }
    }

    //Método para abrir a tela da lista de produtos
    public void ListadeProdutos(View view){
        Intent in = new Intent(Principal.this, ListaProdutos.class);
        startActivity(in);
    }

}