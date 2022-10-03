package com.example.lab52fix;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class WordPublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    protected Word words = new Word();
//    @RequestMapping(value = "/addBad/{word}", method = RequestMethod.GET)
//    public ArrayList<String> addBadWord(@PathVariable("word") String s){
//        words.badWords.add(s);
//        return words.badWords;
//    }
    @RequestMapping(value = "/addBad/{word}", method = RequestMethod.POST)
    public ArrayList<String> addBadWord(@PathVariable("word") String s){
        words.badWords.add(s);
        return words.badWords;
    }
    @RequestMapping(value = "/delBad/{word}", method = RequestMethod.GET)
    public ArrayList<String> deleteBadWord(@PathVariable("word") String s){
        for(int i = 0; i < words.badWords.size(); i++) {
            if(words.badWords.get(i).equals(s)){
                words.badWords.remove(i);
                break;
            }
        }
        return words.badWords;
    }
//    @RequestMapping(value = "/addGood/{word}", method = RequestMethod.GET)
//    public ArrayList<String> addGoodWord(@PathVariable("word") String s){
//        words.goodWords.add(s);
//        return words.goodWords;
//    }
    @RequestMapping(value = "/addGood/{word}", method = RequestMethod.POST)
    public ArrayList<String> addGoodWord(@PathVariable("word") String s){
        words.goodWords.add(s);
        return words.goodWords;
    }
    @RequestMapping(value = "/delGood/{word}", method = RequestMethod.GET)
    public ArrayList<String> deleteGoodWord(@PathVariable("word") String s){
        for(int i = 0; i < words.goodWords.size(); i++) {
            if(words.goodWords.get(i).equals(s)){
                words.goodWords.remove(i);
                break;
            }
        }
        return words.goodWords;
    }
//    @RequestMapping(value = "/proof/{sentence}", method = RequestMethod.GET)
//    public void proofSentence(@PathVariable("sentence") String s){
//        boolean good = false;
//        boolean bad = false;
//        for(int i = 0; i < words.goodWords.size(); i++) {
//            if(s.contains(words.goodWords.get(i))){
//                good = true;
//            }
//        }
//        for(int i = 0; i < words.badWords.size(); i++) {
//            if(s.contains(words.badWords.get(i))){
//                bad = true;
//            }
//        }
//        if(bad && good){
//            System.out.println("Have Both");
//            rabbitTemplate.convertAndSend("Fanout", "", s);
//        }
//        else if(good){
//            System.out.println("Good");
//            rabbitTemplate.convertAndSend("Direct", "good", s);
//        }
//        else if(bad){
//            System.out.println("Bad");
//            rabbitTemplate.convertAndSend("Direct", "bad", s);
//        }
//    }
@RequestMapping(value = "/proof/{sentence}", method = RequestMethod.POST)
public String proofSentence(@PathVariable("sentence") String s){
    boolean good = false;
    boolean bad = false;
    for(int i = 0; i < words.goodWords.size(); i++) {
        if(s.contains(words.goodWords.get(i))){
            good = true;
        }
    }
    for(int i = 0; i < words.badWords.size(); i++) {
        if(s.contains(words.badWords.get(i))){
            bad = true;
        }
    }
    if(bad && good){
        rabbitTemplate.convertAndSend("Fanout", "", s);
        return "Both";
    }
    else if(good){
        System.out.println("Good");
        rabbitTemplate.convertAndSend("Direct", "good", s);
        return "Good";
    }
    else if(bad){
        System.out.println("Bad");
        rabbitTemplate.convertAndSend("Direct", "bad", s);
        return "Bad";
    }
    else {
        return "No";
    }
}
    @RequestMapping(value = "/getSentence", method = RequestMethod.GET)
    public Sentence getSentences(){
        Object str = rabbitTemplate.convertSendAndReceive("Direct", "key", "");
        return ((Sentence) str);
    }

}
