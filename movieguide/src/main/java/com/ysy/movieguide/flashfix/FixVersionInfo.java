package com.ysy.movieguide.flashfix;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.ysy.movieguide.Constants;
import com.ysy.movieguide.util.SpDataUtils;

public class FixVersionInfo {

    private Context mContext;
    private boolean isUpdated;
    private static final String[] HASH_CODES = new String[]{
            "13b5b38a5b00b8c5649b25c8eb42c27e",
            "64d90d18a6e41f18ee943f8f86f55932",
            "7d2000fc322b99117263232a00aa2949",
            "9033129f9a2b44881d836d6e4535d95b",
            "f6b302bc928356666626f546c7bc72e4",
            "9a2a4e53036da935974ce3faa4bea410",
            "aebe39864241375a64846f9f5d1aff48",
            "79d47fe9c62ff7c6fd5e1226230cf7b9",
            "2b44881d84626af546c846f9f5d1af36",
            "e943f8f86665b38a5b00b8c26f546c7f",
            "4c6351ee7e137f5cf339bae887d6065e",
            "359557f93eda60d6f5a2ea74765e0a78",
            "c03206effdef9db5b828fd667a8ba0e9",
            "8eacb5ae172f968f04ecb7ca79bb388e",
            "3eda60d6f5a2ea747650cf7b90cf17be",
            "b18c5649b90d18ac6fd15e6e46b30225"
    };

    public FixVersionInfo(Context context, boolean updated) {
        mContext = context;
        isUpdated = updated;
    }

    public String getVersionInfo() {
        String versionName = getAppVersionName();
        if (isUpdated) {
            String temp[] = versionName.split("\\.");
            int tail = Integer.valueOf(temp[2]) + 2;
            versionName = temp[0] + "." + temp[1] + "." + tail;
        }
        return "Version " + versionName + "-" + getVersionHash();
    }

    private String getVersionHash() {
        SpDataUtils dataUtils = SpDataUtils.getsInstance(mContext);
        int[] fixCheck = new int[4];
        fixCheck[0] = dataUtils.getData(Constants.SP_FAV) ? 1 : 0;
        fixCheck[1] = dataUtils.getData(Constants.SP_TOP_PIC) ? 1 : 0;
        fixCheck[2] = dataUtils.getData(Constants.SP_RATE_SORT) ? 1 : 0;
        fixCheck[3] = dataUtils.getData(Constants.SP_COL_NUM) ? 1 : 0;
        StringBuilder binStr = new StringBuilder();
        for (int i : fixCheck) {
            binStr.append(i);
        }
        return HASH_CODES[Integer.valueOf(binStr.toString(), 2)];
    }

    private String getAppVersionName() {
        String versionName = "3.0.7";
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "3.0.7";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
