package com.example.meu_comercio.Modelo;

public class Produtos {
    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    int img;
    private long id;
    private String nome;
    private int quantidadeEmestoque;
    private Double preco;

    public Produtos(int img, long id, String nome, int quantidadeEmestoque, Double preco) {
        this.img = img;
        this.id = id;
        this.nome = nome;
        this.quantidadeEmestoque = quantidadeEmestoque;
        this.preco = preco;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidadeEmestoque() {
        return quantidadeEmestoque;
    }

    public void setQuantidadeEmestoque(int quantidadeEmestoque) {
        this.quantidadeEmestoque = quantidadeEmestoque;
    }

    public Double getPreco() {return preco; }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
