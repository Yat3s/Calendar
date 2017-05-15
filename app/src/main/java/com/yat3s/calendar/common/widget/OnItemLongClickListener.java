package com.yat3s.calendar.common.widget;

import android.view.View;

/**
 * Created by Yat3s on 11/8/15.
 * Email: yat3s@opentown.cn
 * Copyright (c) 2015 opentown. All rights reserved.
 */

public interface OnItemLongClickListener<TDataModel> {
    void onLongClick(View view, TDataModel tDataModel, int position);
}
