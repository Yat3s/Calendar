package com.yat3s.calendar;

import android.content.Context;

import com.yat3s.calendar.common.widget.BaseAdapter;
import com.yat3s.calendar.common.widget.BaseViewHolder;

import java.util.List;

/**
 * Created by Yat3s on 12/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class MusicAdapter extends BaseAdapter<String> {

    public MusicAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    protected void bindDataToItemView(BaseViewHolder holder, String data, int position) {
        holder.setText(R.id.title_tv, "Music" + data)
                .setText(R.id.section_tv, "Section" + data);
    }

    @Override
    protected int getItemViewLayoutId(int position, String data) {
        return R.layout.item_music;
    }
}
