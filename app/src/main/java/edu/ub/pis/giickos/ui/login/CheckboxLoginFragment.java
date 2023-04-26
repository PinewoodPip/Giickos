package edu.ub.pis.giickos.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;

public class CheckboxLoginFragment extends GiickosFragment {

    private View root;
    private CheckBox checkBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(root == null) {
            root = inflater.inflate(R.layout.fragment_checkbox_label_login, container, false);
        }
        checkBox = (CheckBox)root.findViewById(R.id.checkbox);
        checkBox.setText(R.string.remember_me);


        return root;
    }
}
