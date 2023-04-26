package edu.ub.pis.giickos.ui.activities;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.dialogs.Alert;

public abstract class GiickosActivity extends AppCompatActivity {

    // Changes the support action bar into the custom Giickos one.
    public void setCustomSupportActionBar() {
        // Replace default action bar with the custom layout
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar);
        FrameLayout actionBarView = (FrameLayout) actionBar.getCustomView();
        Toolbar toolbar = (Toolbar) actionBarView.getParent();

        // Remove left and right margins
        toolbar.setContentInsetsAbsolute(0, 0);

        // Setup help button to show info about the activity's usage
        ImageButton button = actionBarView.findViewById(R.id.button_info);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alert alert = new Alert(GiickosActivity.this, getName(), getHelpMessage());

                alert.setNegativeButton(R.string.generic_label_close, null);

                alert.show();
            }
        });
    }

    // Returns the user-friendly name of the activity.
    public abstract String getName();

    // Returns a message describing how to use the activity.
    public abstract String getHelpMessage();
}
