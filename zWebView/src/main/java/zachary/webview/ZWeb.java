package zachary.webview;

import android.content.Context;
import android.view.ViewGroup;

/**
 * Author: Zachary
 * Time: 2017/12/23 下午 5:05
 * Desc:Builder类
 */

public class ZWeb{
    private static Context mContext;
    private ZWeb zWeb;
    public String url;
    public ViewGroup viewGroup;
    public int res;
    public TitleListener titleListener;
    public ProgressListener progressListener;
    public static ZWeb with(Context context){
        mContext=context;
        return new ZWeb();
    }

    public ZWeb addParentView(ViewGroup v){
        this.viewGroup=v;
        return this;
    }

    public ZWeb setUrl(String url){
        this.url=url;
        return this;
    }

    public ZWeb setBackgroundRes(int res){
        this.res=res;
        return this;
    }

    public ZWeb addTitleListener(TitleListener t){
        this.titleListener=t;
        return this;
    }

    public ZWeb addProgressListener(ProgressListener p){
        this.progressListener=p;
        return this;
    }

    public ZWebView go(){
        return new ZWebView(mContext,this);
    }

    public interface TitleListener{
        void getTitle(String title);
    }

    public interface ProgressListener{
        void getProgress(int progress);
    }
}