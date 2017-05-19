package com.yat3s.calendar.event;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.widget.IconTextView;
import com.yat3s.calendar.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Yat3s on 19/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class NewEventActivity extends AppCompatActivity {
    @BindView(R.id.menu_commit)
    IconTextView mMenuCommit;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title_et)
    EditText mTitleEt;
    @BindView(R.id.all_day_switch)
    SwitchCompat mAllDaySwitch;
    @BindView(R.id.description_et)
    EditText mDescriptionEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        ButterKnife.bind(this);

        configureToolbar();
    }

    private void configureToolbar() {
        setSupportActionBar(mToolbar);
        if (null != getSupportActionBar()) {
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        mToolbar.setNavigationIcon(new IconDrawable(this, MaterialIcons.md_clear)
                .colorRes(R.color.md_white_1000)
                .actionBarSize());

        mMenuCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewEventActivity.this, "Sorry, I don't have enough time to complete this.", Toast.LENGTH_SHORT)
                        .show();
                finish();
            }
        });
        setTitle("New Event");
    }
}
