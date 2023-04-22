package edu.ub.pis.giickos;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Optional;

public class GiickosFragment extends Fragment {

    // Creates a child fragment and parents it.
    public void addChildFragment(Fragment fragment, int parentID, boolean synchronous) {
        FragmentManager childrenManager = getChildFragmentManager();
        FragmentTransaction transaction = childrenManager.beginTransaction();

        transaction.add(parentID, fragment);

        if (synchronous) {
            transaction.commitNow();
        }
        else {
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    // Asynchronous overload.
    public void addChildFragment(Fragment fragment, int parentID) {
        addChildFragment(fragment, parentID, false);
    }

    // Replaces a child fragment.
    public void replaceFragment(int id, Fragment newFragment) {
        FragmentManager childrenManager = getChildFragmentManager();
        FragmentTransaction transaction = childrenManager.beginTransaction();
        transaction.setReorderingAllowed(true);

        transaction.replace(id, newFragment);

        transaction.commit();
    }

    public Optional<String> getIntentString(String key) {
        Bundle extras = getActivity().getIntent().getExtras();
        Optional<String> str = Optional.empty();

        if (extras != null && extras.containsKey(key)) {
            String bundledStr = extras.getString(key);

            str = Optional.of(bundledStr);
        }

        return str;
    }

    public boolean getIntentBoolean(String key) {
        Bundle extras = getActivity().getIntent().getExtras();
        boolean bool = false;

        if (extras != null && extras.containsKey(key)) {
            bool = extras.getBoolean(key);
        }

        return bool;
    }

    public Optional<Integer> getIntentInteger(String key) {
        Bundle extras = getActivity().getIntent().getExtras();
        Optional<Integer> integer = Optional.empty();

        if (extras != null && extras.containsKey(key)) {
            integer = Optional.of(Integer.valueOf(extras.getInt(key)));
        }

        return integer;
    }

    public void showOperationResultToast(boolean success, String successString, String failureString) {
        Toast.makeText(getContext(), success ? successString : failureString, Toast.LENGTH_SHORT).show();
    }
}
