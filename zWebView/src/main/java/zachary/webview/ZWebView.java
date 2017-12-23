package zachary.webview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

/**
 * Author: Zachary
 * Time: 2017/12/23 上午 10:30
 * Desc:
 */

public class ZWebView extends RelativeLayout{
    private Context mContext;
    private ProgressBar pg;
    private WebView webView;
    private int mBackgroudColor=R.drawable.progress_drawable;
    private String mUrl;
    private ViewGroup mViewGroup;
    private ZWeb.TitleListener mTitleListener;
    private ZWeb.ProgressListener mProgressListener;

    public ZWebView(Context context,ZWeb zWeb) {
        super(context);
        mContext=context;
        mUrl=zWeb.url;
        mBackgroudColor=zWeb.res;
        mViewGroup=zWeb.viewGroup;
        mTitleListener=zWeb.titleListener;
        mProgressListener=zWeb.progressListener;
        init();
    }


    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout._main, mViewGroup);
        webView = (WebView) view.findViewById(R.id.wv);
        pg= (ProgressBar) view.findViewById(R.id.pg);
        // 设置编码
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setTextZoom(70);
        // 设置背景颜色 透明
        webView.setBackgroundColor(Color.argb(0, 0, 0, 0));
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 设置缓存模式
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // //添加Javascript调用java对象
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        // 扩大比例的缩放设置此属性，可任意比例缩放。
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setBlockNetworkImage(false);
        // 不启用硬件加速
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        // 自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        //不设置将调用手机浏览器
        setClient();
        //设置加载地址
        setUrl(mUrl);//设置加载地址
        //设置进度条颜色（渐变的）
        setProgressDrawable();

    }

    private void setProgressDrawable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            pg.setProgressDrawable(mContext.getResources().getDrawable(mBackgroudColor));
        }
    }


    public void setUrl(String url) {
        if (url.startsWith("http")||url.startsWith("HTTP")){
          /**微信调不起来配置
            Map extraHeaders = new HashMap();
            extraHeaders.put("Referer", "http://gateway.baoguyun.com");*/
            webView.loadUrl(url/*,extraHeaders*/);
        }else {
            webView.loadDataWithBaseURL("网页", url, "text/html", "utf-8", null);
        }
    }

    private void setClient() {
        //创建WebViewClient对象
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //分流
                if(url.startsWith("http:") || url.startsWith("https:")||url.startsWith("HTTP:") || url.startsWith("HTTP:")) {
                    view.loadUrl(url);
                    return false;
                }else{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    mContext.startActivity(intent);
                    return true;
                }
            }});

        //设置setWebChromeClient对象
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (mTitleListener!=null){
                    mTitleListener.getTitle(title);
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (mProgressListener!=null){
                    mProgressListener.getProgress(newProgress);
                }
                if(newProgress==100){
                    pg.setVisibility(View.GONE);//加载完网页进度条消失
                }
                else{
                    pg.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    pg.setProgress(newProgress);//设置进度值
                }
            }
        });
    }
}
