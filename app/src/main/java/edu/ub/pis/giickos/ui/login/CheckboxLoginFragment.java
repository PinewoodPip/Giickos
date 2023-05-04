package edu.ub.pis.giickos.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.activities.login.ViewModel;

public class CheckboxLoginFragment extends GiickosFragment {

    private ViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkbox_label_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance) {
        CheckBox checkBox = view.findViewById(R.id.checkbox);

        checkBox.setText(R.string.remember_me);
        checkBox.setChecked(viewModel.getRememberLogin());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                viewModel.setRememberLogin(b);
            }
        });
    }
}
