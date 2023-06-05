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
    RecyclerView recycler_category;
    private Context context;
    private categoryAdapter categoryAdapter;
    private Call<ThemaAllResponse> allThemaCall;
    private RetrofitAPI apiService;
    private List<Long> themaIdList;
    private List<String> themaList;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        input = view.findViewById(R.id.et_input);
        search = view.findViewById(R.id.btn_search);
        recycler_category = view.findViewById(R.id.recycler_category);

        themaIdList = new ArrayList<>();
        themaList = new ArrayList<>();

        /** 테스트 데이터 추가 **/
//        List<String> categoryList = new ArrayList<>();
//        categoryList.add("음식점");
//        categoryList.add("카페");
//        categoryList.add("관광지");
//        categoryList.add("쇼핑몰");
//        categoryList.add("음식점");
//        categoryList.add("공부");
//        categoryList.add("여행");

        getCategoryList();
        recycler_category.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        return view;
    }

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

                }
                else {
                    Log.d(TAG, "에러 발생 ..." + response.message());
                }
            }

            @Override
            public void onFailure(Call<ThemaAllResponse> call, Throwable t) {
                Log.d(TAG, "onFalilure .. 카테고리 불러오기 연동 실패 ...");
            }
        });
    }
}
