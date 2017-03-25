package com.example.kaleb.agendatap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kaleb.agendatap.helpers.UsuarioHelper;
import com.example.kaleb.agendatap.model.Usuario;
import com.example.kaleb.agendatap.model.dao.UsuarioDao;

public class FormularioUsuarioActivity extends AppCompatActivity {

    private UsuarioHelper usuarioHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_usuario);

        usuarioHelper = new UsuarioHelper(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario_usuario_ok, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_formulario_usuario_ok:
                UsuarioDao usuarioDao = new UsuarioDao(this);
                Usuario usuario = usuarioHelper.getUsuario();
                String info = "";

                if (usuario.getId() == null){
                    usuarioDao.inserirUsuario(usuario);
                    info="Salvo";
                }

                usuarioDao.close();
                Toast.makeText(this, " Usuario : " + usuario.getNome() + info + " com sucesso ", Toast.LENGTH_LONG).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
