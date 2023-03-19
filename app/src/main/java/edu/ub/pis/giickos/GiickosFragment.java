package edu.ub.pis.giickos;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
}
