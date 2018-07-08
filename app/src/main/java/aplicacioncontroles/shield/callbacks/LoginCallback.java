package aplicacioncontroles.shield.callbacks;

import aplicacioncontroles.shield.be.BeUsuario;

/**
 * Created by user on 07/07/2018.
 */

public interface LoginCallback {
    void onLoginSucess(BeUsuario usuario);
    void onLoginError(String mensaje);
}
