package zachary.zwebview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import zachary.webview.ZWeb;
import zachary.webview.ZWebView;

/**
 *Author:Zachary
 *Time:2017/12/23 0023 下午 5:32
 *Dec:测试Activity
 *Call:0592-3278796
 *Email:developer@baogukeji.com
 *Web:www.baogukeji.com
 */
public class MainActivity extends AppCompatActivity {
    private LinearLayout ll;
    private ZWebView zv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        ll= (LinearLayout) findViewById(R.id.ll);


        ZWeb.with(this)
            .addParentView(ll)
           // .setBackgroundRes(R.drawable.progress_drawable)
            /*.addTitleListener(new ZWeb.TitleListener() {
                @Override
                public void getTitle(String title) {
                    Toast.makeText(MainActivity.this,title,Toast.LENGTH_LONG).show();
                }
            })
            .addProgressListener(new ZWeb.ProgressListener() {
                @Override
                public void getProgress(int progress) {
                    Log.d("MainActivityy", "=========progress:" + progress);
                }
            })*/
            .setUrl("https://qpay.qq.com/qr/5604c201")
            .go();
    }
}
