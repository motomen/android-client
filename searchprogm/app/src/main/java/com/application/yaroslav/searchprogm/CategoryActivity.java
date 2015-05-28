package com.application.yaroslav.searchprogm;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.yaroslav.searchprogm.entity.CategoryItem;
import com.application.yaroslav.searchprogm.entity.CategoryItemAdapter;
import com.application.yaroslav.searchprogm.util.Constant;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Yaroslav on 11.05.2015.
 */
public class CategoryActivity extends ListFragment {

    private String partUrl = Constant.url;
    ArrayList<CategoryItem> items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                // Set the Accept header
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
                HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

                String url = partUrl + "/api/category/get";

                // Create a new Rest Template instance
                RestTemplate restTemplate = new RestTemplate();

                // Make the HTTP GET request, marshaling the response from JSON to an array of Events
                ResponseEntity<CategoryItem[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, CategoryItem[].class);
                CategoryItem[] events = responseEntity.getBody();

                // Make the HTTP GET request, marshaling the response to a String
                items = new ArrayList<CategoryItem>(Arrays.asList(events));

                return null;
            }
        }.execute(null, null, null);

        setListAdapter(new CategoryItemAdapter(getActivity(), items));
        return v;
    }
}
