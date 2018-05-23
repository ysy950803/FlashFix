package com.ysy.movieguide.flashfix;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.ysy.movieguide.Constants;
import com.ysy.movieguide.R;
import com.ysy.movieguide.util.ConnectionDetector;
import com.ysy.movieguide.util.SpDataUtils;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FixSettingsActivity extends AppCompatActivity {

    @BindView(R.id.card_clear_patch)
    View mClearPatchCard;
    @BindView(R.id.fab_submit)
    View mSubmitFab;
    @BindView(R.id.checkbox_fav)
    CheckBox mFavCheckBox;
    @BindView(R.id.checkbox_top_pic)
    CheckBox mTopPicCheckBox;
    @BindView(R.id.checkbox_rate_sort)
    CheckBox mRateSortCheckBox;
    @BindView(R.id.checkbox_col_num)
    CheckBox mColNumCheckBox;

    private Unbinder unbinder;
    private SpDataUtils mDataUtils;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_settings);
        unbinder = ButterKnife.bind(this);
        mDataUtils = SpDataUtils.getsInstance(this);
        mHandler = new Handler();
        setToolbar();
        initViews();
        initData();
    }

    private void initViews() {
        mClearPatchCard.setOnClickListener(v -> {
            mDataUtils.saveData(Constants.SP_FAV, false);
            mDataUtils.saveData(Constants.SP_TOP_PIC, false);
            mDataUtils.saveData(Constants.SP_RATE_SORT, false);
            mDataUtils.saveData(Constants.SP_COL_NUM, false);
            initData();
            Toast.makeText(this, "清除成功", Toast.LENGTH_SHORT).show();
        });

        mSubmitFab.setOnClickListener(v -> {
            if (showUploadingDialog()) {
                mDataUtils.saveData(Constants.SP_FAV, mFavCheckBox.isChecked());
                mDataUtils.saveData(Constants.SP_TOP_PIC, mTopPicCheckBox.isChecked());
                mDataUtils.saveData(Constants.SP_RATE_SORT, mRateSortCheckBox.isChecked());
                mDataUtils.saveData(Constants.SP_COL_NUM, mColNumCheckBox.isChecked());
            }
        });
    }

    private void initData() {
        mFavCheckBox.setChecked(mDataUtils.getData(Constants.SP_FAV));
        mTopPicCheckBox.setChecked(mDataUtils.getData(Constants.SP_TOP_PIC));
        mRateSortCheckBox.setChecked(mDataUtils.getData(Constants.SP_RATE_SORT));
        mColNumCheckBox.setChecked(mDataUtils.getData(Constants.SP_COL_NUM));
    }

    private boolean showUploadingDialog() {
        ConnectionDetector cd = new ConnectionDetector(this);
        if (cd.isConnectingToInternet()) {
            ProgressDialog dialog = new ProgressDialog(this);
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
                    mHandler.postDelayed(dialog::dismiss, getUploadingTime());
                }, getUploadingTime());
            }, getUploadingTime());
            return true;
        } else {
            Toast.makeText(this, "请检查网络连接", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private long getUploadingTime() {
        int max = 4000;
        int min = 2000;
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.fix_settings);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
