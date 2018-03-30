package com.adrianraff.quizapp;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
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
    // For layout changes
    private View multi, many, free, intro, end;

    //Radio group for answers
    RadioGroup radioAnswer;

    //Used to move through question array index
    public int questionIndex;

    public String questionType, theQuestion, answerA, answerB, answerC, answerD, theAnswer, theAnswer2, theAnswer3, theAnswer4, totalCorrectAnswers;


    public String[] getQuestions;

    //Used to record stored answers
    public String answerKeeperArray[][];

    // Checkbox answers
    public CheckBox answerCheck1;
    public CheckBox answerCheck2;
    public CheckBox answerCheck3;
    public CheckBox answerCheck4;

    // Radio button for multi choice answers
    RadioButton answerRadio1;
    RadioButton answerRadio2;
    RadioButton answerRadio3;
    RadioButton answerRadio4;

    // used to grab total questions
    public int arrayLengthQuestions;

    // Used to get answer from user in free answer questions.
    EditText selectedFreeAnswer;

    public int totalScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Init all the layouts and inflate them.
        multi = getLayoutInflater().inflate(R.layout.activity_multi_choice, null);
        many = getLayoutInflater().inflate(R.layout.activity_one_or_more, null);
        free = getLayoutInflater().inflate(R.layout.activity_free_text_response, null);

        end = getLayoutInflater().inflate(R.layout.activity_end, null);

        intro = getLayoutInflater().inflate(R.layout.activity_intro, null);

        setContentView(intro);
        userName = findViewById(R.id.editText_user_name);
        Resources res = getResources();
        radioAnswer = findViewById(R.id.radio_answers);

        // used to page through the questions
        questionIndex = 0;
        // used to retrieve the questions from the string array
        getQuestions = res.getStringArray(R.array.testQuestionArray);

        // determine the total amount of questions based on the array length
        arrayLengthQuestions = getQuestions.length;

        // array to store answers the user selected
        answerKeeperArray = new String[arrayLengthQuestions][4];


        // init checkboxes for answers in the more than one answer section

        // init radio buttons for multi choice section

        // init edittext for free answer question

    }


    public void startApp(View view) {


        String enteredName = userName.getText().toString();
        String message = "Thank you" + enteredName + ". Get ready to begin!";
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        selectQuestion();


    }

    public void selectQuestion() {

        // Grab a question from the string array
        StringTokenizer splitString = new StringTokenizer((getQuestions[questionIndex]), ":");


        // Parse the array into some string variables with the tokenizer
        questionType = splitString.nextToken();
        theQuestion = splitString.nextToken();
        answerA = splitString.nextToken();
        answerB = splitString.nextToken();
        answerC = splitString.nextToken();
        answerD = splitString.nextToken();
        theAnswer = splitString.nextToken();
        theAnswer2 = splitString.nextToken();
        theAnswer3 = splitString.nextToken();
        theAnswer4 = splitString.nextToken();
        totalCorrectAnswers = splitString.nextToken();

        // Log the question type for dignostics.
        Log.v("IntroActivity", questionType);


        // Check to see which type of question is being selected from the question string array and call the proper method to display it.
        switch (questionType) {
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
                // Error catching
                Toast.makeText(this, "Uh oh! Something went wrong", Toast.LENGTH_SHORT).show();
                Log.v("IntroActivity", "Some shit went south here.");
        }
    }

    /**
     * A multiple choice question with only one possible answer was selected.
     *
     * @param view      controls which layout is loaded
     * @param question  loads the question into the view
     * @param answerA   loads an answer choice into view
     * @param answerB   loads an answer choice into view
     * @param answerC   loads an answer choice into view
     * @param answerD   loads an answer choice into view
     * @param theAnswer loads an answer choice into view
     */

    public void selectMulti(View view, String question, String answerA, String answerB, String answerC, String answerD, String theAnswer) {

        // Set content view to multi layout
        setContentView(view);
        clearSelections();

        // Set the values of the UI text to the string variable values. String theQuestionOne contains the question type.

        TextView questionTextView = findViewById(textView_question_multi);

        questionTextView.setText(question);


        answerRadio1 = findViewById(radioButton_answer_multi_1);
        answerRadio2 = findViewById(radioButton_answer_multi_2);
        answerRadio3 = findViewById(radioButton_answer_multi_3);
        answerRadio4 = findViewById(radioButton_answer_multi_4);

        answerRadio1.setText(answerA);
        answerRadio2.setText(answerB);
        answerRadio3.setText(answerC);
        answerRadio4.setText(answerD);


    }

    /**
     * A question with many answers is selected. Passed arguments are the questions and answers to set the text of the layout.
     *
     * @param view      controls the loaded view
     * @param question  loads the question into the view
     * @param answerA   loads an answer choice into view
     * @param answerB   loads an answer choice into view
     * @param answerC   loads an answer choice into view
     * @param answerD   loads an answer choice into view
     * @param theAnswer loads the answer for comparison
     */

    public void selectMany(View view, String question, String answerA, String answerB, String answerC, String answerD, String theAnswer, String theAnswer2, String theAnswer3, String theAnswer4, String totalCorrectAnswers) {

        // Set content view to many layout
        setContentView(view);
        clearSelections();

        // Set the values of the UI text to the string variable values. String theQuestionOne contains the question type.

        TextView questionTextView = findViewById(textView_question_one_or_more);

        questionTextView.setText(question);


        answerCheck1 = findViewById(checkbox_answer_one_or_more_1);
        answerCheck2 = findViewById(checkbox_answer_one_or_more_2);
        answerCheck3 = findViewById(checkbox_answer_one_or_more_3);
        answerCheck4 = findViewById(checkbox_answer_one_or_more_4);


        answerCheck1.setText(answerA);
        answerCheck2.setText(answerB);
        answerCheck3.setText(answerC);
        answerCheck4.setText(answerD);


    }


    public void selectFree(View view, String question, String theAnswer) {

        setContentView(view);
        clearSelections();

        TextView questionTextView = findViewById(textView_free_question);
        questionTextView.setText(question);

    }

    /**
     * Answer checking area. Pass the question type to select
     */


    public void submitAnswer(View view) {


        switch (questionType) {

            // If a multiple choice question is selected start here.
            case "MULTI":
                RadioGroup rg = findViewById(R.id.radio_answers);

                String selectedMultiAnswer = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                Toast.makeText(this, "Your answer was " + selectedMultiAnswer, Toast.LENGTH_SHORT).show();

                //If the selected answer matches the stored correct answer from our array of questions, increase the total score counter
                // and record the answer in the answer keeper array
                if (selectedMultiAnswer.equals(theAnswer)) {
                    Toast.makeText(this, "The answer was correct. " + theAnswer, Toast.LENGTH_LONG).show();
                    answerKeeperArray[questionIndex][0] = selectedMultiAnswer;
                    totalScore = totalScore + 1;
                } else {
                    Toast.makeText(this, "WRONG! The answer was " + theAnswer, Toast.LENGTH_LONG).show();
                }
                break;

            // A question with more than one possible answer will start here.
            case "MANY":
                int manyCorrectAnswerKeeper = 0;
                if (answerCheck1.isChecked() && answerCheck1.getText().toString().equals(theAnswer)) {
                    answerKeeperArray[questionIndex][0] = theAnswer;
                    Toast.makeText(this, "Answer A was correct", Toast.LENGTH_SHORT).show();
                    manyCorrectAnswerKeeper++;
                }
                if (answerCheck2.isChecked() && answerCheck2.getText().toString().equals(theAnswer2)) {
                    answerKeeperArray[questionIndex][1] = theAnswer2;
                    Toast.makeText(this, "Answer B was corrrect", Toast.LENGTH_SHORT).show();
                    manyCorrectAnswerKeeper++;
                }
                if (answerCheck3.isChecked() && answerCheck3.getText().toString().equals(theAnswer3)) {
                    answerKeeperArray[questionIndex][2] = theAnswer3;
                    Toast.makeText(this, "Answer C  was correct", Toast.LENGTH_SHORT).show();
                    manyCorrectAnswerKeeper++;
                }

                if (answerCheck4.isChecked() && answerCheck4.getText().toString().equals(theAnswer4)) {
                    answerKeeperArray[questionIndex][3] = theAnswer4;
                    Toast.makeText(this, "Answer D was correct", Toast.LENGTH_SHORT).show();
                    manyCorrectAnswerKeeper++;
                }

                //check to see if the total correct answers have been met
                int castStringTotalCorrectAnswers = Integer.parseInt(totalCorrectAnswers);
                if (manyCorrectAnswerKeeper == castStringTotalCorrectAnswers) {
                    Toast.makeText(this, "You got all the answers correct", Toast.LENGTH_SHORT).show();
                    totalScore = totalScore + 1;
                } else {
                    Toast.makeText(this, "Opps there is an incorrect answer still", Toast.LENGTH_SHORT).show();
                }
                break;

            // A fill in the blank or free style question starts here.
            case "FREE":

                selectedFreeAnswer = findViewById(R.id.editText_free_answer_one);
                //Check to see if the edit text box matches the answer. Convert everything to lowercase for ease of comparison.
                if (selectedFreeAnswer.getText().toString().toLowerCase().equals(theAnswer.toLowerCase())) {
                    Toast.makeText(this, "The answer was correct", Toast.LENGTH_LONG).show();
                    Log.v("Selected answer", "The answer was correct");
                    answerKeeperArray[questionIndex][0] = selectedFreeAnswer.getText().toString();
                    totalScore = totalScore + 1;
                } else {
                    Toast.makeText(this, "The answer was wrong!", Toast.LENGTH_LONG).show();
                    Log.v("Selected answer", "WRONG DUMBASS!");
                }
                break;
            default:
                Toast.makeText(this, "Uh oh! Something went wrong", Toast.LENGTH_SHORT).show();
                Log.v("IntroActivity", "Some shit went south here.");

        }
        increment(view);

    }


    /**
     * Increase the questionIndex to select a question based on the index number from the array.xml file
     */
    public void increment(View view) {
        //Check to see if the total questions answered have met the total length of the string array storing questions. If so, end the quiz by calling the endQuiz() method.
        questionIndex = questionIndex + 1;

        if (questionIndex == arrayLengthQuestions) {
            endQuiz(end);
        } else

        {
            selectQuestion();
        }
    }


    /**
     * Decrease the question index to go to the previous questions.
     */
    public void decrement(View view) {
        if (questionIndex <= 0) {
            // In case the index falls below zero display an error message.
            Toast.makeText(this, "Sorry there are no more questions prior to this one!", Toast.LENGTH_SHORT).show();

        } else {
            // Move backwards through the question index.
            questionIndex = questionIndex - 1;
            selectQuestion();
        }
    }

    /**
     * End of quiz. Total the scores and grade the quiz results then give the user an option to email the results to a pre set mailbox.
     */
    public void endQuiz(View view) {
        Toast.makeText(this, "You finished the quiz!", Toast.LENGTH_SHORT).show();
        setContentView(view);
        TextView endQuizStats = findViewById(R.id.textView_end_totals);
        String message;
        message = "Congratulations " + userName.getText().toString() + "!" + " You made it to the end!" + "\n" + "The total amount of questions you got right were " + totalScore + " out of " + arrayLengthQuestions + "\n" + "The answers you selected were " + Arrays.deepToString(answerKeeperArray);
        endQuizStats.setText(message);
    }


    /**
     * Clear all selections from view. Call this before loading a question.
     */

    public void clearSelections() {

        RadioGroup rg = findViewById(R.id.radio_answers);
        if (rg != null) {
            rg.clearCheck();
        }

        if (answerCheck1 != null) {
            answerCheck1.setChecked(false);
        }

        if (answerCheck2 != null) {
            answerCheck2.setChecked(false);
        }
        if (answerCheck3 != null) {
            answerCheck3.setChecked(false);
        }

        if (answerCheck4 != null) {
            answerCheck4.setChecked(false);
        }

        if (selectedFreeAnswer != null) {
            selectedFreeAnswer.setText("");
        }
    }


}




