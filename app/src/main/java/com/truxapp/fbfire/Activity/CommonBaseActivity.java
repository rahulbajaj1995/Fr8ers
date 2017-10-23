package com.truxapp.fbfire.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.truxapp.fbfire.MainActivity;
import com.truxapp.fbfire.R;
import com.truxapp.fbfire.util.AppPreference;

public class CommonBaseActivity extends AppCompatActivity {
    public AppPreference prefs;
    public boolean isErrorResponse;
    private SharedPreferences permissionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
        prefs = new AppPreference(getApplicationContext());
        isErrorResponse = false;
    }
    public <T> void processResponse(T result) {
        isErrorResponse = false;
        if (result == null) {
            isErrorResponse = true;
            return;
        }
    }

    public void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public static Dialog noInternetConnection(final Context context) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please connect to Network");
//        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialogInterface, int i) {
//                // Show location settings when the user acknowledges the alert dialog
//                Intent intent = new Intent("retry");
//                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//            }
//        });
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                context.startActivity(intent);
            }
        });
        Dialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
        return alertDialog;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            @SuppressWarnings("deprecation")
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }



    public void mExitDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CommonBaseActivity.this);

        LayoutInflater inflater = (LayoutInflater)CommonBaseActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custom_alert_dialogue, null);
        dialogBuilder.setView(dialogView);
        TextView popUpmsg = (TextView) dialogView.findViewById(R.id.popUpmsg);
        Button okBtn = (Button) dialogView.findViewById(R.id.okBtn);
        Button cancelBtn = (Button) dialogView.findViewById(R.id.cancelBtn);
        okBtn.setText("Yes");
        cancelBtn.setText("No");

        popUpmsg.setText("Are you sure you want to Exit?");
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.hide();
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    public void mLogoutdialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CommonBaseActivity.this);

        LayoutInflater inflater = (LayoutInflater)CommonBaseActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custom_alert_dialogue, null);
        dialogBuilder.setView(dialogView);
        TextView popUpmsg = (TextView) dialogView.findViewById(R.id.popUpmsg);
        Button okBtn = (Button) dialogView.findViewById(R.id.okBtn);
        Button cancelBtn = (Button) dialogView.findViewById(R.id.cancelBtn);
        okBtn.setText("Yes");
        cancelBtn.setText("No");

        popUpmsg.setText("Are you sure you want to Logout?");
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.hide();
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences(com.truxapp.fbfire.app.Constants.SHARED_PREF, 0);
                final SharedPreferences.Editor editor = pref.edit();
                editor.remove("registration_id");
                editor.clear();
                editor.commit();
                startActivity(new Intent(CommonBaseActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    public void mSnackBarMessage(View view, String toastMessage){
//        TSnackbar snackbar = TSnackbar.make(findViewById(R.layout.snack_bar_layout), toastMessage, TSnackbar.LENGTH_LONG);
        TSnackbar snackbar = TSnackbar.make(view, toastMessage, TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.parseColor("#000000"));
        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
}
