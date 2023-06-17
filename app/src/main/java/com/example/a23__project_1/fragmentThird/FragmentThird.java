package com.example.a23__project_1.fragmentThird;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a23__project_1.MainActivity;
import com.example.a23__project_1.R;

//Inflater를 통해 각 프래그먼트에 해당하는 레이아웃 리소스 ID 값을 통해 생성된 View를 반환
public class FragmentThird extends Fragment {

    MainActivity activity;
    protected Animation anim;
    protected int layout_ctr = 0;
    protected View menu;
    protected LinearLayout layout_menu;
    protected LinearLayout layout_backgroundmenu;
    protected ImageButton menu_btn;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Initialize view
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        //여기가 문제 ..
        //getActivity().startActivity(new Intent(getActivity(), MapActivity.class));
        return view;

    }


}