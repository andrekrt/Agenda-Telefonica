package com.example.kaleb.agendatap.model;

import java.io.Serializable;

/**
 * Created by André Santos on 04/11/2016.
 */
public class Usuario implements Serializable{
    private Integer id;
    private String nome;
    private String login;
    private String senha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {return id + " - " + nome;}
}
