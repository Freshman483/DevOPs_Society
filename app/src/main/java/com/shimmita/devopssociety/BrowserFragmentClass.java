package com.shimmita.devopssociety;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BrowserFragmentClass extends Fragment {
    //global Variables
    final int INTERNET_REQUEST_CODE = 114;
    WebView webView;
/*
    ProgressBar progressBar;
*/
    //

    //empty constructor required

    public BrowserFragmentClass() {
    }


    //

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //
        View view = inflater.inflate(R.layout.fragment_browser, container, false);
        webView = (WebView) view.findViewById(R.id.web_view_screen);
/*
        progressBar = view.findViewById(R.id.progressBar);
*/
        //code here using Viewer mode
        checkPermissionInternetGrantedFirst();
        //

        return view;
    }

    private void checkPermissionInternetGrantedFirst() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionsFunction();
        } else {
            webFunctionsContinuation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == INTERNET_REQUEST_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                webFunctionsContinuation();
            }
        } else {
            Toast.makeText(getActivity(), "Error Occurred While Granting Permissions!", Toast.LENGTH_SHORT).show();
        }
    }


    private void requestPermissionsFunction() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.INTERNET)) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("INTERNET PERMISSION")
                    .setMessage("Without Permissions Of Using Internet,the App Will Not gain Access To The Internet!")
                    .setIcon(R.drawable.ic_baseline_hotspot_internet)
                    .setCancelable(false)
                    .setPositiveButton("Ok,Allow", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE}, INTERNET_REQUEST_CODE);
                            dialogInterface.dismiss();
                        }
                    }).create().show();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("STORAGE ACCESS")
                    .setMessage("Without Permissions Of  Writing or Caching Data To Storage For Faster Data retrieval processes,Hampers The Operations Of The Browser !.")
                    .setIcon(R.drawable.ic_baseline_storage_24)
                    .setCancelable(false)
                    .setPositiveButton("Ok,Allow", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE}, INTERNET_REQUEST_CODE);
                            dialogInterface.dismiss();
                        }
                    }).create().show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE}, INTERNET_REQUEST_CODE);

        }


    }

    private void webFunctionsContinuation() {
        checkInternetConnectivity();
    }

    private void checkInternetConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            alertUserToConnectInternet();
        } else {
            userStartWebRequests();
        }
    }

    private void userStartWebRequests() {
        String url = "http://www.google.com";
        webView.getSettings().setJavaScriptEnabled(true);
        WebViewClient webViewClient = new WebViewClient();
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getFavicon();

        //progressAdded


        //


        //supporting back button pressed
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (webView != null) {
                            if (webView.canGoBack()) {
                                webView.goBack();
                            } else {
                                getActivity().onBackPressed();
                            }
                        }
                    }
                }

                return true;
            }
        });
        //


    }


    private void alertUserToConnectInternet() {

        ShowInternetBottomDialog showInternetBottomDialog = new ShowInternetBottomDialog();
        showInternetBottomDialog.show(getActivity().getSupportFragmentManager(), "no internet modal sheet");

       /* new MaterialAlertDialogBuilder(getActivity())
                .setTitle("No internet")
                .setMessage("Detected No Internet Connection Please Turn On The Internet," +
                        "If This Is Not the Case Then Please Purchase Internet Bundles To Access Awesome Features " +
                        "From HireWriterTech.")
                .setCancelable(false)
                .setPositiveButton("Open Internet Settings", (dialogInterface, i) -> {
                    startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                    dialogInterface.dismiss();
                }).setNegativeButton("Buy Data Bundles", (dialogInterface, i) -> {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:*544#")));
                    dialogInterface.dismiss();
                }).create()
                .show();*/
    }

    public static class ShowInternetBottomDialog extends BottomSheetDialogFragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            AppCompatButton buttonOpenLikeBoss;

            View view = inflater.inflate(R.layout.please_connect_internet_bottom_sheet, container, false);
            //code if any
            buttonOpenLikeBoss = (AppCompatButton) view.findViewById(R.id.open_like_boss);
            buttonOpenLikeBoss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                }
            });
            //
            return view;
        }
    }


}
