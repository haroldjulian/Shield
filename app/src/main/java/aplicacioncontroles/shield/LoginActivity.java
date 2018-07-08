package aplicacioncontroles.shield;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;

import aplicacioncontroles.shield.be.BeUsuario;
import aplicacioncontroles.shield.callbacks.LoginCallback;
import aplicacioncontroles.shield.dal.DalUsuario;
import aplicacioncontroles.shield.util.Functions;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginCallback {

    @BindView(R.id.lblRegistrarme)  //esto reemplaza a poner findById pero primero debemos importar la implementacion en el gradle
    TextView lblRegistrarme;

    @BindView(R.id.txtUsuario)
    TextView txtUsuario;

    @BindView(R.id.txtClave)
    TextView txtClave;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);  //para usar esta funcion que reemplaza al findbyId se debe incluir en el gradle la implementacion de ButterKnife
        initLink();

        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Iniciando");

       // new DalUsuario().probarServicio();
    }

    public void iniciarSesion(View v){
        if (!txtClave.getText().toString().isEmpty() && !txtUsuario.getText().toString().isEmpty()){
            dialog.show();
            new DalUsuario().validarUsuario(txtUsuario.getText().toString(),txtClave.getText().toString(),this);

        }else{
            Toast.makeText(this,"Proporcione usuario y clave",Toast.LENGTH_SHORT).show();
        }
    }


    private void initLink() {
        Link link = new Link("Registrarme")
                .setTextColor(getResources().getColor(R.color.colorAccent))                  // optional, defaults to holo blue
                .setTextColorOfHighlightedLink(getResources().getColor(R.color.colorAccent)) // optional, defaults to holo blue
                .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                .setUnderlined(true)                                       // optional, defaults to true
                .setBold(true)                                              // optional, defaults to false
                .setOnLongClickListener(new Link.OnLongClickListener() {
                    @Override
                    public void onLongClick(String clickedText) {
                        // long clicked
                    }
                })
                .setOnClickListener(new Link.OnClickListener() {
                    @Override
                    public void onClick(String clickedText) {
                        // single clicked
                        Intent iRegistro = new Intent(LoginActivity.this,RegistroActivity.class);
                        startActivity(iRegistro);
                    }
                });
        LinkBuilder.on(lblRegistrarme)
                .addLink(link)
                .build(); // create the clickable links

    }

    @Override
    public void onLoginSucess(BeUsuario usuario) {
        dialog.dismiss();
        Functions.guardarUsuario(this, usuario);
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onLoginError(String mensaje) {
    Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }
}
