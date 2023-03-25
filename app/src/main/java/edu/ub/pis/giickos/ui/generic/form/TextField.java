package edu.ub.pis.giickos.ui.generic.form;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;


// A task creation field with a text input.
public class TextField extends GiickosFragment {
    private static final String ARG_TEXT = "Text";
    private static final String ARG_INPUT_TYPE = "InputType";

    public TextField() {} // Required empty public constructor

    public static TextField newInstance(String text, int inputType) {
        TextField fragment = new TextField();
        Bundle args = new Bundle();

        args.putString(ARG_TEXT, text);
        args.putInt(ARG_INPUT_TYPE, inputType);
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
        View view = inflater.inflate(R.layout.fragment_form_field_text, container, false);
        EditText textField = view.findViewById(R.id.textfield_input);
        Bundle args = getArguments();

        // Set text field input type and text
        textField.setInputType(args.getInt(ARG_INPUT_TYPE));
        textField.setText(args.getString(ARG_TEXT));

        return view;
    }
}