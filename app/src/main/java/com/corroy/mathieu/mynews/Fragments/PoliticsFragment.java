package com.corroy.mathieu.mynews.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.corroy.mathieu.mynews.R;

public class PoliticsFragment extends Fragment {

    public PoliticsFragment() {
        // Required empty public constructor
    }

    public static PoliticsFragment newInstance(String color) {

        PoliticsFragment politicsFragment = new PoliticsFragment();
        Bundle bdl = new Bundle();
        bdl.putString("color2", color);
        politicsFragment.setArguments(bdl);
        return politicsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_politics, container, false);

        return view;
    }
}