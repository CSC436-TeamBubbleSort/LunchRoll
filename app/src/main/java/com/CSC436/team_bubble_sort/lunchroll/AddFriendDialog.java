package com.csc436.team_bubble_sort.lunchroll;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Devin on 11/5/2015.
 */
public class AddFriendDialog extends DialogFragment implements View.OnClickListener {
    private EditText nameBox;
    private CommunicateFriendUsernameBackToFriendsList communicator;
    private View view;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        communicator = (CommunicateFriendUsernameBackToFriendsList) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle){
        view = inflater.inflate(R.layout.fragment_add_friend, container);
        Button addFriend = (Button) view.findViewById(R.id.add_friend_popup_add_button);
        Button cancel = (Button) view.findViewById(R.id.add_friend_popup_cancel_button);
        nameBox = (EditText) view.findViewById(R.id.add_friend_username_field);
        addFriend.setOnClickListener(this);
        cancel.setOnClickListener(this);
        setCancelable(false);
        getDialog().setTitle("Add Friend");
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.add_friend_popup_cancel_button){
            dismiss();
        }
        else if(v.getId() == R.id.add_friend_popup_add_button){
            String username = nameBox.getText().toString();
            communicator.sendFriendUsernameMessageBack(username);
            dismiss();
        }
    }

    interface CommunicateFriendUsernameBackToFriendsList{
        void sendFriendUsernameMessageBack(String usernameOfFriend);
    }
}
