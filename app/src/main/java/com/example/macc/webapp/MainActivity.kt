package com.example.macc.webapp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webview.webChromeClient= WebChromeClient()

        webview.settings.javaScriptEnabled=true


        //webview.loadUrl("https://www.repubblica.it")
      //        webview.loadUrl("file:///android_asset/index.html")
        //        webview.loadUrl("file:///android_asset/calculator.html")
      //  webview.loadUrl("file:///android_asset/bootstrap.html")

        webview.loadUrl("file:///android_asset/bridge.html")



        val proxy = MainActivity.Proxy(this)
        // sensorReader = new SensorReader(this);

        // sensorReader = new SensorReader(this);
        webview.addJavascriptInterface(proxy, "Android")


        webview.evaluateJavascript("javascript:2+2",
            ValueCallback<String?> { value -> Log.i("result: ", value) })

    }


    class Proxy internal constructor(var mContext: Context) {
        var activity: Activity

        @JavascriptInterface
        fun go(msg: String) {
            val js = "javascript:write('$msg');"
            //   final String js = "javascript:write('Reading');";
            activity.runOnUiThread { activity.webview.evaluateJavascript(js, null) }
            //            Toast.makeText(mContext,js,Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        fun Hi(msg: String): String {
            Toast.makeText(mContext, "called2", Toast.LENGTH_SHORT).show()
            //webView.evaluateJavascript("javascript: go(\"fff\");",null);
            return "$msg\nHere is JS!"
        }

        init {
            activity = mContext as MainActivity
        }
    }

}



