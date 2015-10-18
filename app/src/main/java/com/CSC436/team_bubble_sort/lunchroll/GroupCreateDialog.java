package com.csc436.team_bubble_sort.lunchroll;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by DevinB on 10/17/2015.
 */
public class GroupCreateDialog extends DialogFragment implements  View.OnClickListener{
    Button createGroup, cancel;
    EditText nameBox;
    CommunicateBackToFriendsList communicator;
    private String[] selectedFriends = {"Cortana", "Siri", "someDrunkGuy", "fortuneCookie"};
    private ArrayList<String> selectedFriendsList;
    private ArrayAdapter selectedFriendsAdapter;
    private ListView selectedFriendsView;
    private Context context;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        communicator = (CommunicateBackToFriendsList) activity;
        context = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_create_group, null);
        createGroup = (Button) view.findViewById(R.id.create_group_popup_create_button);
        cancel = (Button) view.findViewById(R.id.create_group_popup_cancel_button);
        nameBox = (EditText) view.findViewById(R.id.create_group_popup_name_field);
        initSelectedFriendsList();
        createGroup.setOnClickListener(this);
        cancel.setOnClickListener(this);
        setCancelable(false);
        getDialog().setTitle("Create Group");
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
        void sendCreateGroupMessageBack(String nameOfGroup);
    }

    private void initSelectedFriendsList(){
        selectedFriendsList = new ArrayList<>();
        selectedFriendsList.addAll(Arrays.asList(selectedFriends));
        selectedFriendsAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_multiple_choice, selectedFriendsList);
        selectedFriendsView = (ListView) getActivity().findViewById(R.id.selected_friends); // Friends list
        selectedFriendsView.setAdapter(selectedFriendsAdapter);
    }
}
