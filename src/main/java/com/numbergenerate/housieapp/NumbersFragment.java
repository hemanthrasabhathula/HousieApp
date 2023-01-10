package com.numbergenerate.housieapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.speech.tts.TextToSpeech;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.TreeMap;

import com.numbergenerate.housieapp.databinding.FragmentNumbersBinding;
import com.numbergenerate.housieapp.model.HousieNumber;
import com.numbergenerate.housieapp.service.NumberGenerator;
import com.numbergenerate.housieapp.view.RecyclerViewAdapter;

public class NumbersFragment extends Fragment {

    private FragmentNumbersBinding binding;
    private Bundle params;
    private static NumberGenerator numberGenerator;
    private RecyclerView recyclerView;
    private TextToSpeech myTTS;
    TreeMap<Integer, HousieNumber> numbersMap= new TreeMap<>();
    private ArrayList<HousieNumber> numbersDataList  = new ArrayList<>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {


        binding = FragmentNumbersBinding.inflate(inflater, container, false);
        recyclerView = binding.numbersRV;
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextToSpeechHelper(getContext());
//        myTTS = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int i) {
//
//            }
//        });

        numberGenerator = NumberGenerator.createInstance(1,90);
        //binding.textviewFirst.setText("nothing");
        ArrayList<Integer> history =  new ArrayList<>();

        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("history",numberGenerator.history);
        //List<Integer> numbers = numberGenerator.numbers;
//        for (Integer integer : numbers) {
//            //numbersMap.put(integer, false);
//            //numbersDataList.add(new HousieNumber(integer,false));
//            numbersMap.put(integer,new HousieNumber(integer,false));
//        }

        RecyclerViewAdapter adapter=new RecyclerViewAdapter(numberGenerator.numbersMap,view.getContext());
        GridLayoutManager layoutManager=new GridLayoutManager(view.getContext(),10);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        binding.history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HistoryActivity.class);
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment,bundle);
                intent.putExtra("historyBundle",bundle);
                startActivity(intent);


            }
        });




        binding.generator.setOnClickListener(view1 -> {

        int selectedNumber = numberGenerator.generateNumber();
            Log.i("info", "Selected Number: "+selectedNumber);
            if(selectedNumber != -1) {

//                numberGenerator.history.add(selectedNumber);
                numberGenerator.numbersMap.put(selectedNumber, true);

                //numbersDataList.set(selectedNumber, new HousieNumber(selectedNumber,true));
                binding.textviewFirst.setTextSize(60);
                binding.textviewFirst.setText("" + selectedNumber);
                speakThis("" + selectedNumber);
                //myTTS.speak(selectedNumber+"",TextToSpeech.QUEUE_FLUSH,null);
                adapter.notifyItemChanged(selectedNumber-1);
            }
            else{
                binding.textviewFirst.setTextSize(20);
                binding.textviewFirst.setText("All Caught Up ..!");
            }

        });


        binding.mute.setOnCheckedChangeListener((checkBox, isChecked) -> {

            if(isChecked){
               mute();
               // binding.mute.setBackgroundResource(R.mipmap.mute);
            }else{
                soundOn();
                //binding.mute.setBackgroundResource(R.mipmap.sound);
            }
        });

        binding.reset.setOnClickListener(v -> {


            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Are you sure ?")
                    .setTitle("Confirm")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            numberGenerator.reset();
                            binding.textviewFirst.setTextSize(30);
                            binding.textviewFirst.setText("Draw ?");
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // CANCEL
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        });
    }



    public void TextToSpeechHelper(Context context) {
        myTTS = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result =  myTTS.setLanguage(Locale.UK);
                    params = new Bundle();

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });
    }
    public void speakThis(String whatToSay){
        String speech = whatToSay;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            myTTS.speak(speech,TextToSpeech.QUEUE_FLUSH, params, null);
        } else {
            myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void mute() {
        params.putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, 0.0f);

    }

    public void soundOn() {
        params.putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, 1.0f);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}