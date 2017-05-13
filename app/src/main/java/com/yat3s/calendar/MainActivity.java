package com.yat3s.calendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private TextView mHeader;
    private int mTotalDy = 0;
    private int mItemDy = 0;
    private int mLastFristPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mHeader = (TextView) findViewById(R.id.header_tv);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        final MusicAdapter musicAdapter = new MusicAdapter(this, generateMockData());
        mRecyclerView.setAdapter(musicAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastPosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
                mHeader.setText("Section" + musicAdapter.getDataSource().get(firstPosition));
                mTotalDy += dy;
                if (firstPosition != mLastFristPosition) {
                    mItemDy = 0;
                    mLastFristPosition = firstPosition;
                } else {
                    mItemDy += dy;
                }
                moveHeader(mItemDy);
                Log.d(TAG, "mTotalDy: " + mTotalDy);
                Log.d(TAG, "dy: " + dy);
                Log.d(TAG, "lastPosition: " + lastPosition);
                Log.d(TAG, "computeScrollVectorForPosition: " + mLinearLayoutManager.computeScrollVectorForPosition
                        (lastPosition - 1).toString());
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

    }

    private void moveHeader(int dy) {
        if (dy < 0) {
            return;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mHeader.getLayoutParams();
        layoutParams.setMargins(0, -dy, 0, 0);
        mHeader.setLayoutParams(layoutParams);
    }

    private List<String> generateMockData() {
        int quantity = 100;
        List<String> mockData = new ArrayList<>();
        for (int idx = 0; idx < quantity; idx++) {
            mockData.add(String.valueOf(idx));
        }
        return mockData;
    }
}
