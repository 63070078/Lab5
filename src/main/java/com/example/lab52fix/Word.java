package com.example.lab52fix;

import java.util.ArrayList;

public class Word {
    public ArrayList<String> badWords = new ArrayList<>();
    public ArrayList<String> goodWords = new ArrayList<>();
    public Word(){
        this.goodWords.add("happy");
        this.goodWords.add("enjoy");
        this.goodWords.add("life");
        this.badWords.add("fuck");
        this.badWords.add("olo");
    }
}
