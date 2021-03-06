package com.rainmachine.presentation.screens.pushnotifications;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rainmachine.R;
import com.rainmachine.presentation.widgets.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class PushNotificationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_SECTION_NAME = 0;
    private static final int TYPE_SECTION_LIST = 1;

    private final Context context;
    private List<SectionViewModel> items;
    private final PushNotificationsContract.Presenter presenter;
    private final LayoutInflater inflater;

    PushNotificationsAdapter(Context context, List<SectionViewModel> items,
                             PushNotificationsContract.Presenter presenter) {
        this.context = context;
        this.items = items;
        this.presenter = presenter;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_SECTION_NAME) {
            View convertView = inflater.inflate(R.layout.item_section_name,
                    parent, false);
            return new ViewHolderSectionName(convertView);
        } else {
            View convertView = inflater.inflate(R.layout.item_section_list,
                    parent, false);
            return new ViewHolderSectionList(convertView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        SectionViewModel sectionViewModel = getItem(position);
        if (viewType == TYPE_SECTION_NAME) {
            ViewHolderSectionName viewHolder = (ViewHolderSectionName) holder;
            viewHolder.sectionName.setText(null);
        } else if (viewType == TYPE_SECTION_LIST) {
            ViewHolderSectionList viewHolder = (ViewHolderSectionList) holder;
            viewHolder.setAdapter(new PushNotificationsOneSectionAdapter(context,
                    sectionViewModel.pushNotifications, presenter));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return TYPE_SECTION_NAME;
        } else {
            return TYPE_SECTION_LIST;
        }
    }

    @Override
    public int getItemCount() {
        return 2 * items.size();
    }

    public SectionViewModel getItem(int position) {
        return items.get(position / 2);
    }

    public void setItems(List<SectionViewModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    class ViewHolderSectionName extends RecyclerView.ViewHolder {
        @BindView(R.id.section_name)
        TextView sectionName;

        ViewHolderSectionName(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolderSectionList extends RecyclerView.ViewHolder {
        @BindView(R.id.recycler_section_list)
        RecyclerView recyclerSectionList;

        private PushNotificationsOneSectionAdapter sectionAdapter;

        ViewHolderSectionList(View view) {
            super(view);
            ButterKnife.bind(this, view);
            recyclerSectionList.addItemDecoration(new DividerItemDecoration(ContextCompat
                    .getDrawable(context, android.R.drawable.divider_horizontal_textfield)));
            recyclerSectionList.setLayoutManager(new LinearLayoutManager(context,
                    LinearLayoutManager.VERTICAL, false));
            recyclerSectionList.setBackgroundColor(ContextCompat.getColor(context, android.R
                    .color.white));
        }

        void setAdapter(PushNotificationsOneSectionAdapter adapter) {
            sectionAdapter = adapter;
            recyclerSectionList.setAdapter(sectionAdapter);
        }
    }
}
