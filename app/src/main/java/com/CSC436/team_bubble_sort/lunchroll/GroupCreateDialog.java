package com.csc436.team_bubble_sort.lunchroll;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class GroupCreateDialog extends DialogFragment implements  View.OnClickListener{
    private EditText nameBox;
    private CommunicateBackToFriendsList communicator;
    private View view;
    private ArrayList<String> selectedFriendsList;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        communicator = (CommunicateBackToFriendsList) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_create_group, container);
        Button createGroup = (Button) view.findViewById(R.id.create_group_popup_create_button);
        Button cancel = (Button) view.findViewById(R.id.create_group_popup_cancel_button);
        nameBox = (EditText) view.findViewById(R.id.create_group_popup_name_field);
        Bundle args = getArguments();
        selectedFriendsList = args.getStringArrayList("selected_friends_list");
        initSelectedFriendsList();
        createGroup.setOnClickListener(this);
        cancel.setOnClickListener(this);
        setCancelable(false);
        getDialog().setTitle("Create UserGroup");

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
            // TODO Store new group name in server with associated list of friends
            communicator.sendCreateGroupMessageBack(groupName);
            dismiss();
        }
    }

    interface CommunicateBackToFriendsList{
        void sendCreateGroupMessageBack(String nameOfGroup);
    }

    private void initSelectedFriendsList(){
        ArrayAdapter selectedFriendsAdapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_list_item_1, selectedFriendsList);
        ListView selectedFriendsView = (ListView) view.findViewById(R.id.selected_friends);
        selectedFriendsView.setAdapter(selectedFriendsAdapter);
    }
}
