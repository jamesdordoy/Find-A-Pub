package com.example.a1dordj54.findapub.presenters;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.helpers.StateManager;
import com.example.a1dordj54.findapub.models.PointofInterest;
import com.example.a1dordj54.findapub.views.activities.MainActivity;
import com.example.a1dordj54.findapub.views.activities.MarkerListActivity;
import com.example.a1dordj54.findapub.views.activities.PrefActivity;
import com.example.a1dordj54.findapub.views.activityInterfaces.MainActivityView;
import com.example.a1dordj54.findapub.webservices.GetPOIFromWeb;

public class MainActivityPresenter{

    //MVP
    private MainActivityView view;

    public MainActivityPresenter(MainActivityView view){
        this.view = view;
    }

    public Boolean setUpMenu(Menu menu){

        MainActivity context = (MainActivity) this.view;

        context.getMenuInflater().inflate(R.menu.main_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(context.getComponentName()));

        return true;
    }

    public Boolean onMenuItemSelected(MenuItem item){

        Context context = (Context) this.view;

        if (item.getItemId() == R.id.list_poi) {

            Intent intent = new Intent(context, MarkerListActivity.class);
            context.startActivity(intent);
            return true;

        } else if (item.getItemId() == R.id.settings) {

            Intent intent = new Intent(context, PrefActivity.class);
            context.startActivity(intent);
            return true;

        } else if (item.getItemId() == R.id.cloud) {

            view.getMapFragment().getMarkersFromWeb();
            return true;
        }
        return false;
    }

    public void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            this.view.displaySnackbar(query);
        }
    }

    public void addNewPubToMap(PointofInterest poi){

        //Add point of interest to StateManager For Config Changes
        StateManager.getInstance().addPointOfInterest(poi);

        //Add Marker To View
        view.getMapFragment().getMap().addMarker(poi, true);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences((Context) this.view);

        boolean saveToDB = prefs.getBoolean("save_to_db", true);

        if(saveToDB){
            //dataSource.CreateItem(pub);

        }
    }
}
