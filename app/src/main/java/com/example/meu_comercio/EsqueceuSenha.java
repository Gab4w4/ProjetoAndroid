package com.example.meu_comercio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.meu_comercio.Util.ConfiguracaoBD;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class EsqueceuSenha extends AppCompatActivity {
    private FirebaseAuth autenticar;
    // Instância do FirebaseAuth

    EditText emailRecuperacao;
    // Campo de entrada do email de recuperação
    Button botaoRecuperacao;
    // Botão de recuperação de senha

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Habilita layout de borda a borda
        setContentView(R.layout.activity_esqueceu_senha);
        // Define o layout da atividade
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            // Ajusta o padding para suportar áreas de recorte da tela
            return insets;
        });
        autenticar = ConfiguracaoBD.Firebaseautenticacao();
        // Obtém a instância do FirebaseAuth
        iniciarComp();
        // Inicializa os componentes da interface
    }

    public void recuperarsenha(View view){

        String email = emailRecuperacao.getText().toString();

        if(!email.isEmpty()){ // Verifica se o campo email de recuperação não está vazio
            enviarEmail(email);
        }else{
            Toast.makeText(this, "Digite o email para recuperação de senha!", Toast.LENGTH_SHORT).show();
            // Exibe mensagem se o campo email de recuperação estiver vazio
        }

    }

    // Método que recebe email digitado pelo usuário para enviar-lhe um link de redefinição de senha
    private void enviarEmail(String email){
        autenticar.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(EsqueceuSenha.this, "Enviamos o link de redefinir senha para o seu email!", Toast.LENGTH_SHORT).show();
                // Exibe uma mensagem para confirmação de redefinição de senha via email
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(EsqueceuSenha.this, "Erro ao enviar email!", Toast.LENGTH_SHORT).show();
                // Exibe uma mensagem de erro

            }
        });
    }

    // Inicializa os componentes da interface
    private void iniciarComp(){
        emailRecuperacao = findViewById(R.id.emailRecuperacao);
        botaoRecuperacao = findViewById(R.id.buttonEnviar);
    }

    // Método para abrir a tela de login
    public void TelaLogin(View view) {
        Intent in = new Intent(EsqueceuSenha.this, MainActivity.class);
        startActivity(in);
    }
}