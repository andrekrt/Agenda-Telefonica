package com.example.kaleb.agendatap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kaleb.agendatap.helpers.ContatoHelper;
import com.example.kaleb.agendatap.model.Contato;
import com.example.kaleb.agendatap.model.Usuario;
import com.example.kaleb.agendatap.model.dao.ContatoDao;

public class FormularioContatoActivity extends AppCompatActivity {

    private ContatoHelper helper;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_contato);

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        Contato contato = (Contato) getIntent().getSerializableExtra("contato");

        helper = new ContatoHelper(this);

        if (contato != null) {
            helper.preencherDadosFormulario(contato);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario_contato_ok, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_formulario_contato_ok:

                Usuario usuario = (Usuario) getIntent().getSerializableExtra("usuario");
                ContatoDao contatoDao = new ContatoDao(this);
                Contato contato = helper.getContato();
                String info = "";

                if (contato.getId() == null) {
                    contatoDao.inserir(contato);
                    info = " salvo";
                } else {
                    contatoDao.atualizar(contato);
                    info = " alterado";
                }

                contatoDao.close();

                Toast.makeText(this, "Contato: "
                        + contato.getNome() + info + " com sucesso!", Toast.LENGTH_LONG).show();

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
