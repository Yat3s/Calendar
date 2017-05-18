package com.yat3s.calendar.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yat3s.calendar.R;
import com.yat3s.calendar.data.source.WeatherDataSource;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Yat3s on 18/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class TemperatureView extends FrameLayout {
    @BindView(R.id.cursor_iv)
    ImageView mCursorIv;

    @BindView(R.id.day_quantum_tv)
    TextView mDayQuantumTv;

    @BindView(R.id.icon_iv)
    ImageView mIconIv;

    @BindView(R.id.temperature_tv)
    TextView mTemperatureTv;

    private String mDayQuantumName;

    public TemperatureView(Context context) {
        this(context, null);
    }

    public TemperatureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TemperatureView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.TemperatureView);
        mDayQuantumName = typeArray.getString(R.styleable.TemperatureView_name);
        typeArray.recycle();
        initialize();
    }

    private void initialize() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_temperature, this, true);
        ButterKnife.bind(this);

        if (!TextUtils.isEmpty(mDayQuantumName)) {
            mDayQuantumTv.setText(mDayQuantumName);
        }
    }

    public void setTemperatureData(WeatherDataSource.TimeQuantum timeQuantum) {
        mTemperatureTv.setText(timeQuantum.getTemperature());
        mIconIv.setImageResource(timeQuantum.getIconId());
    }

    public void setDayQuantumName(CharSequence name) {
        mDayQuantumTv.setText(name);
    }
}
