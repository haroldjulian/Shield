package aplicacioncontroles.shield.be;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by user on 07/07/2018.
 */

@JsonObject
public class BeUsuario {
    @JsonField
    public  int id;
    @JsonField
    public String nombres;
    @JsonField
    public int dni;
    @JsonField
    public int telefono;
    @JsonField
    public String email;
    @JsonField
    public String password;

}
