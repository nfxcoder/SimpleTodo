package com.codepath.simpletodo.ui.editItem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.simpletodo.R;


public class EditItemFragment extends DialogFragment implements TextView.OnEditorActionListener {
    public static String ARG_ITEM_NAME = "itemName";
    public static String ARG_ITEM_POSITION = "itemPosition";

    private String itemName;
    private int itemPosition;
    private EditText etItemName;

    public EditItemFragment() {}

    public static EditItemFragment newInstance(String itemName, int itemPosition) {
        EditItemFragment fragment = new EditItemFragment();

        Bundle args = new Bundle();
        args.putString(ARG_ITEM_NAME, itemName);
        args.putInt(ARG_ITEM_POSITION, itemPosition);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        if (args != null) {
            itemName = args.getString(ARG_ITEM_NAME);
            itemPosition = args.getInt(ARG_ITEM_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setTitle("Edit Item");

        etItemName = (EditText)view.findViewById(R.id.etItemName);

        etItemName.setText(itemName);
        etItemName.setSelection(etItemName.getText().length());

        etItemName.requestFocus();

        etItemName.setOnEditorActionListener(this);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (EditorInfo.IME_ACTION_DONE == i || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            String updatedItemName = etItemName.getText().toString();

            EditItemDialogListener listener = (EditItemDialogListener)this.getActivity();
            listener.onFinishEditDialog(updatedItemName, itemPosition);

            dismiss();

            return true;
        }

        return false;
    }
}
