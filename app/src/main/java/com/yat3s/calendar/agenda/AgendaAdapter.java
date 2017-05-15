package com.yat3s.calendar.agenda;

import android.content.Context;

import com.yat3s.calendar.Day;
import com.yat3s.calendar.R;
import com.yat3s.calendar.common.widget.BaseAdapter;
import com.yat3s.calendar.common.widget.BaseViewHolder;

/**
 * Created by Yat3s on 15/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class AgendaAdapter extends BaseAdapter<Day> {
    public AgendaAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindDataToItemView(BaseViewHolder holder, Day data, int position) {
        holder.setText(R.id.date_tv, data.getDateSectionString());
    }

    @Override
    protected int getItemViewLayoutId(int position, Day data) {
        return R.layout.item_agenda;
    }
}
