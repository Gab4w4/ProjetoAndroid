package com.example.meu_comercio;

public class Usuario {
    private String Nome;
    private String Telefone;
    private String Email;
    private String Senha;
    private String Confirmarsenha;

    public Usuario(){
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }
    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public String getConfirmarsenha(String Confirmarsenha) {
        return Confirmarsenha;
    }

    public void setConfirmarsenha(String Confirmarsenha) {
        Confirmarsenha = Confirmarsenha;
    }
}
