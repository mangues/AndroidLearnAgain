package com.mangues.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mangues.com.rxandroiddemo.R;

/**
 * Created by mangues on 16/12/2.
 */

public class FragmentOne extends Fragment {

    public Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        button = (Button) view.findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    FragmentThree fragment = new FragmentThree();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.id_content,fragment);
                    //将当前的事务添加到了回退栈
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();


            }
        });
        return view;
    }





}
