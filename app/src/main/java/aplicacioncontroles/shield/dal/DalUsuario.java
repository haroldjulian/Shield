package aplicacioncontroles.shield.dal;

import android.util.Log;

import com.bluelinelabs.logansquare.LoganSquare;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import aplicacioncontroles.shield.be.BeUsuario;

import aplicacioncontroles.shield.callbacks.LoginCallback;
import aplicacioncontroles.shield.callbacks.RegistroUsuarioCallback;
import aplicacioncontroles.shield.util.RESTClient;
import cz.msebera.android.httpclient.Header;

/**
 * Created by user on 30/06/2018.
 */

public class DalUsuario {

    public void  registrarUsuario(BeUsuario usuario, final RegistroUsuarioCallback callback){

        RequestParams params = new RequestParams();
        params.add("nombres", usuario.nombres);
        params.add("telefono", String.valueOf(usuario.telefono));
        params.add("email", usuario.email);
        params.add("dni",String.valueOf(usuario.dni));
        params.add("password",usuario.password);


        RESTClient.post("usuario/registro", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (response.has("codigo")){
                    try {
                        if (response.getInt("codigo") ==1){
                            //exito cerraremos la pantalla de registro de usuario
                            callback.onRegistroSucess();
                        }else{
                            //error mostraremos un mensaje en la interfaz
                            callback.onRegistroError("error en el registro");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                callback.onRegistroError(responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                callback.onRegistroError(errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                callback.onRegistroError(errorResponse.toString());
            }
        });
    }

    public void validarUsuario(String usuario, String clave, final LoginCallback callback){
        RequestParams params = new RequestParams();
        params.add("usuario",usuario);
        params.add("clave",clave);

        RESTClient.post("usuario/login", params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    try {
                        String serializado = response.getJSONObject("usuario").toString();
                        BeUsuario usuarioLogeado = LoganSquare.parse(serializado,BeUsuario.class);
                        callback.onLoginSucess(usuarioLogeado);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                callback.onLoginError(responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                callback.onLoginError(errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                callback.onLoginError(errorResponse.toString());
            }


        });



    }
}
