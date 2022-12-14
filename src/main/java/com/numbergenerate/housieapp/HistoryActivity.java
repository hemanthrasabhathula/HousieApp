package com.numbergenerate.housieapp;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import androidx.navigation.ui.AppBarConfiguration;

import java.util.ArrayList;

import com.numbergenerate.housieapp.databinding.ActivityHistoryBinding;

public class HistoryActivity extends BaseActivity  {

    private AppBarConfiguration appBarConfiguration;
    private ActivityHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        ArrayList<Integer> historyList = intent.getBundleExtra("historyBundle").getIntegerArrayList("history");
        Log.i("info", "History Activity "+historyList);

        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String historyText = "";
        if(!historyList.isEmpty()){

            for (Integer integer : historyList
            ) {

                historyText = historyText.isEmpty()? ""+integer : historyText+" -> "+integer;
            }
        }

        if(!historyList.isEmpty()){
            binding.textviewHistory.setText(historyText) ;
        }
        binding.buttonHistory.setOnClickListener(view ->
        {

            super.onBackPressed();
        });
//        setSupportActionBar(binding.toolbar);

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_history);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_history);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}