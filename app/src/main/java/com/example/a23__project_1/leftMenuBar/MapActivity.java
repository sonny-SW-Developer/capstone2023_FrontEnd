package com.example.a23__project_1.leftMenuBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.a23__project_1.MainActivity;
import com.example.a23__project_1.R;
import com.example.a23__project_1.data.DataMoreInfo;
import com.example.a23__project_1.data.Places;
import com.example.a23__project_1.firstFragment.FragFirstInfoActivity;
import com.example.a23__project_1.firstFragment.RecyclerFragFirstThemeAdapter;
import com.example.a23__project_1.response.PositionResponse;
import com.example.a23__project_1.response.ThemaAllResponse;
import com.example.a23__project_1.retrofit.RetrofitAPI;
import com.example.a23__project_1.retrofit.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MapActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapView.MapViewEventListener, MapView.POIItemEventListener {
    private static final String TAG = "MapActivity";
    private MapView mapView;
    private ViewGroup mapViewContainer;
    private MapPoint mapPoint;
    private MapView mMapView;
    private double nlatitude;
    private double nlongitude;
    private ImageButton nowOnMap;
    private MapPOIItem[] mCustomMarker;
    private List<PositionResponse.Result> positionList;
    private MapInit mapInit;
    private Call<PositionResponse> allPlaceCall;
    private List<Long> positionIdList;
    private int ITEMNUMS = 5;
    private int background_ctr = 0;
    private int main_ctr=0;
    private int marker_ctr = 0;

    private static final String LOG_TAG = "MainActivity";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};

    private LinearLayout layout_menu;
    private LinearLayout layout_background;
    private LinearLayout layout_under_background;
    private CardView layout_card;

    private RetrofitAPI apiService;
    private ImageButton map_close1;
    private ImageButton map_close2;
    private RecyclerView recyclerViewMenu;
    private LinearLayout map;

    private TextView menu_title;
    private TextView menu_popular;
    private LottieAnimationView menu_popular_lottie;
    private TextView back_title;
    private TextView back_popular;
    private LottieAnimationView menu_back_lottie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Log.d(TAG, "onCreate");
        layout_menu = (LinearLayout) findViewById(R.id.layout_menu_map);
        layout_background = (LinearLayout) findViewById(R.id.layout_background_map);
        layout_card = (CardView)findViewById(R.id.cardview_activity_map);
        map_close1 = (ImageButton) findViewById(R.id.btn_map_close1);
        map_close2 = (ImageButton) findViewById(R.id.btn_map_close2);
        map = (LinearLayout)findViewById(R.id.layout_map_main);
        layout_under_background= (LinearLayout) findViewById(R.id.layout_under_background_map);
        recyclerViewMenu = (RecyclerView)findViewById(R.id.recycler_map_menu_origin);
//        menu_title = (TextView) findViewById(R.id.activity_map_place_title);
//        menu_popular = (TextView)findViewById(R.id.activity_first_place_popular) ;
//        menu_popular_lottie = (LottieAnimationView) findViewById(R.id.lottie_activity_first_place_popular);
//        back_title = (TextView) findViewById(R.id.activity_map_background_title);
//        back_popular= (TextView)findViewById(R.id.activity_map_background_popular) ;
//        menu_back_lottie= (LottieAnimationView) findViewById(R.id.activity_map_background_lottie_popular);



        positionList = new ArrayList<>();
        positionIdList = new ArrayList<>();

        /**********************************************************
         *  액티비티 동작
         * ********************************************************/

        //툴바 옆, setting 관련 코드
        ImageButton btn_setting = (ImageButton) findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, SettingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein_right, R.anim.stay);

            }
        });

        //menu 관련 코드
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);

        new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withMenuLayout(R.layout.activity_menu)
                .inject();

        //메뉴클릭 리스너 등록
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setSelectedItemId(R.id.thirdItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());

        /**********************************************************
         *  여기까지
         * ********************************************************/

        map_close1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.nfc2_anim); //애니메이션설정파일
                layout_background.startAnimation(anim);
                layout_menu.setClickable(false);
                layout_menu.setVisibility(View.GONE);
                layout_background.setClickable(false);
                layout_background.setVisibility(View.GONE);

                //menu_btn.setVisibility(View.VISIBLE);
                map.setClickable(true);
                background_ctr = 0;
                main_ctr = 0;
                marker_ctr = 0;
                mapView.removeAllCircles();
                mapView.getCircles();

            }
        });

        map_close2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.nfc2_anim); //애니메이션설정파일
                layout_menu.startAnimation(anim);
                layout_background.startAnimation(anim);
                layout_menu.setClickable(false);
                layout_menu.setVisibility(View.GONE);
                layout_background.setClickable(false);
                layout_background.setVisibility(View.GONE);

                //menu_btn.setVisibility(View.VISIBLE);
                map.setClickable(true);
                background_ctr = 0;
                main_ctr = 0;
                marker_ctr = 0;
                mapView.removeAllCircles();
                mapView.getCircles();
            }
        });

        layout_under_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(main_ctr == 1){
                    layout_background.setClickable(false);
                    layout_background.setVisibility(View.GONE);
                    layout_menu.setClickable(true);
                    layout_menu.setVisibility(View.VISIBLE);
                    anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.nfc_anim); //애니메이션설정파일
                    layout_menu.startAnimation(anim);
                    map.setClickable(false);
                    main_ctr = 2;
                    Log.d("main counter : ", Integer.toString(main_ctr));
                    mapView.removeAllCircles();

                }

            }
        });


        mapView = new MapView(this);
        mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        mapView.setMapViewEventListener(this);

        // POIItemEventListener interface 리스너
        mapView.setPOIItemEventListener(this);
        // 고해상도 지도 타일 사용
        mapView.setHDMapTileEnabled(true);

        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        }else {

            checkRunTimePermission();
        }

        //Map 마커및 혼잡도 원 등록
        getPositionList();


        // 현재 내 위치 찾기
        nowOnMap = findViewById(R.id.btn_now);
        nowOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 현위치 트래킹 이벤트를 통보
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                //MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
                //Log.d("현재위치",Double.toString(mapView.));
                //현재 좌표 따오는거 필요
                //mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(nlatitude, nlongitude), 1, true);
            }
        });




    }

    // 여기도 수정..
    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Intent intent = new Intent(MapActivity.this, MainActivity.class);
            switch(menuItem.getItemId())
            {

                case R.id.firstItem:
                    intent.putExtra("key", "firstItem");
                    startActivity(intent);
                    break;

                case R.id.secondItem:
                    intent.putExtra("key", "secondItem");
                    startActivity(intent);
                    break;

                case R.id.thirdItem:
                    break;

                case R.id.fourthItem:
                    intent.putExtra("key", "fourthItem");
                    startActivity(intent);
                    break;

                case R.id.fifthItem:
                    intent.putExtra("key", "fifthItem");
                    startActivity(intent);
                    break;

            }
            return true;
        }
    }

    /** 모든 장소 가져오기 API **/
    public void getPositionList() {

        apiService = RetrofitClient.getApiService();
        allPlaceCall = apiService.getAllPosition();
        allPlaceCall.enqueue(new Callback<PositionResponse>() {


            @Override
            public void onResponse(Call<PositionResponse> call, Response<PositionResponse> response) {
                if(response.isSuccessful()) {
                    Log.d(TAG, "성공");
                    List<PositionResponse.Result> positionList = response.body().getResult();
                    mapInit = new MapInit(getApplicationContext(), positionList);

                    for (PositionResponse.Result result : positionList) {
                        positionIdList.add(result.getPlaceId());
                    }
                    mapInit.init(mapView);
                    //mapInit.startInit(mapView,0);
                }
                else {
                    Log.d(TAG, "에러발생 .." + response.message());
                }
            }

            @Override
            public void onFailure(Call<PositionResponse> call, Throwable t) {
                Log.d(TAG, "onFalilure .. 카테고리 불러오기 연동 실패 ..., 메세지 : " + t.getMessage());
            }

        });
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {
        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
        this.nlatitude = mapPointGeo.latitude;
        this.nlongitude = mapPointGeo.longitude;
        Log.i(LOG_TAG, String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
    }

    private ArrayList<DataMoreInfo> list_place;
    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        MapCircle[] circles = new MapCircle[mapInit.getListSize()];
        list_place=new ArrayList<>();
        Log.d("tag","clicked");
        String name = mapPOIItem.getItemName();
        Log.d("tag","name: "+name);
        marker_ctr += 1;
        mapView.setMapCenterPointAndZoomLevel(mapPOIItem.getMapPoint(), 2, false);
        mapInit.drawMapCircle(mapView);
        getPositionMapMenu(name,list_place);
//        menu_title.setText(name);
//       back_title.setText(name);


        if(marker_ctr >= 2){
            showDialogMenu(name,mapView,mapPOIItem);

        }


    }

    public ArrayList<DataMoreInfo> getPositionMapMenu(String isName, ArrayList<DataMoreInfo> list_place) {

        apiService = RetrofitClient.getApiService();
        allPlaceCall = apiService.getAllPosition();
        allPlaceCall.enqueue(new Callback<PositionResponse>() {


            @Override
            public void onResponse(Call<PositionResponse> call, Response<PositionResponse> response) {
                if(response.isSuccessful()) {
                    Log.d(TAG, "성공");
                    List<PositionResponse.Result> positionList = response.body().getResult();


                    for (PositionResponse.Result result : positionList) {
                        positionIdList.add(result.getPlaceId());
                    }
                    settingMenuPage(positionList, isName);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerViewMenu.setAdapter(new RecyclerFragFirstThemeAdapter(MapActivity.this, list_place));
                            recyclerViewMenu.setLayoutManager(new LinearLayoutManager(MapActivity.this, RecyclerView.VERTICAL,false));
                        }
                    });
                }
                else {
                    Log.d(TAG, "에러발생 .." + response.message());
                }
            }

            @Override
            public void onFailure(Call<PositionResponse> call, Throwable t) {
                Log.d(TAG, "onFalilure .. 카테고리 불러오기 연동 실패 ..., 메세지 : " + t.getMessage());
            }

        });
        return list_place;
    }
    public ArrayList<DataMoreInfo> settingMenuPage(List<PositionResponse.Result> resultList, String isName){
        int index;
        String name;
        String themaName;
        long placeid;
        Integer popular;
        PositionResponse.Result resultListIndex;

        for (index = 0; index < resultList.size(); index++) {
            resultListIndex = resultList.get(index);
            name = resultListIndex.getName();
            themaName = resultListIndex.getThema();
            placeid = resultListIndex.getPlaceId();
            popular = resultListIndex.getPopular();

            if(isName.equals(name)){
                list_place.add(new DataMoreInfo(name,"아이템",R.drawable.yeouido ,false,popular,placeid));
            }

            Log.d("fragfirstinfo", "placeID: " + placeid + ", Popular: " + popular + ", Name: " + name + ", ThemaName: " + themaName);
        }

        return list_place;
    }
    private Animation anim;
    private void showDialogMenu(String name,MapView mapView, MapPOIItem mapPOIItem){

        if (background_ctr == 0) {
            layout_background.setClickable(true);
            layout_background.setVisibility(View.VISIBLE);
            anim = AnimationUtils.loadAnimation(this, R.anim.nfc_anim); //애니메이션설정파일
            layout_background.startAnimation(anim);
            map.setClickable(false);
            background_ctr = 1;
            main_ctr = 1;
            Log.d("layout counter : ", Integer.toString(background_ctr));

        }
//        if (background_ctr == 0) {
//            layout_menu.setVisibility(View.VISIBLE);
//            layout_background.setVisibility(View.VISIBLE);
//            anim = AnimationUtils.loadAnimation(this, R.anim.nfc_anim); //애니메이션설정파일
//            layout_menu.startAnimation(anim);
//            map.setClickable(false);
//            background_ctr = 1;
//            main_ctr = 1;
//            Log.d("layout counter : ", Integer.toString(background_ctr));
//            mapView.removeAllCircles();
//        }

    }
    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if (check_result) {
                Log.d("@@@", "start");
                //위치 값을 가져올 수 있음
                mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                    Toast.makeText(MapActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                } else {

                    Toast.makeText(MapActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mapView.setShowCurrentLocationMarker(false);
    }


    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MapActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음
//            mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MapActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자에rp 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MapActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MapActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        Log.d("menu","singleTouch");
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {
        Log.d("menu","DoubleTouch");
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {
        Log.d("menu","LongTouch");
    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
        Log.d("menu","DragStarted");
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
        Log.d("menu","DragEnded");
    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
        Log.d("menu","DragFinished");
    }
    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}