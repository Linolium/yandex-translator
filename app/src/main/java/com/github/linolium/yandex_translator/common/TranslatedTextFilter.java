package com.github.linolium.yandex_translator.common;

import android.widget.Filter;

import com.github.linolium.yandex_translator.common.adapters.SavedTextAdapter;
import com.github.linolium.yandex_translator.domain.TranslateText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linolium on 21.04.2017.
 */

public class TranslatedTextFilter extends Filter {

    private List<TranslateText> textsList;
    private List<TranslateText> filteredTextsList;
    private SavedTextAdapter adapter;

    public TranslatedTextFilter(List<TranslateText> textsList, SavedTextAdapter adapter) {
        this.textsList = textsList;
        this.filteredTextsList = new ArrayList<>();
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        filteredTextsList.clear();
        final FilterResults results = new FilterResults();
//        textsList.forEach(item -> {
//            if (item.getEnteredText().toLowerCase().trim().contains("pattern")) {
//                filteredTextsList.add(item);
//            }
//        });
        for (TranslateText item : textsList) {
            if (item.getEnteredText().toLowerCase().trim().contains("pattern")) {
                filteredTextsList.add(item);
            }
        }
        results.values = filteredTextsList;
        results.count = filteredTextsList.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapter.setList(filteredTextsList);
        adapter.notifyDataSetChanged();
    }
}
