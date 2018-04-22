package com.ysy.sophix.app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.Keep;
import android.util.Log;
import android.widget.Toast;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

public class SophixStubApplication extends SophixApplication {

    private final static String TAG = "SophixStubApplication";

    @Keep
    @SophixEntry(MainApplication.class)
    static class RealApplicationStub {
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        initSophix();
    }

    private void initSophix() {
        String appVersion = "0.0.0";
        try {
            appVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData("24849356-1", "b53946a6552b5b6f8dca88bce83c271d",
                        "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCsaDAJ7HziTsDHcTgIZb7XCmpOUvsx7JlgV6zg/2N7LCZPnjL4qtuYjl81PkU98KLOHxQzyuoIZBw41/GBWXioqjd+kME61KYvxwgMvWlXxZQ7R9C3McyFyfrjMxh2iyHSnpANQ1iAp093xsZEF5CPybTv2e22WIfBuJvskgiBUH6ys24EfaNjyBfsobcEsukWLqrkSH9zsEndBaJjHk8u4p38/HjBSzfCkfqeNUFVmTC+h2paBtchzIrqXxXiTzurOXsoTunWi+UhAlwjDc61gQn3KwdOe3iEr8hHP3Fad/XaxdKh1P106PH+6V2N8/LxRranKs6ms1Gd+NqEOfXNAgMBAAECggEBAJmHemgL8bYyPVgeCbDoskabHsm6+bieooL58Ji4tOVGDqPOFEP/GujCuCQY3N5owQJfCMWKiFiKldtE6eqvO1SvqBWMYJa8MXXQfumhbwLbjsIvlLzemuv1TvcuVDWUdr0xuMQm2dRPqePW3k5a0cBJ9le1MQu10McBV9EWxn4oMveWbW97QiWvg3ti0TC8ZpKsi1fLn0IiZgXBMzFLQlVQ5uouVOnjo+FSu1qb/gXSev4Gc3noHjvvC2Fur5fYHW4Gom8OpZx42gpUaEMPpuvJav+oQsr5vmbCoBBRRqEmIzMziGRKH8b5l799fkdRdOp1Rmk7A7shF2/wDq+OloECgYEA20rh6orpcAN9PNP231zFtdN3R+KT5V7LoG9UKxKM9jfUII54nkB3za+9+Lm63AQ2IVNYJk7I+14Lhe3FcfuTEh/So16FVIyXhIMr/oZoiFjZM1B4YBG1e3sC+kpFGBv4xmGfWcHH9yHP7wuAjd1c0m1aQb2N42gszL0ghaDi8eECgYEAyUQq7F6V9he8qIHkwTU2fQYFHGQ1a3MsbtMFK1jorOglWNJZusm1l3OzfMPwBokSPW0m9/+F0FeOOheFDwe+oFSBJ8yXDV95Am3UY5+sM4HCa+syOB9Fmltpq5AyIuBHyFPrPkAie64rHITxf+956pVzhG8iyTjUBGA8P606GW0CgYAY8fC2/GxHiK4TYE1B5Uy8PTzhWX3XZqwlKOTbLXYJgBpKRcqkvdKi/C3RAFeoNXZu3gs1+FpWdcE7z+tY5vQ46JPOb68dCg0mTsGPZ3y2ndy/QJUg4ILTdA/HpvErJ+tprasxy6BSlW8Q0y8hdB/d9ACDX+unBa+q7oynhsCqIQKBgGrU/z+2OT2GfgKn6CfSOhkE8AORAXYGjYhKBI1sxRy6oNBT34MxfG1m4dgRHUAq0/69CJlNDdd4EiutvlgrETmDgYcBpxBXJCFw8PLsl3hDoVKtZlu55bU4Xo/Nm389s3yXZuAFJQine4TI4HO7RuiI6edbIojS3F9yQai/J37FAoGBALxPkEOGK0WnMGKENdyZsp5tAd0MOjyMZS6Wdw2A/VmVdLkEnrnM8cUozqxSLxB301L3ZMDkVWhBTAxuXx3H9aNMHcdoZZwCkDPzBwN0kcSfYWwO/hNbVq/m5YXVZlrClZ3yHHvh1y/Go2rf0IIJVuyXIEOBN6eLmO8JZq2srQQa")
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info,
                                       final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG, "sophix load patch success!");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                        }
                    }
                })
                .initialize();
    }
}
