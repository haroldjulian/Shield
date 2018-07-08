package aplicacioncontroles.shield;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import aplicacioncontroles.shield.be.BeUsuario;
import aplicacioncontroles.shield.callbacks.RegistroUsuarioCallback;
import aplicacioncontroles.shield.dal.DalUsuario;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistroActivity extends AppCompatActivity implements
        Validator.ValidationListener, RegistroUsuarioCallback {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @NotEmpty(message = "Campo Obligatorio")
    @BindView(R.id.txtNombres)
    EditText txtNombres;

    @NotEmpty(message = "Campo Obligatorio")
    @BindView(R.id.txtClave)
    EditText txtClave;



    @NotEmpty(message = "Campo Obligatorio")
    @Length(min = 8, max=8, message = "DNI debe tener 8 digitos")
    @BindView(R.id.txtDni)
    EditText txtDni;

    @NotEmpty(message = "Campo Obligatorio")
    @BindView(R.id.txtTelefono)
    EditText txtTelefono;

    @NotEmpty(message = "Campo Obligatorio")
    @Email(message = "Email Invalido")
    @BindView(R.id.txtEmail)
    EditText txtEmail;

    @Checked(message = "Debes aceptar los terminos")
    @BindView(R.id.chkTerminos)
    CheckBox chkTerminos;

Validator validator;
    ProgressDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);  //aqui recien el control toolbar puede recibir las opciones siguientes;
        setTitle("Registrame");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        validator = new Validator(this);

        validator.setValidationListener(this);


        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);  //no se cierra si presionas fuera del dialog
        dialog.setMessage("Registrando..");;
        dialog.setIndeterminate(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onValidationSucceeded() {
        dialog.show();
        //aqui hacemos la peticion http para guardar los datos del usuario !!!!
      //  Toast.makeText(this,"Todo ok......",Toast.LENGTH_LONG).show();
        BeUsuario usuario = new BeUsuario();
        usuario.nombres = txtNombres.getText().toString();
        usuario.dni = Integer.parseInt(txtDni.getText().toString());
        usuario.telefono = Integer.parseInt((txtTelefono.getText().toString()));
        usuario.email = txtEmail.getText().toString();
        usuario.password = txtClave.getText().toString();

        new DalUsuario().registrarUsuario(usuario,this);

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }

    }

    public void registrarme(View v){   //esto va actuar cada vez que presiones el boton REGISTRAME en el diseñador
        validator.validate();
    }

    @Override
    public void onRegistroSucess() {
        dialog.dismiss();
        Toast.makeText(this,"usuario registrado exitosamente",Toast.LENGTH_LONG).show();
        finish();

    }

    @Override
    public void onRegistroError(String mensaje) {
    dialog.dismiss();
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }
}
