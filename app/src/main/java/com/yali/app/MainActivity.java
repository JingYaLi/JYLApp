package com.yali.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author jingyali
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webView = (WebView) findViewById(R.id.webView);
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        initWebview(webView);
    }

    private void initWebview(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);//自适应屏幕
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);//支持缩放
        settings.setBuiltInZoomControls(true);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);

        //支持内容重新布局
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置缓存模式
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setLoadsImagesAutomatically(true);
// 是否保存表单数据
        settings.setSaveFormData(true);
        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
        settings.setAllowUniversalAccessFromFileURLs(true);

        // 开启 DOM storage API 功能
        settings.setDomStorageEnabled(true);
        //开启 database storage API 功能
        settings.setDatabaseEnabled(true);
        //设置数据库缓存路径
        settings.setTextZoom(100);
        // 修复webview不能加载图片的问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setBlockNetworkImage(false);

//        webView.loadUrl("https://h5.hrs100.com/html/third/school/index.html");
        webView.loadUrl("http://www.baidu.com");
        webView.setWebViewClient(new WebViewClient() {
                                     @Override
                                     public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                         Log.i("MainActivity", "shouldOverrideUrlLoading" + " view = " + "");

//                                         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                             if (request.isForMainFrame()) {
//                                                 Log.i("MainActivity", "shouldOverrideUrlLoading" + " view = " + request.isForMainFrame());
//                                             }
//                                         }
//                                         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                             view.loadUrl(request.getUrl().toString());
//                                         }
                                         view.loadUrl(url);
                                         return true;
                                     }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.i("BaseWebView", "onReceivedError" + " failingUrl = " + failingUrl);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                }
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
               Log.i("MainActivity", "onReceivedError" + " view = " + error.toString());
                if (request.isForMainFrame()) {
                    Log.i("MainActivity", "onReceivedError" + " request.isForMainFrame() = " + request.isForMainFrame());
                }
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.i("MainActivity", "onReceivedError" + " view = " + error.toString());
                handler.proceed();  // 接受所有网站的证书
            }
                                 }
        );
    }

    private void syncCookie(String url) {

        try {

            CookieSyncManager.createInstance(this);

            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();// 移除
            cookieManager.removeAllCookie();
            String oldCookie = cookieManager.getCookie(url);
            if (oldCookie != null) {
                Log.d("oldCookie", oldCookie);
            }

            StringBuilder sbCookie = new StringBuilder();
            sbCookie.append(String.format("JSESSIONID=%s", "QQXB_ID"));
            sbCookie.append(String.format(";domain=%s", "QQXB_DOMAIN"));
            sbCookie.append(String.format(";path=%s", "QQXB_PATH"));

            String cookieValue = sbCookie.toString();
            cookieManager.setCookie(url, cookieValue);
            CookieSyncManager.getInstance().sync();

            String newCookie = cookieManager.getCookie(url);
            if (newCookie != null) {
                Log.d("newCookie", newCookie);
            }
        } catch (Exception e) {
            Log.e("syncCookie failed", e.toString());
        }
    }
}
