package com.rainmachine.presentation.screens.hiddendrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rainmachine.R;
import com.rainmachine.presentation.util.adapter.BindableAdapter;

import java.util.List;

import butterknife.ButterKnife;

public class GenericSpinnerAdapter<T> extends BindableAdapter {

    private List<T> items;

    public GenericSpinnerAdapter(Context context, List<T> items) {
        super(context);
        this.items = items;
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container) {
        return inflater.inflate(R.layout.item_generic_spinner, container, false);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public final View newDropDownView(LayoutInflater inflater, int position, ViewGroup container) {
        return inflater.inflate(android.R.layout.simple_spinner_dropdown_item, container, false);
    }

    @Override
    public void bindView(Object item, int position, View view) {
        TextView tv = ButterKnife.findById(view, android.R.id.text1);
        tv.setText(String.valueOf(item));
    }

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
