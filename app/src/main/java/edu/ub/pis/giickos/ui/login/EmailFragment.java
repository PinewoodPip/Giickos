package edu.ub.pis.giickos.ui.login;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;

public class EmailFragment extends GiickosFragment {

    private View root;
    private LinearLayout linearLayout;
    private TextView textView;
    private ImageView imageView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(root == null) {
            root = inflater.inflate(R.layout.fragment_card_form, container, false);
        }
        linearLayout = (LinearLayout) root.findViewById(R.id.list_main);
        textView = (TextView)root.findViewById(R.id.label);
        imageView = (ImageView) root.findViewById(R.id.image_icon);
        imageView.setImageResource(R.drawable.mail);
        textView.setText(R.string.prompt_email);
        View field_text = inflater.inflate(R.layout.fragment_form_field_text, linearLayout, false);
        EditText editText = (EditText)field_text.findViewById(R.id.textfield_input);
        editText.setHint(R.string.msg_email);
        linearLayout.addView(field_text);

        return root;
    }
}