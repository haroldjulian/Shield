package aplicacioncontroles.shield.be;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;

/**
 * Created by user on 30/06/2018.
 */
@JsonObject
public class Personas {
    @JsonField
    public int page;
    @JsonField
    public int per_page;
    @JsonField
    public int total;
    @JsonField
    public  int total_page;
    @JsonField
    public ArrayList<Persona> data;
}
