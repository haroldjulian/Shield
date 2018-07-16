package aplicacioncontroles.shield.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import aplicacioncontroles.shield.R;
import aplicacioncontroles.shield.RegistroIncidenciaActivity;
import aplicacioncontroles.shield.be.BeIncidencia;
import aplicacioncontroles.shield.callbacks.ObtenerIncidenciasCallback;
import aplicacioncontroles.shield.dal.DalIncidencia;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by user on 08/07/2018.
 */

public class InicioFragment extends Fragment implements OnMapReadyCallback, LocationListener,ObtenerIncidenciasCallback {

    @BindView(R.id.btn_nueva_incidencia)
    FloatingActionButton btnNuevaIncidencia;

    private GoogleMap mMap;

    LocationManager manager;
    Location ubicacionActual;
    ProgressDialog dialog;

    boolean firstLoad = false ;

    ArrayList<Marker> listaMarcadores = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //aqui inflamos
        View v = inflater.inflate(R.layout.fragment_inicio,null);

        ButterKnife.bind(this,v);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                        pedirUbicacion();
                        /* ... */}
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                }).check();

        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Cargando....");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setIndeterminate(true);

        return v;
    }

    private void  pedirUbicacion(){
        manager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        for (String provider : manager.getProviders(criteria,true))
        {
            manager.requestLocationUpdates(provider,0,0,this);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        btnNuevaIncidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aca deberia llamar a la actividad para registrar mi incidencia
                //Toast.makeText(getContext(),"nueva incidencia",Toast.LENGTH_SHORT).show(); //dentro de un fragmento no es this es getContext()
                Intent i = new Intent(getContext(), RegistroIncidenciaActivity.class);
               // startActivity(i);
                startActivityForResult(i,1004);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMyLocationEnabled(true);

        mMap.setMapStyle (
                MapStyleOptions.loadRawResourceStyle(getContext(),R.raw.style));
        obtenerIncidencias();
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void obtenerIncidencias() {
        dialog.show();
        new DalIncidencia().obtenerIncidencias(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        ubicacionActual = location;
        if (!firstLoad){
            enfocarUbicacionActual();
            firstLoad = true;
        }


    }

    private void enfocarUbicacionActual() {
        //Log.d("Ubicacion",ubicacionActual)
        LatLng latActual = new LatLng(ubicacionActual.getLatitude(),ubicacionActual.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latActual,17));
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

    @Override
    public void onListaSucess(List<BeIncidencia> lista) {
        dialog.dismiss();
        for (BeIncidencia incidencia:lista){
            Marker marker = mMap.addMarker(new MarkerOptions().
                    position(new LatLng(incidencia.latitud,incidencia.longitud)).title(incidencia.descripcion));
            listaMarcadores.add(marker);

        }

    }

    @Override
    public void onListaError(String mensaje) {
        dialog.dismiss();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1004)
        {
            if (resultCode == RESULT_OK){
                for (Marker marker : listaMarcadores){
                    marker.remove();
                }
                listaMarcadores.clear();
                obtenerIncidencias();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
