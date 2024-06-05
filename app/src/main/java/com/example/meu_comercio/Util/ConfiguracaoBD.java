package com.example.meu_comercio.Util;

import com.google.firebase.auth.FirebaseAuth;

public class ConfiguracaoBD {
    private static FirebaseAuth auth;
    // Declaração de uma variável estática do FirebaseAuth

    // Método estático que retorna uma instância do FirebaseAuth
    public static FirebaseAuth Firebaseautenticacao(){
        if(auth ==null){ // Verifica se a instância do FirebaseAuth é nula
            auth =FirebaseAuth.getInstance(); // Se for nula, obtém uma nova instância do FirebaseAuth
        }
        return auth; // Retorna a instância do FirebaseAuth
    }
}
