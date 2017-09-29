package com.omeerfk.dizitakibi.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.omeerfk.dizitakibi.R;


public class NetworkDialogFragment extends DialogFragment {

    public NetworkDialogFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.no_internet)
                .setMessage(R.string.unable_to_connect_internet)
                .setIcon(R.mipmap.no_internet)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                        getActivity().startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
                    }
                });
        return builder.create();
    }
}
