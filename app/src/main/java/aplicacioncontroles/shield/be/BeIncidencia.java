package aplicacioncontroles.shield.be;


import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by user on 15/07/2018.
 */
@JsonObject
public class BeIncidencia {
    @JsonField
    public int id;
    @JsonField
    public  String descripcion;
    @JsonField
    public String tipo;
    @JsonField
    public double latitud;
    @JsonField
    public  double longitud;

}
