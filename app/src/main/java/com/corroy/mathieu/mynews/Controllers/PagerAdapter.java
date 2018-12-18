package com.corroy.mathieu.mynews.Controllers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.corroy.mathieu.mynews.Fragments.MostPopularFragment;
import com.corroy.mathieu.mynews.Fragments.PoliticsFragment;
import com.corroy.mathieu.mynews.Fragments.TopStoriesFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm){
        super(fm);
    }

    public int getCount(){
        return(3);
    }

    @Override
    public CharSequence getPageTitle(int position){
        return "Page" +position;
    }

    public Fragment getItem(int pos){

        switch(pos){

        case 0: return TopStoriesFragment.newInstance("First Fragment");
        case 1: return MostPopularFragment.newInstance("Second Fragment");
        case 2: return PoliticsFragment.newInstance("Third Fragment");
        default: return TopStoriesFragment.newInstance("Default Fragment");
        }
    }
}