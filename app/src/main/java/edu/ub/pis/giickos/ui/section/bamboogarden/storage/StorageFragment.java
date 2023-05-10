package edu.ub.pis.giickos.ui.section.bamboogarden.storage;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.model.garden.Bamboo;
import edu.ub.pis.giickos.model.team.Team;
import edu.ub.pis.giickos.ui.generic.form.FormCardGarden;
import edu.ub.pis.giickos.ui.generic.form.FormCardStatisticsSettings;
import edu.ub.pis.giickos.ui.section.bamboogarden.ViewModel;
import edu.ub.pis.giickos.ui.section.bamboogarden.garden.GardenFragment;
import edu.ub.pis.giickos.ui.section.miscellaneous.team.TeamListAdapter;
import edu.ub.pis.giickos.ui.section.miscellaneous.team.TeamMembersListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StorageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StorageFragment extends GiickosFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ViewModel viewModel;

    public StorageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StorageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StorageFragment newInstance(String param1, String param2) {
        StorageFragment fragment = new StorageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static StorageFragment newInstance()
    {
        return  StorageFragment.newInstance("TODO","TODO");
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
        return inflater.inflate(R.layout.fragment_section_bamboograden_storage, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.storage_bamboo_info_menu).setVisibility(View.GONE);

        ImageView blocker = view.findViewById(R.id.storage_blocker_info);
        ImageView back = view.findViewById(R.id.storage_back_info_menu);

        FormCardStatisticsSettings title = addCardWithTint(R.drawable.title, "",
                Color.rgb(126,105,0), //left frame
                Color.rgb(163,136,0), //right frame
                Color.rgb(160,32,240)); //text color);
        FormCardStatisticsSettings label = addCardWithTint(R.drawable.label, "",
                Color.rgb(126,105,0), //left frame
                Color.rgb(163,136,0), //right frame
                Color.rgb(160,32,240)); //text color);
        FormCardStatisticsSettings grow = addCardWithTint(R.drawable.timer_white, "",
                Color.rgb(126,105,0), //left frame
                Color.rgb(163,136,0), //right frame
                Color.rgb(160,32,240)); //text color);

        String answer = "Place holder";
        FormCardGarden firstQ = addFragmentToInfoMenu(getResources().getString(R.string.bamboo_question_1), answer);
        FormCardGarden secondQ = addFragmentToInfoMenu(getResources().getString(R.string.bamboo_question_2), answer);
        FormCardGarden thirdQ = addFragmentToInfoMenu(getResources().getString(R.string.bamboo_question_3), answer);
        FormCardGarden fourthQ = addFragmentToInfoMenu(getResources().getString(R.string.bamboo_question_4), answer);
        FormCardGarden letter = addFragmentToInfoMenu(getResources().getString(R.string.bamboo_question_letter), answer);


        blocker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do nothing, just block the clicks in the background
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.storage_bamboo_info_menu).setVisibility(View.GONE);
            }
        });



        //Bamboo buttons
        RecyclerView recyclerView = view.findViewById(R.id.bamboo_recycler_view);
        //Li assignem un layout manager
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        //Init adapter + set adapter to recycler view
        BambooScrollListAdapter bambooListAdapter = new BambooScrollListAdapter(getContext(), viewModel.getBamboos());
        recyclerView.setAdapter(bambooListAdapter);

        bambooListAdapter.setOnItemClickListener(new BambooScrollListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Bamboo bamboo) {
                title.updateLabel("Title: " + bamboo.getTitle());
                label.updateLabel("Label: " + bamboo.getLabel());
                grow.updateLabel("Total growth time: " + bamboo.getTotalGrowth() + "days");


                firstQ.setDescription(bamboo.getAnswer("1"));
                secondQ.setDescription(bamboo.getAnswer("2"));
                thirdQ.setDescription(bamboo.getAnswer("3"));
                fourthQ.setDescription(bamboo.getAnswer("4"));
                letter.setDescription(bamboo.getAnswer("letter"));

                view.findViewById(R.id.storage_bamboo_info_menu).setVisibility(View.VISIBLE);
            }
        });

    }
    private FormCardGarden addFragmentToInfoMenu(String question, String answer)
    {
        FormCardGarden formCardGarden = FormCardGarden.newInstance(question, answer);
        addChildFragment(formCardGarden, R.id.storage_bambo_goals, true);
        return formCardGarden;
    }
    private FormCardStatisticsSettings addCardWithTint(int iconID, String label, int colorLeft, int colorRight, int colorText) {
        FormCardStatisticsSettings card = FormCardStatisticsSettings.newInstance(iconID, label, colorLeft, colorRight, colorText);
        addChildFragment(card, R.id.storage_bambo_goals, true);

        return card;
    }




}