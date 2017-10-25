package com.example.a1dordj54.findapub.views.activities;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.design.widget.Snackbar;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a1dordj54.findapub.helpers.BaseActivity;
import com.example.a1dordj54.findapub.helpers.StateManager;
import com.example.a1dordj54.findapub.models.Pub;
import com.example.a1dordj54.findapub.views.fragments.MapFragment;
import com.example.a1dordj54.findapub.presenters.MainActivityPresenter;
import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.presenters.activityInterfaces.MainActivityView;
import com.example.a1dordj54.findapub.views.fragments.MapListFragment;
import com.example.a1dordj54.findapub.views.fragments.SetMapView;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainActivityView, SetMapView {

    @BindView(R.id.home_activity)
    CoordinatorLayout layout;

    //MVP
    private MainActivityPresenter presenter;

    //Fragments
    private MapFragment mapFragment;
    private MapListFragment listFragment;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        //MVP
        this.presenter = new MainActivityPresenter(this);

        //Bind Views
        ButterKnife.bind(this);

        //Set Toolbar
        this.setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));

        this.mapFragment = new MapFragment();

        //Check Screen Orientation to Load Fragments
        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:

                this.getSupportFragmentManager().beginTransaction().add(R.id.mapFragment, mapFragment).commit();

                break;

            case Configuration.ORIENTATION_LANDSCAPE:

                setContentView(R.layout.horizontal_activity_main);

                listFragment = MapListFragment.newInstance(StateManager.getInstance().getPointsofInterests());

                FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();

                transaction.add(R.id.mapFragment, mapFragment);

                transaction.add(R.id.listFragment, listFragment);

                transaction.commit();

                break;
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //do nothing
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

                    Pub pub = bundle.getParcelable(Pub.NEW_PUB);

                    this.presenter.addNewPubToMap(pub);
                }
            }
        }
    }

    @Override
    public void displayToast(String txt) {
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displaySnackbar(String txt) {
        Snackbar snackbar = Snackbar.make(this.layout, txt, Snackbar.LENGTH_SHORT);

        View view = snackbar.getView();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);

        snackbar.show();
    }

    @Override
    public void displayAlert(String title, String message){

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Edit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


    @Override
    public ViewGroup getLayout() {
        return this.layout;
    }

    @Override
    public MapFragment getMapFragment(){
        return this.mapFragment;
    }

    @Override
    public MapListFragment getListFragment(){
        return this.listFragment;
    }

    @Override
    public void setMapView(double lat, double lon) {

        this.displaySnackbar("help me");

        this.mapFragment.getMap().setView(new GeoPoint(lat, lon));

    }
}