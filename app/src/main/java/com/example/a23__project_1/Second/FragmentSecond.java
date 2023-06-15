package com.example.a23__project_1.Second;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a23__project_1.R;
import com.example.a23__project_1.request.LikeRequest;
import com.example.a23__project_1.response.LikeResponse;
import com.example.a23__project_1.response.PlaceAllResponse;
import com.example.a23__project_1.response.ThemaAllResponse;
import com.example.a23__project_1.retrofit.RetrofitAPI;
import com.example.a23__project_1.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Inflater를 통해 각 프래그먼트에 해당하는 레이아웃 리소스 ID 값을 통해 생성된 View를 반환
public class FragmentSecond extends Fragment {
    private static final String TAG = "FragmentSecond";
    EditText input;
    ImageButton search;
    RecyclerView recycler_category, recycler_place;
    private Context context;
    private categoryAdapter categoryAdapter;
    private PlaceListAdapter placeListAdapter;
    private Call<ThemaAllResponse> allThemaCall;
    private Call<PlaceAllResponse> allPlaceCall;
    private RetrofitAPI apiService;
    private List<Long> themaIdList;
    private List<String> themaList;
    private List<PlaceAllResponse.Result> placeList, searchList; // 모든 장소 리스트 및 검색 리스트
    private List<Long> placeIdList;

    private Call<LikeResponse> likeCall;

    private Dialog cctvDialog;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "userInfo";


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        input = view.findViewById(R.id.et_input);
        search = view.findViewById(R.id.btn_search);
        search.setOnClickListener(searchClickListener);
        search.setClickable(false);

        // 카테고리 리스트 가져오기
        recycler_category = view.findViewById(R.id.recycler_category);
        // 카테고리 모든 장소 가져오기
        recycler_place = view.findViewById(R.id.recycler_place);
        themaIdList = new ArrayList<>();
        themaList = new ArrayList<>();
        placeList = new ArrayList<>();
        searchList = new ArrayList<>();
        placeIdList = new ArrayList<>();

        // cctv 모달
        cctvDialog = new Dialog(requireContext());
        cctvDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        cctvDialog.setContentView(R.layout.dialog_cctv_webview);

        // sharedPreference로 로그인 여부 판단.
        sharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        getCategoryList();
        getPlaceList();

        editsetTextWatcher(input);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getCategoryList();
        getPlaceList();
    }

    /** 검색 버튼 클릭 시 **/
    View.OnClickListener searchClickListener = v -> {
        clearSearchList(input);
    };

    /** 검색 가능하게 만들기 **/
    private void editsetTextWatcher(EditText et) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                clearSearchList(et);
            }
        });
    }

    /** 장소 검색 리스트 초기화 **/
    public void clearSearchList(EditText et) {
        String searchText = et.getText().toString();
        searchList.clear();

        if(searchText.equals(""))
            placeListAdapter.setItems(placeList);
        else {
            for (int i=0; i<placeList.size(); i++) {
                if(placeList.get(i).getName().contains(searchText)) {
                    searchList.add(placeList.get(i));
                }
                placeListAdapter.setItems(searchList);
            }
        }
        search.setClickable(true);
    }

    /** 카테고리 가져오는 API 통신 **/
    private void getCategoryList() {
        apiService = RetrofitClient.getApiService();
        allThemaCall = apiService.getAllThema();
        allThemaCall.enqueue(new Callback<ThemaAllResponse>() {
            @Override
            public void onResponse(Call<ThemaAllResponse> call, Response<ThemaAllResponse> response) {
                if(response.isSuccessful()) {
                    /** 요청이 성공적이라면 **/
                    List<ThemaAllResponse.Result> resultList = response.body().getResult();

                    /** 리스트에 각 값 저장 **/
                    for (ThemaAllResponse.Result result : resultList) {
                        themaIdList.add(result.getThema_id());
                        themaList.add(result.getName());
                    }

                    categoryAdapter = new categoryAdapter(requireContext(), themaList);
                    categoryAdapter.setOnCategoryClickListener(new categoryAdapter.categoryClickListener() {
                        @Override
                        public void OnCategoryClickListener(int pos) {
                            String cate = themaList.get(pos);


                            Toast.makeText(getActivity(), pos + "번 아이템 선택..!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    recycler_category.setAdapter(categoryAdapter);
                    recycler_category.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
                }
                else {
                    Log.d(TAG, "에러 발생 ..." + response.message());
                }
            }

            @Override
            public void onFailure(Call<ThemaAllResponse> call, Throwable t) {
                Log.d(TAG, "onFalilure .. 카테고리 불러오기 연동 실패 ..., 메세지 : " + t.getMessage());
            }
        });
    }

    /** 모든 장소 가져오기 API **/
    public void getPlaceList() {
        apiService = RetrofitClient.getApiService();
        allPlaceCall = apiService.getAllPlace();
        allPlaceCall.enqueue(new Callback<PlaceAllResponse>() {
            @Override
            public void onResponse(Call<PlaceAllResponse> call, Response<PlaceAllResponse> response) {
                if(response.isSuccessful()) {
                    placeList = response.body().getResult();
                    placeListAdapter = new PlaceListAdapter(requireContext(), placeList);

                    for (PlaceAllResponse.Result result : placeList) {
                        placeIdList.add(result.getPlaceId());
                    }

                    /** CCTV 버튼 클릭 리스너 설정 **/
                    placeListAdapter.setOnCCTVClickListener(new PlaceListAdapter.cctvClickListener() {
                        @Override
                        public void cctvButtonClick(int position) {
                            /** 모달 띄우기 **/
                            String url = placeList.get(position).getCctv();
                            showCCTV(url);
                            //String url = placeList.get(position).getCctv();
                            //Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            //startActivity(urlintent);
                        }
                    });

                    /** 찜 버튼 클릭 리스너 설정 **/
                    placeListAdapter.setOnLikeClickListener(new PlaceListAdapter.likeClickListener() {
                        @Override
                        public void likeButtonClick(int position) {
                            // 버튼 클릭했을 때 API 통신
                            Long placeId = placeList.get(position).getPlaceId();
                            Log.d(TAG, "place_id 값1 : " + placeId);
                            Log.d(TAG, "position " + position);
                            postLike(position, placeId);
                        }
                    });

                    recycler_place.setAdapter(placeListAdapter);
                    recycler_place.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
                }
                else {
                    Log.d(TAG, "에러발생 .." + response.message());
                }
            }

            @Override
            public void onFailure(Call<PlaceAllResponse> call, Throwable t) {
                Log.d(TAG, "onFalilure .. 카테고리 불러오기 연동 실패 ..., 메세지 : " + t.getMessage());
            }
        });
    }

    /** 찜 버튼 API 통신 **/
    public void postLike(int pos, Long place_id) {
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
        likeCall = apiService.doLike(request);
        likeCall.enqueue(new Callback<LikeResponse>() {
            @Override
            public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {
                if(response.isSuccessful()) {
                    /** 요청에 성공했을 때 **/
                    if(response.body().getCode() == 200 && response.body().getMessage().contains("성공")) {
                        // 지우기가 성공했을 때
                        if(response.body().getResult().equals("delete")) {
                            Toast.makeText(requireContext(), "찜 리스트에 정상적으로 취소되었습니다.", Toast.LENGTH_SHORT).show();
                            placeList.get(pos).setLikeYn(0);
                            Log.d(TAG, String.valueOf(placeList.get(pos).getLikeYn()));
                            /** 변경 감지 **/
                            placeListAdapter.notifyDataSetChanged();
                        }
                        else {
                            Toast.makeText(requireContext(), "찜 리스트에 정상적으로 추가되었습니다.", Toast.LENGTH_SHORT).show();
                            placeList.get(pos).setLikeYn(1);
                            Log.d(TAG, String.valueOf(placeList.get(pos).getLikeYn()));
                            placeListAdapter.notifyDataSetChanged();
                        }
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
