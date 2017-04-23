package com.github.linolium.yandex_translator.common.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.linolium.yandex_translator.R;
import com.github.linolium.yandex_translator.common.TranslatedTextFilter;
import com.github.linolium.yandex_translator.common.eventbus.Bus;
import com.github.linolium.yandex_translator.common.eventbus.events.dictionary.ShowClearDialogEvent;
import com.github.linolium.yandex_translator.domain.TranslateText;
import com.jakewharton.rxbinding.view.RxView;

import java.util.List;

/**
 * Created by Linolium on 21.04.2017.
 */

public class SavedTextAdapter extends RecyclerView.Adapter<SavedTextAdapter.ItemViewHolder> {

    private List<TranslateText> translateTextList;
    private List<TranslateText> filteredTextList;
    private TranslatedTextFilter filter;
    private Context context;
    private Bus bus;

    public SavedTextAdapter(List<TranslateText> translateTextList, Context context, Bus bus) {
        this.translateTextList = translateTextList;
        this.filteredTextList = translateTextList;
        this.context = context;
        this.bus = bus;
        filter = new TranslatedTextFilter(translateTextList, this);
    }

    @Override
    public SavedTextAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_text_adapter_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SavedTextAdapter.ItemViewHolder holder, int position) {
        final TranslateText translateText = getTranslateText(position);

        holder.enteredText.setText(translateText.getEnteredText());
        holder.translatedText.setText(translateText.getTranslatedText());
        holder.fromToCode.setText(translateText.getFromToCode());

        RxView.longClicks(holder.itemView).subscribe(aVoid -> {
            bus.send(new ShowClearDialogEvent(translateText));
        });
    }

    public void setList(List<TranslateText> list) {
        this.filteredTextList = list;
    }

    public void filterList(String text) {
        filter.filter(text);
    }

    @Override
    public int getItemCount() {
        return filteredTextList.size();
    }

    private TranslateText getTranslateText(int position) {
        return filteredTextList.get(position);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView enteredText;
        TextView translatedText;
        TextView fromToCode;

        public ItemViewHolder(View itemView) {
            super(itemView);
            enteredText = (TextView) itemView.findViewById(R.id.enteredText);
            translatedText = (TextView) itemView.findViewById(R.id.translatedText);
            fromToCode = (TextView) itemView.findViewById(R.id.fromToCode);
        }
    }
}
