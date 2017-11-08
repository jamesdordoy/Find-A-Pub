package com.example.a1dordj54.findapub.views.activities;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a1dordj54.findapub.OpenStreetMaps.Map;
import com.example.a1dordj54.findapub.OpenStreetMaps.MapTouchListener;
import com.example.a1dordj54.findapub.OpenStreetMaps.OpenStreetMap;
import com.example.a1dordj54.findapub.OpenStreetMaps.interfaces.Mappable;
import com.example.a1dordj54.findapub.OpenStreetMaps.interfaces.OnMapTouched;
import com.example.a1dordj54.findapub.helpers.BaseActivity;
import com.example.a1dordj54.findapub.helpers.StateManager;
import com.example.a1dordj54.findapub.models.Pub;
import com.example.a1dordj54.findapub.presenters.Presenter;
import com.example.a1dordj54.findapub.views.dialogs.PubDialog;
import com.example.a1dordj54.findapub.views.fragments.MapFragment;
import com.example.a1dordj54.findapub.presenters.MainActivityPresenter;
import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.views.activityInterfaces.MainActivityView;
import com.example.a1dordj54.findapub.views.fragments.MapListFragment;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainActivityView, OnMapTouched,
                                            MapFragment.OnAttachedListener, OpenStreetMap.OnMapSetUp,
                                            PubDialog.PubDialogListener {

    public static final String INSTANCE_STATE_TAG = "pub_list";

    @BindView(R.id.home_activity)
    CoordinatorLayout layout;

    @BindView(R.id.my_toolbar)
    Toolbar toolbar;

    //MVP
    private MainActivityPresenter presenter;

    //Fragments
    private MapFragment mapFragment;
    private MapListFragment listFragment;

    private Mappable map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Bind Views
        ButterKnife.bind(this);

        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();

        MapFragment mapFragment;
        MapListFragment listFragment;

        if (savedInstanceState == null) {
            mapFragment = new MapFragment();
            listFragment = MapListFragment.newInstance(StateManager.getInstance().getPointsofInterests());
        } else {
            mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag("map");
            listFragment = (MapListFragment) getSupportFragmentManager().findFragmentByTag("list");
        }

        this.mapFragment = mapFragment;
        this.listFragment = listFragment;

        //Check Screen Orientation to Load Fragments
        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:

                transaction.add(R.id.map_fragment, this.mapFragment, "map");

                transaction.add(R.id.list_fragment, this.listFragment, "list");

                transaction.commit();
                break;

            case Configuration.ORIENTATION_LANDSCAPE:

                setContentView(R.layout.horizontal_activity_main);

                this.toolbar = (Toolbar) findViewById(R.id.my_toolbar);
                this.layout = (CoordinatorLayout) findViewById(R.id.home_activity);

                transaction.replace(R.id.map_fragment, this.mapFragment, "map");
                transaction.replace(R.id.list_fragment, this.listFragment, "list");

                transaction.commit();

                break;
        }

        //Set Toolbar
        this.setSupportActionBar(this.toolbar);

        //MVP
        this.presenter = new MainActivityPresenter(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void setUpFragments(){


        if(mapFragment == null){

            this.mapFragment = new MapFragment();
        }

        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();

        //Check Screen Orientation to Load Fragments
        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:

                transaction.add(R.id.map_fragment, this.mapFragment).commit();

                break;

            case Configuration.ORIENTATION_LANDSCAPE:

                setContentView(R.layout.horizontal_activity_main);

                this.toolbar = (Toolbar) findViewById(R.id.my_toolbar);
                this.layout = (CoordinatorLayout) findViewById(R.id.home_activity);

                transaction.add(R.id.map_fragment, this.mapFragment);

                transaction.add(R.id.list_fragment, this.listFragment);

                transaction.commit();

                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.presenter.closeConnection();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.presenter.reopenConnection();
    }

    @Override
    public Presenter getPresenter() {
        return this.presenter;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        this.setIntent(intent);
        this.presenter.handleIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { return this.presenter.setUpMenu(menu); }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { return this.presenter.onMenuItemSelected(item); }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode==0){

            if (resultCode==RESULT_OK){

                if(intent.getExtras() != null){

                    Bundle bundle = intent.getExtras();

                    Pub pub = bundle.getParcelable(Pub.NEW_PUB);
                    Boolean addToMap = bundle.getBoolean("lookup");
                    Boolean edit = bundle.getBoolean("edit", false);

                    if(addToMap && pub != null){

                        OverlayItem item = this.getMapFragment().getMap().removeMarker(pub);

                        if(item != null){
                            Drawable newMarker = this.getResources().getDrawable(R.drawable.ic_active_marker);
                            pub.setMarker(newMarker);

                            double lat = pub.getLat();
                            double lon = pub.getLon();

                            pub.setLon(lat);
                            pub.setLat(lon);

                            this.getMapFragment().getMap().addMarker(pub);

                            this.getMapFragment().getMap().setView(pub.getPoint());
                            this.getMapFragment().getMap().setZoom(14);

                            this.displaySnackbar("setting view");
                        }else{
                            this.displaySnackbar("can not delete item");
                        }

                    }else if(edit && pub != null){

                        this.presenter.editPubInDatabase(pub);
                        this.displaySnackbar(pub.getName() + "Has Been Updated");
                    }else{
                        this.presenter.addNewPubToMap(pub);
                    }
                }
            }
        }
    }

    @Override
    public void displayToast(String txt) {
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displaySnackbar(String txt) { Snackbar.make(this.layout, txt, Snackbar.LENGTH_SHORT).show(); }

    @Override
    public void displayPubAlert(Pub pub){

        PubDialog.newInstance(pub).show(getSupportFragmentManager(), "PubDialog");
    }


    @Override
    public ViewGroup getLayout() {
        return this.layout;
    }

    @Override
    public MapFragment getMapFragment(){
        return mapFragment;
    }


    @Override
    public Mappable getMap(){
        return this.map;
    }

    @Override
    public void setMapPositionAndZoom(GeoPoint point, int zoom) {

        if (mapFragment != null) {
            mapFragment.getMap().setZoom(zoom);
            mapFragment.getMap().setView(point);
        }else{
            this.displayToast("null frag");
        }
    }

    @Override
    public MapListFragment getListFragment(){
        return this.listFragment;
    }

    @Override
    public void mapTouched(GeoPoint geoPoint) {

        if(geoPoint != null){

            Intent intent = new Intent(this, AddPointOfInterestActivity.class);

            Bundle bundle = new Bundle();

            bundle.putDouble("LAT", geoPoint.getLatitude());
            bundle.putDouble("LON", geoPoint.getLongitude());

            intent.putExtras(bundle);

            startActivityForResult(intent, 0);
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void complete() {

        ArrayList<Pub> pubs = this.presenter.getPubsFromDatabase();

        for(Pub pub : pubs){
            if(!StateManager.getInstance().checkPointOfInterestExists(pub)){
                StateManager.getInstance().addPointOfInterest(pub);
            }
        }

        if(this.getMapFragment() != null){

            this.mapFragment.getMap().setUpOverlays(pubs, new MapTouchListener(this));
        }else{
            displayToast("frag null");
        }
    }


    //Dialog Edit Click
    @Override
    public void onDialogPositiveClick(PubDialog dialog) {
        Intent intent = new Intent(this, EditPointOfInterestActivity.class);

        Bundle bundle = new Bundle();

        bundle.putParcelable(Pub.NEW_PUB, dialog.getPub());

        intent.putExtras(bundle);

        startActivityForResult(intent, 0);
    }

    //Dialog Remove Click
    @Override
    public void onDialogNegativeClick(PubDialog dialog) {
        this.presenter.removePubFromDatabase(dialog.getPub());
    }

    //Dialog Review Click
    @Override
    public void onDialogNeutralClick(PubDialog dialog) {


    }
}