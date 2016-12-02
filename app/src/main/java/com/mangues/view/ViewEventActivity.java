package com.mangues.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.mangues.view.view.SwipeDismissListView;

import java.util.ArrayList;
import java.util.List;

import mangues.com.rxandroiddemo.R;

public class ViewEventActivity extends AppCompatActivity {

    private SwipeDismissListView swipeDismissListView;
    private ArrayAdapter<String> adapter;
    private List<String> dataSourceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        init();
//        EventView eventView = new EventView(this);
//        eventView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
//        eventView.setBackgroundColor(Color.YELLOW);
//        setContentView(eventView);

    }
    private void init() {
        swipeDismissListView = (SwipeDismissListView) findViewById(R.id.swipeDismissListView);
        for (int i = 0; i < 20; i++) {
            dataSourceList.add("滑动删除" + i);
        }

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, dataSourceList);

        swipeDismissListView.setAdapter(adapter);

        swipeDismissListView.setOnDismissCallback(new SwipeDismissListView.OnDismissCallback() {

            @Override
            public void onDismiss(int dismissPosition) {
                adapter.remove(adapter.getItem(dismissPosition));
            }
        });


        swipeDismissListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(ViewEventActivity.this, adapter.getItem(position), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
