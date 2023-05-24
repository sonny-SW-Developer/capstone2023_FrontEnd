package com.example.a23__project_1;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


public class MapActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapView.MapViewEventListener, MapView.POIItemEventListener {
    private MapView mapView;
    private ViewGroup mapViewContainer;
    private MapPoint mapPoint;
    private MapView mMapView;
    private double nlatitude;
    private double nlongitude;
    private ImageButton nowOnMap;
    private MapPOIItem[] mCustomMarker;

    private static final String LOG_TAG = "MainActivity";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET);

        int permission2 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        int permission3 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        // 권한이 열려있는지 확인
        if (permission == PackageManager.PERMISSION_DENIED || permission2 == PackageManager.PERMISSION_DENIED || permission3 == PackageManager.PERMISSION_DENIED) {
            // 마쉬멜로우 이상버전부터 권한을 물어본다
            if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 권한 체크(READ_PHONE_STATE의 requestCode를 1000으로 세팅
                requestPermissions(
                        new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        1000);
            }
            return;
        }

        mapView = new MapView(this);
        mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        mapView.setMapViewEventListener(this);
        // 현위치 트래킹 이벤트를 통보
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
        // POIItemEventListener interface 리스너
        mapView.setPOIItemEventListener(this);
        // 고해상도 지도 타일 사용
        mapView.setHDMapTileEnabled(true);

        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        }else {

            checkRunTimePermission();
        }


        //
//        onCurrentLocationUpdate(mapView,mapPoint,5);
//        mapView.setMapCenterPoint(mapPoint,true);
//        // 현위치 표시 아이콘 on
//        mapView.setShowCurrentLocationMarker(true);

//        mapView.setCurrentLocationRadius(3);
//        mapView.setCurrentLocationRadiusFillColor(0xFFFFFF);
//        mapView.setCurrentLocationRadiusStrokeColor(0xFFFFFF);

        /* 마커를 표시하자 */
        MapPOIItem customMarker = new MapPOIItem();
        MapPoint mapPoint= MapPoint.mapPointWithGeoCoord(37.480426, 126.900177); //마커 표시할 위도경도
        customMarker.setItemName("우리집 근처당");
        customMarker.setTag(1);
        customMarker.setMapPoint(mapPoint);
        customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
        customMarker.setCustomImageResourceId(R.drawable.custom_marker_red); // 마커 이미지.
        customMarker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
        customMarker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
 //       mapView.addPOIItem(customMarker);

//        /* 폴리라인을 그리자 */
//        MapPolyline polyline = new MapPolyline();
//        polyline.setTag(1000);
//        polyline.setLineColor(Color.argb(128, 255, 51, 0)); // Polyline 컬러 지정.
//
//        // Polyline 좌표 지정.
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.479928, 126.900169));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.480624,126.900735));
//        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.481667,126.900713));
//
//        // Polyline 지도에 올리기.
//        mapView.addPolyline(polyline);
//
//        // 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정.
//        MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
//        int padding = 100; // px
//        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));

        // 혼잡도 표시 원 추가
        Places[] places = new Places[5];
        places[0] = new Places("강남역","혼잡",37.497952,127.027619,"null","역");
        places[1] = new Places("롯데월드","보통",37.51114877018953, 127.09793885391981,"null","공원");
        places[2] = new Places("경복궁","한적",37.578158285970645, 126.97637354819015 ,"null","관광");
        places[3] = new Places("이태원","보통",37.53912917638327,126.99190986859745 ,"null","음식점");
        places[4] = new Places("김포공항","혼잡", 37.565378514599345,126.80070932089309 ,"null","공항");

        Log.d("places0",places[0].getName());
        Log.d("places1",places[1].getName());
        Log.d("places2",places[2].getName());
        Log.d("places3",places[3].getName());
        Log.d("places4",places[4].getName());

        addCircles(places);
        addMarkers(places);

        // 현재 내 위치 찾기
        nowOnMap = findViewById(R.id.btn_now);
        nowOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
                //현재 좌표 따오는거 필요
                mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(nlatitude, nlongitude), 1, true);
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

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

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

    private void addCircles(Places[] places) {

        MapCircle[] circle = new MapCircle[5];

        for(int i =0; i<places.length; i++){
            circle[i] = new MapCircle(
                    MapPoint.mapPointWithGeoCoord(places[i].getLatitude(), places[i].getLongitude()), // center
                    400, // radius
                    Color.argb(128, 0, 0, 0), // strokeColor
                    Color.argb(128, 0, 255, 0) // fillColor
            );

            if( "혼잡".equals(places[i].getCongestion()) ) { circle[i].setFillColor(0x60FF0000); }
            else if( "보통".equals(places[i].getCongestion()) ) { circle[i].setFillColor(0x60FFFF00); }
            else{ circle[i].setFillColor(0x60008000);}
            circle[i].setTag(i);
            mapView.addCircle(circle[i]);

        }

        // 지도뷰의 중심좌표와 줌레벨을 Circle이 모두 나오도록 조정.
//        MapPointBounds[] mapPointBoundsArray = { circle.getBound(), circle2.getBound() };
//        MapPointBounds mapPointBounds = new MapPointBounds(mapPointBoundsArray);
//        int padding = 50; // px
//        mMapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
    }

    private void addMarkers(Places[] places) {

        mCustomMarker = new MapPOIItem[5];

        for(int i =0; i<places.length; i++){

            mCustomMarker[i] = new MapPOIItem();
            mCustomMarker[i].setItemName(places[i].getName());
            mCustomMarker[i].setMarkerType(MapPOIItem.MarkerType.CustomImage);
            mCustomMarker[i].setTag(i);
            mCustomMarker[i].setMapPoint(MapPoint.mapPointWithGeoCoord(places[i].getLatitude(), places[i].getLongitude()));
            mCustomMarker[i].setCustomImageAutoscale(true); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
            mCustomMarker[i].setCustomImageAnchor(0.0f, 0.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.

            if( "공항".equals(places[i].getTheme()) ) { mCustomMarker[i].setCustomImageResourceId(R.drawable.icon_airplane);  }
            else if( "음식점".equals(places[i].getTheme()) ) { mCustomMarker[i].setCustomImageResourceId(R.drawable.custom_marker_red); }
            else if( "공원".equals(places[i].getTheme()) ) { mCustomMarker[i].setCustomImageResourceId(R.drawable.custom_marker_red); }
            else if( "역".equals(places[i].getTheme()) ) { mCustomMarker[i].setCustomImageResourceId(R.drawable.custom_marker_red); }
            else if( "관광".equals(places[i].getTheme()) ) { mCustomMarker[i].setCustomImageResourceId(R.drawable.icon_ancient); }
            else{ }

            mapView.addPOIItem(mCustomMarker[i]);

        }

        // 지도뷰의 중심좌표와 줌레벨을 Circle이 모두 나오도록 조정.
//        MapPointBounds[] mapPointBoundsArray = { circle.getBound(), circle2.getBound() };
//        MapPointBounds mapPointBounds = new MapPointBounds(mapPointBoundsArray);
//        int padding = 50; // px
//        mMapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
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
        mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mMapView.setShowCurrentLocationMarker(false);
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
//            mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MapActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
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

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}