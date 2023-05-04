package edu.ub.pis.giickos.ui.activities.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.activities.register.RegisterActivity;
import edu.ub.pis.giickos.ui.activities.main.MainActivity;
import edu.ub.pis.giickos.ui.activities.main.MainViewModel;
import edu.ub.pis.giickos.ui.generic.form.FormCard;
import edu.ub.pis.giickos.ui.login.CheckboxLoginFragment;

public class LoginActivity extends AppCompatActivity {

    public final String PREF_REMEMBERLOGIN_EMAIL = "REMEMBER_LOGIN_EMAIL";
    public final String PREF_REMEMBERLOGIN_PASSWORD = "REMEMBER_LOGIN_PW";

    private Button signInButton, registerButton, guestButton;

    private ViewModel viewModel;

    @Override
    public void onAttachFragment(Fragment frag) {
        int id = frag.getId();

        // onAttachFragment() can be called before onCreate, which is very stupid
        if (viewModel == null) {
            viewModel = new ViewModelProvider(this).get(ViewModel.class);

            updateFields();
        }

        if (id == R.id.card_email) {
            FormCard emailCard = (FormCard) frag;

            // Initialize email card
            Bundle emailCardArgs = new Bundle();
            emailCardArgs.putString(FormCard.ARG_LABEL, getString(R.string.prompt_email));
            emailCardArgs.putInt(FormCard.ARG_ICON, R.drawable.user);
            emailCardArgs.putInt(FormCard.ARG_BG_COLOR, getResources().getColor(R.color.positive_action));
            emailCard.setArguments(emailCardArgs);

            emailCard.addTextField(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, viewModel.getEmail(), new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    viewModel.setEmail(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {}
            }, getString(R.string.msg_email));
        }
        else if (id == R.id.card_password) {
            FormCard passwordCard = (FormCard) frag;

            // Initialize password card
            Bundle passwordCardArgs = new Bundle();
            passwordCardArgs.putString(FormCard.ARG_LABEL, getString(R.string.prompt_password));
            passwordCardArgs.putInt(FormCard.ARG_ICON, R.drawable.password);
            passwordCardArgs.putInt(FormCard.ARG_BG_COLOR, getResources().getColor(R.color.positive_action));
            passwordCard.setArguments(passwordCardArgs);

            passwordCard.addTextField(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD, viewModel.getPassword(), new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    viewModel.setPassword(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {}
            }, getString(R.string.msg_psw));
        }
    }

    private void updateFields() {
        SharedPreferences sharedPref = getSharedPreferences("GIICKOS_LOGIN", Context.MODE_PRIVATE);

        // Set email and password from shared preferences, or default to empty string
        viewModel.setEmail(sharedPref.getString(PREF_REMEMBERLOGIN_EMAIL, ""));
        viewModel.setPassword(sharedPref.getString(PREF_REMEMBERLOGIN_PASSWORD, ""));

        // Toggle "remember me" if any pref was present
        if (!viewModel.getEmail().isEmpty() || !viewModel.getPassword().isEmpty()) {
            viewModel.setRememberLogin(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sharedPref = getSharedPreferences("GIICKOS_LOGIN", Context.MODE_PRIVATE);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        updateFields();

        signInButton = (Button)findViewById(R.id.sign_in_login);
        registerButton = (Button)findViewById(R.id.register_login);
        guestButton = (Button)findViewById(R.id.guest_login);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.transitionToSection(LoginActivity.this, MainViewModel.SECTION_TYPE.CALENDAR, null);
                finish();
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.canLogIn()) {
                    Task loginTask = viewModel.tryLogIn();

                    loginTask.addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            SharedPreferences sharedPref = getSharedPreferences("GIICKOS_LOGIN", Context.MODE_PRIVATE);

                            // Show toast
                            Toast.makeText(LoginActivity.this, getString(R.string.auth_login_success), Toast.LENGTH_SHORT).show();

                            // If the login was successful and "remember me" was ticked, save to preferences
                            if (viewModel.getRememberLogin()) {
                                SharedPreferences.Editor editor = sharedPref.edit();

                                editor.putString(PREF_REMEMBERLOGIN_EMAIL, viewModel.getEmail());
                                editor.putString(PREF_REMEMBERLOGIN_PASSWORD, viewModel.getPassword());

                                editor.apply();
                            }

                            MainActivity.transitionToSection(LoginActivity.this, MainViewModel.SECTION_TYPE.CALENDAR, null, true);
                        }
                    });
                    loginTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, getString(R.string.auth_login_failure), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
