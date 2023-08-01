package com.example.a23__project_1.fragmentFourth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a23__project_1.MainActivity;
import com.example.a23__project_1.R;
import com.example.a23__project_1.response.CommonResponse;
import com.example.a23__project_1.response.PlaceAllResponse;
import com.example.a23__project_1.response.PlaceInfoResponse;
import com.example.a23__project_1.response.PlanListResponse;
import com.example.a23__project_1.retrofit.RetrofitAPI;
import com.example.a23__project_1.retrofit.RetrofitClient;
import com.example.a23__project_1.retrofit.RetrofitClientJwt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanListActivity extends AppCompatActivity {
    private static final String TAG = "PlanListActivity";
    ImageButton close;
    RecyclerView recyclerView;
    List<PlanListResponse.Result> resultList;
    TextView tv_rec;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "userInfo";
    private String email = "", cate = "";
    private RetrofitAPI apiService;
    private PlanListAdapter planListAdapter;
    private Dialog planInfoDialog;
    private List<String> planThemaList;
    private List<String> planName;
    private List<Long> scheIdList;
    private LinearLayout linText;

    List<PlanListResponse.Result> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list);

        // sharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email", "null");

        close = findViewById(R.id.btn_back);
        close.setOnClickListener(closeClickListener);
        linText = findViewById(R.id.lin_noplan);

        recyclerView = findViewById(R.id.recycler_plan);
        resultList = new ArrayList<>();
        planThemaList = new ArrayList<>();
        scheIdList = new ArrayList<>();
        planName = new ArrayList<>();
        results = new ArrayList<>();

        getPlanList();

        // 리스트가 없으면 해당 문구가 보이도록 설정.
        linText.setVisibility((planName.size() == 0) ? View.VISIBLE : View.GONE);

        // Dialog
        planInfoDialog = new Dialog(this);
        planInfoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        planInfoDialog.setContentView(R.layout.dialog_plan_info);
    }

    // 뒤로 가기
    View.OnClickListener closeClickListener = v -> {
        finish();
    };

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume 호출 :");
        // 리스트가 없으면 해당 문구가 보이도록 설정.
        linText.setVisibility((planName.size() == 0) ? View.VISIBLE : View.GONE);
    }

    // 리스트 가져오기
    private void getPlanList() {
        String accessToken = sharedPreferences.getString("accessToken", "null");
        apiService = RetrofitClientJwt.getApiService(accessToken);
        Call<PlanListResponse> call = apiService.getPlanList(accessToken, email);
        call.enqueue(new Callback<PlanListResponse>() {
            @Override
            public void onResponse(Call<PlanListResponse> call, Response<PlanListResponse> response) {
                if (response.isSuccessful() && response.body().getMessage().contains("성공")) {
                    results = response.body().getResult();

                    // 리스트가 없으면 해당 문구가 보이도록 설정.
                    linText.setVisibility((results.size() == 0) ? View.VISIBLE : View.GONE);

                    List<String> themaList = new ArrayList<>();
                    for (PlanListResponse.Result result : results) {
                        planName.add(result.getTitle());

                        themaList = result.getThema();
                        for (String thema : themaList)
                            cate += thema + ", ";
                        cate = cate.substring(0, cate.length() - 2); // 카테고리 문자열로 출력
                        planThemaList.add(cate);
                        scheIdList.add(result.getScheId());
                    }

                    planListAdapter = new PlanListAdapter(getApplicationContext(), results);

                    /** 아이템 클릭 리스너 **/
                    planListAdapter.setOnItemClickListener(new PlanListAdapter.itemClickListener() {
                        @Override
                        public void itemClick(List<PlanListResponse.Result> list, int position) {
                            planInfo(list, position);
                        }
                    });
                    recyclerView.setAdapter(planListAdapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                } else
                    Log.d(TAG, "에러발생 1.." + response.errorBody().toString());
            }

            @Override
            public void onFailure(Call<PlanListResponse> call, Throwable t) {
                Log.d(TAG, "에러발생 2.." + t.getMessage());
            }
        });
    }

    /**
     * 하나의 자세한 일정 조회
     **/
    private void planInfo(List<PlanListResponse.Result> list, int position) {
        planInfoDialog.show();

        TextView plan_title = planInfoDialog.findViewById(R.id.tv_infoTitle);
        TextView place = planInfoDialog.findViewById(R.id.tv_infoPlace);
        TextView dDay = planInfoDialog.findViewById(R.id.tv_infodDay);
        ImageView iv_cate = planInfoDialog.findViewById(R.id.iv_cate);
        TextView tv_cate = planInfoDialog.findViewById(R.id.tv_cate);
        TextView tv_date = planInfoDialog.findViewById(R.id.tv_date);
        TextView tv_time = planInfoDialog.findViewById(R.id.tv_time);

        tv_rec = planInfoDialog.findViewById(R.id.tv_rec);
        Button close = planInfoDialog.findViewById(R.id.btn_close);
        Button delete = planInfoDialog.findViewById(R.id.btn_delete);

        plan_title.setText(list.get(position).getTitle());
        place.setText(list.get(position).getPlaceName());
        Long scheduleId = list.get(position).getScheId();

        /** 한가한 시간을 추천받는다. **/
        getPlaceInform(list.get(position).getPlaceName());

        // D-Day 날짜 설정
        String str_date = list.get(position).getStartDate();
        try {
            TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
            // 오늘 날짜
            String todayFm = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setTimeZone(tz);

            Date date = new Date(dateFormat.parse(str_date).getTime());
            Date today = new Date(dateFormat.parse(todayFm).getTime());
            long calculate = date.getTime() - today.getTime();

            int Ddays = (int) (calculate / ( 24*60*60*1000));
            if(Ddays <0) {
                dDay.setText("종료된 일정입니다.");
            }
            else if (Ddays == 0) {
                dDay.setText("D-Day");
            }
            else
                dDay.setText("D-" + String.valueOf(Ddays));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        /** 카테고리 이미지 설정 **/
        if (planThemaList.get(position).contains("상업"))
            iv_cate.setBackgroundResource(R.drawable.ic_business_park);
        else if (cate.contains("공원"))
            iv_cate.setBackgroundResource(R.drawable.ic_park);
        else if (cate.contains("스파"))
            iv_cate.setBackgroundResource(R.drawable.ic_spa);
        else if (cate.contains("쇼핑몰"))
            iv_cate.setBackgroundResource(R.drawable.ic_shoppingmall);
        else if (cate.contains("거리"))
            iv_cate.setBackgroundResource(R.drawable.ic_street);
        else if (cate.contains("백화점"))
            iv_cate.setBackgroundResource(R.drawable.ic_baekwha);
        else if (cate.contains("관광지"))
            iv_cate.setBackgroundResource(R.drawable.ic_trip);
        else if (cate.contains("지하철"))
            iv_cate.setBackgroundResource(R.drawable.ic_subway);
        else if (cate.contains("마트"))
            iv_cate.setBackgroundResource(R.drawable.ic_supermarket);
        else if (cate.contains("공항"))
            iv_cate.setBackgroundResource(R.drawable.ic_airport);

        // 카테고리 텍스트 설정
        tv_cate.setText(planThemaList.get(position));
        tv_date.setText(list.get(position).getStartDate());
        tv_time.setText(list.get(position).getStartTime());


        // 닫기 버튼 클릭 시
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planInfoDialog.dismiss();
            }
        });

        // 삭제하기 버튼 클릭 시
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlanListActivity.this);
                builder.setTitle("일정 삭제");
                builder.setMessage("해당 일정을 정말 삭제하시겠습니까?");
                builder.setIcon(R.drawable.ic_caution);

                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteSchedule(scheduleId, position);
                        planInfoDialog.dismiss();

                        // 해당 리스트 삭제
                        planName.remove(plan_title.getText().toString());
                        // 리스트가 없으면 해당 문구가 보이도록 설정.
                        linText.setVisibility((planName.size() == 0) ? View.VISIBLE : View.GONE);

                    }
                });

                builder.setNeutralButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // 다이얼로그 표시
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    /** 삭제 API **/
    private void deleteSchedule(Long scheduleId, int position) {
        String accessToken = sharedPreferences.getString("accessToken", "null");
        apiService = RetrofitClientJwt.getApiService(accessToken);
        Call<CommonResponse> call = apiService.deleteSchedule(accessToken, scheduleId);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(response.isSuccessful()) {
                    Object result = response.body().getResult();
                    if (result instanceof String) {
                        String resultString = (String) result;
                        // 삭제 성공!
                        if(resultString.equals("update")) {
                            Toast.makeText(getApplicationContext(), "해당 일정이 성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            results.remove(position);
                            planListAdapter.notifyDataSetChanged();
                        }
                    }
                    else
                        Log.d(TAG, "일정 삭제 오류 1..." + response.message());
                }
                else
                    Log.d(TAG, "일정 삭제 오류 2..." + response.code() + response.message());
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.d(TAG, "일정 삭제 오류 3..." + t.getMessage());
            }
        });
    }

    /** rec 얻기 위해 **/
    private void getPlaceInform(String placeName) {
        apiService = RetrofitClient.getApiService();
        Call<PlaceInfoResponse> call = apiService.getPlaceInfo(placeName);

        call.enqueue(new Callback<PlaceInfoResponse>() {
            @Override
            public void onResponse(Call<PlaceInfoResponse> call, Response<PlaceInfoResponse> response) {
                if(response.isSuccessful() && response.body().getMessage().contains("성공")) {
                    PlaceInfoResponse.PlaceInfo info = response.body().getResult();

                    tv_rec.setText(info.getRec());
                }
                else {
                    Log.d(TAG, "rec 연동 실패 1..." + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<PlaceInfoResponse> call, Throwable t) {
                Log.d(TAG, "rec 연동 실패 2..." + t.getMessage());
            }
        });
    }
}