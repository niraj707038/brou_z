package com.ranjan.myapps.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ranjan.myapps.Model.NewsModel;
import com.ranjan.myapps.fragment.EventFragment;
import com.ranjan.myapps.fragment.NewsEvent;
import com.ranjan.myapps.fragment.NewsFragment;

public class News_Event_Adapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;
    NewsModel newsModel;

    public News_Event_Adapter(Context context, FragmentManager fm, int totalTabs, NewsModel newsModel) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
        this.newsModel=newsModel;
    }


    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                NewsFragment newsFragment = new NewsFragment(newsModel.data.news_data);
                return newsFragment;
            case 1:
                EventFragment eventFragment = new EventFragment(newsModel.data.event_data);
                return eventFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
