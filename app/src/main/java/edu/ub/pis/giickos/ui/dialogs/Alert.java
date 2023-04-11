package edu.ub.pis.giickos.ui.dialogs;

import android.app.Activity;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import edu.ub.pis.giickos.R;

// Wrapper class for AlertDialog.
public class Alert extends AlertDialog.Builder {
    public Alert(Activity activity, String title, String message) {
        super(activity);

        setTitle(title);
        setMessage(message);

        // No button is present by default
        setNegativeButton(getContext().getText(R.string.generic_label_no), null);
    }

    public void setPositiveButton(DialogInterface.OnClickListener listener) {
        setPositiveButton(getContext().getString(R.string.generic_label_yes), listener);
    }
}
