package com.mangues.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import mangues.com.rxandroiddemo.R;

public class FragmentBackStacksActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn_a,btn_b,btn_a1,btn_b1,btn_c1;
    FragmentOne fragmentOne;
    FragmentTwo fragmentTwo;
    Integer type = 1;
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_back_stacks);


        btn_a = (Button) findViewById(R.id.btn_a);
        btn_b = (Button) findViewById(R.id.btn_b);
        btn_a1 = (Button) findViewById(R.id.btn_a1);
        btn_b1 = (Button) findViewById(R.id.btn_b1);
        btn_c1 = (Button) findViewById(R.id.btn_c1);
        ll = (LinearLayout) findViewById(R.id.ll);

        btn_a.setOnClickListener(this);
        btn_b.setOnClickListener(this);
        btn_a1.setOnClickListener(this);
        btn_b1.setOnClickListener(this);
        btn_c1.setOnClickListener(this);



         fragmentOne = new FragmentOne();
         fragmentTwo = new FragmentTwo();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.id_content,fragmentOne).show(fragmentOne).commit();

        temp = fragmentOne;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_a:
                if (type==1){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.id_content, fragmentOne);
                    ft.commit();
                }else if (type==2){
                    switchFragment(fragmentOne);
                }
                break;

            case R.id.btn_b:
                if (type==1){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.id_content, fragmentTwo);
                    ft.commit();
                }else if (type==2){
                    switchFragment(fragmentTwo);
                }
                break;

            case R.id.btn_a1:
                type = 1;
                ll.setVisibility(View.VISIBLE);
                fragmentOne.button.setVisibility(View.GONE);

                Toast.makeText(this,"replace",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_b1:
                type = 2;
                ll.setVisibility(View.VISIBLE);
                fragmentOne.button.setVisibility(View.GONE);
                Toast.makeText(this,"hide(show)",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_c1:
                type = 3;
                ll.setVisibility(View.GONE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.id_content, fragmentOne);
                fragmentOne.button.setVisibility(View.VISIBLE);
                ft.commit();
                Toast.makeText(this,"栈",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    Fragment temp;
    /**
     * hide和show切换Fragment
     *
     * @param fragment
     */
    private void switchFragment(Fragment fragment)
    {
        // temp默认为Fragment1
        if (fragment != temp)
        {
            if (!fragment.isAdded())
            {
                getSupportFragmentManager().beginTransaction().hide(temp)
                        .add(R.id.id_content, fragment).commit();
            }
            else
            {
                getSupportFragmentManager().beginTransaction().hide(temp)
                        .show(fragment).commit();
            }
            temp = fragment;
        }
    }
}
