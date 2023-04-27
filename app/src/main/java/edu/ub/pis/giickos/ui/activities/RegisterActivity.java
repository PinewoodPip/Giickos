package edu.ub.pis.giickos.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.main.MainViewModel;

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
                MainActivity.transitionToSection(regActivity, MainViewModel.TYPE.CALENDAR, null);
            }
        });
    }
}