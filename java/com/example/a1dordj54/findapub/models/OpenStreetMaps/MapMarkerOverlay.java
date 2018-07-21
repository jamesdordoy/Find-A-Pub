package com.example.a1dordj54.findapub.models.OpenStreetMaps;

import android.content.Context;
import android.support.design.widget.Snackbar;

import com.example.a1dordj54.findapub.models.PointofInterest;
import com.example.a1dordj54.findapub.views.activityInterfaces.MainActivityView;

import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.List;

public class MapMarkerOverlay extends ItemizedOverlayWithFocus {

    private Context c;

    public MapMarkerOverlay(final Context context, final List aList) {

        super(context, aList, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {

            @Override
            public boolean onItemSingleTapUp(final int index, final OverlayItem item) {

                Snackbar.make(((MainActivityView) context).getLayout(), ((PointofInterest) item).getName() , Snackbar.LENGTH_SHORT).show();
                return true;
            }
            @Override
            public boolean onItemLongPress(final int index, final OverlayItem item) {

                Snackbar.make(((MainActivityView) context).getLayout(), ((PointofInterest) item).getDescription() , Snackbar.LENGTH_LONG).show();
                return false;
            }
        });

        this.c = context;
        this.setFocusItemsOnTap(true);
    }


    @Override
    public boolean onTap(int index){

        Snackbar.make(((MainActivityView) this.c).getLayout(), ((PointofInterest) super.getItem(index)).getName() , Snackbar.LENGTH_SHORT).show();
        return true;
    }


    public boolean addMarker(OverlayItem poi, Boolean toogleMessage){

        if(super.addItem(poi)){

            if(toogleMessage){
                MainActivityView activity = (MainActivityView) this.c;

                Snackbar.make(activity.getLayout(), "Marker Added", Snackbar.LENGTH_SHORT).show();
            }

            return true;
        }

        return false;
    }
}
