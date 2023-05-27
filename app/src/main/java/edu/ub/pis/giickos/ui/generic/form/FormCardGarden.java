package edu.ub.pis.giickos.ui.generic.form;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
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

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_QUESTION = "questionText";
    private static final String ARG_ANSWER = "answerText";

    private EditText answer;
    private EditTextListener answerListener;
    private String questionText;
    private String description;

    public FormCardGarden() {
        // Required empty public constructor
    }

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
        answer = view.findViewById(R.id.garden_answer);
        answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (answerListener != null) {
                    answerListener.onTextChanged(s.toString());
                }
            }
        });



        if (description != null)
        {
            answer.setText(description);
            answer.setKeyListener(null);
        }
    }


    public void setDescription(String description)
    {
        //Sets the description and disables the EditText,  view mode
        this.description = description;
        EditText answer = getView().findViewById(R.id.garden_answer);
        answer.setKeyListener(null);
        answer.setText(description);
    }
    public void cleanDescription()
    {
        //Cleans the description after planting a bamboo for a clean menu for the next bamboo
        this.description = null;
        EditText answer = getView().findViewById(R.id.garden_answer);
        answer.setText("");
    }


    public interface EditTextListener {
        void onTextChanged(String newText);
    }
    public void setEditTextListener(EditTextListener listener) {
        answerListener = listener;
    }




}