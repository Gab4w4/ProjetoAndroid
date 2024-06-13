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

import com.example.meu_comercio.Util.ConfiguracaoBD;
import com.example.meu_comercio.Util.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class TelaCadastro extends AppCompatActivity {

    Usuario usuario;
    // Objeto Usuario que armazena as informações do usuário

    FirebaseAuth autenticacao;
    // Objeto FirebaseAuth para autenticação com Firebase
    EditText campoNome, campoTelefone, campoCadastroEmail, campoCadastroSenha, campoCadastroCsenha;
    // Campos de entrada para nome, telefone, email, senha e confirmação de senha
    Button botaoCadastrar;
    // Botão de cadastrar

    CheckBox mostrarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Habilita layout de borda a borda
        setContentView(R.layout.activity_tela_cadastro);
        // Define o layout da atividade
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            // Ajusta o padding para suportar áreas de recorte da tela
            return insets;
        });
        inicializar();
        // Inicializa os componentes da interface

        mostrarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mostrarSenha.isChecked()){
                    if(campoCadastroSenha.getTransformationMethod() instanceof PasswordTransformationMethod
                    && campoCadastroCsenha.getTransformationMethod() instanceof PasswordTransformationMethod){
                        campoCadastroSenha.setTransformationMethod(null);
                        campoCadastroCsenha.setTransformationMethod(null);
                    }
                    mostrarSenha.setTextColor(getResources().getColor(R.color.colorAccent));
                }else{
                    campoCadastroSenha.setTransformationMethod(new PasswordTransformationMethod());
                    campoCadastroCsenha.setTransformationMethod(new PasswordTransformationMethod());
                    mostrarSenha.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });
    }

    // Inicializa os elementos da interface com seus respectivos ID's
    private void inicializar(){
        campoNome = findViewById(R.id.textNome);
        campoTelefone = findViewById(R.id.textTelefone);
        campoCadastroEmail = findViewById(R.id.textCadastroEmail);
        campoCadastroSenha = findViewById(R.id.textCadastroSenha);
        campoCadastroCsenha = findViewById(R.id.textCadastroCsenha);
        botaoCadastrar = findViewById(R.id.buttonCadastrar);
        mostrarSenha = findViewById(R.id.mostraSCadastro);
    }

    // Valida os campos de entrada e, se válidos, chama o método para cadastrar o usuário
    public void validarcampos(View view){
        String nome = campoNome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoCadastroEmail.getText().toString();
        String senha = campoCadastroSenha.getText().toString();
        String Confirmarsenha = campoCadastroCsenha.getText().toString();

        if(!nome.isEmpty()){ // Verifica se o campo nome não está vazio
            if(!telefone.isEmpty()){ // Verifica se o campo telefone não está vazio
                if(!email.isEmpty()){ // Verifica se o campo email não está vazio
                    if(!senha.isEmpty()){ // Verifica se o campo senha não está vazio
                        if(!Confirmarsenha.isEmpty()){ // Verifica se o campo de confirmação de senha não está vazio
                            if(senha.equals(Confirmarsenha)){// Verifica se o usuário está colocando a mesma senha em ambos os campos
                                usuario = new Usuario(); // Cria um novo objeto Usuario
                                usuario.setNome(nome); // Define o nome do usuário
                                usuario.setTelefone(telefone); // Define o telefone do usuário
                                usuario.setEmail(email); // Define o email do usuário
                                usuario.setSenha(senha); // Define a senha do usuário
                                usuario.getConfirmarsenha(Confirmarsenha); // Define a confirmação de senha do usuário

                                cadastrarUsuario();
                                // Chama o método para cadastrar o usuário

                                limparCampos();

                            }else{
                                Toast.makeText(this, "As senhas estão divergentes!", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(this, "Digite a senha novamente!", Toast.LENGTH_SHORT).show();
                            // Exibe mensagem se o campo senha de confirmação estiver vazio
                        }

                    }else{
                        Toast.makeText(this, "Digite a senha!", Toast.LENGTH_SHORT).show();
                        // Exibe mensagem se o campo senha estiver vazio
                    }

                }else{
                    Toast.makeText(this, "Digite o endereço de email!", Toast.LENGTH_SHORT).show();
                    // Exibe mensagem se o campo email estiver vazio
                }

            }
            else{
                Toast.makeText(this, "Digite o seu número de telefone!", Toast.LENGTH_SHORT).show();
                // Exibe mensagem se o campo telefone estiver vazio
            }
        }
        else{
            Toast.makeText(this, "Preencha o nome!", Toast.LENGTH_SHORT).show();
            // Exibe mensagem se o campo nome estiver vazio
        }
    }

    private void limparCampos() {
        campoNome.setText("");
        campoTelefone.setText("");
        campoCadastroEmail.setText("");
        campoCadastroSenha.setText("");
        campoCadastroCsenha.setText("");
    }

    // Método para cadastrar o usuário utilizando Firebase Authentication
    private void cadastrarUsuario() {
        autenticacao = ConfiguracaoBD.Firebaseautenticacao();
        // Obtém a instância do FirebaseAuth

        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(TelaCadastro.this, "Sucesso ao cadastrar o usuário!", Toast.LENGTH_SHORT).show();
                    // Exibe mensagem de sucesso de cadastramento
                    Irlogin();
                    // Chama o método para a ir para tela de login
                } else {
                    String excecao = "";

                    try{
                        throw task.getException();
                    }catch(FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte!"; // Exibe uma mensagem para senhas fracas
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        excecao = "Digite um email válido!"; //Exibe uma mensagem para emails não cadastrados
                    }catch(FirebaseAuthUserCollisionException e){
                        excecao = "Este email já está cadastrado!"; //Exibe uma mensagem para um email já cadastrado
                    }catch(Exception e){
                        excecao = "Erro ao cadastrar o usuário " + e.getMessage(); //Exibe uma mensagem de erro
                        e.printStackTrace(); // Imprime o rastreamento completo da pilha de execução da exceção
                    }
                    Toast.makeText(TelaCadastro.this, excecao, Toast.LENGTH_SHORT).show();
                    // Exibe a mensagem de exceção
                }
            }
        });
    }

    // Método para abrir a tela de login
    private void Irlogin() {
        Intent i = new Intent(TelaCadastro.this, MainActivity.class);
        startActivity(i);
    }

    // Método para abrir a tela de login
    public void TelaLogin(View view) {
        Intent in = new Intent(TelaCadastro.this, MainActivity.class);
        startActivity(in);
    }
}