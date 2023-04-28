package edu.ub.pis.giickos.ui.activities.main;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.activities.GiickosActivity;
import edu.ub.pis.giickos.ui.activities.login.LoginActivity;

public class MainActivity extends GiickosActivity {

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setCustomSupportActionBar();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
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