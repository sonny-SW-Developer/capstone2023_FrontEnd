package com.example.a23__project_1.thirdFragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.a23__project_1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivityGoogle extends AppCompatActivity implements OnMapReadyCallback{

    private SupportMapFragment supportMapFragment;
    private FragmentManager fragmentManager;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_google);

        fragmentManager = getSupportFragmentManager();
        supportMapFragment = (SupportMapFragment)fragmentManager.findFragmentById(R.id.googleMap);
        supportMapFragment.getMapAsync(this);

    }

    // GoogleMap에서 마커 설정 메소드
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        LatLng JungboIsland = new LatLng(37.494571580758944, 126.95976562605374);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(JungboIsland);
        markerOptions.title("숭실대학교");
        markerOptions.snippet("정보 island");

        mMap.addMarker(markerOptions);



//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(JungboIsland, 16));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(JungboIsland, 16));
    }

}