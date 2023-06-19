package com.example.a23__project_1.leftMenuBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.a23__project_1.R;
import com.example.a23__project_1.data.DataMoreInfo;
import com.example.a23__project_1.response.PositionResponse;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

public class MapInit extends AppCompatActivity {
    private final Context mainContext;
    private List<PositionResponse.Result> resultList = new ArrayList<>();
    private List<String> themaList;
    private SharedPreferences sharedPreferences;
    //private static final String PREF_NAME = "userInfo";
    private MapPOIItem[] initCustomMarker;
    private MapCircle[] circle;

    public MapInit(Context mainContext, List<PositionResponse.Result> result) {
        this.mainContext = mainContext;
        this.resultList = result;
    }
    public MapPOIItem[] getMapPoIItem(){
        return initCustomMarker;
    }

    public MapCircle[] getMapCircle(){
        return circle;
    }
    public int getListSize(){
        return resultList.size();
    }
    public void init(MapView init_mapView) {

        int index;
        String name;
        double latitude;
        double longitude;
        String themaName;
        long placeid;
        Integer popular;
        long placeid_before = -1;

        PositionResponse.Result resultListIndex;
        MapView mapView = init_mapView;
        initCustomMarker = new MapPOIItem[resultList.size()];
        circle = new MapCircle[resultList.size()];

        for (index = 0; index < resultList.size(); index++) {
            resultListIndex = resultList.get(index);

            name = resultListIndex.getName();
            latitude = resultListIndex.getLatitude();
            longitude = resultListIndex.getLongitude();
            themaName = resultListIndex.getThema();
            placeid = resultListIndex.getPlaceId();
            popular = resultListIndex.getPopular();

            //Log.d("init", "placeID: "+placeid+", Popular: "+popular+", Name: " + name + ", Latitude: " + latitude + ", Longitude: " + longitude + ", ThemaName: " + themaName);

            /*****************************************************
             *   마커 그리기
             *
             *******************************************************/
            initCustomMarker[index] = new MapPOIItem();
            initCustomMarker[index].setItemName(name);
            initCustomMarker[index].setMarkerType(MapPOIItem.MarkerType.CustomImage);
            initCustomMarker[index].setTag((int)placeid);
            initCustomMarker[index].setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
            // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
            initCustomMarker[index].setCustomImageAutoscale(true);
            // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
            initCustomMarker[index].setCustomImageAnchor(0.0f, 0.0f);
            initCustomMarker[index].setShowCalloutBalloonOnTouch(true);
            initCustomMarker[index].setShowDisclosureButtonOnCalloutBalloon(false);
            if(placeid!=placeid_before){

                if (themaName.contains("상업"))
                    initCustomMarker[index].setCustomImageResourceId(R.drawable.icon_business_park);
                else if (themaName.contains("공원"))
                    initCustomMarker[index].setCustomImageResourceId(R.drawable.icon_park);
                else if (themaName.contains("스파"))
                    initCustomMarker[index].setCustomImageResourceId(R.drawable.icon_spa);
                else if (themaName.contains("쇼핑몰"))
                    initCustomMarker[index].setCustomImageResourceId(R.drawable.icon_shoppingmall);
                else if (themaName.contains("거리"))
                    initCustomMarker[index].setCustomImageResourceId(R.drawable.icon_street);
                else if (themaName.contains("백화점"))
                    initCustomMarker[index].setCustomImageResourceId(R.drawable.icon_baekwha);
                else if (themaName.contains("관광지"))
                    initCustomMarker[index].setCustomImageResourceId(R.drawable.icon_trip);
                else if (themaName.contains("지하철"))
                    initCustomMarker[index].setCustomImageResourceId(R.drawable.icon_subway);
                else if (themaName.contains("마트"))
                    initCustomMarker[index].setCustomImageResourceId(R.drawable.icon_supermarket);
                else if (themaName.contains("공항"))
                    initCustomMarker[index].setCustomImageResourceId(R.drawable.icon_airport);

                mapView.addPOIItem(initCustomMarker[index]);

            }

            placeid_before = placeid;

        }
        // 지도뷰의 중심좌표와 줌레벨을 POIItem이 모두 나오도록 조정.
        mapView.fitMapViewAreaToShowAllPOIItems();
    }
    public void drawMapCircle(MapView init_mapView) {

        int index;
        String name;
        double latitude;
        double longitude;
        String themaName;
        long placeid;
        Integer popular;
        long placeid_before = -1;

        PositionResponse.Result resultListIndex;
        MapView mapView = init_mapView;
        initCustomMarker = new MapPOIItem[resultList.size()];
        circle = new MapCircle[resultList.size()];

        for (index = 0; index < resultList.size(); index++) {
            resultListIndex = resultList.get(index);

            name = resultListIndex.getName();
            latitude = resultListIndex.getLatitude();
            longitude = resultListIndex.getLongitude();
            themaName = resultListIndex.getThema();
            placeid = resultListIndex.getPlaceId();
            popular = resultListIndex.getPopular();

            /*****************************************************
             *   혼잡도 원 그리기
             *
             ******************************************************/

            circle[index]  = new MapCircle(
                    MapPoint.mapPointWithGeoCoord(latitude, longitude), // center
                    400, // radius
                    Color.argb(128, 0, 0, 0), // strokeColor
                    Color.argb(50, 0, 0, 0) // fillColor
            );

            switch (popular) {
                case 0:
                    //vh.traffic.setText("로딩중...");
                    mapView.addCircle(circle[index]);
                    break;
                case 1:
                    //vh.traffic.setText("여유");
                    circle[index].setFillColor(0x60008000);
                    mapView.addCircle(circle[index]);
                    break;
                case 2:
                    circle[index].setFillColor(0x60FFFF00);
                    mapView.addCircle(circle[index]);
                    break;
                case 3:
                    circle[index].setFillColor(0x60FF8000);
                    mapView.addCircle(circle[index]);
                    break;
                case 4:
                    circle[index].setFillColor(0x60FF0000);
                    mapView.addCircle(circle[index]);
                    break;
            }

        }


    }

    public void startInit(MapView init_mapView, int position){
        MapView mapView = init_mapView;
        MapPOIItem initCustomMarker = new MapPOIItem();
        String themaName = resultList.get(position).getThema();

        Log.d("init",themaName);


        /*****************************************************
         *   마커 그리기
         *
         *******************************************************/
        initCustomMarker.setItemName(resultList.get(position).getName());
        initCustomMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        initCustomMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(resultList.get(position).getLatitude(), resultList.get(position).getLongitude()));
        // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
        initCustomMarker.setCustomImageAutoscale(true);
        // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
        initCustomMarker.setCustomImageAnchor(0.0f, 0.0f);
        initCustomMarker.setShowCalloutBalloonOnTouch(false);

        if (themaName.contains("상업"))
            initCustomMarker.setCustomImageResourceId(R.drawable.ic_business_park);
        else if (themaName.contains("공원"))
            initCustomMarker.setCustomImageResourceId(R.drawable.ic_park);
        else if (themaName.contains("스파"))
            initCustomMarker.setCustomImageResourceId(R.drawable.ic_spa);
        else if (themaName.contains("쇼핑몰"))
            initCustomMarker.setCustomImageResourceId(R.drawable.ic_shoppingmall);
        else if (themaName.contains("거리"))
            initCustomMarker.setCustomImageResourceId(R.drawable.ic_street);
        else if (themaName.contains("백화점"))
            initCustomMarker.setCustomImageResourceId(R.drawable.ic_baekwha);
        else if (themaName.contains("관광지"))
            initCustomMarker.setCustomImageResourceId(R.drawable.ic_trip);
        else if (themaName.contains("지하철"))
            initCustomMarker.setCustomImageResourceId(R.drawable.ic_subway);
        else if (themaName.contains("마트"))
            initCustomMarker.setCustomImageResourceId(R.drawable.ic_supermarket);
        else if (themaName.contains("공항"))
            initCustomMarker.setCustomImageResourceId(R.drawable.ic_airport);

        mapView.addPOIItem(initCustomMarker);


        /*****************************************************
         *   혼잡도 원 그리기
         *
         ******************************************************/

        MapCircle circle  = new MapCircle(
                MapPoint.mapPointWithGeoCoord(resultList.get(position).getLatitude(), resultList.get(position).getLongitude()), // center
                400, // radius
                Color.argb(128, 0, 0, 0), // strokeColor
                Color.argb(200, 0, 0, 0) // fillColor
        );

        int traffic_rate = resultList.get(position).getPopular();
        switch (traffic_rate) {
            case 0:
                //vh.traffic.setText("로딩중...");
                circle.setFillColor(0x60000000);
                mapView.addCircle(circle);
                Log.d("popular","혼잡도: "+traffic_rate);
                break;
            case 1:
                //vh.traffic.setText("여유");
                circle.setFillColor(0x60008000);
                mapView.addCircle(circle);
                break;
            case 2:
                // 보통
                circle.setFillColor(0x60FFFF00);
                mapView.addCircle(circle);
                break;
            case 3:
                //혼잡
                circle.setFillColor(0x60FF8000);
                mapView.addCircle(circle);
                break;
            case 4:
                //매우 혼잡
                circle.setFillColor(0x60FF0000);
                mapView.addCircle(circle);
                break;
        }

    }

}
