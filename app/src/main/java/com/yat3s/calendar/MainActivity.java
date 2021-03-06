package com.yat3s.calendar;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;
import com.yat3s.calendar.common.util.ActivityUtils;
import com.yat3s.calendar.other.ContactFragment;
import com.yat3s.calendar.other.FileFragment;
import com.yat3s.calendar.other.InboxFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yat3s.calendar.R.id.toolbar;

/**
 * The main activity contains all fragment and tab switch processing.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // The Github url of this project.
    private static final String GITHUB_URL = "https://github.com/Yat3s/Calendar";

    // The tab item index.
    private static final int NAVIGATION_TAB_INDEX_INBOX = 0;
    private static final int NAVIGATION_TAB_INDEX_CALENDAR = 1;
    private static final int NAVIGATION_TAB_INDEX_FILE = 2;
    private static final int NAVIGATION_TAB_INDEX_CONTACT = 3;

    @BindView(toolbar)
    Toolbar mToolbar;

    @BindView(R.id.inbox_tab)
    IconTextView mInboxTab;

    @BindView(R.id.calendar_tab)
    IconTextView mCalendarTab;

    @BindView(R.id.file_tab)
    IconTextView mFileTab;

    @BindView(R.id.contact_tab)
    IconTextView mContactTab;

    @BindView(R.id.nav_view)
    NavigationView mNavView;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.menu_option_setting)
    IconTextView mMenuOptionSetting;

    @BindView(R.id.menu_option_optional)
    IconTextView mMenuOptionOptional;

    // The fragments attached to activity.
    private InboxFragment mInboxFragment;
    private CalendarFragment mCalendarFragment;
    private FileFragment mFileFragment;
    private ContactFragment mContactFragment;

    // Current tab index, default is NAVIGATION_TAB_INDEX_CALENDAR.
    private int mCurrentShowTabIndex = NAVIGATION_TAB_INDEX_CALENDAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        configureToolbar();
        configureFragments();

        // Set default page item.
        selectTabWithIndex(mCurrentShowTabIndex);
    }

    /**
     * Configure toolbar and DrawLayout.
     * {@link DrawerLayout}
     */
    private void configureToolbar() {
        ActionBarDrawerToggle mDrawerToggle;
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                    R.string.open_drawer_desc, R.string.close_drawer_desc) {
                public void onDrawerClosed(View view) {
                    supportInvalidateOptionsMenu();
                }

                public void onDrawerOpened(View drawerView) {
                    supportInvalidateOptionsMenu();
                }
            };
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            mDrawerLayout.setDrawerListener(mDrawerToggle);
            mDrawerToggle.syncState();

            if (null != mNavView.getHeaderView(0)) {
                mNavView.getHeaderView(0).findViewById(R.id.github_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_URL)));
                    }
                });
            }
        }
    }

    /**
     * Configure and load all fragment to activity.
     * {@see} {@link ActivityUtils#addFragmentToActivity(FragmentManager, Fragment, int)}
     */
    private void configureFragments() {
        if (null == mCalendarFragment) {
            mInboxFragment = InboxFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mInboxFragment, R.id.content_layout);
        }
        if (null == mCalendarFragment) {
            mCalendarFragment = CalendarFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mCalendarFragment, R.id.content_layout);
        }
        if (null == mFileFragment) {
            mFileFragment = FileFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFileFragment, R.id.content_layout);
        }
        if (null == mContactFragment) {
            mContactFragment = ContactFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mContactFragment, R.id.content_layout);
        }

    }

    /**
     * Bottom navigation tab click callback.
     *
     * @param view
     */
    @OnClick({R.id.inbox_tab, R.id.calendar_tab, R.id.file_tab, R.id.contact_tab})
    public void navigationTabTap(View view) {
        switch (view.getId()) {
            case R.id.inbox_tab:
                selectTabWithIndex(NAVIGATION_TAB_INDEX_INBOX);
                break;
            case R.id.calendar_tab:
                selectTabWithIndex(NAVIGATION_TAB_INDEX_CALENDAR);
                break;
            case R.id.file_tab:
                selectTabWithIndex(NAVIGATION_TAB_INDEX_FILE);
                break;
            case R.id.contact_tab:
                selectTabWithIndex(NAVIGATION_TAB_INDEX_CONTACT);
                break;
        }
    }

    /**
     * Toolbar menu click callback.
     * <p>
     * menu_option_optional is FLEXIBLE/OPTIONAL menu option, it can configure by user.
     * menu_option_optional is SETTING page.
     *
     * @param view
     */
    @OnClick({R.id.menu_option_setting, R.id.menu_option_optional})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_option_setting:
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_option_optional:
                Toast.makeText(this, mCurrentShowTabIndex == NAVIGATION_TAB_INDEX_CALENDAR ?
                        "View Switch Dialog" : "Search", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * Select a navigation tab, contain switch fragment and update navigation tab UI.
     *
     * @param index Navigation tab index, now it has 4 tabs, REF:
     *              {@link #NAVIGATION_TAB_INDEX_INBOX}, {@link #NAVIGATION_TAB_INDEX_CALENDAR},
     *              {@link #NAVIGATION_TAB_INDEX_FILE}, {@link #NAVIGATION_TAB_INDEX_CONTACT}
     */
    private void selectTabWithIndex(int index) {
        Resources resources = getResources();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (index) {
            case NAVIGATION_TAB_INDEX_INBOX:
                mInboxTab.setTextColor(resources.getColor(R.color.colorPrimary));
                mCalendarTab.setTextColor(resources.getColor(R.color.textColorGrey));
                mFileTab.setTextColor(resources.getColor(R.color.textColorGrey));
                mContactTab.setTextColor(resources.getColor(R.color.textColorGrey));
                transaction.show(mInboxFragment);
                transaction.hide(mCalendarFragment);
                transaction.hide(mFileFragment);
                transaction.hide(mContactFragment);
                break;
            case NAVIGATION_TAB_INDEX_CALENDAR:
                mInboxTab.setTextColor(resources.getColor(R.color.textColorGrey));
                mCalendarTab.setTextColor(resources.getColor(R.color.colorPrimary));
                mFileTab.setTextColor(resources.getColor(R.color.textColorGrey));
                mContactTab.setTextColor(resources.getColor(R.color.textColorGrey));
                transaction.hide(mInboxFragment);
                transaction.show(mCalendarFragment);
                transaction.hide(mFileFragment);
                transaction.hide(mContactFragment);
                break;
            case NAVIGATION_TAB_INDEX_FILE:
                mInboxTab.setTextColor(resources.getColor(R.color.textColorGrey));
                mCalendarTab.setTextColor(resources.getColor(R.color.textColorGrey));
                mFileTab.setTextColor(resources.getColor(R.color.colorPrimary));
                mContactTab.setTextColor(resources.getColor(R.color.textColorGrey));
                transaction.hide(mInboxFragment);
                transaction.hide(mCalendarFragment);
                transaction.show(mFileFragment);
                transaction.hide(mContactFragment);
                break;
            case NAVIGATION_TAB_INDEX_CONTACT:
                mInboxTab.setTextColor(resources.getColor(R.color.textColorGrey));
                mCalendarTab.setTextColor(resources.getColor(R.color.textColorGrey));
                mFileTab.setTextColor(resources.getColor(R.color.textColorGrey));
                mContactTab.setTextColor(resources.getColor(R.color.colorPrimary));
                transaction.hide(mInboxFragment);
                transaction.hide(mCalendarFragment);
                transaction.hide(mFileFragment);
                transaction.show(mContactFragment);
                break;
        }

        // Set optional menu icon.
        if (index == NAVIGATION_TAB_INDEX_CALENDAR) {
            mMenuOptionOptional.setText(R.string.icon_event_note);
        } else {
            mMenuOptionOptional.setText(R.string.icon_search);
        }
        transaction.commitAllowingStateLoss();
        mCurrentShowTabIndex = index;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
