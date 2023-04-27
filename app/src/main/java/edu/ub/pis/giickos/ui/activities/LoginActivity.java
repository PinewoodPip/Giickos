package edu.ub.pis.giickos.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.main.MainViewModel;

public class LoginActivity extends AppCompatActivity {

    Button sign_in, register, guest;

    LoginActivity loginActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sign_in = (Button)findViewById(R.id.sign_in_login);
        register = (Button)findViewById(R.id.register_login);
        guest = (Button)findViewById(R.id.guest_login);
        loginActivity = this;
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity, RegisterActivity.class);
                startActivity(intent);
            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.transitionToSection(loginActivity, MainViewModel.TYPE.CALENDAR, null);
            }
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.transitionToSection(loginActivity, MainViewModel.TYPE.CALENDAR, null);
            }
        });

    }




}
