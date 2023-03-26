package com.example.a23__project_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//Inflater를 통해 각 프래그먼트에 해당하는 레이아웃 리소스 ID 값을 통해 생성된 View를 반환
public class FragmentSecond extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Initialize view
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        // Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);


        // Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // When map is loaded
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        // When clicked on map
                        // Initialize marker options
                        latLng = new LatLng(37.494571580758944, 126.95976562605374);
                        MarkerOptions markerOptions=new MarkerOptions();
                        // Set position of marker
                        markerOptions.position(latLng);
                        // Set title of marker
                        //markerOptions.title(latLng.latitude+" : "+latLng.longitude);
                        markerOptions.title("숭실대학교");
                        markerOptions.snippet("정보 island");
                        // Remove all marker
                        googleMap.clear();
                        // Animating to zoom the marker
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
                        // Add marker on map
                        googleMap.addMarker(markerOptions);



                    }
                });
            }
        });
        // Return view
        return view;
    }

}