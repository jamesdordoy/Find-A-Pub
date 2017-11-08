package com.example.a1dordj54.findapub.helpers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a1dordj54.findapub.presenters.Presenter;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(getLayoutResourceId());
    }

    public abstract Presenter getPresenter();

    protected abstract int getLayoutResourceId();
}
