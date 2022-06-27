package com.mirzahadzic.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.mirzahadzic.quizapp.databinding.ActivityMainBinding;
import com.mirzahadzic.quizapp.model.Question;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;

    private Question[] questionBank = new Question[]{
            new Question(R.string.question_amendments,false),
            new Question(R.string.question_constitution,true),
            new Question(R.string.question_declaration,true),
            new Question(R.string.question_independence_rights,true),
            new Question(R.string.question_religion,true),
            new Question(R.string.question_government,false),
            new Question(R.string.question_government_feds,false),
            new Question(R.string.question_government_senators,true),
            //Dodaj jos po zelji
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.questionTextView.setText(questionBank[currentQuestionIndex].getAnswerResId());
       //Next button
        binding.nextButton.setOnClickListener(view -> {
                //MODUO omogucava da iteracija kroz array sa pitanjima ne ode out of bounds
                currentQuestionIndex = (currentQuestionIndex + 1) % questionBank.length;
                updateQuestion(); //poziv metoda

        });

        //Prev button
        binding.prevButton.setOnClickListener(view ->{
            //Moduo iteracija u dekrementu ce raditi samo ako je indeks pitanja veci od 0
            if (currentQuestionIndex > 0){
                currentQuestionIndex=(currentQuestionIndex-1)%questionBank.length;
                updateQuestion();
            }
        });

        //True button

        binding.trueButton.setOnClickListener(view -> {
            checkAnswer(true);
        });

        //False button
        binding.falseButton.setOnClickListener(view -> {
            checkAnswer(false);
        });





    }
    //Metod koji dohvata objekat iz polja objekata sa pitanjima po id-u
    private void updateQuestion() {
        binding.questionTextView.setText(questionBank[currentQuestionIndex].getAnswerResId());
    }

    //Metod koji provjerava tacnost odgovora
    private void checkAnswer(boolean userChoseCorrect){
        boolean answerIsCorrect = questionBank[currentQuestionIndex].isAnswerTrue();
        int messageId;
        if(answerIsCorrect==userChoseCorrect){
            messageId=R.string.correct_answer;
        }else{
            messageId=R.string.wrong_answer;
        }

        Snackbar.make(binding.imageView, messageId,Snackbar.LENGTH_LONG).show();

    }
}