package com.example.a1dordj54.findapub.views.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.presenters.PrefActivityPresenter;
import com.example.a1dordj54.findapub.views.activityInterfaces.PrefActivityView;

public class PrefActivity extends PreferenceActivity implements PrefActivityView {

    private PrefActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.pub_toolbar, root, false);
        bar.setTitle("Settings");
        root.addView(bar, 0); // insert at top
        bar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));

        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.presenter = new PrefActivityPresenter(this);
    }
}

