package com.example.kaleb.agendatap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kaleb.agendatap.model.Usuario;
import com.example.kaleb.agendatap.model.dao.UsuarioDao;

public class LoginActivity extends AppCompatActivity {

    private EditText campoUsuario;
    private EditText campoSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campoUsuario = (EditText) findViewById(R.id.usuario_login);
        campoSenha = (EditText)findViewById(R.id.senha_login);

        //verificar se o usuario padrao esta cadastrao
        UsuarioDao dao = new UsuarioDao(this);
        boolean usuarioPadraoCadastrado = dao.verificaUsuario("andresantos", "andre123");
        /*if (!usuarioPadraoCadastrado){
            dao.inserirUsuarioPadrao();
        }*/

        Button formularioUsuario = (Button) findViewById(R.id.formulario_usuario);
        formularioUsuario.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent vaiParaFormularioUsuario = new Intent(LoginActivity.this,FormularioUsuarioActivity.class  );
                startActivity(vaiParaFormularioUsuario);
            }
        });

    }

    @Override
    protected void onRestart() {

        super.onRestart();
    }

    public void logar(View view){

        String login = campoUsuario.getText().toString();
        String senha = campoSenha.getText().toString();

        UsuarioDao dao = new UsuarioDao(this);
        Usuario usuario = dao.buscar(login, senha);

        /*if (usuario == null){
            dao.inserirUsuarioPadrao();
        }*/


        if (dao.verificaUsuario(login, senha)) {

            Intent vaiParaContatos = new Intent(this,ContatosActivity.class);
            vaiParaContatos.putExtra("usuario", usuario);
            startActivity(vaiParaContatos);
            finish();
        }else{
            Toast.makeText(this, "Usuario ou senha invalidos.", Toast.LENGTH_LONG).show();
        }

        dao.close();

    }


}
