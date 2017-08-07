package mvp.android.com.mvplib.utils;

import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.webkit.WebSettings.LOAD_NO_CACHE;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class WebViewHelper {

    private static final WebViewHelper ourInstance = new WebViewHelper();

    public static WebViewHelper getInstance() {
        return ourInstance;
    }

    private WebViewHelper() {
    }

    /**
     * setAllowFileAccess 启用或禁止WebView访问文件数据
     * setBlockNetworkImage 是否显示网络图像
     * setBuiltInZoomControls 设置是否支持缩放
     * setCacheMode 设置缓冲的模式
     * setDefaultFontSize 设置默认的字体大小
     * setDefaultTextEncodingName 设置在解码时使用的默认编码
     * setFixedFontFamily 设置固定使用的字体
     * setJavaSciptEnabled 设置是否支持Javascript
     * setLayoutAlgorithm 设置布局方式
     * setLightTouchEnabled 设置用鼠标激活被选项
     * setSupportZoom 设置是否支持变焦
     *
     * @param webView
     */
    public void setWebViewSetting(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setAllowFileAccess(true);//启用或禁止WebView访问文件数据
        webSettings.setBlockNetworkImage(true); //是否显示网络图像
        webSettings.setBuiltInZoomControls(true); //设置是否支持缩放
        webSettings.setCacheMode(LOAD_NO_CACHE); //设置缓冲的模式
        webSettings.setDefaultFontSize(14); //设置默认的字体大小
        webSettings.setDefaultTextEncodingName("utf-8"); //设置在解码时使用的默认编码
//        webSettings.setFixedFontFamily(); //设置固定使用的字体
        webSettings.setJavaScriptEnabled(true); //设置是否支持Javascript
//        webSettings.setLayoutAlgorithm(); //设置布局方式
//        webSettings.setLightTouchEnabled(); //设置用鼠标激活被选项
        webSettings.setSupportZoom(true); //设置是否支持变焦

    }

    /**
     * doUpdate VisitedHistory 更新历史记录
     * onFormResubmission 应用程序重新请求网页数据
     * onLoadResource 加载指定地址提供的资源
     * onPageFinished 网页加载完毕
     * onPageStarted 网页开始加载
     * onReceivedError 报告错误信息
     * onScaleChanged WebView发生改变
     * shouldOverrideUrlLoading 控制新的连接在当前WebView中打开
     */
    public void setWebViewClient() {
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        };
    }

    /**
     * onCloseWindow 关闭WebView
     * onCreateWindow 创建WebView
     * onJsAlert 处理Javascript中的Alert对话框
     * onJsConfirm处理Javascript中的Confirm对话框
     * onJsPrompt处理Javascript中的Prompt对话框
     * onProgressChanged 加载进度条改变
     * onReceivedlcon 网页图标更改
     * onReceivedTitle 网页Title更改
     * onRequestFocus WebView显示焦点
     */
    public void setWebChromeClient() {
        WebChromeClient webChromeClient = new WebChromeClient() {

        };
    }
}
