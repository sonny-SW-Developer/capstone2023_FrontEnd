package com.example.a23__project_1.fragmentFirst;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.a23__project_1.R;

public class MyRecommendResult extends AppCompatActivity {

    private static final String TAG = "MyRecommendResult";
    private Intent intent;
    private String messege;
    private AlertDialog alertDialog;

    private TextView testText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recommend_result);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_loading, null));
        builder.setCancelable(false); // if you want the user to be forced to wait

        alertDialog = builder.create();

        new MyAsyncTask().execute();

//        intent = getIntent();
//        if(intent.hasExtra("elements")){
//            messege = intent.getStringExtra("elements");
//        }
//
//        switch (messege){
//            case "tour":
//
////                getPositionList("카테고리더보기",list_place);
//                break;
//            case "cafe":
////                getPositionList("카테고리더보기",list_place);
//                break;
//            case "cook":
////                getPositionList("카테고리더보기",list_place);
//                break;
//            case "shopping":
////                getPositionList("카테고리더보기",list_place);
//                break;
//            case "park":
////                getPositionList("카테고리더보기",list_place);
//                break;
//            case "street":
////                getPositionList("카테고리더보기",list_place);
//                break;
//            case "activity":
////                getPositionList("카테고리더보기",list_place);
//                break;
//            default:
//                try {
//
//                    Integer.parseInt(messege);
//                    Log.d(TAG,messege);
//
//                }catch(NumberFormatException e){
//                    Log.d(TAG,"error");
//                }
//
//        }

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
            testText = (TextView) findViewById(R.id.test_text);
            alertDialog.dismiss(); // hide loading dialog
            if (result != null) {
                switch (messege) {
                    case "tour":
                        testText.setText(messege);
//                getPositionList("카테고리더보기",list_place);
                        break;
                   case "cafe":
                            testText.setText(messege);
//                getPositionList("카테고리더보기",list_place);
                       break;
                   case "cook":
                       testText.setText(messege);
//                getPositionList("카테고리더보기",list_place);
                       break;
                   case "shopping":
                       testText.setText(messege);
//                getPositionList("카테고리더보기",list_place);
                       break;
                   case "park":
                       testText.setText(messege);
//                getPositionList("카테고리더보기",list_place);
                       break;
                   case "street":
                       testText.setText(messege);
//                getPositionList("카테고리더보기",list_place);
                       break;
                   case "activity":
                       testText.setText(messege);
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
