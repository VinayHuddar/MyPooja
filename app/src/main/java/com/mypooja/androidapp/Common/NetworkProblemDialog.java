package com.mypooja.androidapp.Common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;

import com.mypooja.androidapp.MainScreen;
import com.mypooja.androidapp.R;

/**
 * Created by Vinay on 24-06-2015.
 */
public class NetworkProblemDialog extends DialogFragment {
    public static NetworkProblemDialog newInstance() { //int dialogType) {
        NetworkProblemDialog  frag = new NetworkProblemDialog ();
        /*Bundle args = new Bundle();
        args.putInt("DIALOG_TYPE", dialogType);
        frag.setArguments(args);*/
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //int dialogType = getArguments().getInt("DIALOG_TYPE");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.network_error_label)
                .setPositiveButton(R.string.alert_dialog_retry,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent intent = getActivity().getIntent();
                                getActivity().finish();
                                startActivity(intent);
                            }
                        }
                )
                .setNegativeButton(R.string.alert_dialog_trylater,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent intent = new Intent(getActivity(), MainScreen.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }
                );

        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.network_problem_dialog_layout, null, false));

        return  builder.create();
    }

    public void doPositiveClick() {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Positive click!");
    }

    public void doNegativeClick() {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Negative click!");
    }
}


