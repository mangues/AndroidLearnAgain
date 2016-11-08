package mangues.com.rxandroiddemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        EventBus.getDefault().register(this);

        EventBus.getDefault().post(new Item("hello EventBus"));
    }


    public void onEventMainThread(Item event)
    {
            Toast.makeText(this,event.content,Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onDestroy()
    {
        super.onDestroy();
        // Unregister
        EventBus.getDefault().unregister(this);
    }


//        Observable.just("images/logo.png") // 输入类型 String
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(new Func1<String, String>() {
//                    @Override
//                    public String call(String filePath) { // 参数类型 String
//                        return filePath+"eeee"; // 返回类型 Bitmap
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .map(new Func1<String, String>() {
//                    @Override
//                    public String call(String filePath) { // 参数类型 String
//                        Toast.makeText(mContext,filePath,Toast.LENGTH_SHORT).show();
//                        return filePath+"eeee"+"ddd"; // 返回类型 Bitmap
//                    }
//                })
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String bitmap) { // 参数类型 Bitmap
//                        Log.i("122122", bitmap);
//                    }
//                });
//
//
//
//        rxBusObservers();
//        rxBusPost();
//
//    }
//
//
//    private void rxBusPost() {
//        Log.d("wxl", "hasObservers=" + RxBus.getInstance().hasObservers());
//        if (RxBus.getInstance().hasObservers()) {
//            RxBus.getInstance().post(new TapEvent());
//        }
//    }
//
//    private void rxBusObservers() {
//        Subscription subscription = RxBus.getInstance()
//                .toObserverable()
//                .subscribe(new Action1<Object>() {
//                    @Override
//                    public void call(Object event) {
//                        if (event instanceof TapEvent) {
//                            //do something
//                            Log.d("wxl", "rxBusHandle");
//                        }
//                    }
//                });
//        addSubscription(subscription);
//    }
//
//    public void addSubscription(Subscription subscription) {
//        if (this.mCompositeSubscription == null) {
//            this.mCompositeSubscription = new CompositeSubscription();
//        }
//
//        this.mCompositeSubscription.add(subscription);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (this.mCompositeSubscription != null) {
//            this.mCompositeSubscription.unsubscribe();//取消注册，以避免内存泄露
//        }
//    }
//
//    public class TapEvent {
//    }
}
