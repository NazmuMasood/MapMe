package com.example.mapme;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class InputLocationDialogFragment extends DialogFragment {
    Context context; Integer clicked_id;

    public InputLocationDialogFragment(Context context, Integer clicked_id) {
        this.context = context;
        this.clicked_id = clicked_id;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Employee's location not available. Do you know his address?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        //Toast.makeText(context,"You clicked yes",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, preSaveLocationPermissionActivity.class);
                        intent.putExtra("id",clicked_id);
                        startActivity(intent);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        Toast.makeText(context,"You clicked no",Toast.LENGTH_LONG).show();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
