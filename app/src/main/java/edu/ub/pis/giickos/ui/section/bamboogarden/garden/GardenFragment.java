package edu.ub.pis.giickos.ui.section.bamboogarden.garden;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.databinding.FragmentCardFormStatisticsBinding;
import edu.ub.pis.giickos.ui.generic.form.FormCard;
import edu.ub.pis.giickos.ui.generic.form.FormCardGarden;
import edu.ub.pis.giickos.ui.generic.form.FormCardStatisticsSettings;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GardenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GardenFragment extends GiickosFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GardenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GardenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GardenFragment newInstance(String param1, String param2) {
        GardenFragment fragment = new GardenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static GardenFragment newInstance()
    {
        return  GardenFragment.newInstance("TODO","TODO");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_section_bamboogarden_garden, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        closeWindows(view);
        bambooInfoMenu(view);
        //TODO move to methods to make it more readable

        ImageView plantBamboo = view.findViewById(R.id.garden_plant_action);
        ImageView back = view.findViewById(R.id.garden_cancel);
        ImageView remove = view.findViewById(R.id.garden_remove);
        ImageView plantMenu = view.findViewById(R.id.garden_plant);

        View blocker = view.findViewById(R.id.garden_blocker);

        blocker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Do nothing, blocks the clicks of the behind views
            }
        });
        plantBamboo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO get all the info from the fragments and send it to the viewmodel
                System.out.println("Plant bamboo");
                closeWindows(view);
                //Plants the bamboo in the garden in a free space or selected space
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeWindows(view);
            }
        });
        plantMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO open the plant menu
                closeWindows(view);
                openView(view.findViewById(R.id.garden_menu));
                System.out.println("Plant menu");

            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO remove plant mode, if touch on bamboo, pop up to confirm and then remove
                System.out.println("Remove plant");
            }
        });



        addTextField(R.drawable.title, "Title: ", "", InputType.TYPE_TEXT_VARIATION_NORMAL, new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                //TODO update to viewmodel
                System.out.println("Title: " + editable.toString());
            }
        });
        addTextField(R.drawable.label, "Label: ", "", InputType.TYPE_TEXT_VARIATION_NORMAL, new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                //TODO update to viewmodel
                System.out.println("Label: " + editable.toString());
            }
        });
        FormCard growTime = addField(R.drawable.timer_white, "Grow time: ");
        //Probably get from viewmodel
        ArrayList<String> time = new ArrayList<>();
        time.add("3 Days");
        time.add("1 Week");
        time.add("2 Week");
        time.add("1 Month");
        time.add("3 Month");
        time.add("6 Month");
        time.add("1 Year");
        growTime.addSpinner(time,0); //TODO update to viewmodel


        addQuestions(getResources().getString(R.string.bamboo_question_1));
        addQuestions(getResources().getString(R.string.bamboo_question_2));
        addQuestions(getResources().getString(R.string.bamboo_question_3));
        addQuestions(getResources().getString(R.string.bamboo_question_4));
        addQuestions(getResources().getString(R.string.bamboo_question_letter));
    }

    private void addQuestions(String question)
    {
        FormCardGarden formCardGarden = FormCardGarden.newInstance(question);
        addChildFragment(formCardGarden, R.id.garden_plant_questions, true);
    }
    private void addTextField(int iconID, String label, String inputLabel, int inputType, @Nullable TextWatcher listener) {
        FormCard field = addField(iconID, label);

        field.addTextField(inputType, inputLabel, listener);
    }
    private FormCard addField(int iconID, String label) {
        return addField(iconID, label, -1);
    }
    private FormCard addField(int iconID, String label, int backgroundColor) {
        FormCard field = FormCard.newInstance(iconID, label, backgroundColor);

        addChildFragment(field, R.id.garden_plant_questions, true);

        return field;
    }
    private void closeWindows(View view){
        View gardenPlantMenu = view.findViewById(R.id.garden_menu);
        gardenPlantMenu.setVisibility(View.GONE);
        View gardenInfoMenu = view.findViewById(R.id.garden_bamboo_info_menu);
        gardenInfoMenu.setVisibility(View.GONE);
    }

    private void openView(View view){
        view.setVisibility(View.VISIBLE);
    }
    private void bambooInfoMenu(View view)
    {

        ImageView bamboo_s0 = view.findViewById(R.id.bamboo_slot_0);
        ImageView bamboo_s1 = view.findViewById(R.id.bamboo_slot_1);
        ImageView bamboo_s2 = view.findViewById(R.id.bamboo_slot_2);
        ImageView bamboo_s3 = view.findViewById(R.id.bamboo_slot_3);
        ImageView bamboo_s4 = view.findViewById(R.id.bamboo_slot_4);
        ImageView bamboo_s5 = view.findViewById(R.id.bamboo_slot_5);


        ImageView block = view.findViewById(R.id.garden_blocker_info);
        ImageView close = view.findViewById(R.id.garden_back_info_menu);
        ImageView harvest = view.findViewById(R.id.garden_harvest_action);

        FormCardStatisticsSettings title = addCardWithTint(R.drawable.title, "Title: Water habit",
                Color.rgb(126,105,0), //left frame
                Color.rgb(163,136,0), //right frame
                Color.rgb(160,32,240)); //text color);
        FormCardStatisticsSettings label = addCardWithTint(R.drawable.label, "Label: WaterH",
                Color.rgb(126,105,0), //left frame
                Color.rgb(163,136,0), //right frame
                Color.rgb(160,32,240)); //text color);
        FormCardStatisticsSettings grow = addCardWithTint(R.drawable.timer_white, "Growth: 1/7 days",
                Color.rgb(126,105,0), //left frame
                Color.rgb(163,136,0), //right frame
                Color.rgb(160,32,240)); //text color);

        String bambooName = "Bamboo"; //TODO get the information from the viewmodel
        FormCardGarden firstQ = addFragmentToInfoMenu(getResources().getString(R.string.bamboo_question_1), bambooName);
        FormCardGarden secondQ = addFragmentToInfoMenu(getResources().getString(R.string.bamboo_question_2), bambooName);
        FormCardGarden thirdQ = addFragmentToInfoMenu(getResources().getString(R.string.bamboo_question_3), bambooName);
        FormCardGarden fourthQ = addFragmentToInfoMenu(getResources().getString(R.string.bamboo_question_4), bambooName);


        //TODO update the bamboo grow phase from the viewmodel and set the image
        //TODO, if the shovel is selected, and then we select a bamboo slot, we remove the bamboo from that slot
        //Touching on a bamboo, opens a menu with the information of the bamboo
        bamboo_s0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO select the bamboo slot
                //TODO open the menu with the information of the bamboo that we get from the viewmodel

                updateBambooMenuInfo(title, label, grow, firstQ, secondQ, thirdQ, fourthQ,0);
                openView(view.findViewById(R.id.garden_bamboo_info_menu));
                System.out.println("Select bamboo slot 0");
            }
        });
        bamboo_s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO select the bamboo slot
                updateBambooMenuInfo(title, label, grow, firstQ, secondQ, thirdQ, fourthQ,1);
                openView(view.findViewById(R.id.garden_bamboo_info_menu));
                System.out.println("Select bamboo slot 1");
            }
        });
        bamboo_s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO select the bamboo slot
                updateBambooMenuInfo(title, label, grow, firstQ, secondQ, thirdQ, fourthQ,2);
                openView(view.findViewById(R.id.garden_bamboo_info_menu));
                System.out.println("Select bamboo slot 2");
            }
        });
        bamboo_s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO select the bamboo slot
                updateBambooMenuInfo(title, label, grow, firstQ, secondQ, thirdQ, fourthQ,3);
                openView(view.findViewById(R.id.garden_bamboo_info_menu));
                System.out.println("Select bamboo slot 3");
            }
        });
        bamboo_s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO select the bamboo slot
                updateBambooMenuInfo(title, label, grow, firstQ, secondQ, thirdQ, fourthQ,4);
                openView(view.findViewById(R.id.garden_bamboo_info_menu));
                System.out.println("Select bamboo slot 4");
            }
        });
        bamboo_s5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO select the bamboo slot
                updateBambooMenuInfo(title, label, grow, firstQ, secondQ, thirdQ, fourthQ,5);
                openView(view.findViewById(R.id.garden_bamboo_info_menu));
                System.out.println("Select bamboo slot 5");
            }
        });

        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do nothing, block background touch
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeWindows(view);
            }
        });

        harvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO harvest the bamboo, add to the storage
                System.out.println("Harvest the bamboo");
                closeWindows(view);
            }
        });
    }
    private void updateBambooMenuInfo(FormCardStatisticsSettings title, FormCardStatisticsSettings label, FormCardStatisticsSettings grow,
                                      FormCardGarden firstQ, FormCardGarden secondQ, FormCardGarden thirdQ, FormCardGarden fourthQ ,int slot)
    {
        //TODO put the list from start
        //TODO get the information from the viewmodel
        String bambooName = "Bamboo"; //TODO get the information from the viewmodel
        title.updateLabel("Title: " + bambooName);
        label.updateLabel("Label: " + bambooName);
        grow.updateLabel("Growth: 1/7 days");

        String answer = "Answer"; //TODO get the information from the viewmodel
        firstQ.updateDescription(answer);
        secondQ.updateDescription(answer);
        thirdQ.updateDescription(answer);
        fourthQ.updateDescription(answer);
    }


    private FormCardGarden addFragmentToInfoMenu(String question, String answer)
    {
        FormCardGarden formCardGarden = FormCardGarden.newInstance(question, answer);
        addChildFragment(formCardGarden, R.id.garden_bambo_goals, true);
        return formCardGarden;
    }
    private FormCardStatisticsSettings addCardWithTint(int iconID, String label, int colorLeft, int colorRight, int colorText) {
        FormCardStatisticsSettings card = FormCardStatisticsSettings.newInstance(iconID, label, colorLeft, colorRight, colorText);
        addChildFragment(card, R.id.garden_bambo_goals, true);

        return card;
    }


}