package com.tencent.tls.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tencent.tls.helper.MResource;
import com.tencent.tls.helper.SmsContentObserver;
import com.tencent.tls.service.Constants;
import com.tencent.tls.service.TLSService;

public class PhonePwdRegisterActivity extends Activity {

    private final static String TAG = "PhonePwdRegisterActivity";

    private TLSService tlsService;
//    private SmsContentObserver smsContentObserver = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(MResource.getIdByName(getApplication(), "layout", "tencent_tls_ui_activity_phone_pwd_register"));

        // 设置返回按钮
        findViewById(MResource.getIdByName(getApplication(), "id", "btn_back"))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });

        tlsService = TLSService.getInstance();
        tlsService.initPhonePwdRegisterService(this,
                (Button) findViewById(MResource.getIdByName(getApplication(), "id", "selectCountryCode")),
                (EditText) findViewById(MResource.getIdByName(getApplication(), "id", "phone")),
                (EditText) findViewById(MResource.getIdByName(getApplication(), "id", "txt_checkcode")),
                (Button) findViewById(MResource.getIdByName(getApplication(), "id", "btn_requirecheckcode")),
                (Button) findViewById(MResource.getIdByName(getApplication(), "id", "btn_verify"))
        );

        findViewById(MResource.getIdByName(getApplication(), "id", "selectCountryCode")).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhonePwdRegisterActivity.this, SelectCountryCodeActivity.class);
                intent.putExtra(Constants.EXTRA_LOGIN_WAY, Constants.PHONE_PWD_REG);
                startActivity(intent);
            }
        });

//        smsContentObserver = new SmsContentObserver(new Handler(),
//                this,
//                (EditText) findViewById(MResource.getIdByName(getApplication(), "id", "txt_checkcode")),
//                Constants.PHONEPWD_REGISTER_SENDER);

        //注册短信变化监听
//        this.getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, smsContentObserver);
    }

    protected void onDestroy() {
        super.onDestroy();
//        if (smsContentObserver != null) {
//            this.getContentResolver().unregisterContentObserver(smsContentObserver);
//        }
//
//        smsContentObserver = null;
        tlsService = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String countryCode = intent.getStringExtra(Constants.COUNTRY_CODE);
        String countryName = intent.getStringExtra(Constants.COUNTRY_NAME);
        if (countryName != null && countryCode != null) {
            ((Button) findViewById(MResource.getIdByName(getApplication(), "id", "selectCountryCode"))).setText(countryName + "+" + countryCode);
        }
    }
}
