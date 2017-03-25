package com.example.kaleb.agendatap.helpers;

import android.widget.EditText;

import com.example.kaleb.agendatap.FormularioUsuarioActivity;
import com.example.kaleb.agendatap.R;
import com.example.kaleb.agendatap.model.Usuario;

/**
 * Created by André Santos on 15/11/2016.
 */
public class UsuarioHelper {
    private EditText nome;
    private EditText login;
    private EditText senha;
    private Usuario usuario;

    public UsuarioHelper(FormularioUsuarioActivity activity){

        nome = (EditText) activity.findViewById(R.id.nome_formulario_usuario);
        login = (EditText) activity.findViewById(R.id.usuario_formulario_usuario);
        senha = (EditText) activity.findViewById(R.id.senha_formulario_usuario);
        usuario = new Usuario();
    }

    public void preencherDadosFormularioUsuario(Usuario usuario){
        this.usuario = usuario;
        nome.setText(usuario.getNome());
        login.setText(usuario.getLogin());
        senha.setText(usuario.getSenha());

    }

    public Usuario getUsuario(){
        usuario.setNome(nome.getText().toString());
        usuario.setLogin(login.getText().toString());
        usuario.setSenha(senha.getText().toString());

        return usuario;
    }

}
