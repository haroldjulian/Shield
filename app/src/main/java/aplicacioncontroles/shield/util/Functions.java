package aplicacioncontroles.shield.util;

import android.content.Context;
import android.content.SharedPreferences;

import aplicacioncontroles.shield.be.BeUsuario;

/**
 * Created by user on 07/07/2018.
 */

public class Functions {
    public static void guardarUsuario(Context c, BeUsuario usuario){
        //aca debo guardar el usuario en el
        SharedPreferences preferences = c.getSharedPreferences("usuario_logeado", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("id",usuario.id);
        editor.putString("nombres",usuario.nombres);
        editor.putString("email",usuario.email);
        editor.putInt("telefono",usuario.telefono);
        editor.putInt("dni",usuario.dni);
        editor.commit();
    }

    public static BeUsuario obtenerUsuario(Context c){
        //aca debo leer el usuario previamente guardado
        SharedPreferences preferences = c.getSharedPreferences("usuario_logeado",Context.MODE_PRIVATE);
        BeUsuario usuario = null;
        if (preferences.contains("id")){
            usuario = new BeUsuario();
            usuario.id = preferences.getInt("id",0);
            usuario.email = preferences.getString("email","");
            usuario.telefono = preferences.getInt("telefono",0);
            usuario.dni = preferences.getInt("dni",0);
            usuario.nombres = preferences.getString("nombres","");

        }
        return usuario;
    }
    public static void eliminarUsuario(Context c){
        SharedPreferences preferences = c.getSharedPreferences("usuario_logeado",Context.MODE_PRIVATE);
        preferences.edit().clear().commit();
    }

}
