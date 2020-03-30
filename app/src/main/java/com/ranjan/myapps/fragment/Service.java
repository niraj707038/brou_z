package com.ranjan.myapps.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.ranjan.myapps.Activity.ZoomServiceActivity;
import com.ranjan.myapps.Model.ServiceModel;
import com.ranjan.myapps.R;
import com.ranjan.myapps.adapter.RecyclerService;
import com.ranjan.myapps.webservice.APIClient;
import com.ranjan.myapps.webservice.OnResponseInterface;
import com.ranjan.myapps.webservice.ResponseListner;

import retrofit2.Call;

public class Service extends Fragment implements RecyclerService.RecyclerItemClick, OnResponseInterface {

    private RecyclerView staggerd;
    private RecyclerService adapter;
    private StaggeredGridLayoutManager manger;

    ImageView view_photos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.service_view, container, false);


        view_photos = view.findViewById(R.id.view_photos);

        staggerd = view.findViewById(R.id.recycl_list);
        manger = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggerd.setLayoutManager(manger);

       view_photos.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FragmentManager fragmentManager = getParentFragmentManager();
               fragmentManager.popBackStack();
           }
       });

        getData();
        return view;
    }

    private void getData() {
        try {
            Call<ServiceModel> call = APIClient.getInstance().getApiInterface()
                    .getServiceData();
            call.request().url();
            Log.e("MyUrl", call.request().url() + "");
            new ResponseListner(this).getResponse(call);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    ServiceModel services = new ServiceModel();

    @Override
    public void onApiResponse(Object response) {

        try {
            if (response != null) {
                if (response instanceof ServiceModel) {
                    services = (ServiceModel) response;
                    Log.d("", "");
                    adapter = new RecyclerService(getContext(), services, this);
                    staggerd.setAdapter(adapter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onApiFailure(String message) {

    }

    @Override
    public void itemClick(int pos) {

        String a= services.data.get(pos).image;
        String ds=services.data.get(pos).getDescription();

        Intent aa=new Intent(getContext(), ZoomServiceActivity.class);
        aa.putExtra("img",a);
        aa.putExtra("disc",ds);
        startActivity(aa);

    }

}
