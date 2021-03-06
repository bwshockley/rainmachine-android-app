package com.rainmachine.presentation.screens.windsensitivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.rainmachine.R;
import com.rainmachine.presentation.activities.SprinklerActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WindSensitivityActivity extends SprinklerActivity {

    @Inject
    WindSensitivityPresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private View customActionBarView;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, WindSensitivityActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!buildGraphAndInject()) {
            return;
        }
        setContentView(R.layout.activity_wind_sensitivity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(device.name);
        getSupportActionBar().setSubtitle(R.string.all_wind_sensitivity);

        buildCustomActionBarView();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    public Object getModule() {
        return new WindSensitivityModule(this);
    }

    private void buildCustomActionBarView() {
        customActionBarView = View.inflate(getSupportActionBar().getThemedContext(), R.layout
                .include_actionbar_defaults_save, null);
        customActionBarView.findViewById(R.id.actionbar_save).setOnClickListener(
                v -> presenter.onClickedSave());
        customActionBarView.findViewById(R.id.actionbar_defaults).setOnClickListener(
                v -> presenter.onClickedDefaults());

        // Show the custom action bar view and hide the normal Home icon and title.
        getSupportActionBar().setDisplayOptions(
                ActionBar.DISPLAY_SHOW_CUSTOM,
                ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
                        | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setCustomView(customActionBarView,
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void showActionBarView() {
        customActionBarView.setVisibility(View.VISIBLE);
    }

    public void hideActionBarView() {
        customActionBarView.setVisibility(View.INVISIBLE);
    }
}
