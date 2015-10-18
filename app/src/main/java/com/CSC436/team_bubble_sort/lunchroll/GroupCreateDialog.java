package com.csc436.team_bubble_sort.lunchroll;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by DevinB on 10/17/2015.
 */
public class GroupCreateDialog extends DialogFragment implements  View.OnClickListener{
    Button createGroup, cancel;
    EditText nameBox;
    CommunicateBackToFriendsList communicator;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        communicator = (CommunicateBackToFriendsList) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_create_group, null);
        createGroup = (Button) view.findViewById(R.id.create_group_popup_create_button);
        cancel = (Button) view.findViewById(R.id.create_group_popup_cancel_button);
        nameBox = (EditText) view.findViewById(R.id.create_group_popup_name_field);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        nameBox.requestFocus();
        createGroup.setOnClickListener(this);
        cancel.setOnClickListener(this);
        setCancelable(false);
        // Don't need ViewGroup parent so pass null
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.create_group_popup_cancel_button){
            dismiss();
        }
        else if(v.getId() == R.id.create_group_popup_create_button){
            String groupName = nameBox.getText().toString();
            communicator.sendCreateGroupMessageBack(groupName);
            dismiss();
        }
    }

    interface CommunicateBackToFriendsList{
        public void sendCreateGroupMessageBack(String nameOfGroup);
    }
}
