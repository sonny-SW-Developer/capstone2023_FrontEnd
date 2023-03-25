package com.example.a23__project_1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

import com.example.a23__project_1.R;

public class SettingActivity extends PreferenceActivity {

    private static final String SETTING_CHKBOX = "key_chk_box";
    private static final String SETTING_STYLE = "key_style";
    private static final String SETTING_SIZE = "key_size";
    private static final String SETTING_LIGHT = "key_lightmode";
    private static final String SETTING_INFO = "key_info";
    private static final String SETTING_MAIL = "key_mail";

    String themeColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);


        //preference 관련 코드
        SharedPreferences preferences;
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences
                .registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
                    public void onSharedPreferenceChanged(SharedPreferences sp, String key) {

                        if (key.equals(SETTING_CHKBOX)) { //체크박스

                            Log.d("TAG", key + "SELECTED");

                        } else if (key.equals(SETTING_STYLE)) { //글꼴

                            Log.d("TAG", key + "SELECTED");

                        } else if (key.equals(SETTING_SIZE)) { //글자크기

                            Log.d("TAG", key + "SELECTED");

                        } else if (key.equals(SETTING_LIGHT)) { //다크모드

                            if (preferences.getBoolean("key_lightmode", false)){
                                Log.d("TAG", key + "다크모드");
                                //themeColor = ThemeUtil.DARK_MODE;
                                //ThemeUtil.applyTheme(themeColor);
                                //ThemeUtil.modSave(getApplicationContext(),themeColor);

                            }else {
                                Log.d("TAG", key + "라이트모드");
                                //themeColor = ThemeUtil.LIGHT_MODE;
                                //ThemeUtil.applyTheme(themeColor);
                                //ThemeUtil.modSave(getApplicationContext(),themeColor);
                            }


                        } else if (key.equals(SETTING_INFO)) { //앱 정보



                        }else if (key.equals(SETTING_MAIL)) { //메일 보내기

                            Log.d("TAG", key + "SELECTED");
                        }

                    }
                });


    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();
        Log.d("tag","클릭된 Preference의 key는 "+key);
        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.stay,R.anim.fadeout_right);

    }

}