package lib.android.com.webviewlib.weblib;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author hjhrq1991 created at 8/22/16 14 41.
 */
public class BridgeWebViewClient extends WebViewClient {
    private BridgeWebView webView;
    /**
     * 是否重定向，避免web为渲染即跳转导致系统未调用onPageStarted就调用onPageFinished方法引起的js桥初始化失败
     */
    private boolean isRedirected;
    /**
     * onPageStarted连续调用次数,避免渲染立马跳转可能连续调用onPageStarted多次并且调用shouldOverrideUrlLoading后不调用onPageStarted引起的js桥未初始化问题
     */
    private int onPageStartedCount = 0;

    private OnShouldOverrideUrlLoading onShouldOverrideUrlLoading;

    public BridgeWebViewClient(BridgeWebView webView) {
        this.webView = webView;
    }

    public void setOnShouldOverrideUrlLoading(OnShouldOverrideUrlLoading onShouldOverrideUrlLoading) {
        this.onShouldOverrideUrlLoading = onShouldOverrideUrlLoading;
    }

    public void removeListener() {
        if (onShouldOverrideUrlLoading != null) {
            onShouldOverrideUrlLoading = null;
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //modify：hjhrq1991，web为渲染即跳转导致系统未调用onPageStarted就调用onPageFinished方法引起的js桥初始化失败
        if (onPageStartedCount < 2) {
            isRedirected = true;
        }
        onPageStartedCount = 0;

        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) { // 如果是返回数据
            webView.handlerReturnData(url);
            return true;
        } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { //
            webView.flushMessageQueue();
            return true;
        } else {
            if (onShouldOverrideUrlLoading != null) {
                return onShouldOverrideUrlLoading.onShouldOverrideUrlLoading(view, url);
            } else {
                return super.shouldOverrideUrlLoading(view, url);
            }
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        //modify：hjhrq1991，web为渲染即跳转导致系统未调用onPageStarted就调用onPageFinished方法引起的js桥初始化失败
        isRedirected = false;
        onPageStartedCount++;

        if (onShouldOverrideUrlLoading != null) {
            onShouldOverrideUrlLoading.onPageStarted(view, url, favicon);
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        //modify：hjhrq1991，web为渲染即跳转导致系统未调用onPageStarted就调用onPageFinished方法引起的js桥初始化失败
        if (BridgeConfig.toLoadJs != null && !url.contains("about:blank") && !isRedirected) {
            BridgeUtil.webViewLoadLocalJs(view, BridgeConfig.toLoadJs, BridgeConfig.defaultJs, BridgeConfig.customJs);
        }

        if (webView.getStartupMessage() != null) {
            for (Message m : webView.getStartupMessage()) {
                webView.dispatchMessage(m);
            }
            webView.setStartupMessage(null);
        }

        if (onShouldOverrideUrlLoading != null) {
            onShouldOverrideUrlLoading.onPageFinished(view, url);
        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        if (onShouldOverrideUrlLoading != null) {
            onShouldOverrideUrlLoading.onReceivedError(view, errorCode, description, failingUrl);
        }
    }
}