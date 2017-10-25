package com.example.a1dordj54.findapub.OpenStreetMaps;

import android.content.Context;
import android.support.design.widget.Snackbar;

import com.example.a1dordj54.findapub.models.PointofInterest;
import com.example.a1dordj54.findapub.models.Pub;
import com.example.a1dordj54.findapub.presenters.activityInterfaces.MainActivityView;

import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.List;

public class MapMarkerOverlay extends ItemizedOverlayWithFocus {

    private OnItemGestureListener listener;

    public MapMarkerOverlay(final Context context, final List aList, final OnItemGestureListener listener) {

        super(context, aList, listener);

        new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {

            @Override
            public boolean onItemSingleTapUp(final int index, final OverlayItem item) {

                ((MainActivityView) context).displayAlert(((Pub) item).getName(),
                        ((Pub) item).getDescription() + "\nRating: " + ((Pub) item).getRating());

                return true;
            }
            @Override
            public boolean onItemLongPress(final int index, final OverlayItem item) {


                return false;
            }
        };

        this.setFocusItemsOnTap(true);
    }
}
