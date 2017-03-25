package com.example.kaleb.agendatap.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.kaleb.agendatap.model.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by André Santos on 04/11/2016.
 */
public class UsuarioDao extends SQLiteOpenHelper{

    private static final int VERSAO_DB=1;

    public UsuarioDao(Context context){
        super(context, "usuarios", null, VERSAO_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS usuarios (");
        sql.append("id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sql.append("nome TEXT NOT NULL, ");
        sql.append("login TEXT NOT NULL, ");
        sql.append("senha TEXT NOT NULL);");

        db.execSQL(sql.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean verificaUsuario(String login, String senha){

        SQLiteDatabase db = getReadableDatabase();
        String[] params = {login, senha};
        String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ?;";

        Cursor cursor = db.rawQuery(sql, params);
        int resultado = cursor.getCount();
        cursor.close();

        return resultado>0;

    }

    public Usuario buscar(String login, String senha){

        SQLiteDatabase db = getReadableDatabase();
        String[] params = {login, senha};
        String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ?;";

        Cursor cursor = db.rawQuery(sql, params);


        if (cursor.moveToFirst()){
            Usuario usuario = new Usuario();
            usuario.setId(cursor.getInt(cursor.getColumnIndex("id")));
            usuario.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            usuario.setLogin(cursor.getString(cursor.getColumnIndex("login")));
            usuario.setSenha(cursor.getString(cursor.getColumnIndex("senha")));

            return usuario;
        }

        cursor.close();

        return null;
    }


   /* public void inserirUsuarioPadrao(){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues dados = new ContentValues();
        dados.put("nome", "André Santos");
        dados.put("login", "andresantos");
        dados.put("senha", "andre123");

        db.insert("usuarios", null, dados);
        }*/

    public void inserirUsuario(Usuario usuario){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues dados = pegarDadosUsuarios(usuario);
        db.insert("usuarios", null, dados);

    }

    @NonNull
    private ContentValues pegarDadosUsuarios(Usuario usuario){
        ContentValues dados = new ContentValues();
        dados.put("nome", usuario.getNome());
        dados.put("login", usuario.getLogin());
        dados.put("senha", usuario.getSenha());
        return dados;
    }


    public List<Usuario> buscarUsuarios(){
        List<Usuario> usuarios = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM usuarios;";


        Cursor cursor = db.rawQuery(sql,null );

        while (cursor.moveToNext()){
            Usuario usuario = new Usuario();
            usuario.setId(cursor.getInt(cursor.getColumnIndex("id")));
            usuario.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            usuario.setLogin(cursor.getString(cursor.getColumnIndex("login")));
            usuario.setSenha(cursor.getString(cursor.getColumnIndex("senha")));

            usuarios.add(usuario);
        }
        return usuarios;
    }

    public void apagarUsuario(Usuario usuario){
        SQLiteDatabase db = getReadableDatabase();
        String[] params = {usuario.getId().toString()};
        db.delete("usuarios", "id = ?", params);
    }

}

