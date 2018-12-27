package com.corroy.mathieu.mynews.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.corroy.mathieu.mynews.R;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view2);

        webView = findViewById(R.id.webView);
        webView.loadUrl("https://www.youtube.com/?hl=fr&gl=FR");
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}