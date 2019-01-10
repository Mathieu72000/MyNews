package com.corroy.mathieu.mynews.Controllers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.corroy.mathieu.mynews.R;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    WebSettings webSettings;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view2);

        Intent intent = new Intent();
        String url = intent.getStringExtra("Url");
        webView = findViewById(R.id.webView);
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}