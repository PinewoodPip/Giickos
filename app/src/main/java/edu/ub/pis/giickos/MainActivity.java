package edu.ub.pis.giickos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import edu.ub.pis.giickos.ui.GiickosActivity;
import edu.ub.pis.giickos.ui.main.MainFragment;
import edu.ub.pis.giickos.ui.main.MainViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends GiickosActivity {

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setCustomSupportActionBar();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // TODO remove once login screen is implemented
        Task loginTask = viewModel.tryLogIn("pip@pinewood.team", "hunter2");
        loginTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(MainActivity.this, getString(R.string.auth_login_success), Toast.LENGTH_SHORT).show();
            }
        });
        loginTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, getString(R.string.auth_login_failure), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Transitions to a section.
    public static void transitionToSection(Activity sourceActivity, MainViewModel.TYPE sectionID, @Nullable Bundle bundle, boolean clearStack) {
        Intent transition = new Intent(sourceActivity, MainActivity.class);
        if (clearStack) {
            transition.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        if (bundle == null) {
            bundle = new Bundle();
        }
        transition.putExtras(bundle);
        transition.putExtra(MainFragment.INTENT_EXTRA_SECTION, sectionID.ordinal());

        sourceActivity.startActivity(transition);
    }

    public static void transitionToSection(Activity sourceActivity, MainViewModel.TYPE sectionID, @Nullable Bundle bundle) {
        transitionToSection(sourceActivity, sectionID, bundle, false);
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