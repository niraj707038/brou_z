package com.ranjan.myapps.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ranjan.myapps.Activity.ZoomActivity;
import com.ranjan.myapps.Model.NewsModel;
import com.ranjan.myapps.R;
import com.ranjan.myapps.adapter.Event_Adapter;
import com.ranjan.myapps.adapter.NewsAdapter;
import com.ranjan.myapps.adapter.News_Event_Adapter;
import com.ranjan.myapps.webservice.APIClient;
import com.ranjan.myapps.webservice.OnResponseInterface;
import com.ranjan.myapps.webservice.ResponseListner;

import retrofit2.Call;

public class NewsEvent extends Fragment implements NewsAdapter.NewsItemClick, OnResponseInterface {

    ImageView view_photos;
    private RecyclerView recycl_list;
    private NewsAdapter adapter;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = null;
        try {
            v = inflater.inflate(R.layout.news_event, container, false);

            init(v);
            view_photos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.popBackStack();
                }
            });

            tabLayout.addTab(tabLayout.newTab().setText("News"));
            tabLayout.addTab(tabLayout.newTab().setText("Event"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }


    public void getData() {

        try {
            Call<NewsModel> call = APIClient.getInstance().getApiInterface().getNewsData();
            call.request().url();
            Log.e("MyUrl", call.request().url() + "");
            new ResponseListner(this).getResponse(call);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void init(View v) {
        view_photos = v.findViewById(R.id.view_photos);
        recycl_list = v.findViewById(R.id.recycl_list);
        tabLayout = v.findViewById(R.id.tabLayout);
        viewPager = v.findViewById(R.id.viewPager);


    }

    NewsModel newsModel = new NewsModel();

    @Override
    public void onApiResponse(Object response) {
        try {
            if (response != null) {
                newsModel = (NewsModel) response;


                final News_Event_Adapter adapter = new News_Event_Adapter(getContext(), getFragmentManager(), tabLayout.getTabCount(),newsModel);
                viewPager.setAdapter(adapter);
                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });


                Log.d("", "");
            }
        } catch (Exception e) {
            Log.d("Error:", e.getMessage());

        }
    }

    @Override
    public void onApiFailure(String message) {
        try {
            Log.d("Error:", message);
        } catch (Exception e) {
            Log.d("Error:", e.getMessage());

        }
    }

    @Override
    public void itemClick(int pos) {

    }
}
