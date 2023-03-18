package edu.ub.pis.giickos;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class GiickosFragment extends Fragment {

    // Creates a child fragment and parents it.
    public void addChildFragment(Fragment fragment, int parentID) {
        FragmentManager childrenManager = getChildFragmentManager();
        FragmentTransaction childFragTrans = childrenManager.beginTransaction();

        childFragTrans.add(parentID, fragment);
        childFragTrans.addToBackStack(null);
        childFragTrans.commit();
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
