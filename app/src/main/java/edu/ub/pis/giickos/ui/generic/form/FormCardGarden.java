package edu.ub.pis.giickos.ui.generic.form;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import edu.ub.pis.giickos.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormCardGarden#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormCardGarden extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_QUESTION = "questionText";
    private static final String ARG_ANSWER = "answerText";

    // TODO: Rename and change types of parameters
    private String questionText;
    private String description;

    public FormCardGarden() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters

    public static FormCardGarden newInstance(String questionText, String description) {
        FormCardGarden fragment = new FormCardGarden();
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION, questionText);
        args.putString(ARG_ANSWER, description);
        fragment.setArguments(args);
        return fragment;
    }

    public static FormCardGarden newInstance(String questionText) {
        FormCardGarden fragment = newInstance(questionText, null);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionText= getArguments().getString(ARG_QUESTION);
            description = getArguments().getString(ARG_ANSWER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_card_garden, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        TextView question = view.findViewById(R.id.garden_question_label);
        question.setText(questionText);
        EditText answer = view.findViewById(R.id.garden_answer);
        if (description != null)
        {
            answer.setText(description);
            answer.setKeyListener(null);
        }

    }

    public void updateDescription(String description)
    {
        this.description = description;
        EditText answer = getView().findViewById(R.id.garden_answer);
        answer.setText(description);
    }
}