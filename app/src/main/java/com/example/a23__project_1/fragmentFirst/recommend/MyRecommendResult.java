package com.example.a23__project_1.fragmentFirst.recommend;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a23__project_1.R;

public class MyRecommendResult extends AppCompatActivity {

    private static final String TAG = "MyRecommendResult";
    private Intent intent;
    private String messege;
    private AlertDialog alertDialog;

    private TextView testText;
    private LinearLayout layout_recommend_result_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recommend_result);

//        intent = getIntent();
//        if (intent.hasExtra("elements")) {
//            messege = intent.getStringExtra("elements");
//        }
//        switch (messege) {
//            case "tour":
//                Log.d(TAG, messege);
////                getPositionList("카테고리더보기",list_place);
//                break;
//            case "cafe":
//                Log.d(TAG, messege);
////                getPositionList("카테고리더보기",list_place);
//                break;
//            case "cook":
//                Log.d(TAG, messege);
////                getPositionList("카테고리더보기",list_place);
//                break;
//            case "shopping":
//                Log.d(TAG, messege);
////                getPositionList("카테고리더보기",list_place);
//                break;
//            case "park":
//                Log.d(TAG, messege);
////                getPositionList("카테고리더보기",list_place);
//                break;
//            case "street":
//                Log.d(TAG, messege);
////                getPositionList("카테고리더보기",list_place);
//                break;
//            case "activity":
//                Log.d(TAG, messege);
////                getPositionList("카테고리더보기",list_place);
//                break;
//            default:
//                try {
//
//                    Integer.parseInt(messege);
//                    Log.d(TAG, messege);
//
//                } catch (NumberFormatException e) {
//                    Log.d(TAG, "error");
//                }
//        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_loading, null));
        builder.setCancelable(false); // if you want the user to be forced to wait

        alertDialog = builder.create();

        new MyAsyncTask().execute();

    }

    private class MyAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            alertDialog.show(); // show loading dialog
        }

        @Override
        protected String doInBackground(Void... params) {
            // simulated delay for server communication
            String message = null;
            try {
                Thread.sleep(2000);
                intent = getIntent();
                if (intent.hasExtra("elements")) {
                    messege = intent.getStringExtra("elements");
                }
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            return messege;
        }

        @Override
        protected void onPostExecute(String result){
            layout_recommend_result_main = (LinearLayout) findViewById(R.id.layout_recommend_result_main);
            alertDialog.dismiss(); // hide loading dialog
            layout_recommend_result_main.setVisibility(View.VISIBLE);
            if (result != null) {
                switch (messege) {
                    case "tour":
                        Log.d(TAG, messege);
//                getPositionList("카테고리더보기",list_place);
                        break;
                   case "cafe":
                       Log.d(TAG, messege);
//                getPositionList("카테고리더보기",list_place);
                       break;
                   case "cook":
                       Log.d(TAG, messege);
//                getPositionList("카테고리더보기",list_place);
                       break;
                   case "shopping":
                       Log.d(TAG, messege);
//                getPositionList("카테고리더보기",list_place);
                       break;
                   case "park":
                       Log.d(TAG, messege);
//                getPositionList("카테고리더보기",list_place);
                       break;
                   case "street":
                       Log.d(TAG, messege);
//                getPositionList("카테고리더보기",list_place);
                       break;
                   case "activity":
                       Log.d(TAG, messege);
//                getPositionList("카테고리더보기",list_place);
                       break;
                   default:
                       try {

                           Integer.parseInt(messege);
                           Log.d(TAG, messege);

                       } catch (NumberFormatException e) {
                           Log.d(TAG, "error");
                       }
                }
            }
        }
    }
}
