package com.example.a1dordj54.findapub.views.activities;

import android.support.design.widget.Snackbar;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.a1dordj54.findapub.helpers.BaseActivity;
import com.example.a1dordj54.findapub.models.Pub;
import com.example.a1dordj54.findapub.views.fragments.MapFragment;
import com.example.a1dordj54.findapub.presenters.MainActivityPresenter;
import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.views.activityInterfaces.MainActivityView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainActivityView {

    @BindView(R.id.home_activity)
    CoordinatorLayout layout;

    private MainActivityPresenter presenter;

    private MapFragment mapFragment;

    public static final String NEW_PUB = "pub";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        this.presenter = new MainActivityPresenter(this);

        ButterKnife.bind(this);

        this.setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));

        this.mapFragment = new MapFragment();

        this.getSupportFragmentManager().beginTransaction().add(R.id.mapFragment, mapFragment).commit();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        this.mapFragment.getMap().retainCurrentPointsOfInterest();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        this.setIntent(intent);
        this.presenter.handleIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return this.presenter.setUpMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return this.presenter.onMenuItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode==0){

            if (resultCode==RESULT_OK){

                if(intent.getExtras() != null){

                    Bundle bundle = intent.getExtras();

                    Pub pub = bundle.getParcelable(NEW_PUB);

                    this.presenter.addNewPubToMap(pub);
                }
            }
        }
    }

    @Override
    public void displaySnackbar(String txt) {
        Snackbar.make(this.layout, txt, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public ViewGroup getLayout() {
        return this.layout;
    }

    @Override
    public MapFragment getMapFragment(){
        return this.mapFragment;
    }

}