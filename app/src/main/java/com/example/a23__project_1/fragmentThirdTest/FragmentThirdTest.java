package com.example.a23__project_1.fragmentThirdTest;

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

public class FragmentThirdTest extends Fragment {

    protected Animation anim;
    protected View menu;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Initialize view
        View view = inflater.inflate(R.layout.fragment_third_test, container, false);
        return view;

    }


}