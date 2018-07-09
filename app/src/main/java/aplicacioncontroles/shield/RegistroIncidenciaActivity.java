package aplicacioncontroles.shield;

import android.Manifest;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.maps.LocationSource;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistroIncidenciaActivity extends AppCompatActivity implements LocationListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtDescripcion)
    EditText txtDescripcion;
    @BindView(R.id.spinnerTipo)
    Spinner spinnerTipo;

    String[] tipos = new String[] {"Accidente","Robo","Secuestro","Incendio"};


    LocationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_incidencia);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle("Reportar Incidente");

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,tipos );
        spinnerTipo.setAdapter(adapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                        pedirUbicacion();
                        /* ... */}
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                }).check();

    }

    private void  pedirUbicacion(){
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        for (String provider : manager.getProviders(criteria,true))
        {
            manager.requestLocationUpdates(provider,0,0,this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void registrarIncidencia (View v){

    }


    @Override
    public void onLocationChanged(Location location) {
        Log.d("ubicacion",location.getLatitude()+","+location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
