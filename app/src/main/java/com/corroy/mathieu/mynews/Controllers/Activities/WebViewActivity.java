package com.corroy.mathieu.mynews.Controllers.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.corroy.mathieu.mynews.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.webView) WebView webView;
    WebSettings webSettings;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view2);
        ButterKnife.bind(this);

        // Retrieve the URL and display news in a webView
        Intent intent = getIntent();
        String url = intent.getStringExtra("Url");
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}