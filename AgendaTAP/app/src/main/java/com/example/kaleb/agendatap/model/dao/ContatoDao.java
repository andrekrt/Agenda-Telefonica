package com.example.kaleb.agendatap.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.kaleb.agendatap.model.Contato;
import com.example.kaleb.agendatap.model.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by André Santos on 04/11/2016.
 */
public class ContatoDao extends SQLiteOpenHelper {

    private static final int VERSAO_DB = 1;

    public ContatoDao(Context context) {
        super(context, "contatos", null, VERSAO_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS contatos ( ");
        sql.append("id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sql.append("nome TEXT, ");
        sql.append("telefone TEXT, ");
        sql.append("endereco TEXT, ");
        sql.append("email TEXT, ");
        sql.append("site TEXT, ");
        sql.append("usuario_id INTEGER, ");
        sql.append("FOREIGN KEY(usuario_id) REFERENCES usuarios (id));");

        db.execSQL(sql.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void inserir(Contato contato) {

        SQLiteDatabase db = getReadableDatabase();
        ContentValues dados = pegarDadosContato(contato);

        db.insert("contatos", null, dados);
    }

    @NonNull
    private ContentValues pegarDadosContato(Contato contato) {
        ContentValues dados = new ContentValues();
        dados.put("nome", contato.getNome());
        dados.put("telefone", contato.getTelefone());
        dados.put("endereco", contato.getEndereco());
        dados.put("email", contato.getEmail());
        dados.put("site", contato.getSite());
        dados.put("usuario_id", contato.getUsuarioId());
        return dados;
    }

    public void atualizar(Contato contato) {

        SQLiteDatabase db = getReadableDatabase();
        ContentValues dados = pegarDadosContato(contato);
        String[] params = {contato.getId().toString()};

        db.update("contatos", dados, "id = ?", params);
    }

    public void apagar(Contato contato) {

        SQLiteDatabase db = getReadableDatabase();
        String[] params = {contato.getId().toString()};

        db.delete("contatos", "id = ?", params);
    }

    public List<Contato> bucarTodos(Usuario usuario) {
        List<Contato> contatos = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM contatos WHERE usuario_id = ?;";
        String[] params = {usuario.getId().toString()};

        Cursor cursor = db.rawQuery(sql, params);

        while (cursor.moveToNext()) {

            Contato contato = new Contato();
            contato.setId(cursor.getInt(cursor.getColumnIndex("id")));
            contato.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            contato.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            contato.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            contato.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            contato.setSite(cursor.getString(cursor.getColumnIndex("site")));
            contato.setUsuarioId(cursor.getInt(cursor.getColumnIndex("usuario_id")));

            contatos.add(contato);
        }

        return contatos;
    }
}
