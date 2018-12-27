package com.corroy.mathieu.mynews.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.corroy.mathieu.mynews.R;

public class MostPopularFragment extends Fragment {

    public MostPopularFragment() {
        // Required empty public constructor
    }

    public static MostPopularFragment newInstance(String color) {

        MostPopularFragment mostPopularFragment = new MostPopularFragment();
        Bundle bdl = new Bundle();
        bdl.putString("color3", color);
        mostPopularFragment.setArguments(bdl);
        return mostPopularFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_most_popular, container, false);


        return view;
    }
}