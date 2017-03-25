package com.example.kaleb.agendatap.helpers;

import android.content.Context;
import android.widget.EditText;

import com.example.kaleb.agendatap.FormularioContatoActivity;
import com.example.kaleb.agendatap.R;
import com.example.kaleb.agendatap.model.Contato;
import com.example.kaleb.agendatap.model.Usuario;

/**
 * Created by André Santos on 11/11/2016.
 */
public class ContatoHelper {
    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoEndereco;
    private EditText campoEmail;
    private EditText campoSite;
    private Contato contato;
    private Usuario usuario;

    public ContatoHelper(FormularioContatoActivity activity) {
        campoNome = (EditText) activity.findViewById(R.id.nome_formulario_contato);
        campoTelefone = (EditText) activity.findViewById(R.id.telefone_formulario_contato);
        campoEndereco = (EditText) activity.findViewById(R.id.endereco_formulario_contato);
        campoEmail = (EditText) activity.findViewById(R.id.email_formulario_contato);
        campoSite = (EditText) activity.findViewById(R.id.site_formulario_contato);

        usuario = (Usuario) activity.getIntent().getSerializableExtra("usuario");
        contato = new Contato();
    }

    public void preencherDadosFormulario(Contato contato) {
        this.contato = contato;
        campoNome.setText(contato.getNome());
        campoTelefone.setText(contato.getTelefone());
        campoEndereco.setText(contato.getEndereco());
        campoEmail.setText(contato.getEmail());
        campoSite.setText(contato.getSite());
    }

    public Contato getContato() {

        contato.setNome(campoNome.getText().toString());
        contato.setTelefone(campoTelefone.getText().toString());
        contato.setEndereco(campoEndereco.getText().toString());
        contato.setEmail(campoEmail.getText().toString());
        contato.setSite(campoSite.getText().toString());
        contato.setUsuarioId(usuario.getId());

        return contato;
    }
}
