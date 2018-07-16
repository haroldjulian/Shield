package aplicacioncontroles.shield.callbacks;

import java.util.List;

import aplicacioncontroles.shield.be.BeIncidencia;

/**
 * Created by user on 07/07/2018.
 */

public interface RegistroIncidenciaCallback {
    void onRegistroSucess();
    void onRegistroError(String mensaje);



}
