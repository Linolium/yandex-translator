package com.github.linolium.yandex_translator.common.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.linolium.yandex_translator.R;
import com.github.linolium.yandex_translator.common.eventbus.Bus;
import com.github.linolium.yandex_translator.common.eventbus.events.translator.FavouriteEvent;
import com.github.linolium.yandex_translator.domain.TranslateText;
import com.jakewharton.rxbinding.view.RxView;

import java.util.List;

/**
 * Created by linolium on 20.04.17.
 */

public class TranslateTextAdapter extends RecyclerView.Adapter<TranslateTextAdapter.ItemViewHolder> {

    private List<TranslateText> translateTextList;
    private Context context;
    private Bus bus;

    public TranslateTextAdapter(List<TranslateText> postList, Context context, Bus bus) {
        this.translateTextList = postList;
        this.context = context;
        this.bus = bus;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.translate_text_adapter_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final TranslateText translateText = getTranslateText(position);

        //Отображаем данные
        holder.text.setText(translateText.getTranslatedText());

        RxView.clicks(holder.getFavouriteButton()).subscribe(aVoid -> {
            bus.send(new FavouriteEvent(translateText));
            holder.getFavouriteButton().setImageResource(R.drawable.ic_bookmark_black_24dp);
        });
    }

    public TranslateText getItem(int position) {
        return translateTextList.get(position);
    }

    private TranslateText getTranslateText(int position) {
        return translateTextList.get(position);
    }

    @Override
    public int getItemCount() {
        return translateTextList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        ImageButton favouriteButton;
        TextView byYandex;

        public ImageButton getFavouriteButton() {
            return favouriteButton;
        }

        public ItemViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            favouriteButton = (ImageButton) itemView.findViewById(R.id.addToFavourite);
            byYandex = (TextView) itemView.findViewById(R.id.byYandex);
        }
    }
}
