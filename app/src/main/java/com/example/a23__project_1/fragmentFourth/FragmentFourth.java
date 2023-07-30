package com.example.a23__project_1.fragmentFourth;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a23__project_1.MainActivity;
import com.example.a23__project_1.R;
import com.example.a23__project_1.SharedViewModel;
import com.example.a23__project_1.fragmentSecond.PlaceListAdapter;
import com.example.a23__project_1.request.LikeRequest;
import com.example.a23__project_1.response.LikeResponse;
import com.example.a23__project_1.response.PlaceAllResponse;
import com.example.a23__project_1.retrofit.RetrofitAPI;
import com.example.a23__project_1.retrofit.RetrofitClient;
import com.example.a23__project_1.fragmentThird.FragmentThird;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Inflater를 통해 각 프래그먼트에 해당하는 레이아웃 리소스 ID 값을 통해 생성된 View를 반환
public class FragmentFourth extends Fragment {
    private static final String TAG = "FragmentFourth";
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "userInfo";
    private String name = "", email="";
    private RetrofitAPI apiService;
    private List<PlaceAllResponse.Result> likeList;
    private LikeListAdapter likeListAdapter;
    private HashMap<Long, String> themaIdNameMap;
    TextView tv_name, tv_login;
    Button btn_cal;
    RecyclerView recyclerView;
    private Dialog cctvDialog, infoDialog;
    private SharedViewModel model;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth, container, false);
        tv_name = view.findViewById(R.id.tv_name);
        tv_login = view.findViewById(R.id.tv_login);
        btn_cal = view.findViewById(R.id.btn_calendar);
        btn_cal.setOnClickListener(checkPlanClickListener);

        model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        // sharedPreferences
        sharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name", "null");
        email = sharedPreferences.getString("email", "null");

        // ArrayList 초기화
        likeList = new ArrayList<>();
        themaIdNameMap = new HashMap<>();

        // 리사이클러뷰 설정
        recyclerView = view.findViewById(R.id.recycler_likePlace);

        //로그인 되어있지 않은 경우
        if(name.equals("null")) {
            tv_login.setVisibility(View.VISIBLE);
            tv_name.setVisibility(View.GONE);
            btn_cal.setVisibility(View.GONE);
        } else {
            // 로그인 되어있는 경우
            tv_name.setText(name + "님의 찜 리스트 항목");
            tv_name.setVisibility(View.VISIBLE);
            tv_login.setVisibility(View.GONE);
            btn_cal.setVisibility(View.VISIBLE);
            getLikePlace();
        }

        // CCTV Dialog 설정
        cctvDialog = new Dialog(requireContext());
        cctvDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        cctvDialog.setContentView(R.layout.dialog_cctv_webview);

        // 자세히 보기 (InfoDialog) 설정
        infoDialog = new Dialog(requireContext());
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        infoDialog.setContentView(R.layout.dialog_likeplace_info);

        return view;
    }

    /** 일정 확인하기 버튼 클릭 시 **/
    View.OnClickListener checkPlanClickListener = v -> {
        Intent intent = new Intent(getActivity(), PlanListActivity.class);
        startActivity(intent);
    };

    /** 찜리스트 가져오기 API **/
    public void getLikePlace() {
        apiService = RetrofitClient.getApiService();
        Call<PlaceAllResponse> call = apiService.getAllLikes(email);

        call.enqueue(new Callback<PlaceAllResponse>() {
            @Override
            public void onResponse(Call<PlaceAllResponse> call, Response<PlaceAllResponse> response) {
                if(response.isSuccessful()) {
                    likeList = response.body().getResult();
                    likeListAdapter = new LikeListAdapter(requireContext(), likeList);

                    for (PlaceAllResponse.Result result : likeList) {
                        // thema
                        themaIdNameMap.put(result.getPlaceId(), result.getName());
                    }

                    /** CCTV 버튼 클릭 리스너 설정 **/
                    likeListAdapter.setOnCCTVClickListener(new PlaceListAdapter.cctvClickListener() {
                        @Override
                        public void cctvButtonClick(List<PlaceAllResponse.Result> list , int position) {
                            /** 모달 띄우기 **/
                            String url = list.get(position).getCctv();
                            showCCTV(url);

                        }
                    });

                    /** 찜 버튼 클릭 리스너 설정 **/
                    likeListAdapter.setOnLikeClickListener(new PlaceListAdapter.likeClickListener() {
                        @Override
                        public void likeButtonClick(List<PlaceAllResponse.Result> list, int position) {
                            // 버튼 클릭했을 때 API 통신
                            String placeName = list.get(position).getName();
                            Log.d(TAG, "placeName : " + placeName);
                            // Position 반환
                            Long placeId = searchPlaceId(themaIdNameMap, placeName);
                            Log.d(TAG, "place_id 값1 : " + placeId);

                            postLike(list, position, placeId);
                        }
                    });

                    /** 자세히 보기 버튼 클릭 리스너 설정 **/
                    likeListAdapter.setOnMapClickListener(new PlaceListAdapter.mapClickListener() {
                        @Override
                        public void mapButtonClick(List<PlaceAllResponse.Result> list, int position) {
                            String placeName = list.get(position).getName();
                            String rec = list.get(position).getRec();

                            // 모달 띄우기
                            getInfoModal(placeName, rec);

//                            /** 이름 넘기기 **/
//                            Toast.makeText(requireContext(), placeName+"클릭!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    recyclerView.setAdapter(likeListAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
                }
                else {
                    Log.d(TAG, "에러발생(로그인) .." + response.message());
                }
            }

            @Override
            public void onFailure(Call<PlaceAllResponse> call, Throwable t) {
                Log.d(TAG, "onFalilure .. 로그인 시 장소리스트 연동 실패 ..., 메세지 : " + t.getMessage());
            }
        });
    }

    /** 자세히 보기 모달 띄우기 **/
    private void getInfoModal(String name, String rec) {
        infoDialog.show();

        Button close = infoDialog.findViewById(R.id.btn_close);
        Button makePlan = infoDialog.findViewById(R.id.btn_plan);
        Button lookMap = infoDialog.findViewById(R.id.btn_map);
        TextView tv_name = infoDialog.findViewById(R.id.tv_name);
        TextView recommend = infoDialog.findViewById(R.id.tv_rec);
        tv_name.setText(name);
        recommend.setText(rec);

        // 뒤로 가기
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoDialog.dismiss();
            }
        });

        // 일정 생성하기 버튼 클릭 시
        makePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoDialog.dismiss();
                Intent intent = new Intent(getActivity(), MakePlanActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
        model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        // 지도로 보기 버튼 클릭 시
        lookMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setfromWhere(3);
                // 이름으로 placeID ??
                int placeId = Long.valueOf(searchPlaceId(themaIdNameMap, name)).intValue();
                model.selectItem(placeId, 3);
                model.setFromWhere(3);
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.navigationView);
                bottomNavigationView.setSelectedItemId(R.id.thirdItem);
                infoDialog.dismiss();
            }
        });
    }

    /** 해당하는 찜 리스트에 대한 position 가져오는 메서드 **/
    public Long searchPlaceId(HashMap<Long, String> map, String name) {
        Set<Map.Entry<Long,String>> entrySet = map.entrySet();
        for (Map.Entry<Long, String> entry : entrySet) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        // 해당하는 정보가 없으면 -1 리턴
        return -1L;
    }

    /** 찜 버튼 API 통신 **/
    public void postLike(List<PlaceAllResponse.Result> list, int pos, Long place_id) {
        apiService = RetrofitClient.getApiService();
        String email = sharedPreferences.getString("email", "null");
        if (email.equals("null")) {
            /** 다이얼로그 출력해주기 **/
            Toast.makeText(requireContext(), "로그인을 먼저 진행해주세요...", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d(TAG, "place_id 값2 : " + place_id);
        LikeRequest.Member member = new LikeRequest.Member(email);
        LikeRequest.Place place = new LikeRequest.Place(place_id);
        LikeRequest request = new LikeRequest(member, place);
        Call<LikeResponse> call = apiService.doLike(request);
        call.enqueue(new Callback<LikeResponse>() {
            @Override
            public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {
                if(response.isSuccessful()) {
                    /** 요청에 성공했을 때 **/
                    if(response.body().getCode() == 200 && response.body().getMessage().contains("성공")) {
                        Object result = response.body().getResult();
                        // 문자열로 온 경우
                        if (result instanceof String) {
                            String resultString = (String) result;
                            // 찜리스트 취소하는 경우
                            if(resultString.equals("delete")) {
                                Toast.makeText(requireContext(), "찜 리스트에 정상적으로 취소되었습니다.", Toast.LENGTH_SHORT).show();
                                list.get(pos).setLikeYn(0);
                                Log.d(TAG, String.valueOf(list.get(pos).getLikeYn()));

                            }
                        }
                        else {
                            //찜 리스트 추가하는 경우
                            Toast.makeText(requireContext(), "찜 리스트에 정상적으로 추가되었습니다.", Toast.LENGTH_SHORT).show();
                            list.get(pos).setLikeYn(1);
                            Log.d(TAG, String.valueOf(list.get(pos).getLikeYn()));
                        }
                        /** 변경 감지 **/
                        likeListAdapter.notifyDataSetChanged();
                    }
                }
                else {
                    Log.d(TAG, "찜 버튼 연동 실패...");
                    Log.d(TAG, "오류 메세지 : " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<LikeResponse> call, Throwable t) {
                Log.d(TAG, "찜 버튼 연동 실패2...");
                Log.d(TAG, "오류 메세지2 : " + t.getMessage());
            }
        });
    }

    /** CCTV 모달 띄우기 **/
    public void showCCTV(String url) {
        cctvDialog.show();

        Button close = cctvDialog.findViewById(R.id.btn_close);
        WebView webView = cctvDialog.findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClientClass());

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cctvDialog.dismiss();
            }
        });

    }

    /** CCTV 웹뷰 구현 **/
    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            view.loadUrl(url);
            return true;
        }
    }
}