# ZWebView
[![](https://img.shields.io/badge/build-success-green.svg)](https://github.com/Zachary46/ZWebView)
[![](https://img.shields.io/badge/version-1.0-orange.svg)](https://github.com/Zachary46/ZWebView)
[![](https://img.shields.io/badge/author-zachary46-blue.svg)](https://github.com/Zachary46/ZWebView)

### 一个支持H5调起微信、支付宝、QQ的Webview
# 添加依赖
`compile 'com.github.Zachary46:ZWebView:v1.1'`
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
