package com.example.a1dordj54.findapub.presenters;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.helpers.StateManager;
import com.example.a1dordj54.findapub.models.Pub;
import com.example.a1dordj54.findapub.views.activities.MainActivity;
import com.example.a1dordj54.findapub.views.activities.MarkerListActivity;
import com.example.a1dordj54.findapub.views.activities.PrefActivity;
import com.example.a1dordj54.findapub.presenters.activityInterfaces.MainActivityView;
import com.example.a1dordj54.findapub.webservices.async.AsyncAddPubToWebService;
import com.example.a1dordj54.findapub.webservices.PointOfInterestsClientActions;
import com.example.a1dordj54.findapub.webservices.PointofInterestURLS;
import com.example.a1dordj54.findapub.webservices.PointsofInterestClient;

import java.io.UnsupportedEncodingException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter{

    //MVP
    private MainActivityView view;

    //Construct
    public MainActivityPresenter(MainActivityView view){
        this.view = view;
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
            context.startActivity(intent);
            return true;

        } else if (item.getItemId() == R.id.settings) {

            Intent intent = new Intent(context, PrefActivity.class);
            context.startActivity(intent);
            return true;

        } else if (item.getItemId() == R.id.cloud) {

            PointOfInterestsClientActions client = PointsofInterestClient.getInstance().getClient();

            Call<String> call = client.getPubs();

            call.enqueue(new Callback<String>() {

                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    String json = response.body();

                    if (!(json != null && json.isEmpty())) {

                        view.getMapFragment().getMap().addMarkers(Pub.fromJSONToArray(json, view));

                        view.getMapFragment().displaySnackbar("Add Pubs From Web");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    view.getMapFragment().displaySnackbar("Web Service Unavailable");
                }
            });

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

    public void addNewPubToMap(final Pub pub){

        //Add point of interest to StateManager For Config Changes
        StateManager.getInstance().addPointOfInterest(pub);

        //Add Marker To View
        view.getMapFragment().getMap().addMarker(pub);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences((Context) this.view);

        boolean saveToDB = prefs.getBoolean("save_to_db", false);

        if(saveToDB){
            //dataSource.CreateItem(pub);
        }

        boolean addToWeb = prefs.getBoolean("add_to_web_server", false);

        if(addToWeb){

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

                     pub.setId("");

                     view.displaySnackbar("Pub added to Internet");
                 }

                 @Override
                 public void onFailure(Call<ResponseBody> call, Throwable t) {

                     view.displaySnackbar("went wrong");
                 }
            });
        }

        this.view.displaySnackbar("Pub Added");
    }
}
