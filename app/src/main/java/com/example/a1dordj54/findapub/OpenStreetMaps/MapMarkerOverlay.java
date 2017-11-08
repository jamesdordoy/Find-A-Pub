package com.example.a1dordj54.findapub.OpenStreetMaps;

import android.content.Context;

import com.example.a1dordj54.findapub.models.Pub;
import com.example.a1dordj54.findapub.views.activityInterfaces.MainActivityView;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.List;

public class MapMarkerOverlay extends ItemizedIconOverlay {

    private Context context;

    public MapMarkerOverlay(final Context context, final List aList) {

        super(context, aList, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {

            @Override
            public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                ((MainActivityView) context).displayPubAlert(((Pub) item));

                return true;
            }
            @Override
            public boolean onItemLongPress(final int index, final OverlayItem item) {
                ((MainActivityView) context).displayPubAlert(((Pub) item));

                return false;
            }
        });

        this.context = context;

    }

    @Override
    public OverlayItem removeItem(int position) {
        return super.removeItem(position);
    }

    @Override
    protected boolean onSingleTapUpHelper(final int index, final OverlayItem item, final MapView mapView) {

        ((MainActivityView) context).displayPubAlert(((Pub) item));

        return true;
    }

    @Override
    public void removeAllItems() {
        super.removeAllItems();
    }
}
