package edu.ub.pis.giickos.ui.activities.main;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.util.Log;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.model.notification.Notification;
import edu.ub.pis.giickos.ui.activities.GiickosActivity;
import edu.ub.pis.giickos.ui.activities.login.LoginActivity;

public class MainActivity extends GiickosActivity {

    private MainViewModel viewModel;
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // feature requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setCustomSupportActionBar();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        //codi notification
        Notification.createNotificationChannel(this);
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            //performAction(...);
            Log.i("MainActivity","Permission already granted");
        }
        /*else if (shouldShowRequestPermissionRationale(...)) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected, and what
            // features are disabled if it's declined. In this UI, include a
            // "cancel" or "no thanks" button that lets the user continue
            // using your app without granting the permission.
            //showInContextUI(...);
        }*/
        else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS);
        }
        //exemple enviar notification
        Notification.sendNotification(this, "notification titile", "notification content",
                LoginActivity.class );
        Notification.sendNotification(this, "notification titile", "notification content",
                LoginActivity.class );
    }


    // Transitions to a section.
    public static void transitionToSection(Activity sourceActivity, MainViewModel.SECTION_TYPE sectionID, @Nullable Bundle bundle, boolean clearStack) {
        Intent transition = new Intent(sourceActivity, MainActivity.class);
        if (clearStack) {
            transition.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        if (bundle == null) {
            bundle = new Bundle();
        }
        transition.putExtras(bundle);
        transition.putExtra(MainFragment.INTENT_EXTRA_SECTION, sectionID.ordinal());

        sourceActivity.startActivity(transition);
    }

    public static void transitionToSection(Activity sourceActivity, MainViewModel.SECTION_TYPE sectionID, @Nullable Bundle bundle) {
        transitionToSection(sourceActivity, sectionID, bundle, false);
    }

    // TODO move this call elsewhere?
    public static void goToLogin(Activity source) {
        Intent intent = new Intent(source, LoginActivity.class);

        source.startActivity(intent);
        source.finish();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public String getName() {
        return getString(viewModel.getCurrentSection().getValue().getNameStringResource());
    }

    @Override
    public String getHelpMessage() {
        return getString(viewModel.getCurrentSection().getValue().getHelpStringResource());
    }
}