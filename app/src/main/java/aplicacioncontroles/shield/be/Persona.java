package aplicacioncontroles.shield.be;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by user on 30/06/2018.
 */
@JsonObject
public class Persona {
    @JsonField
    public int id;
    @JsonField
    public String first_name;
    @JsonField
    public String last_name;
    @JsonField
    public String avatar;
}
