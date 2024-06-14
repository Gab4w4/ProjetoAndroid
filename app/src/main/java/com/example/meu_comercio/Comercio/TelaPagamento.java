package com.example.meu_comercio.Comercio;

import static com.example.meu_comercio.R.layout.activity_tela_pagamento;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.meu_comercio.R;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class TelaPagamento extends AppCompatActivity {

    private TextView pixLink;
    private Button voltarButton;
    private EditText productName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_tela_pagamento);

        pixLink = findViewById(R.id.linkNubank);
        voltarButton = findViewById(R.id.ButtonVoltarP);
        productName = findViewById(R.id.NomeprodutoComprar);

        // Configurar o botão "Confirmar produto"
        Button confirmarButton = findViewById(R.id.ButtonGerarLinkP);
        confirmarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String produto = productName.getText().toString().trim();
                if (!produto.isEmpty()) {
                    // Atualizar o link do PIX com base no produto confirmado
                    String novoLink = "https://nubank.com.br/cobrar/nr3pk/" + produto.replace(" ", "_").toLowerCase();
                    pixLink.setText(novoLink);
                    pixLink.setVisibility(View.VISIBLE); // Mostrar o link do PIX
                } else {
                    // Se o campo de produto estiver vazio, exibir uma mensagem
                    Toast.makeText(TelaPagamento.this, "Por favor, insira o nome do produto.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Definir o link clicável
        pixLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = pixLink.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        // Configurar o botão "Voltar"
        voltarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}