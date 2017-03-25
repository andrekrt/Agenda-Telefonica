package com.example.kaleb.agendatap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Toast;

import com.example.kaleb.agendatap.model.Contato;
import com.example.kaleb.agendatap.model.Usuario;
import com.example.kaleb.agendatap.model.dao.ContatoDao;

import java.util.List;

public class ContatosActivity extends AppCompatActivity {

    private Usuario usuario;
    private ListView listaContatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        listaContatos = (ListView) findViewById(R.id.lista_contatos);

        listaContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista,
                                    View item, int position, long id) {

                Contato contato = (Contato) listaContatos.getItemAtPosition(position);

                Intent vaiParaFormulario = new Intent(ContatosActivity.this,
                        FormularioContatoActivity.class);
                vaiParaFormulario.putExtra("contato", contato);
                vaiParaFormulario.putExtra("usuario", usuario);
                startActivity(vaiParaFormulario);

            }
        });

        Button botaoNovoContato = (Button) findViewById(R.id.novo_contato_contatos);
        botaoNovoContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiParaFormularioContato =
                        new Intent(ContatosActivity.this,
                                FormularioContatoActivity.class);
                vaiParaFormularioContato.putExtra("usuario", usuario);
                startActivity(vaiParaFormularioContato);
            }
        });

        registerForContextMenu(listaContatos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLista();
    }

    private void carregarLista() {

        ContatoDao dao = new ContatoDao(this);
        List<Contato> contatosDB = dao.bucarTodos(usuario);
        dao.close();

        ArrayAdapter<Contato> adapter = new ArrayAdapter<Contato>(
                this, android.R.layout.simple_list_item_1, contatosDB
        );

        listaContatos.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contatos, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_contatos_sobre:

                Intent vaiParaTelaSobre = new Intent(
                        this, SobreActivity.class);
                startActivity(vaiParaTelaSobre);
                break;

            case R.id.menu_contatos_usuario:
                Intent vaiParaListaUsuario = new Intent(this, UsuariosActivity.class);
                startActivity(vaiParaListaUsuario);
                break;
            case R.id.menuu_sair_contato:
                Intent saiContato = new Intent(this, LoginActivity.class);
                startActivity(saiContato);
                break;
            case android.R.id.home:
                Intent voltaParaLogin = new Intent(this, LoginActivity.class);
                startActivity(voltaParaLogin);
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v, ContextMenu.ContextMenuInfo menuInfo) {


        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo)
                        menuInfo;

        final Contato contato = (Contato) listaContatos
                .getItemAtPosition(info.position);

        MenuItem itemLigar = menu.add("Ligar");
        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (ActivityCompat.checkSelfPermission(ContatosActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(ContatosActivity.this, new String[] {Manifest.permission.CALL_PHONE},999);
                }else {
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + contato.getTelefone()));
                    startActivity(intentLigar);
                }

                return false;
            }
        });

        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent vaiParaSMS = new Intent(Intent.ACTION_VIEW);
        String telefoneContato = contato.getTelefone();
        vaiParaSMS.setData(Uri.parse("sms" + telefoneContato));
        itemSMS.setIntent(vaiParaSMS);

        MenuItem itemEndereco = menu.add("Visualizar Endereço");
        Intent vaiParaEndereco = new Intent(Intent.ACTION_VIEW);
        String enderecoContato = contato.getEndereco();
        vaiParaEndereco.setData(Uri.parse("geo:0,0q=" + enderecoContato));
        itemEndereco.setIntent(vaiParaEndereco);

        MenuItem itemSite = menu.add("Acessar Site");
        Intent vaiParaSite = new Intent(Intent.ACTION_VIEW);
        String siteContato = contato.getSite();
        if (!siteContato.startsWith("http://")){
            siteContato = "http://" + siteContato;
        }

        vaiParaSite.setData(Uri.parse(siteContato));
        itemSite.setIntent(vaiParaSite);




        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                ContatoDao dao = new ContatoDao(
                        ContatosActivity.this);

                dao.apagar(contato);
                dao.close();

                carregarLista();

                return false;
            }
        });

    }
}
