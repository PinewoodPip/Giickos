package edu.ub.pis.giickos.ui.generic.form;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;


// A task creation field with a text input.
public class TextField extends GiickosFragment {
    private static final String ARG_TEXT = "Text";
    private static final String ARG_HINT = "Hint";
    private static final String ARG_INPUT_TYPE = "InputType";
    private static final String ARG_EDITABLE = "Editable";
    private static final String ARG_TEXT_COLOR = "TextColor";

    private TextWatcher listener = null;

    public TextField() {} // Required empty public constructor

    public static TextField newInstance(String text, int inputType, boolean editable, int color, String hintLabel) { // TODO cleanup params, make some aux class for initializing
        TextField fragment = new TextField();
        Bundle args = new Bundle();

        args.putString(ARG_TEXT, text);
        args.putString(ARG_HINT, hintLabel);
        args.putInt(ARG_INPUT_TYPE, inputType);
        args.putBoolean(ARG_EDITABLE, editable);
        args.putInt(ARG_TEXT_COLOR, color);
        fragment.setArguments(args);

        return fragment;
    }

    // Overloads.
    public static TextField newInstance(String text, int inputType, boolean editable, int color) {
        return newInstance(text, inputType, editable, color, "");
    }
    public static TextField newInstance(String text, int inputType, boolean editable, String hintLabel) {
        return newInstance(text, inputType, editable, Color.BLACK, hintLabel);
    }
    public static TextField newInstance(String text, int inputType) {
        return newInstance(text, inputType, true, Color.BLACK, "");
    }
    public static TextField newInstance(String text, int inputType, boolean editable) {
        return newInstance(text, inputType, editable, Color.BLACK, "");
    }

    public void setListener(TextWatcher listener) {
        View view = getView();

        if (view != null) {
            EditText textField = view.findViewById(R.id.textfield_input);

            textField.removeTextChangedListener(this.listener);

            if (listener != null) {
                textField.addTextChangedListener(listener);
            }
            else {
                textField.removeTextChangedListener(this.listener);
            }
        }

        this.listener = listener;
    }

    // Sets the text of the field.
    public void setText(String text) {
        View view = getView();
        Bundle arguments = getArguments();

        if (arguments == null) {
            arguments = new Bundle();
            setArguments(arguments); // TODO is this safe to call before operating on the bundle?
        }

        arguments.putString(ARG_TEXT, text);

        if (view != null) {
            EditText textField = view.findViewById(R.id.textfield_input);

            textField.setText(text);
        }
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
        textField.setHint(args.getString(ARG_HINT));

        if (!args.getBoolean(ARG_EDITABLE)) {
            // TODO extract method
            textField.setEnabled(false);
            textField.setFocusable(false);
            textField.setBackgroundColor(Color.TRANSPARENT);
        }
        textField.setTextColor(args.getInt(ARG_TEXT_COLOR));

        return view;
    }

    public void onViewCreated(View view, Bundle savedInstance) {
        setListener(listener);
    }
}