package com.corroy.mathieu.mynews.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.corroy.mathieu.mynews.R;


public class TopStoriesFragment extends Fragment {

    public TopStoriesFragment() {
        // Required empty public constructor
    }

    public static TopStoriesFragment newInstance(String color) {
        TopStoriesFragment topStoriesFragment = new TopStoriesFragment();
        Bundle bdl = new Bundle();
        bdl.putString("color", color);
        topStoriesFragment.setArguments(bdl);

        return topStoriesFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_stories, container, false);

        TextView textView = view.findViewById(R.id.textViewTopStories);
        textView.setText(getArguments().getString("color"));

        return view;
    }
}