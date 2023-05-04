package edu.ub.pis.giickos.ui.section.miscellaneous.settings;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.activities.login.LoginActivity;
import edu.ub.pis.giickos.ui.activities.main.MainActivity;
import edu.ub.pis.giickos.ui.dialogs.Alert;
import edu.ub.pis.giickos.ui.generic.Switch;
import edu.ub.pis.giickos.ui.generic.form.FormCardStatisticsSettings;
import edu.ub.pis.giickos.ui.generic.form.TextField;

// Displays the settings of the app. TODO
public class SettingsFragment extends GiickosFragment {

    private ViewModel viewModel;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_section_miscellaneous_settings, container, false);
    }

    private FormCardStatisticsSettings addCard(int iconID, String label) {
        FormCardStatisticsSettings card = FormCardStatisticsSettings.newInstance(iconID, label);
        addChildFragment(card, R.id.settings_list, true);

        return card;
    }

    private FormCardStatisticsSettings addCardWithTint(int iconID, String label, int colorLeft, int colorRight, int colorText) {
        FormCardStatisticsSettings card = FormCardStatisticsSettings.newInstance(iconID, label, colorLeft, colorRight, colorText);
        addChildFragment(card, R.id.settings_list, true);

        return card;
    }

    private String getDisplayedUserName() {
        ViewModel.UserData user = viewModel.getLoggedInUser().getValue();
        String username = getString(R.string.miscellaneous_tab_settings_notloggedin);

        if (user != null) {
            username = user.username;
        }

        return username;
    }

    private String getDisplayedEmail() {
        ViewModel.UserData user = viewModel.getLoggedInUser().getValue();
        String username = getString(R.string.miscellaneous_tab_settings_notloggedin);

        if (user != null) {
            username = user.email;
        }

        return username;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle)
    {
        View giickosPlusMenu = view.findViewById(R.id.show_giickos_plus);
        giickosPlusMenu.setVisibility(View.GONE);

        View blocker = view.findViewById(R.id.view_blocker);
        blocker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do nothing
            }
        });

        FormCardStatisticsSettings notificationsCard = addCard(R.drawable.notification, getString(R.string.miscellaneous_tab_settings_notifications));
        Switch notificationsSwitch = Switch.newInstance(true);
        notificationsSwitch.setListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("TODO", "Notifications toggled");
            }
        });
        notificationsCard.addElement(notificationsSwitch);

        FormCardStatisticsSettings feedbackCard = addCard(R.drawable.feed_back, getString(R.string.miscellaneous_tab_settings_feedback));
        FormCardStatisticsSettings aboutUsCard = addCard(R.drawable.info, getString(R.string.miscellaneous_tab_settings_about));



        //If the user is a guest
        if(viewModel.isGuest())
        {
            FormCardStatisticsSettings loginCard = addCardWithTint(R.drawable.profile_white, getString(R.string.miscellaneous_tab_settings_login),
                    Color.rgb(0,80,0), //left frame
                    Color.rgb(0,100,0), //right frame
                    Color.WHITE); //text color
            loginCard.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
        else
        {
            FormCardStatisticsSettings usernameCard = addCard(R.drawable.profile_white, getString(R.string.generic_label_username));
            TextField usernameTextField = TextField.newInstance(getDisplayedUserName(), InputType.TYPE_TEXT_VARIATION_PERSON_NAME, false, Color.WHITE);
            usernameCard.addElement(usernameTextField);

            FormCardStatisticsSettings emailCard = addCard(R.drawable.user, getString(R.string.generic_label_email));
            TextField emailTextField = TextField.newInstance(getDisplayedEmail(), InputType.TYPE_CLASS_TEXT, false, Color.WHITE);
            emailCard.addElement(emailTextField);

            FormCardStatisticsSettings giickosPlusCard = addCardWithTint(R.drawable.giickos_plus, getString(R.string.miscellaneous_tab_settings_giickos_plus),
                    Color.rgb(126,105,0), //left frame
                    Color.rgb(163,136,0), //right frame
                    Color.rgb(160,32,240)); //text color);

            FormCardStatisticsSettings logoutCard = addCardWithTint(R.drawable.exit, getString(R.string.miscellaneous_tab_settings_logout),
                    Color.rgb(113,48,12), //left frame
                    Color.rgb(158,66,16), //right frame
                    Color.WHITE); //text color

            FormCardStatisticsSettings removeAccountCard = addCardWithTint(R.drawable.delete_account, getString(R.string.miscellaneous_tab_settings_delete_account),
                    Color.rgb(80,0,0), //left frame
                    Color.rgb(100,0,0), //right frame
                    Color.WHITE); //text color
            giickosPlusCard.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    giickosPlusMenu.setVisibility(View.VISIBLE);
                }
            });

            logoutCard.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Alert alert = new Alert(getActivity(), getString(R.string.miscellaneous_tab_settings_msg_logout_title), getString(R.string.miscellaneous_tab_settings_msg_logout_body));

                    alert.setPositiveButton(getString(R.string.generic_label_confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            viewModel.logOut();
                            MainActivity.goToLogin(getActivity());
                        }
                    });
                    alert.setNegativeButton(android.R.string.cancel, null);

                    alert.show();
                }
            });

            removeAccountCard.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Alert alert = new Alert(getActivity(), getString(R.string.miscellaneous_tab_settings_msg_deleteaccount_title), getString(R.string.miscellaneous_tab_settings_msg_deleteaccount_body));

                    alert.setPositiveButton(getString(R.string.generic_label_confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            viewModel.removeAccount();
                            viewModel.logOut();
                            Toast.makeText(getContext(), getString(R.string.miscellaneous_tab_settings_msg_deleteaccount_success), Toast.LENGTH_SHORT).show();
                            MainActivity.goToLogin(getActivity());
                        }
                    });
                    alert.setNegativeButton(getString(android.R.string.cancel), null);

                    alert.show();
                }
            });
        }


        view.findViewById(R.id.exit_purchase_menu_giickos_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                giickosPlusMenu.setVisibility(View.GONE);
            }
        });

        view.findViewById(R.id.giickos_plus_purchase_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TODO", "Purchase/cancel subs");
                //TODO: purchase/cancel subs. purchaseSuccess? viewModel.setGiickosPlus(true); visi.gone :do nothing
                // viewModel.getGiickosPlus() == true? changeToCancelSubs : changeToPurchase
            }
        });
        feedbackCard.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Generates intent to send email to giickos
                String[] email = new String[]{getString(R.string.giickos_contact_email)};
                String subject = getString(R.string.miscellaneous_tab_settings_feedback_subject);

                Intent emailIntent = viewModel.composeEmail(email, subject);
                if(emailIntent.resolveActivity(getActivity().getPackageManager()) != null)
                    startActivity(emailIntent);
                else
                    Toast.makeText(getActivity(), getString(R.string.miscellaneous_tab_settings_msg_no_email_app), Toast.LENGTH_SHORT).show();
            }
        });
        aboutUsCard.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Generates intent to a website
                Intent browserIntent = viewModel.openBrowser(getString(R.string.giickos_website));
                if(browserIntent.resolveActivity(getActivity().getPackageManager()) != null)
                    startActivity(browserIntent);
                else
                    Toast.makeText(getActivity(), getString(R.string.miscellaneous_tab_settings_msg_no_browser_app), Toast.LENGTH_SHORT).show();
            }
        });

        notificationsCard.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationsSwitch.setChecked(!notificationsSwitch.isChecked());
            }
        });


    }
}