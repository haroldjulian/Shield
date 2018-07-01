package aplicacioncontroles.shield.dal;

import android.util.Log;

import com.bluelinelabs.logansquare.LoganSquare;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import aplicacioncontroles.shield.be.Persona;
import aplicacioncontroles.shield.be.Personas;
import aplicacioncontroles.shield.util.RESTClient;
import cz.msebera.android.httpclient.Header;

/**
 * Created by user on 30/06/2018.
 */

public class DalUsuario {

    public void  probarServicio(){
        RESTClient.get("users?page=2", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Personas personas = LoganSquare.parse(response.toString(), Personas.class);
                    for (Persona p: personas.data){
                        Log.d("Persona ->", p.first_name);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {

            }
        });
    }
}
