# ZWebView
### 一个支持H5调起微信、支付宝、QQ的Webview
# 添加依赖
`compile 'com.github.Zachary46:ZWebView:v1.0'`
# 添加仓库
```
allprojects {
    repositories {
        jcenter()
        maven { url 'https://www.jitpack.io' }
    }
}
```
# 代码使用
```
LinearLayout rootView= (LinearLayout) findViewById(R.id.ll);
        ZWeb.with(this)
                .addParentView(rootView)
                .setBackgroundRes(R.drawable.progress_drawable)
                .addTitleListener(new ZWeb.TitleListener() {
                    @Override
                    public void getTitle(String title) {
                       
                    }
                })
                .addProgressListener(new ZWeb.ProgressListener() {
                    @Override
                    public void getProgress(int progress) {
                        
                    }
                })
                .setUrl("https://www.github.com/Zachary46")
                .go();
```
