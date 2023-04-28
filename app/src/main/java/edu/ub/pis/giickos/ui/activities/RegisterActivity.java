package edu.ub.pis.giickos.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.activities.main.MainActivity;
import edu.ub.pis.giickos.ui.activities.main.MainViewModel;

public class RegisterActivity extends AppCompatActivity {
    Button register;
    RegisterActivity regActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resigter);
        register = (Button)findViewById(R.id.register_reg);
        regActivity = this;
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.transitionToSection(regActivity, MainViewModel.SECTION_TYPE.CALENDAR, null);
            }
        });
    }
}