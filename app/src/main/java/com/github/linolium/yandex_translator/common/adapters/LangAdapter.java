package com.github.linolium.yandex_translator.common.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.linolium.yandex_translator.R;
import com.github.linolium.yandex_translator.domain.Lang;

import java.util.List;

/**
 * Created by Linolium on 17.04.2017.
 */

public class LangAdapter extends BaseAdapter {

    private Context context;
    private List<Lang> langList;

    public LangAdapter(Context context, List<Lang> langList) {
        this.context = context;
        this.langList = langList;
    }

    @Override
    public int getCount() {
        return langList.size();
    }

    @Override
    public Lang getItem(int i) {
        return langList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_header_spinner, parent, false);

        TextView textView = (TextView) view.findViewById(R.id.spinnerItem);
        textView.setText(langList.get(position).getValue());

        return view;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_header_spinner, parent, false);

        TextView textView = (TextView) view.findViewById(R.id.spinnerItem);
        textView.setText(langList.get(i).getValue());
        return view;
    }
}
