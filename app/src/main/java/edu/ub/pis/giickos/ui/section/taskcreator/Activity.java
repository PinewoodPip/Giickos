package edu.ub.pis.giickos.ui.section.taskcreator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.GiickosActivity;

public class Activity extends GiickosActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskcreator);

        setCustomSupportActionBar();
    }

    @Override
    public String getName() {
        return getString(R.string.taskcreator_name);
    }

    @Override
    public String getHelpMessage() {
        return getString(R.string.taskcreator_help);
    }
}