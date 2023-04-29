package edu.ub.pis.giickos.ui.activities.login;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    private Button signInButton, registerButton, guestButton;

    private ViewModel viewModel;

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

            emailCard.addTextField(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, "", new TextWatcher() {
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

            passwordCard.addTextField(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD, "", new TextWatcher() {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewModel = new ViewModelProvider(this).get(ViewModel.class);

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
                            Toast.makeText(LoginActivity.this, getString(R.string.auth_login_success), Toast.LENGTH_SHORT).show();

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
