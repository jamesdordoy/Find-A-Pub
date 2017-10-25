package com.example.a1dordj54.findapub.views.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.presenters.PrefActivityPresenter;
import com.example.a1dordj54.findapub.presenters.activityInterfaces.PrefActivityView;

public class PrefActivity extends PreferenceActivity implements PrefActivityView {

    private PrefActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        this.presenter = new PrefActivityPresenter(this);
    }
}

