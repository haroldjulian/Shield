package aplicacioncontroles.shield;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import aplicacioncontroles.shield.util.Functions;

public class Splashscreen extends AppCompatActivity {

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if ( Functions.obtenerUsuario(Splashscreen.this) != null){
                Intent iLogin = new Intent(Splashscreen.this,MainActivity.class);
                startActivity(iLogin);
                finish();
            }else{
                Intent iLogin = new Intent(Splashscreen.this,LoginActivity.class);
                startActivity(iLogin);
                finish();
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    handler.sendMessage(handler.obtainMessage());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        t.start();
    }


}
