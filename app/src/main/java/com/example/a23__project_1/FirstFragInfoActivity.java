package com.example.a23__project_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class FirstFragInfoActivity extends AppCompatActivity {
    private Intent intent;
    private String messege;
    private TextView testText;
    private RecyclerView recyclerView;
    private ImageButton backbtn;
    private Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_frag_info);

        testText = (TextView)findViewById(R.id.testText);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_activity_first_fragment_menu);

        //리사이클러뷰
        ArrayList<DataMoreInfo> list_place = new ArrayList<>();

        list_place.add(new DataMoreInfo("중구","1번째 아이템 입니다.",R.drawable.junggu, true));
        list_place.add(new DataMoreInfo("종로구","2번째 아이템 입니다.",R.drawable.jongro , false));
        list_place.add(new DataMoreInfo("송파구","3번째 아이템 입니다.",R.drawable.songpa , false));
        list_place.add(new DataMoreInfo("강남구","4번째 아이템 입니다.",R.drawable.gangnam , false ));
        list_place.add(new DataMoreInfo("영등포구","5번째 아이템 입니다.",R.drawable.yeouido ,false));


        
        
        intent = getIntent();
        if(intent.hasExtra("elements")){
            messege = intent.getStringExtra("elements");
        }

        switch (messege){
            case "more_category":
                testText.setText("카테고리더보기");
                break;
            case "more_place":
                testText.setText("장소더보기");
                break;
            case "cardview1":
                testText.setText("쇼핑");
                break;
            case "cardview2":
                testText.setText("지하철·기차역");
                break;
            case "cardview3":
                testText.setText("음식점");
                break;
            case "cardview4":
                testText.setText("공원");
                break;
            case "cardview5":
                testText.setText("공항");
                break;
            case "cardview6":
                testText.setText("카페");
                break;
            default:
                try {

                    Integer.parseInt(messege);
                    testText.setText(messege);

                }catch(NumberFormatException e){

                    testText.setText("에러");

                }

        }


        recyclerView.setAdapter(new RecyclerFragFirstMoreInfoAdapter(this,list_place));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        //횡 방향 스크롤시 아이템별 정지 위함
        SnapHelper snapHelper = new PagerSnapHelper();
        if (recyclerView.getOnFlingListener() == null)
            snapHelper.attachToRecyclerView(recyclerView);

        //툴바 옆, setting 관련 코드
        ImageButton btn_setting = (ImageButton) findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstFragInfoActivity.this, com.example.a23__project_1.SettingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein_right, R.anim.stay);

            }
        });

        //menu 관련 코드
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_first_info_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}