package com.proatcoding.pets.activity.payment.WeAcceptSDK;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.proatcoding.pets.R;


/**
 * This is a web-view activity that opened when the transactions needed to be authorized using 3DSecure method.
 */
public class ThreeDSecureWebViewActivty extends AppCompatActivity {

    String authenticationUrl;
    int themeColor;
    String title;
    private static final String TAG = "ThreeDSecureWebViewActi";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_d_secure);

        resetVariables();
        getThreeDSecureParameters();
        //linkViews();

        /*
         Uncomment the below line in case of testing
          The below URL shall redirect you to another URL to simulate the authentication scenario
          */
        // authenticationUrl = "http://gdabackend.com/paymob/testintegration/createpaymenttoken/testredirect2.php";

        WebView webView = findViewById(R.id.webView1);

        // overriding the web-view default client into a custom one to listen to the redirected URLs
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG, url);

                // Check if the new redirected URL is the success landing URL to close the web-view activity and return to the payment activity
                if (url.startsWith(ServerUrls.PAYMOB_SUCCESS_URL)) {

                    Intent intent = new Intent();
                    try {
                        intent.putExtra(ThreeDSecureResponseKeys.RAW_PAY_RESPONSE, QueryParamsExtractor.getQueryParams(url));
                        setResult(IntentConstants.USER_FINISHED_3D_VERIFICATION, intent);
                    } catch (Exception E) {
                        // Log.d("Accept", "Excpetion thrown while getting query params");
                    }
                    finish();

                    // return true Indicates WebView to NOT load the url;
                    return true;
                } else {
                    //return false Indicates WebView to load the url;
                    return false;
                }

            }
        });

        // Allow the web-view to execute Javascript
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(authenticationUrl);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onCancelPress();
        }
        return super.onOptionsItemSelected(item);
    }

    private void onCancelPress() {
        Intent canceledIntent = new Intent();
        setResult(IntentConstants.USER_CANCELED, canceledIntent);
        finish();
    }

    private void resetVariables() {
        authenticationUrl = null;
    }

    private void getThreeDSecureParameters() {
        Intent intent = getIntent();
        authenticationUrl = intent.getStringExtra(ThreeDSecureIntentKeys.THREE_D_SECURE_URL);
        checkIfPassed(ThreeDSecureIntentKeys.THREE_D_SECURE_URL, authenticationUrl);
        title = intent.getStringExtra(ThreeDSecureIntentKeys.THREE_D_SECURE_ACTIVITY_TITLE);
        themeColor = intent.getIntExtra(ThreeDSecureIntentKeys.THEME_COLOR, getResources().getColor(R.color.colorDefaultButton));
    }

    private void linkViews() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ColorDrawable colorDrawable = new ColorDrawable(themeColor);
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle(title);
    }

    private void checkIfPassed(String key, String value) {
        Log.d(TAG, "checkIfPassed: "+value);
        Log.d(TAG, "checkIfPassed: "+key);
        if (value == null) {
            abortForNotPassed(key);
        }
    }

    private void abortForNotPassed(String key) {
        Log.d(TAG, "abortForNotPassed: "+key);
        Intent errorIntent = new Intent();
        errorIntent.putExtra(IntentConstants.MISSING_ARGUMENT_VALUE, key);
        setResult(IntentConstants.MISSING_ARGUMENT, errorIntent);
        finish();
    }
}
