package com.csc436.team_bubble_sort.lunchroll;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by DevinB on 10/17/2015.
 */
public class GroupCreateDialog extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Don't need ViewGroup parent so pass null
        return inflater.inflate(R.layout.fragment_create_group, null);
    }
}
