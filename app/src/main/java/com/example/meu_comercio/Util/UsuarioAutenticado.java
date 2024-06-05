package com.example.meu_comercio.Util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UsuarioAutenticado {


    // Método para verificar se o usuário já está logado
    public static FirebaseUser usuarioLogado(){
        FirebaseAuth usuario = ConfiguracaoBD.Firebaseautenticacao();
        // Obtém a instância do FirebaseAuth
        return usuario.getCurrentUser();
        // Retorna o usuário atualmente logado no sistema
    }
}
