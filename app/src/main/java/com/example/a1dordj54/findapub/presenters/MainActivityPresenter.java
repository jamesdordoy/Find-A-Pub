package com.example.a1dordj54.findapub.presenters;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.a1dordj54.findapub.OpenStreetMaps.MapTouchListener;
import com.example.a1dordj54.findapub.OpenStreetMaps.interfaces.OnMapTouched;
import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.database.DBHelper;
import com.example.a1dordj54.findapub.database.DataSource;
import com.example.a1dordj54.findapub.helpers.StateManager;
import com.example.a1dordj54.findapub.models.Pub;
import com.example.a1dordj54.findapub.views.activities.MainActivity;
import com.example.a1dordj54.findapub.views.activities.MarkerListActivity;
import com.example.a1dordj54.findapub.views.activities.PrefActivity;
import com.example.a1dordj54.findapub.views.activityInterfaces.MainActivityView;
import com.example.a1dordj54.findapub.views.fragments.MapListFragment;
import com.example.a1dordj54.findapub.webservices.PointOfInterestsClientActions;
import com.example.a1dordj54.findapub.webservices.PointsofInterestClient;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter implements Presenter, MapListFragment.SetMapView {

    //MVP
    private MainActivityView view;

    private DataSource dataSource;

    private static Boolean webDataCollected = false;

    //Construct
    public MainActivityPresenter(MainActivityView view){
        this.view = view;

        dataSource = new DataSource((Context) this.view);
        dataSource.open();
    }

    public void closeConnection(){
        if(this.dataSource.isOpen()){
            this.dataSource.close();
        }
    }

    public void reopenConnection(){
        if(!this.dataSource.isOpen()){
            this.dataSource.open();
        }
    }

    public ArrayList<Pub> getPubsFromDatabase(){

        ArrayList<Pub> pubs = null;

        if(this.dataSource.isOpen()){
            pubs = dataSource.getAllPubs();
        }else{
            this.view.displaySnackbar(DBHelper.CONNECTION_CLOSED);
        }

        if(pubs != null && pubs.size() > 0) {

            return pubs;
        }

        return null;
    }

    public void editPubInDatabase(Pub pub){

        OverlayItem i = this.view.getMapFragment().getMap().removeMarker(pub);

        if(i != null){
            this.dataSource.updatePub(pub);
            this.view.getMapFragment().getMap().addMarker(pub);
        }else{
            this.view.displaySnackbar("Marker Has Not Been Stored, It will be deleted by the " +
                                            "system in the future unless stored.");
        }
    }

    public void removePubFromDatabase(Pub pub){

        StateManager.getInstance().removePointOfInterest(pub);

        this.dataSource.removePub(pub.getId());
        pub.reverseLatLon();
        this.view.getMapFragment().getMap().removeMarker(pub);
        this.view.displaySnackbar(pub.getName() + " Has Been Deleted." );
    }


    @Override
    public void locationSelectedCallback(int pos) {

        Pub pub = this.view.getListFragment().getListItemByPosition(pos);

        if(pub != null){

            this.view.setMapPositionAndZoom(pub.getPoint(), 5);
            //this.view.displayPubAlert(pub);
        }else{
            this.view.displaySnackbar("pub is null");
        }
    }

    //Set Up Main Activity Menu
    public Boolean setUpMenu(Menu menu){

        MainActivity context = (MainActivity) this.view;

        context.getMenuInflater().inflate(R.menu.main_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(context.getComponentName()));

        return true;
    }

    //Handle Menu Item Selected
    public Boolean onMenuItemSelected(MenuItem item){

        Context context = (Context) this.view;

        if (item.getItemId() == R.id.list_poi) {

            Intent intent = new Intent(context, MarkerListActivity.class);

            ((Activity) this.view).startActivityForResult(intent, 0);
            return true;

        } else if (item.getItemId() == R.id.settings) {

            Intent intent = new Intent(context, PrefActivity.class);
            context.startActivity(intent);
            return true;

        } else if (item.getItemId() == R.id.cloud) {

            view.displayToast("Collecting Data");

            if(!webDataCollected){

                PointOfInterestsClientActions client = PointsofInterestClient.getInstance().getClient();

                Call<String> call = client.getPubs();

                call.enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        String json = response.body();

                        if (!(json != null && json.isEmpty())) {

                            ArrayList<Pub> pubs = Pub.fromJSONToArray(json, view);

                            if(pubs != null){
                                for(Pub pub : pubs){
                                    if(!StateManager.getInstance().checkPointOfInterestExists(pub)){
                                        StateManager.getInstance().addPointOfInterest(pub);
                                    }
                                }
                            }

                            view.getMapFragment().getMap().setUpOverlays(StateManager.getInstance().getPointsofInterests(),
                                                            new MapTouchListener((OnMapTouched) view));

                            view.displaySnackbar("Adding Pubs From Web Service.");
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        view.displaySnackbar("Web Service Unavailable.");
                    }
                });

                webDataCollected = true;
            }else{
                view.displaySnackbar("Pubs Have Already Been Downloaded.");
            }

            return true;
        }
        return false;
    }

    //Handle Intents
    public void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            this.view.displaySnackbar(query);
        }
    }


    public void addNewPubToMap(Pub pub){

        //Add point of interest to StateManager For Config Changes
        StateManager.getInstance().addPointOfInterest(pub);

        //Add Marker To View
        view.getMapFragment().getMap().addMarker(pub);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences((Context) this.view);

        boolean saveToDB = prefs.getBoolean("store_locally", false);

        if(saveToDB){

            view.displaySnackbar("Pub added to Database");
            dataSource.CreateItem(pub);
        }

        boolean addToWeb = prefs.getBoolean("add_to_web_server", false);

        if(addToWeb){
            addMarkerToWebService(pub);

            view.displaySnackbar("Pub added to Internet");
        }
    }

    private void addMarkerToWebService(final Pub pub){

        PointOfInterestsClientActions client = PointsofInterestClient.getInstance().getClient();

        String authHeader = PointsofInterestClient.getInstance().getBasicAuthHeader();

        Call<ResponseBody> call = client.createPub(
                pub.getName(),
                pub.getType(),
                pub.getCountry(),
                pub.getRegion(),
                pub.getLat(),
                pub.getLon(),
                pub.getDescription(),
                1,
                pub.getId(), authHeader);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                ResponseBody body = response.body();

                if (body != null) {
                    Log.e("Help", body.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                view.displaySnackbar("went wrong");
            }
        });
    }
}
