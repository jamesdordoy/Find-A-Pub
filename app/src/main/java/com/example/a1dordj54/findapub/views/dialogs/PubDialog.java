package com.example.a1dordj54.findapub.views.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.models.Pub;


public class PubDialog extends DialogFragment {

    private Pub pub;

    public interface PubDialogListener {
        void onDialogPositiveClick(PubDialog dialog);
        void onDialogNegativeClick(PubDialog dialog);
        void onDialogNeutralClick(PubDialog dialog);
    }

    // Use this instance of the interface to deliver action events
    private PubDialogListener mListener;

    public static PubDialog newInstance(Pub pub){

        PubDialog dialog = new PubDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Pub.NEW_PUB, pub);
        dialog.setArguments(bundle);

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        View content = inflater.inflate(R.layout.pub_pop_up, null);

        builder.setView(content)
                // Add action buttons
                .setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(PubDialog.this);
                    }
                })
                .setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(PubDialog.this);
                    }
                })
                .setNeutralButton(R.string.review, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNeutralClick(PubDialog.this);
                    }
                });

        final AlertDialog dialog = builder.create();

        TextView head = (TextView) content.findViewById(R.id.pop_header);
        TextView desc = (TextView) content.findViewById(R.id.pop_description);
        TextView rating = (TextView) content.findViewById(R.id.pop_rating);

        if(getArguments() != null){
            Pub pub = getArguments().getParcelable(Pub.NEW_PUB);

            this.pub = pub;

            String fullDescription = getString(R.string.pub_description_template, pub.getDescription());
            String fullRating = getString(R.string.pub_rating_template, Double.toString(pub.getRating()));

            head.setText(pub.getName());
            desc.setText(fullDescription);
            rating.setText(fullRating);
        }else{
            Toast.makeText(getActivity(), "No Instance State", Toast.LENGTH_SHORT).show();
        }

        return dialog;
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (PubDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement PubDialogListener");
        }
    }

    public Pub getPub(){
        return this.pub;
    }
}
