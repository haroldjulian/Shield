package aplicacioncontroles.shield;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.lblRegistrarme)  //esto reemplaza a poner findById pero primero debemos importar la implementacion en el gradle
    TextView lblRegistrarme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);  //para usar esta funcion que reemplaza al findbyId se debe incluir en el gradle la implementacion de ButterKnife
        initLink();

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
}
