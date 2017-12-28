package zachary.webview;

import android.content.Context;
import android.content.Intent;
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
        if (zWeb.res!=0){
            mBackgroudColor=zWeb.res;
        }
        mViewGroup=zWeb.viewGroup;
        mTitleListener=zWeb.titleListener;
        mProgressListener=zWeb.progressListener;
        init();
    }


    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout._main, mViewGroup);
        webView = (WebView) view.findViewById(R.id.wv);
        pg= (ProgressBar) view.findViewById(R.id.pg);
        WebSettings setting=webView.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setJavaScriptCanOpenWindowsAutomatically(true);//允许js弹出窗口
        setting.setBuiltInZoomControls(true); // 原网页基础上缩放
        setting.setUseWideViewPort(true);
        setting.setSupportZoom(true);//支持缩放
        //设置 缓存模式
        setting.setCacheMode(WebSettings.LOAD_DEFAULT);
        setting.setLoadWithOverviewMode(true);
        setting.setDomStorageEnabled(true);
        setting.setBlockNetworkImage(false);
        setting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        setting.setBlockNetworkLoads(false);
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局
        this.setVerticalScrollBarEnabled(false);
        this.setHorizontalScrollBarEnabled(false);// 水平不显示
        this.setVerticalScrollBarEnabled(false); // 垂直不显示
        setPageCacheCapacity(setting);
        //不设置将调用手机浏览器
        setClient();
        //设置加载地址
        setUrl(mUrl);//设置加载地址
        //设置进度条颜色（渐变的）
        setProgressDrawable();

    }

    /**
     * add by zhsf @2015-11-10 当进行goBack的时候 使用前一个页面的缓存 避免每次都重新载入
     *
     * @param webSettings webView的settings
     */
    protected void setPageCacheCapacity(WebSettings webSettings) {
        try {
            Class<?> c = Class.forName("android.webkit.WebSettingsClassic");
            if (null != c) {
                Method tt = c.getMethod("setPageCacheCapacity", new Class[]{int.class});
                if (null != tt) {
                    tt.invoke(webSettings, 5);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
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
