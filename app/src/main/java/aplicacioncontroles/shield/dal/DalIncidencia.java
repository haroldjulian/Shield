package aplicacioncontroles.shield.dal;

import com.bluelinelabs.logansquare.LoganSquare;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import aplicacioncontroles.shield.be.BeIncidencia;
import aplicacioncontroles.shield.be.BeUsuario;
import aplicacioncontroles.shield.callbacks.ObtenerIncidenciasCallback;
import aplicacioncontroles.shield.callbacks.RegistroIncidenciaCallback;
import aplicacioncontroles.shield.callbacks.RegistroUsuarioCallback;
import aplicacioncontroles.shield.util.RESTClient;
import cz.msebera.android.httpclient.Header;

/**
 * Created by user on 15/07/2018.
 */

public class DalIncidencia {
    public void  registrarIncidencia(String tipo,
                                     String descripcion,
                                     double latitud,
                                     double longitud,
                                     int idUsuario,
                                     final RegistroIncidenciaCallback callback){

        RequestParams params = new RequestParams();
        params.add("descripcion", descripcion);
        params.add("tipo", tipo);
        params.add("latitud", String.valueOf(latitud));
        params.add("longitud",String.valueOf(longitud));
        params.add("id_usuario",String.valueOf(idUsuario));



        RESTClient.post("incidencias/registro", params, new JsonHttpResponseHandler() {
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


    public void  obtenerIncidencias(final ObtenerIncidenciasCallback callback){

        RequestParams params = new RequestParams();

        RESTClient.get("incidencias", params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    List<BeIncidencia> incidenciaList = LoganSquare.parseList(response.toString(), BeIncidencia.class);
                    callback.onListaSucess(incidenciaList);
                }catch (IOException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                callback.onListaError(responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                callback.onListaError(errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                callback.onListaError(errorResponse.toString());
            }
        });
    }
}

