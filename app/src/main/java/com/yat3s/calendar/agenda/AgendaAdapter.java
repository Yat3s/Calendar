package com.yat3s.calendar.agenda;

import android.content.Context;
import android.view.View;

import com.yat3s.calendar.data.model.Day;
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
    protected void bindDataToItemView(BaseViewHolder holder, Day day, int position) {
        holder.setText(R.id.date_tv, day.getDateSectionString())
                .setTextColorRes(R.id.date_tv, day.isToday ? R.color.colorPrimary : R.color.textColorGrey)
                .setOnClickListener(R.id.event_layout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
    }

    @Override
    protected int getItemViewLayoutId(int position, Day data) {
        return R.layout.item_agenda;
    }
}
