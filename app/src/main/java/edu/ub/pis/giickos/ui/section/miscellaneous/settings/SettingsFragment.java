package edu.ub.pis.giickos.ui.section.miscellaneous.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.generic.Switch;
import edu.ub.pis.giickos.ui.generic.form.FormCard;
import edu.ub.pis.giickos.ui.generic.form.FormCardStatisticsSettings;
import edu.ub.pis.giickos.ui.generic.form.FormStatistics;
import edu.ub.pis.giickos.ui.generic.form.TextField;

// Displays the settings of the app. TODO
public class SettingsFragment extends GiickosFragment {

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

    @Override
    public void onViewCreated(View view, Bundle bundle)
    {
        //TODO: live data username and email, some action to trigger the update -> tab change?
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


        FormCardStatisticsSettings usernameCard = addCard(R.drawable.profile_white, getString(R.string.generic_label_username));
        TextField usernameTextField = TextField.newInstance("TODO", InputType.TYPE_TEXT_VARIATION_PERSON_NAME, false, Color.WHITE);

        usernameCard.addElement(usernameTextField);

        FormCardStatisticsSettings emailCard = addCard(R.drawable.user, getString(R.string.generic_label_email));
        TextField emailTextField = TextField.newInstance("TODO", InputType.TYPE_CLASS_TEXT, false, Color.WHITE);
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
                //Purchase or cancel subscription
            }
        });
        feedbackCard.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TODO", "Feedback clicked");
                //Generates intent to send email to giickos
            }
        });
        aboutUsCard.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TODO", "About us clicked");
                //send to web or something
            }
        });

        notificationsCard.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationsSwitch.setChecked(!notificationsSwitch.isChecked());
            }
        });

        giickosPlusCard.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                giickosPlusMenu.setVisibility(View.VISIBLE);
            }
        });
        logoutCard.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TODO", "Logout clicked");

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("LOG OUT");
                builder.setMessage("Are you sure you want to LOG OUT from your account?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO go to login
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        removeAccountCard.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("DANGEROUS OPERATION");
                builder.setMessage("Are you sure you want to DELETE your account?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO: delete account
                                Toast.makeText(getContext(), "Your account have been successfully deleted", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}