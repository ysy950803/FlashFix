package com.ysy.movieguide.flashfix;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.widget.Toast;

import com.ysy.movieguide.util.ConnectionDetector;

import java.util.Random;

public class FixDialogFactory {

    public interface DialogStatCallback {
        void onProgressDone();
    }

    private Context mContext;
    private Handler mHandler;

    public FixDialogFactory(Context context, Handler handler) {
        mContext = context;
        mHandler = handler;
    }

    public void showFlashFixDialog(boolean isDebug, DialogStatCallback callback) {
        ConnectionDetector cd = new ConnectionDetector(mContext);
        if (cd.isConnectingToInternet()) {
            ProgressDialog dialog = new ProgressDialog(mContext);
            dialog.setCancelable(false);
            dialog.setTitle("请稍等");
            dialog.setMessage("正在上传信息...");
            dialog.show();
            mHandler.postDelayed(() -> {
                dialog.dismiss();
                dialog.setMessage("正在生成补丁...");
                dialog.show();
                mHandler.postDelayed(() -> {
                    dialog.dismiss();
                    dialog.setMessage("正在进行热修复...");
                    dialog.show();
                    mHandler.postDelayed(() -> {
                        dialog.dismiss();
                        callback.onProgressDone();
                    }, getUploadingTime(isDebug));
                }, getUploadingTime(isDebug));
            }, getUploadingTime(isDebug));
        } else {
            Toast.makeText(mContext, "请检查网络连接", Toast.LENGTH_SHORT).show();
        }
    }

    public void showResetDialog(boolean isDebug, DialogStatCallback callback) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setCancelable(false);
        dialog.setTitle("请稍等");
        dialog.setMessage("正在清除补丁...");
        dialog.show();
        mHandler.postDelayed(() -> {
            dialog.dismiss();
            dialog.setMessage("正在回滚热修复...");
            dialog.show();
            mHandler.postDelayed(() -> {
                dialog.dismiss();
                Toast.makeText(mContext, "重置成功", Toast.LENGTH_SHORT).show();
                callback.onProgressDone();
            }, getUploadingTime(isDebug));
        }, getUploadingTime(isDebug));
    }

    public void showUpdateDialog(boolean isDebug) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setCancelable(false);
        dialog.setTitle("请稍等");
        dialog.setMessage("正在检查更新...");
        dialog.show();
        mHandler.postDelayed(() -> {
            dialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Update")
                    .setMessage("There is a new version(3.0.9) released. Confirm to download.")
                    .setPositiveButton("Confirm", (dialog1, which) -> {
                        // TODO
                        dialog1.dismiss();
                    })
                    .setNegativeButton("Cancel", (dialog12, which) -> dialog12.dismiss()).show();
        }, getUploadingTime(isDebug));
    }

    private long getUploadingTime(boolean isDebug) {
        if (isDebug) {
            return 500;
        }
        int max = 3500;
        int min = 2000;
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }
}
