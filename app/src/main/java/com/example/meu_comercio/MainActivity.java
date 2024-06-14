package com.example.meu_comercio;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.meu_comercio.Comercio.Principal;
import com.example.meu_comercio.Util.ConfiguracaoBD;
import com.example.meu_comercio.Util.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    // Instância do FirebaseAuth

    EditText campoEmail, campoSenha;
    // Campo de entrada do email e senha
    Button botaoLogin;
    // Botão de acesso

    CheckBox mostrarSenha;
    // Check box de mostrar senha

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Habilita layout de borda a borda
        setContentView(R.layout.activity_main);
        // Define o layout da atividade
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            // Ajusta o padding para suportar áreas de recorte da tela
            return insets;
        });
        auth = ConfiguracaoBD.Firebaseautenticacao();
        // Obtém a instância do FirebaseAuth
        inicializarComponentes();
        // Inicializa os componentes da interface


        //Método responsável por revelar caracteres ocultos
        mostrarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mostrarSenha.isChecked()){
                    if(campoSenha.getTransformationMethod() instanceof PasswordTransformationMethod){
                        campoSenha.setTransformationMethod(null);
                    }
                    mostrarSenha.setTextColor(getResources().getColor(R.color.colorAccent));
                }else{
                    campoSenha.setTransformationMethod(new PasswordTransformationMethod());
                    mostrarSenha.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });
    }

    // Método para validar a autenticação do usuário
    public void validarAutenticacao(View view){
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        if(!email.isEmpty()){ // Verifica se o campo email não está vazio
            if(!senha.isEmpty()){ // Verifica se o campo senha não está vazio
                Usuario usuario = new Usuario();

                usuario.setEmail(email); // Define o email do usuário
                usuario.setSenha(senha); // Define a senha do usuário

                logar(usuario);
                // Chama o método para logar o usuário

                limparCampos();
            }else{
                Toast.makeText(this, "Digite a sua senha!", Toast.LENGTH_SHORT).show();
                // Exibe mensagem se o campo senha estiver vazio
            }
        }else{
            Toast.makeText(this, "Digite o seu email!", Toast.LENGTH_SHORT).show();
            // Exibe mensagem se o campo email estiver vazio
        }
    }

    //Método para limpeza de campos de digitação
    private void limparCampos() {
        campoEmail.setText("");
        campoSenha.setText("");
    }

    // Método para logar o usuário utilizando Firebase Authentication
    private void logar(Usuario usuario) {

        auth.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    abrirTelaPrincipal(); // Se o login for bem-sucedido, abre a tela principal
                }else{
                    String excecao = "";

                    try {
                        throw task.getException();
                    }catch(FirebaseAuthInvalidUserException e){
                        excecao = "O usuário não está cadastrado!"; // Captura exceção para usuário não cadastrado
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        excecao = "Email e/ou senha incorreto(os)"; // Captura exceção para credenciais incorretas
                    }catch(Exception e){
                        excecao = "Erro ao logar o usuário " + e.getMessage(); // Captura outras exceções
                        e.printStackTrace(); // Imprime o rastreamento completo da pilha de execução da exceção
                    }
                    Toast.makeText(MainActivity.this, excecao, Toast.LENGTH_SHORT).show(); // Exibe a mensagem de exceção
                }
            }
        });

    }

    // Método para abrir a tela principal
    private void abrirTelaPrincipal() {
        Intent i = new Intent(MainActivity.this, Principal.class);
        startActivity(i);
    }

    // Método para manter o usuário logado até que deslogue
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser usuarioAuth = auth.getCurrentUser();
        if(usuarioAuth != null){
            abrirTelaPrincipal();
        }
    }

    // Inicializa os componentes da interface
    private void inicializarComponentes(){
        campoEmail = findViewById(R.id.EmailLogin);
        campoSenha = findViewById(R.id.SenhaLogin);
        botaoLogin = findViewById(R.id.buttonLogin);
        mostrarSenha = findViewById(R.id.MostraSLogin);
    }

    // Método para abrir a tela de cadastro
    public void TelaCadastro(View view) {
        Intent in = new Intent(MainActivity.this, TelaCadastro.class);
        startActivity(in);
    }
    // Método para abrir a tela de esqueceu senha
    public void Esqueceusenha(View view) {
        Intent in = new Intent(MainActivity.this, EsqueceuSenha.class);
        startActivity(in);
    }
}