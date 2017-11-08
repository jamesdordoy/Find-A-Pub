package com.example.a1dordj54.findapub.presenters;

import android.content.Intent;
import android.os.Bundle;

import com.example.a1dordj54.findapub.helpers.BaseActivity;
import com.example.a1dordj54.findapub.models.Pub;
import com.example.a1dordj54.findapub.views.activityInterfaces.MarkerListView;
import com.example.a1dordj54.findapub.views.fragments.MapListFragment;

import static android.app.Activity.RESULT_OK;

public class MarkerListPresenter implements Presenter, MapListFragment.SetMapView {

    private MarkerListView view;

    public MarkerListPresenter(MarkerListView view){
        this.view = view;
    }

    @Override
    public void locationSelectedCallback(int position) {
        Pub pub = this.view.getFragment().getListItemByPosition(position);

        Intent intent = new Intent();

        Bundle bundle = new Bundle();
        bundle.putParcelable(Pub.NEW_PUB, pub);

        bundle.putBoolean("lookup", true);

        intent.putExtras(bundle);
        ((BaseActivity) this.view).setResult(RESULT_OK, intent);
        ((BaseActivity) this.view).finish();
    }
}
