package com.example.kaleb.agendatap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.kaleb.agendatap.model.Contato;
import com.example.kaleb.agendatap.model.Usuario;
import com.example.kaleb.agendatap.model.dao.UsuarioDao;

import java.util.List;

public class UsuariosActivity extends AppCompatActivity {


    private ListView listaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listaUsuario = (ListView) findViewById(R.id.lista_usuarios);

        Button botaoNovoUsuario = (Button) findViewById(R.id.novo_usuario_usuarios);
        botaoNovoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiParaFormularioUsuario = new Intent(UsuariosActivity.this, FormularioUsuarioActivity.class);
                startActivity(vaiParaFormularioUsuario);
            }
        });
        registerForContextMenu(listaUsuario);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLista();

    }

    private void carregarLista(){
        UsuarioDao dao = new UsuarioDao(this);
        List<Usuario> usuariosDB = dao.buscarUsuarios();
        dao.close();

        ArrayAdapter<Usuario> adapter = new ArrayAdapter<Usuario>(
                this, android.R.layout.simple_list_item_1, usuariosDB
        );

        listaUsuario.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_usuarios, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_usuarios_sobre:
                Intent vaiParaSobre = new Intent(this, SobreActivity.class);
                startActivity(vaiParaSobre);
                break;
            case R.id.menu_sair_usuario:
                Intent saiUsuario = new Intent(this,LoginActivity.class);
                startActivity(saiUsuario);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
