package com.adrianraff.quizapp;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;
import java.util.StringTokenizer;

import static com.adrianraff.quizapp.R.id.checkbox_answer_one_or_more_1;
import static com.adrianraff.quizapp.R.id.checkbox_answer_one_or_more_2;
import static com.adrianraff.quizapp.R.id.checkbox_answer_one_or_more_3;
import static com.adrianraff.quizapp.R.id.checkbox_answer_one_or_more_4;
import static com.adrianraff.quizapp.R.id.radioButton_answer_multi_1;
import static com.adrianraff.quizapp.R.id.radioButton_answer_multi_2;
import static com.adrianraff.quizapp.R.id.radioButton_answer_multi_3;
import static com.adrianraff.quizapp.R.id.radioButton_answer_multi_4;
import static com.adrianraff.quizapp.R.id.textView_free_question;
import static com.adrianraff.quizapp.R.id.textView_question_multi;
import static com.adrianraff.quizapp.R.id.textView_question_one_or_more;


public class IntroActivity extends AppCompatActivity {


    /**
     * Set up variables to be used app wide starting with user name, score, total right and total wrong answers
     */

    // For user name
    private EditText userName;
    // For question string array
    private Resources res;
    // For layout changes
    private View multi, many, free, intro;
    // For user answer
    private String userAnswer;
    //Radio group for answers
    RadioGroup radioAnswer;
    public int questionIndex;

    String questionType, theQuestion, answerA, answerB, answerC, answerD, theAnswer, theAnswer2, theAnswer3, theAnswer4, totalCorrectAnswers;


    public String[] getQuestions;

    public String answerKeeperArray[][];

    // Checkbox answers
    public CheckBox answerCheck1;
    public CheckBox answerCheck2;
    public CheckBox answerCheck3;
    public CheckBox answerCheck4;


    public int arrayLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Init all the layouts and inflate them.
        multi = getLayoutInflater().inflate(R.layout.activity_multi_choice, null);
        many = getLayoutInflater().inflate(R.layout.activity_one_or_more, null);
        free = getLayoutInflater().inflate(R.layout.activity_free_text_response, null);

        intro = getLayoutInflater().inflate(R.layout.activity_intro, null);

        setContentView(intro);
        userName = findViewById(R.id.editText_user_name);
        res = getResources();
        radioAnswer = (RadioGroup) findViewById(R.id.radio_answers);

        // used to page through the questions
        questionIndex = 0;
        // used to retrieve the questions from the string array
        getQuestions = res.getStringArray(R.array.testQuestionArray);

        // determine the total amount of questions based on the array length
        arrayLength = getQuestions.length;

        // array to store answers the user selected
        answerKeeperArray = new String[arrayLength][4];


    }


    public void startApp(View view) {


        String enteredName = userName.getText().toString();
        String message = "Thank you" + enteredName + ". Get ready to begin!";
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        selectQuestion();


    }

    public void selectQuestion() {
        // Grab a question from the string array

        if (questionIndex == arrayLength){
            endQuiz;
        }

        StringTokenizer splitString = new StringTokenizer((getQuestions[questionIndex]), ":");


        // Parse the array into some string variables with the tokenizer
        questionType = splitString.nextToken().toString();
        theQuestion = splitString.nextToken().toString();
        answerA = splitString.nextToken().toString();
        answerB = splitString.nextToken().toString();
        answerC = splitString.nextToken().toString();
        answerD = splitString.nextToken().toString();
        theAnswer = splitString.nextToken().toString();
        theAnswer2 = splitString.nextToken().toString();
        theAnswer3 = splitString.nextToken().toString();
        theAnswer4 = splitString.nextToken().toString();
        totalCorrectAnswers = splitString.nextToken().toString();

        Log.v("IntroActivity", questionType);

        switch (questionType.toString()) {
            case "MULTI":

                selectMulti(multi, theQuestion, answerA, answerB, answerC, answerD, theAnswer);
                break;

            case "MANY":

                selectMany(many, theQuestion, answerA, answerB, answerC, answerD, theAnswer, theAnswer2, theAnswer3, theAnswer4, totalCorrectAnswers);
                break;

            case "FREE":

                selectFree(free, theQuestion, theAnswer);

                break;
            default:
                Toast.makeText(this, "Uh oh! Something went wrong", Toast.LENGTH_SHORT).show();
                Log.v("IntroActivity", "Some shit went south here.");
        }
    }

    /**
     * A multiple choice question with only one possible answer was selected.
     *
     * @param view
     * @param question
     * @param answerA
     * @param answerB
     * @param answerC
     * @param answerD
     * @param theAnswer
     */

    public void selectMulti(View view, String question, String answerA, String answerB, String answerC, String answerD, String theAnswer) {

        // Set content view to multi layout
        setContentView(view);

        // Set the values of the UI text to the string variable values. String theQuestionOne contains the question type.

        TextView questionTextView = findViewById(textView_question_multi);

        questionTextView.setText(question);

        RadioButton answerRadio1 = findViewById(radioButton_answer_multi_1);
        answerRadio1.setText(answerA);

        RadioButton answerRadio2 = findViewById(radioButton_answer_multi_2);
        answerRadio2.setText(answerB);

        RadioButton answerRadio3 = findViewById(radioButton_answer_multi_3);
        answerRadio3.setText(answerC);

        RadioButton answerRadio4 = findViewById(radioButton_answer_multi_4);
        answerRadio4.setText(answerD);

        Toast.makeText(this, "The answer is" + theAnswer, Toast.LENGTH_LONG).show();
    }

    /**
     * A question with many answers is selected. Passed arguments are the questions and answers to set the text of the layout.
     *
     * @param view
     * @param question
     * @param answerA
     * @param answerB
     * @param answerC
     * @param answerD
     * @param theAnswer
     */

    public void selectMany(View view, String question, String answerA, String answerB, String answerC, String answerD, String theAnswer, String theAnswer2, String theAnswer3, String theAnswer4, String totalCorrectAnswers) {

        // Set content view to many layout
        setContentView(view);

        // Set the values of the UI text to the string variable values. String theQuestionOne contains the question type.

        TextView questionTextView = findViewById(textView_question_one_or_more);

        questionTextView.setText(question);

        answerCheck1 = findViewById(checkbox_answer_one_or_more_1);
        answerCheck1.setText(answerA);

        answerCheck2 = findViewById(checkbox_answer_one_or_more_2);
        answerCheck2.setText(answerB);

        answerCheck3 = findViewById(checkbox_answer_one_or_more_3);
        answerCheck3.setText(answerC);

        answerCheck4 = findViewById(checkbox_answer_one_or_more_4);
        answerCheck4.setText(answerD);

        Toast.makeText(this, "The answer is" + theAnswer, Toast.LENGTH_LONG).show();

    }


    public void selectFree(View view, String question, String theAnswer) {

        setContentView(view);

        TextView questionTextView = findViewById(textView_free_question);
        questionTextView.setText(question);
        Toast.makeText(this, "The answer is " + theAnswer, Toast.LENGTH_SHORT).show();
    }

    /**
     * Answer checking area
     */


    public void submitAnswer(View view) {


        switch (questionType) {
            case "MULTI":

                RadioGroup rg = (RadioGroup) findViewById(R.id.radio_answers);

                String selectedMultiAnswer = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                Toast.makeText(this, "Your answer was " + selectedMultiAnswer, Toast.LENGTH_LONG).show();

                if (selectedMultiAnswer.equals(theAnswer)) {
                    Log.v("Selected answer", "The answer was correct");
                    answerKeeperArray[questionIndex][0] = selectedMultiAnswer;
                } else {
                    Log.v("Selected answer", "WRONG DUMBASS!");
                }
                break;

            case "MANY":
                int manyCorrectAnswerKeeper = 0;
                if (answerCheck1.getText().toString().equals(theAnswer)) {
                    answerKeeperArray[questionIndex][0] = theAnswer;
                    manyCorrectAnswerKeeper++;
                }
                if (answerCheck2.getText().toString().equals(theAnswer2)) {
                    answerKeeperArray[questionIndex][1] = theAnswer2;
                    manyCorrectAnswerKeeper++;
                }
                if (answerCheck3.getText().toString().equals(theAnswer3)) {
                    answerKeeperArray[questionIndex][2] = theAnswer3;
                    manyCorrectAnswerKeeper++;
                }

                if (answerCheck4.getText().toString().equals(theAnswer4)) {
                    answerKeeperArray[questionIndex][3] = theAnswer4;
                    manyCorrectAnswerKeeper++;
                }

                //check to see if the total correct answers have been met
                int castStringTotalCorrectAnswers = Integer.parseInt(totalCorrectAnswers);
                if (manyCorrectAnswerKeeper == castStringTotalCorrectAnswers) {
                    Toast.makeText(this, "You got all the answers correct", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Opps there is an incorrect answer still", Toast.LENGTH_SHORT).show();
                }
                break;

            case "FREE":
                EditText selectedFreeAnswer = findViewById(R.id.editText_free_answer_one);

                if (selectedFreeAnswer.getText().toString().equals(theAnswer)) {
                    Log.v("Selected answer", "The answer was correct");
                    answerKeeperArray[questionIndex][0] = selectedFreeAnswer.getText().toString();
                } else {
                    Log.v("Selected answer", "WRONG DUMBASS!");
                }
                break;
            default:
                Toast.makeText(this, "Uh oh! Something went wrong", Toast.LENGTH_SHORT).show();
                Log.v("IntroActivity", "Some shit went south here.");

        }
    }


    /**
     * Increase the questionIndex to select a question based on the index number from the array.xml file
     *
     * @param view
     */
    public void increment(View view) {
        if (questionIndex >= 100) {

            Toast.makeText(this, "OPPS!", Toast.LENGTH_SHORT).show();

        } else {
            questionIndex = questionIndex + 1;
            selectQuestion();
        }
    }

    /**
     * Decrease the question index to go to the previous questions.
     *
     * @param view
     */
    public void decrement(View view) {
        if (questionIndex <= 0) {
            Toast.makeText(this, "SHIT FUCK!", Toast.LENGTH_SHORT).show();

        } else {
            questionIndex = questionIndex - 1;
            selectQuestion();
        }
    }

    public void endQuiz(){

        
    }

}




