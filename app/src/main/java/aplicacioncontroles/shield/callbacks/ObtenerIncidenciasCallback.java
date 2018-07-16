package aplicacioncontroles.shield.callbacks;

import java.util.List;

import aplicacioncontroles.shield.be.BeIncidencia;

/**
 * Created by user on 15/07/2018.
 */

public interface ObtenerIncidenciasCallback {
    void onListaSucess(List<BeIncidencia> lista);
    void onListaError(String mensaje);
}
