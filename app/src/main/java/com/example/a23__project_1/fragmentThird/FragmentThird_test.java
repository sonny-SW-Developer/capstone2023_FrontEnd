package com.example.a23__project_1.fragmentThird;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a23__project_1.MainActivity;
import com.example.a23__project_1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//Inflater를 통해 각 프래그먼트에 해당하는 레이아웃 리소스 ID 값을 통해 생성된 View를 반환
public class FragmentThird_test extends Fragment implements View.OnClickListener {

    MainActivity activity;
    protected Animation anim;
    protected int layout_ctr = 0;
    protected View menu;
    protected LinearLayout layout_menu;
    protected LinearLayout layout_backgroundmenu;
    protected ImageButton menu_btn;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Initialize view
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        //menu = new View(getActivity().getApplicationContext());
        layout_menu = view.findViewById(R.id.layout_thirdmenu);
        layout_backgroundmenu = view.findViewById(R.id.layout_backgroundmenu);

        //Fragment의 view가 inflate하기전에 컴포넌트를 호출하기 때문에
        //View 함수에 inflater를 한 후 return 해주면 findviewbyid를 사용할 수 있다.
        menu_btn = (ImageButton)view.findViewById(R.id.btn_map_menu);
        menu_btn.setOnClickListener(this);
        ImageButton close_btn = (ImageButton)view.findViewById(R.id.btn_close);
        close_btn.setOnClickListener(this);




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


    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.btn_map_menu:

                if (layout_ctr == 0) {
                    // nfcReader LinearLayout이 보여짐
                    layout_menu.setVisibility(View.VISIBLE);
                    layout_backgroundmenu.setVisibility(View.VISIBLE);

                    anim = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                            //fragment는 Activity가 아니기때문에 MainActivity.this(), this 사용이 불가함
                            R.anim.nfc_anim); //애니메이션설정파일
                    layout_menu.startAnimation(anim);

                    view.setClickable(false);
                    view.setVisibility(View.GONE);

                    layout_ctr = 1;
                    Log.d("layout counter : ", Integer.toString(layout_ctr));
                }
                break;

            case R.id.btn_close:

                if (layout_ctr == 1) {

                    anim = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                            R.anim.nfc2_anim); //애니메이션설정파일
                    layout_menu.startAnimation(anim);

                    layout_menu.setVisibility(View.GONE);
                    layout_backgroundmenu.setVisibility(View.GONE);

                    menu_btn.setVisibility(View.VISIBLE);
                    menu_btn.setClickable(true);
                    layout_ctr = 0;
                }
                break;

        }
    }
}