package aplicacioncontroles.shield.fragments;

import android.content.Intent;
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
import com.google.android.gms.maps.model.MarkerOptions;

import aplicacioncontroles.shield.R;
import aplicacioncontroles.shield.RegistroIncidenciaActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 08/07/2018.
 */

public class InicioFragment extends Fragment implements OnMapReadyCallback {

    @BindView(R.id.btn_nueva_incidencia)
    FloatingActionButton btnNuevaIncidencia;

    private GoogleMap mMap;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //aqui inflamos
        View v = inflater.inflate(R.layout.fragment_inicio,null);

        ButterKnife.bind(this,v);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        btnNuevaIncidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aca deberia llamar a la actividad para registrar mi incidencia
                //Toast.makeText(getContext(),"nueva incidencia",Toast.LENGTH_SHORT).show(); //dentro de un fragmento no es this es getContext()
                Intent i = new Intent(getContext(), RegistroIncidenciaActivity.class);
                startActivity(i);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
