package com.github.phonemirror.phonemirrorclient.ui.pair;

import android.arch.lifecycle.ViewModel;
import android.widget.TextView;

import com.github.phonemirror.phonemirrorclient.R;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;

/**
 * ViewModel for {@link PairDialogFragment}
 */
public class PairDialogViewModel extends ViewModel {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.dialogTitle)
    TextView dialogTitle;

    @SuppressWarnings("WeakerAccess")
    @BindString(R.string.pairing_title_fmt)
    String titleTextFormat;

    @Inject
    PairDialogViewModel() {

    }

    /**
     * Set the dialog's title with a format parameter.
     * @param formatStr the parameter to plug into the string format
     */
    void setTitle(String formatStr) {
        dialogTitle.setText(String.format(titleTextFormat, formatStr));
    }
}
