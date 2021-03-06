package com.example.android.sample.myrssreader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewFragment;

/**
 * 横幅が広い画面の時にWebViewでページを表示するFragment。
 */
public class WebPageFragment extends WebViewFragment {

    /**
     * このフラグメントのインスタンスを返す。
     *
     * @param url
     * @return
     */
    public static WebPageFragment newInstance(String url) {
        WebPageFragment fragment = new WebPageFragment();

        Bundle args = new Bundle();
        args.putString("url",url);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        WebView webView = (WebView) super.onCreateView(inflater,container,savedInstanceState);

        Bundle args = getArguments();
        String url = args.getString("url");

        // 指定したURLをロードする
        webView.loadUrl(url);

        return webView;
    }
}
