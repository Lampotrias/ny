package com.example.ny.utils;


import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

import com.example.ny.data.NewsItem;

class DiffCallBack extends DiffUtil.Callback{

    private final List<NewsItem> oldItems;
    private final List<NewsItem> newItems;

    public DiffCallBack(List<NewsItem> oldItems, List<NewsItem> newItems) {
        this.newItems = newItems;
        this.oldItems = oldItems;
    }

    @Override
    public int getOldListSize() {
        return oldItems.size();
    }

    @Override
    public int getNewListSize() {
        return newItems.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItems.get(oldItemPosition).getTitle() == newItems.get(newItemPosition).getTitle();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItems.get(oldItemPosition).equals(newItems.get(newItemPosition));
    }
}