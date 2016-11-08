package com.mangues.okhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import mangues.com.rxandroiddemo.R;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SocketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);


            Observable.just("images/logo.png") // 输入类型 String
                .subscribeOn(Schedulers.io())

                .map(new Func1<String, String>() {
                    @Override
                    public String call(String filePath) { // 参数类型 String
                        MyWebServer myWebServer = new MyWebServer(80,"www.baidu.com");
                        try {
                            myWebServer.sendGet();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return e.getLocalizedMessage();
                        }
                        return "success"; // 返回类型 Bitmap
                    }
                })
                    .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String bitmap) { // 参数类型 Bitmap
                        Log.i("122122", bitmap);
                    }
                });


//        Subscriber<String> subscriber = new Subscriber<String>() {
//            @Override
//            public void onNext(String s) {
//                Log.d("1", "Item: " + s);
//            }
//
//            @Override
//            public void onCompleted() {
//                Log.d("1", "Completed!");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d("1", "Error!");
//            }
//        };
//
//
//
//        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                MyWebServer myWebServer = new MyWebServer(80,"www.baidu.com");
//                try {
//                    myWebServer.sendGet();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    subscriber.onNext(e.getLocalizedMessage());
//                }
//            }
//        });
//
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);

    }
}
