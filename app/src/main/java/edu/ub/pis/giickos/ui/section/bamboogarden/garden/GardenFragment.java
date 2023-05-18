package edu.ub.pis.giickos.ui.section.bamboogarden.garden;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.model.garden.Bamboo;
import edu.ub.pis.giickos.ui.dialogs.Alert;
import edu.ub.pis.giickos.ui.generic.form.FormCard;
import edu.ub.pis.giickos.ui.generic.form.FormCardGarden;
import edu.ub.pis.giickos.ui.generic.form.FancyFormCard;
import edu.ub.pis.giickos.ui.generic.form.FormSpinner;
import edu.ub.pis.giickos.ui.generic.form.TextField;
import edu.ub.pis.giickos.ui.section.bamboogarden.ViewModel;

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

    private ViewModel viewModel;

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
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
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

        //This function loads the bamboo menu, where you can see the info of the SELECTED bamboo
        bambooInfoMenu(view);

        //This is the function that crates the menu for planting a bamboo
        plantingBambooMenu(view);
    }


    //This function updates the bamboo view
    private void updateBambooView(View view, Map<Integer, Bamboo> integerBambooMap)
    {
        ImageView[] bambooSlots = new ImageView[6];
        bambooSlots[0] = view.findViewById(R.id.bamboo_slot_0);
        bambooSlots[1] = view.findViewById(R.id.bamboo_slot_1);
        bambooSlots[2] = view.findViewById(R.id.bamboo_slot_2);
        bambooSlots[3] = view.findViewById(R.id.bamboo_slot_3);
        bambooSlots[4] = view.findViewById(R.id.bamboo_slot_4);
        bambooSlots[5] = view.findViewById(R.id.bamboo_slot_5);

        for(int i = 0; i < 6; i++)
        {
            if(integerBambooMap.containsKey(i)) //Si en slot i, hi ha un bamboo plantat
            {
                int growthPhase = integerBambooMap.get(i).getCurrentPhase();
                System.out.println("Growth phase: " + growthPhase);
                int imageID = 0;

                switch (growthPhase)
                {
                    case 0:
                        imageID = R.drawable.bamboo_phase_1;
                        break;
                    case 1:
                        imageID = R.drawable.bamboo_phase_2;
                        break;
                    case 2:
                        imageID = R.drawable.bamboo_phase_3;
                        break;
                    case 3:
                        imageID = R.drawable.bamboo_phase_4;
                        break;
                    case 4:
                        imageID = R.drawable.bamboo_phase_5;
                        break;
                    case 5:
                        imageID = R.drawable.bamboo_phase_6;
                        break;

                }
                bambooSlots[i].setImageResource(imageID);
            }
            else
            {
                //If there is no bamboo in the slot, we set the image to the empty slot
                bambooSlots[i].setImageResource(R.drawable.bamboo_phase_0);
            }
        }
    }

    private void plantingBambooMenu(View view)
    {
        //Title, label, growtime cards for the bamboo form
        int[] colors = {
                R.color.neutral_garden_1, //left frame
                R.color.neutral_garden_2, //right frame
                R.color.white, //text color
        };

        TextField cardTitle = addTextField(R.drawable.title, "Title: ", colors,"" , InputType.TYPE_TEXT_VARIATION_NORMAL, new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                viewModel.setBambooTitle(editable.toString());
                System.out.println("Title: " + editable.toString());
            }
        });
        FancyFormCard growTime = addField(R.drawable.timer_white, "Grow time: ", colors);
        //Probably get from viewmodel
        ArrayList<String> time = new ArrayList<>();
        //From the ViewModel, we get the enum values and add them to the arraylist for the spinner
        for (ViewModel.BAMBOO_GROWTH_TIME i : ViewModel.BAMBOO_GROWTH_TIME.values()) {
            time.add(getString(i.getNameResource()));
        }
        FormSpinner spinner = growTime.addSpinner(time,0);

        //Pack of FormCardGarden for controlling them in batch
        FormCardGarden[] questions = new FormCardGarden[5];
        questions[0] = addQuestions(getResources().getString(R.string.bamboo_question_1), ViewModel.BAMBOO_QUESTIONS.QUESTION_ONE);
        questions[1] = addQuestions(getResources().getString(R.string.bamboo_question_2), ViewModel.BAMBOO_QUESTIONS.QUESTION_TWO);
        questions[2] = addQuestions(getResources().getString(R.string.bamboo_question_3), ViewModel.BAMBOO_QUESTIONS.QUESTION_THREE);
        questions[3] = addQuestions(getResources().getString(R.string.bamboo_question_4), ViewModel.BAMBOO_QUESTIONS.QUESTION_FOUR);
        questions[4] = addQuestions(getResources().getString(R.string.bamboo_question_letter), ViewModel.BAMBOO_QUESTIONS.QUESTION_LETTER);

        //Images related to the menu
        ImageView plantBamboo = view.findViewById(R.id.garden_plant_action);
        ImageView back = view.findViewById(R.id.garden_cancel);
        ImageView plantMenu = view.findViewById(R.id.garden_plant);

        //Blocker of clicks for the menu
        View blocker = view.findViewById(R.id.garden_blocker);

        //Blocks the clicks of the behind menu
        blocker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Do nothing, blocks the clicks of the behind views
            }
        });

        //Goes back to the garden view
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeWindows(view);
            }
        });

        //Listener of the spinner, gets the selected value and sends it to the viewmodel
        spinner.setListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setBambooGrowTime(ViewModel.BAMBOO_GROWTH_TIME.values()[position]);
                System.out.println("Grow time: " + parent.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //Listener of the "plant button", plants the bamboo in the garden
        plantBamboo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Plant bamboo");
                //Plants the bamboo in the garden in a free space or selected space
                view.findViewById(R.id.garden_menu);
                int freeSlot = viewModel.getFreeSlot();

                if(freeSlot != -1)
                {
                    if(!viewModel.plantBamboo(freeSlot))
                    {
                        Alert alert = new Alert(getActivity(), "Empty fields!", "Please fill all the required fields!");
                        alert.setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alert.setNegativeButton("", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        alert.show();
                    }
                    else
                    {
                        closeWindows(view);
                    }
                }
                else
                {
                    //This should be never prompted since before opening the menu, it checks if there is a free slot
                    Toast.makeText(getActivity(), "No free slots", Toast.LENGTH_SHORT).show();
                    closeWindows(view);
                }
            }
        });

        //Menu that lets you plant a bamboo if there is enough space
        plantMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeWindows(view);
                if(viewModel.getFreeSlot() == -1)
                {
                    //TODO warning missatges -> add to values/strings -> getResouces().getString(R.string.warning_message)....
                    Alert alert = new Alert(getActivity(), "Not enough space!", "Please remove a bamboo or collect a bamboo to plant a new one.");
                    alert.setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alert.setNegativeButton("", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //There is no negative button, you just can accept >:)
                        }
                    });
                    alert.show();
                }
                else
                {
                    //Cleans the bambooForm before opening the menu while setting the scroll to the top
                    cleanFormCardGarden(questions);
                    cardTitle.setText("");
                    spinner.setSelection(0);
                    viewModel.setBambooGrowTime(ViewModel.BAMBOO_GROWTH_TIME.values()[0]);
                    ScrollView scrollView = view.findViewById(R.id.garden_menu_scroll_view);
                    scrollView.scrollTo(0,0);

                    //Opens the menu
                    openView(view.findViewById(R.id.garden_menu));
                    System.out.println("Plant menu");
                }
            }
        });

        //Observer for the planted bamboo in order to update the view using updateBambooView
        final Observer<Map<Integer, Bamboo>> lookerOfBamboo = new Observer<Map<Integer, Bamboo>>() {
            @Override
            public void onChanged(Map<Integer, Bamboo> integerBambooMap) {
                //Update the bamboo phase
                updateBambooView(view, integerBambooMap);
                System.out.println("Bamboo observer");
            }

        };

        viewModel.getPlantedBamboo().observe(getViewLifecycleOwner(),lookerOfBamboo);
    }


    private FormCardGarden addQuestions(String question, ViewModel.BAMBOO_QUESTIONS questionKey)
    {
        FormCardGarden formCardGarden = FormCardGarden.newInstance(question);
        addChildFragment(formCardGarden, R.id.garden_plant_questions, true);
        formCardGarden.setEditTextListener(new FormCardGarden.EditTextListener() {
            @Override
            public void onTextChanged(String text) {
                viewModel.addBambooQuestionAnswer(questionKey.getKey(), text);
            }
        });
        return formCardGarden;
    }
    private TextField addTextField(int iconID, String label, int[] colors, String inputLabel, int inputType, @Nullable TextWatcher listener) {
        FancyFormCard field = addField(iconID, label, colors);
        return field.addTextField(inputType, inputLabel, listener);
    }
    private FancyFormCard addField(int iconID, String label, int[] colors) {
        FancyFormCard field = FancyFormCard.newInstance(iconID, label,colors[0] ,colors[1], colors[2]);

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
        ImageView remove = view.findViewById(R.id.garden_remove_action);
        ImageView water = view.findViewById(R.id.garden_water_action);

        FancyFormCard[] cards = new FancyFormCard[2];

        cards[0] = addCardWithTint(R.drawable.title, "Title: Water habit",
                R.color.garden_1, // left frame
                R.color.garden_2, // right frame
                R.color.garden_3); // text color

        cards[1] = addCardWithTint(R.drawable.timer_white, "Growth: 1/7 days",
                R.color.garden_1, // left frame
                R.color.garden_2, // right frame
                R.color.garden_3); // text color

        String bambooName = "Bamboo"; //Place holder, touching on the bamboo will update the info menu

        FormCardGarden[] questions = new FormCardGarden[4];
        questions[0] = addFragmentToInfoMenu(getResources().getString(R.string.bamboo_question_1), bambooName);
        questions[1] = addFragmentToInfoMenu(getResources().getString(R.string.bamboo_question_2), bambooName);
        questions[2] = addFragmentToInfoMenu(getResources().getString(R.string.bamboo_question_3), bambooName);
        questions[3] = addFragmentToInfoMenu(getResources().getString(R.string.bamboo_question_4), bambooName);

        //The following listener gets the information from the viewmodel and updates the view using the slot.
        bamboo_s0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slot = 0;
                tryOpenBambooInfoMenu(cards, questions, slot, view);
            }
        });
        bamboo_s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slot = 1;
                tryOpenBambooInfoMenu(cards, questions, slot, view);
            }
        });
        bamboo_s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slot = 2;
                tryOpenBambooInfoMenu(cards, questions, slot, view);
            }
        });
        bamboo_s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slot = 3;
                tryOpenBambooInfoMenu(cards, questions, slot, view);
            }
        });
        bamboo_s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slot = 4;
                tryOpenBambooInfoMenu(cards, questions, slot, view);
            }
        });
        bamboo_s5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slot = 5;
                tryOpenBambooInfoMenu(cards, questions, slot, view);
            }
        });

        //Blocks the background touch
        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do nothing, block background touch
            }
        });

        //Closes the menu
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeWindows(view);
            }
        });

        //Harvests the bamboo
        harvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Bamboo bamboo = viewModel.getSlotBamboo(viewModel.getSelectedSlot());
                if(!bamboo.harvest())
                {
                    Toast.makeText(getActivity(), "The bamboo is not ready to be harvested!", Toast.LENGTH_LONG).show();
                    return;
                }

                Alert alert = new Alert(getActivity(), "Harvest", "Are you sure you want to harvest the bamboo?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (viewModel.harvestBamboo(viewModel.getSelectedSlot()))
                        {
                            System.out.println("Harvest the bamboo");
                            closeWindows(view);
                            Alert alert = new Alert(getActivity(), "Congratulations!", "You harvested the bamboo! Go to the storage tab to view the collected bamboo.");
                            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            alert.setNegativeButton("", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });


                        }
                    }
                }).show();
            }});

        //Removes the selected bamboo.
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alert alert = new Alert(getActivity(), "Dangerous Operation!", "Are you sure you want to remove this bamboo? If the objectives that you proposed are too hard, consider lowering them! Start by little and then increase the difficulty!");
                alert.setPositiveButton("I want to remove it!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.removeBamboo(viewModel.getSelectedSlot());
                        closeWindows(view);
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
                System.out.println("Remove plant");
            }
        });

        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bamboo bamboo = viewModel.getSlotBamboo(viewModel.getSelectedSlot());

                //If the bamboo is already watered, we can't water it again
                if(!bamboo.canBeWatered())
                {
                    String message = "Already watered! You can only water once a day! Come back tomorrow!";
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    return;
                }

                //If the bamboo is already grown, we can't water it
                if(bamboo.getGrowth() != bamboo.getTotalGrowth())
                {

                    Alert objectiveAlert = new Alert(getActivity(), "Objective completed?", "Are you sure you have completed the objective? Please, be sincere to yourself, if you didn't complete the objective, you are only cheating yourself!");
                    objectiveAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(viewModel.waterBamboo(viewModel.getSelectedSlot())) {
                                tryOpenBambooInfoMenu(cards, questions, viewModel.getSelectedSlot(), view);
                                closeWindows(view);
                                Bamboo grownBamboo = viewModel.getSlotBamboo(viewModel.getSelectedSlot());
                                String message = "You have watered the bamboo! It has grown " + grownBamboo.getGrowth() + " out of " + grownBamboo.getTotalGrowth() + " days!";

                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                    objectiveAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    objectiveAlert.show();
                }
                else
                {
                    Alert alert = new Alert(getActivity(), "Fully growth!", "Please, consider harvesting the bamboo or removing it. By harvesting it, you can store it in to the storage.");
                    alert.setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alert.setNegativeButton("", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alert.show();
                    System.out.println("Bamboo is fully grown");
                }

            }
        });



    }

    private void tryOpenBambooInfoMenu(FancyFormCard[] cards, FormCardGarden[] questions, int slot, View view) {
        if(viewModel.isBambooPlanted(slot))
        {
            viewModel.setSelectedSlot(slot);
            updateBambooMenuInfo(cards, questions, view);
            openView(view.findViewById(R.id.garden_bamboo_info_menu));
        }

    }

    private void updateBambooMenuInfo(FancyFormCard[] cards, FormCardGarden[] questions, View view)
    {
        //Puts the list menu at the top
        ScrollView scrollView = view.findViewById(R.id.garden_view_menu_scroller);
        scrollView.scrollTo(0,0);

        //Gets the selected bamboo and loads the info to the menu (could be prettier...ik)
        Bamboo bamboo = viewModel.getSlotBamboo(viewModel.getSelectedSlot());
        cards[0].updateLabel("Title: " + bamboo.getTitle());
        cards[1].updateLabel("Growth: " + bamboo.getGrowth() + "/" + bamboo.getTotalGrowth() + " days");

        questions[0].setDescription(bamboo.getAnswer(ViewModel.BAMBOO_QUESTIONS.QUESTION_ONE.getKey()));
        questions[1].setDescription(bamboo.getAnswer(ViewModel.BAMBOO_QUESTIONS.QUESTION_TWO.getKey()));
        questions[2].setDescription(bamboo.getAnswer(ViewModel.BAMBOO_QUESTIONS.QUESTION_THREE.getKey()));
        questions[3].setDescription(bamboo.getAnswer(ViewModel.BAMBOO_QUESTIONS.QUESTION_FOUR.getKey()));
    }


    private FormCardGarden addFragmentToInfoMenu(String question, String answer)
    {
        FormCardGarden formCardGarden = FormCardGarden.newInstance(question, answer);
        addChildFragment(formCardGarden, R.id.garden_bambo_goals, true);
        return formCardGarden;
    }
    private FancyFormCard addCardWithTint(int iconID, String label, int colorLeft, int colorRight, int colorText) {
        FancyFormCard card = FancyFormCard.newInstance(iconID, label, colorLeft, colorRight, colorText);
        addChildFragment(card, R.id.garden_bambo_goals, true);

        return card;
    }

    private void cleanFormCardGarden(FormCardGarden[] formCardGardens)
    {
        for(FormCardGarden formCardGarden : formCardGardens)
        {
            formCardGarden.cleanDescription();
        }
    }

}