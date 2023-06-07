package com.example.a23__project_1.placeList;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a23__project_1.R;
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
    private List<PlaceAllResponse.Result> placeList; // 모든 장소 리스트

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        input = view.findViewById(R.id.et_input);
        search = view.findViewById(R.id.btn_search);
        // 카테고리 리스트 가져오기
        recycler_category = view.findViewById(R.id.recycler_category);
        // 카테고리 모든 장소 가져오기
        recycler_place = view.findViewById(R.id.recycler_place);
        themaIdList = new ArrayList<>();
        themaList = new ArrayList<>();
        placeList = new ArrayList<>();

        getCategoryList();
        getPlaceList();
        return view;
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
}
