package com.ranjan.myapps.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.ranjan.myapps.Activity.ZoomActivity;
import com.ranjan.myapps.Model.CertificationModel;
import com.ranjan.myapps.R;
import com.ranjan.myapps.adapter.RecyclerCeritification;
import com.ranjan.myapps.webservice.APIClient;
import com.ranjan.myapps.webservice.OnResponseInterface;
import com.ranjan.myapps.webservice.ResponseListner;

import retrofit2.Call;

public class Certification extends Fragment implements RecyclerCeritification.RecyclerItemClick, OnResponseInterface {

    private RecyclerView staggerd;
    private RecyclerCeritification adapter;
    private GridLayoutManager manger;

    ImageView view_photos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        try {
            view = inflater.inflate(R.layout.certification_view, container, false);

            view_photos = view.findViewById(R.id.view_photos);

            staggerd = view.findViewById(R.id.recycl_list);


            view_photos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.popBackStack();
                }
            });

            getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void getData() {
        try {
            Call<CertificationModel> call = APIClient.getInstance().getApiInterface()
                    .getCertificationData();
            call.request().url();
            Log.e("MyUrl", call.request().url() + "");
            new ResponseListner(this).getResponse(call);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    CertificationModel certification = new CertificationModel();


    @Override
    public void onApiResponse(Object response) {
        try {
            if (response != null) {
                if (response instanceof CertificationModel) {
                    certification = (CertificationModel) response;
                    Log.d("", "");
                    adapter = new RecyclerCeritification(getContext(), certification, this);
                    //manger = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
                    staggerd.setLayoutManager(new GridLayoutManager(getActivity(), 2));

                    //staggerd.setLayoutManager(manger);
                    staggerd.setAdapter(adapter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onApiFailure(String message) {
        try {

        } catch (Exception e) {

        }
    }

    @Override
    public void itemClick(int pos) {
        String a= certification.data.get(pos).image;
        Intent aa=new Intent(getContext(), ZoomActivity.class);
        aa.putExtra("img",a);
        startActivity(aa);
    }
}
