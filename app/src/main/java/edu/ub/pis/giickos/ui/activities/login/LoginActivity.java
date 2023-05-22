package edu.ub.pis.giickos.ui.activities.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import edu.ub.pis.giickos.ui.dialogs.Alert;
import edu.ub.pis.giickos.ui.generic.form.FancyFormCard;
import edu.ub.pis.giickos.ui.generic.form.FormCard;
import edu.ub.pis.giickos.ui.login.CheckboxLoginFragment;

public class LoginActivity extends AppCompatActivity {

    public final String PREF_REMEMBERLOGIN_EMAIL = "REMEMBER_LOGIN_EMAIL";
    public final String PREF_REMEMBERLOGIN_PASSWORD = "REMEMBER_LOGIN_PW";

    private Button signInButton, registerButton, guestButton;

    private ViewModel viewModel;

    private void updateFields(SharedPreferences sharedPref) {

        // Set email and password from shared preferences, or default to empty string
        viewModel.setEmail(sharedPref.getString(PREF_REMEMBERLOGIN_EMAIL, ""));
        viewModel.setPassword(sharedPref.getString(PREF_REMEMBERLOGIN_PASSWORD, ""));

        // Toggle "remember me" if any pref was present
        if (!viewModel.getEmail().isEmpty() || !viewModel.getPassword().isEmpty()) {
            viewModel.setRememberLogin(true);
        }
    }

    private void addFields() {
        FancyFormCard emailCard = FancyFormCard.newInstance( R.drawable.user, getString(R.string.prompt_email));
        FancyFormCard passwordCard = FancyFormCard.newInstance(R.drawable.bloqued, getString(R.string.prompt_password));
        CheckboxLoginFragment rememberCheckBox = new CheckboxLoginFragment();

        // Add email and password cards
        getSupportFragmentManager().beginTransaction()
                .add(R.id.login_layout, emailCard)
                .add(R.id.login_layout, passwordCard)
                .add(R.id.login_layout, rememberCheckBox)
                .commitNow();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView recoverPasswordLink = findViewById(R.id.label_recoverpasswordlink);
        SharedPreferences sharedPref = getSharedPreferences("GIICKOS_LOGIN", Context.MODE_PRIVATE);

        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        updateFields(sharedPref);

        addFields();


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
                            SharedPreferences.Editor editor = sharedPref.edit();
                            if (viewModel.getRememberLogin()) {
                                editor.putString(PREF_REMEMBERLOGIN_EMAIL, viewModel.getEmail());
                                editor.putString(PREF_REMEMBERLOGIN_PASSWORD, viewModel.getPassword());
                            }
                            else {
                                // Remove previous saved login if "remember me" was ticked off
                                editor.remove(PREF_REMEMBERLOGIN_EMAIL);
                                editor.remove(PREF_REMEMBERLOGIN_PASSWORD);
                            }
                            editor.apply();

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

        // Listen for clicks on the "recover password" link
        recoverPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle(getString(R.string.auth_msg_recoverpassword_title));

                View viewInflated = LayoutInflater.from(LoginActivity.this).inflate(R.layout.dialog_createproject, (ViewGroup) LoginActivity.this.findViewById(android.R.id.content), false);
                EditText textField = viewInflated.findViewById(R.id.textfield_name);
                textField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                builder.setView(viewInflated);

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email = textField.getText().toString();
                        viewModel.sendRecoveryEmail(email);

                        dialog.dismiss();
                        Alert alert = new Alert(LoginActivity.this, getString(R.string.auth_msg_recoverpassword_success), getString(R.string.auth_msg_recoverpassword_body));
                        alert.setNegativeButton(R.string.generic_label_ok, null);
                        alert.show();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }
}
