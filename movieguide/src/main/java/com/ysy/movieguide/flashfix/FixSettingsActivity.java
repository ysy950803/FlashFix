package com.ysy.movieguide.flashfix;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.ysy.movieguide.Constants;
import com.ysy.movieguide.R;
import com.ysy.movieguide.util.SpDataUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FixSettingsActivity extends AppCompatActivity {

    @BindView(R.id.tv_debug)
    View mDebugTv;
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

    @BindView(R.id.lyt_fav)
    View mLayoutFav;
    @BindView(R.id.lyt_top_pic)
    View mLayoutTopPic;
    @BindView(R.id.lyt_rate_sort)
    View mLayoutRateSort;
    @BindView(R.id.lyt_col_num)
    View mLayoutColNum;

    @BindView(R.id.card_update)
    View mUpdateCard;
    @BindView(R.id.tv_version)
    TextView mVersionTv;

    private Unbinder unbinder;
    private SpDataUtils mDataUtils;
    private FixDialogFactory mDialogFactory;
    private boolean isDebug = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_settings);
        unbinder = ButterKnife.bind(this);
        mDataUtils = SpDataUtils.getsInstance(this);
        mDialogFactory = new FixDialogFactory(this, new Handler());
        setToolbar();
        initViews();
        initData();
    }

    private void initViews() {
        mDebugTv.setOnClickListener(v -> {
            isDebug = !isDebug;
            Toast.makeText(FixSettingsActivity.this, "D:" + (isDebug ? "T" : "F"),
                    Toast.LENGTH_SHORT).show();
        });

        mClearPatchCard.setOnClickListener(v -> mDialogFactory.showResetDialog(isDebug, () -> {
            mDataUtils.saveData(Constants.SP_FAV, false);
            mDataUtils.saveData(Constants.SP_TOP_PIC, false);
            mDataUtils.saveData(Constants.SP_RATE_SORT, false);
            mDataUtils.saveData(Constants.SP_COL_NUM, false);
            mDataUtils.saveData(Constants.SP_UPDATE, false);
            initData();
        }));

        mSubmitFab.setOnClickListener(v -> mDialogFactory.showFlashFixDialog(isDebug, () -> {
            mDataUtils.saveData(Constants.SP_FAV, mFavCheckBox.isChecked());
            mDataUtils.saveData(Constants.SP_TOP_PIC, mTopPicCheckBox.isChecked());
            mDataUtils.saveData(Constants.SP_RATE_SORT, mRateSortCheckBox.isChecked());
            mDataUtils.saveData(Constants.SP_COL_NUM, mColNumCheckBox.isChecked());
            if (!mFavCheckBox.isChecked() && !mTopPicCheckBox.isChecked()
                    && !mRateSortCheckBox.isChecked() && !mColNumCheckBox.isChecked()) {
                mDataUtils.saveData(Constants.SP_UPDATE, false);
                Toast.makeText(this, "重置成功", Toast.LENGTH_SHORT).show();
            } else {
                mDataUtils.saveData(Constants.SP_UPDATE, true);
            }
            initData();
        }));

        mLayoutFav.setOnClickListener(v -> mFavCheckBox.setChecked(!mFavCheckBox.isChecked()));
        mLayoutTopPic.setOnClickListener(v -> mTopPicCheckBox.setChecked(!mTopPicCheckBox.isChecked()));
        mLayoutRateSort.setOnClickListener(v -> mRateSortCheckBox.setChecked(!mRateSortCheckBox.isChecked()));
        mLayoutColNum.setOnClickListener(v -> mColNumCheckBox.setChecked(!mColNumCheckBox.isChecked()));

        mUpdateCard.setOnClickListener(v -> mDialogFactory.showUpdateDialog(isDebug));
    }

    private void initData() {
        mFavCheckBox.setChecked(mDataUtils.getData(Constants.SP_FAV));
        mTopPicCheckBox.setChecked(mDataUtils.getData(Constants.SP_TOP_PIC));
        mRateSortCheckBox.setChecked(mDataUtils.getData(Constants.SP_RATE_SORT));
        mColNumCheckBox.setChecked(mDataUtils.getData(Constants.SP_COL_NUM));

        mVersionTv.setText(new FixVersionInfo(this, mDataUtils.getData(Constants.SP_UPDATE))
                .getVersionInfo());
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
