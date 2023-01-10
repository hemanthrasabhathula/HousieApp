package com.numbergenerate.housieapp.service;

import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumberGenerator {
    private int min ;
    private int max ;
    private int selectedNumber;

    public  List<Integer> numbers;
    public ArrayList<Integer> history;
    private static NumberGenerator singleton = null;
    public TreeMap<Integer,Boolean> numbersMap;
    public TreeMap<Integer,Boolean> numbersMapBackup;

    public static NumberGenerator createInstance(int min, int max) {
        if (singleton == null) {
            singleton = new NumberGenerator(min,max);
        }
        return singleton;
    }

    public static NumberGenerator resetInstance(int min, int max) {

            singleton = new NumberGenerator(min,max);
        Log.i("info", "resetInstance called: ");

        return singleton;
    }

    public NumberGenerator() {
        history =  new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            numbers = IntStream.rangeClosed(min, max).boxed().collect(Collectors.toList());
        }else{
            for (int i = min; i <= max ; i++) {
                numbers.add(i);
            }
        }
    }



    public NumberGenerator(int min, int max) {
        this.min = min;
        this.max = max;
        this.selectedNumber =0;
        this.numbers =  new ArrayList<>();
        this.history =  new ArrayList<>();
        this.numbersMap =  new TreeMap<>();
        this.numbersMapBackup =  new TreeMap<>();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            numbers.clear();
//            numbers = IntStream.rangeClosed(min, max).boxed().collect(Collectors.toList());
//
//        }else{
            this.numbers.clear();
            this.numbersMap.clear();
            for (int i = min; i <= max ; i++) {
                this.numbers.add(i);
                this.numbersMap.put(i,false);

           // }
        }
        this.numbersMapBackup = this.numbersMap;
    }

    public void reset(){

        this.numbers.clear();
       this.numbersMap.clear();
        this.history.clear();
        for (int i = min; i <= max ; i++) {
            this.numbers.add(i);
           this.numbersMap.put(i,false);

        }



    }
    public int generateNumber(){

        int outputNumber = getRandomNumber(min,numbers.size());
        if(numbers.size()>0) {
            selectedNumber = numbers.get(outputNumber-1);
            history.add(selectedNumber);
            numbers.remove(outputNumber-1);
        }else{
            selectedNumber = -1;
        }

        return selectedNumber;
    }



    public int getRandomNumber(int min, int max) {

        return min+(int)(Math.random() * ((max-min)+1));
    }
}
