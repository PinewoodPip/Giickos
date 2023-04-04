package edu.ub.pis.giickos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import edu.ub.pis.giickos.ui.main.MainFragment;
import edu.ub.pis.giickos.ui.section.Section;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Transitions to a section.
    public static void transitionToSection(Activity sourceActivity, Section.TYPE sectionID, Bundle bundle) {
        Intent transition = new Intent(sourceActivity, MainActivity.class);
        transition.putExtras(bundle);
        transition.putExtra(MainFragment.INTENT_EXTRA_SECTION, sectionID.ordinal());
        sourceActivity.startActivity(transition);
    }
}