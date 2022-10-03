package com.example.lab52fix;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.swing.*;
import java.util.ArrayList;

@Route(value = "index2")
public class MyView2 extends HorizontalLayout {
    private Notification n1;
    private TextField txt1, txt2, txt4, txt5;
    private Label l1;
    private Button btn1, btn2, btn3, btn4;
    private HorizontalLayout h1;
    private VerticalLayout v1, v2;
    public MyView2(){
        h1 = new HorizontalLayout();
        v1 = new VerticalLayout();
        v2 = new VerticalLayout();
        txt1 = new TextField();
        txt1.setLabel("Add Word");
        ComboBox<ArrayList> c1 = new ComboBox<>();
        ComboBox<ArrayList> c2 = new ComboBox<>();
        c1.setLabel("Good Words");
        c2.setLabel("Bad Words");
        btn1 = new Button("Add Good Word");
        btn2 = new Button("Add Bad Word");
        n1 = new Notification();
        txt2 = new TextField();
        txt2.setLabel("Add Sentence");
        btn3 = new Button("Add Sentence");
        txt4 = new TextField();
        txt4.setLabel("Good Sentences");
        txt5 = new TextField();
        txt5.setLabel("Bad Sentences");
        btn4 = new Button("Show Sentences");

        v1.add(txt1, btn1, btn2, c1, c2, n1);
        v2.add(txt2, btn3, txt4, txt5, btn4);
        this.add(v1, v2);

        btn1.addClickListener(event ->{
            String word = txt1.getValue();
            ArrayList out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addGood/"+word)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            n1 = Notification.show("insert "+txt1.getValue()+" to Good Word Lists Complete.");
            c1.setItems(out);
        });
        btn2.addClickListener(event ->{
            String word = txt1.getValue();
            ArrayList out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addBad/"+word)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            n1 = Notification.show("insert "+txt1.getValue()+" to Bad Word Lists Complete.");
            c2.setItems(out);
        });
        btn3.addClickListener(event ->{
            String sentence = txt2.getValue();
            String out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/proof/"+sentence)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            n1 = Notification.show("Found "+out+" Word");
        });
        btn4.addClickListener(event ->{
           Sentence out = WebClient.create()
                   .get()
                   .uri("http://localhost:8080/getSentence")
                   .retrieve()
                   .bodyToMono(Sentence.class)
                   .block();
           txt4.setValue(String.valueOf(out.goodSentences));
           txt5.setValue(String.valueOf(out.badSentences));
        });
    }
}
