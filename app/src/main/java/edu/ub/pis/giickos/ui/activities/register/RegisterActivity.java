package edu.ub.pis.giickos.ui.activities.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.activities.main.MainActivity;
import edu.ub.pis.giickos.ui.activities.main.MainViewModel;
import edu.ub.pis.giickos.ui.generic.form.FormCard;

public class RegisterActivity extends AppCompatActivity {

    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button registerButton = findViewById(R.id.register_reg);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FormCard emailCard = (FormCard) fragmentManager.findFragmentById(R.id.card_email);
        FormCard usernameCard = (FormCard) fragmentManager.findFragmentById(R.id.card_username);
        FormCard passwordCard = (FormCard) fragmentManager.findFragmentById(R.id.card_password);
        FormCard passwordConfirmationCard = (FormCard) fragmentManager.findFragmentById(R.id.card_confirmpassword);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewModel.CREDENTIAL_VALIDATION_RESULT result = viewModel.validateCredentials();

                if (result == ViewModel.CREDENTIAL_VALIDATION_RESULT.VALID) {
                    Task accountCreationTask = viewModel.createAccount();

                    accountCreationTask.addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            MainActivity.transitionToSection(RegisterActivity.this, MainViewModel.SECTION_TYPE.CALENDAR, null, true);
                            finish();
                        }
                    });
                    accountCreationTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, getString(R.string.register_msg_createaccount_error_fallback), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    switch (result) {
                        case MISSING_FIELDS: {
                            Toast.makeText(RegisterActivity.this, getString(R.string.register_msg_createaccount_error_missingfgields), Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case PASSWORD_MISMATCH: {
                            Toast.makeText(RegisterActivity.this, getString(R.string.register_msg_createaccount_error_passwordmismatch), Toast.LENGTH_SHORT).show();
                            break;
                        }
                        default: {
                            Toast.makeText(RegisterActivity.this, getString(R.string.register_msg_createaccount_error_fallback), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        if (!viewModel.isInitialized()) {
            emailCard.addTextField(InputType.TYPE_TEXT_VARIATION_PERSON_NAME, viewModel.getEmail(), new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    viewModel.setEmail(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {}
            }, getString(R.string.msg_email));
            usernameCard.addTextField(InputType.TYPE_TEXT_VARIATION_PERSON_NAME, viewModel.getUsername(), new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    viewModel.setUsername(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {}
            }, getString(R.string.msg_username));
            passwordCard.addTextField(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD, viewModel.getPassword(), new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    viewModel.setPassword(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {}
            });
            passwordConfirmationCard.addTextField(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD, viewModel.getPasswordConfirmation(), new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    viewModel.setPasswordConfirmation(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {}
            });

            viewModel.setInitialized(true);
        }
    }

    @Override
    public void onAttachFragment(Fragment frag) {
        int id = frag.getId();
        if (id == R.id.card_email) {
            FormCard emailCard = (FormCard) frag;

            // Initialize email card
            Bundle emailCardArgs = new Bundle();
            emailCardArgs.putString(FormCard.ARG_LABEL, getString(R.string.prompt_email));
            emailCardArgs.putInt(FormCard.ARG_ICON, R.drawable.user);
            emailCardArgs.putInt(FormCard.ARG_BG_COLOR, getResources().getColor(R.color.positive_action));
            emailCard.setArguments(emailCardArgs);
        }
        else if (id == R.id.card_username) {
            FormCard emailCard = (FormCard) frag;

            // Initialize email card
            Bundle emailCardArgs = new Bundle();
            emailCardArgs.putString(FormCard.ARG_LABEL, getString(R.string.generic_label_username));
            emailCardArgs.putInt(FormCard.ARG_ICON, R.drawable.profile_white);
            emailCardArgs.putInt(FormCard.ARG_BG_COLOR, getResources().getColor(R.color.positive_action));
            emailCard.setArguments(emailCardArgs);
        }
        else if (id == R.id.card_password) {
            FormCard passwordCard = (FormCard) frag;

            // Initialize password card
            Bundle passwordCardArgs = new Bundle();
            passwordCardArgs.putString(FormCard.ARG_LABEL, getString(R.string.prompt_password));
            passwordCardArgs.putInt(FormCard.ARG_ICON, R.drawable.password);
            passwordCardArgs.putInt(FormCard.ARG_BG_COLOR, getResources().getColor(R.color.positive_action));
            passwordCard.setArguments(passwordCardArgs);
        }
        else if (id == R.id.card_confirmpassword) {
            FormCard passwordCard = (FormCard) frag;

            // Initialize password card
            Bundle passwordCardArgs = new Bundle();
            passwordCardArgs.putString(FormCard.ARG_LABEL, getString(R.string.register_password_confirm));
            passwordCardArgs.putInt(FormCard.ARG_ICON, R.drawable.reuse);
            passwordCardArgs.putInt(FormCard.ARG_BG_COLOR, getResources().getColor(R.color.positive_action));
            passwordCard.setArguments(passwordCardArgs);
        }
    }
}