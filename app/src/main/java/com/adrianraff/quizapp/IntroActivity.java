/*
 * Copyright (c) 2018.  Adrian Raff AKA Fr0stsp1re
 * ************PROJECT LICENSE*************
 *
 * This project was submitted by Adrian Raff as part of the  Android Basics Nanodegree At Udacity.
 *
 * The Udacity Honor code requires your submissions must be your own work.
 * Submitting this project as yours will cause you to break the Udacity Honor Code
 * and may result in disciplinary action.
 *
 * The author of this project allows you to check the code as a reference only. You may not submit this project or any part
 * of the code as your own.
 *
 * Besides the above notice, the following license applies and this license notice
 * must be included in all works derived from this project.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.adrianraff.quizapp;


import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


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

    public EditText userName;

    // For email to send results

    public EditText emailAddress;
    public String emailTarget;


    // For layout changes. These designate the layout based on question type.

    private ViewGroup multi;
    private ViewGroup many;
    private ViewGroup free;
    private ViewGroup end;
    private ViewGroup intro;


    //Used to move through question array index. This variable plays a key role in the stack and acts as the index number for the array.

    public int questionIndex;

    // String variables to hold the questions and answers stored in the arrays.xml

    public String questionType, theQuestion, answerA, answerB, answerC, answerD, theAnswer, theAnswer2, theAnswer3, theAnswer4, totalCorrectAnswers;

    // Used for parsing the questions from the arrays.xml

    public String[] getQuestions;

    //Used to record stored answers

    public String answerKeeperArray[][];

    // Checkboxes for mor than one answer type questions

    public CheckBox answerCheck1;
    public CheckBox answerCheck2;
    public CheckBox answerCheck3;
    public CheckBox answerCheck4;

    // Radio group for answers

    public RadioGroup radioAnswer;


    // Radio button for multi choice answers

    public RadioButton answerRadio1;
    public RadioButton answerRadio2;
    public RadioButton answerRadio3;
    public RadioButton answerRadio4;

    // used to grab total questions stored in the string array contained in arrays.xml

    public int arrayLengthQuestions;

    // Used to get answer from user in free answer questions.

    public EditText selectedFreeAnswer;

    // Total correct answer keeper init to zero to start

    public int totalScore = 0;

    // Used for email summary

    public String emailSummary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Init all the layouts as viewgroups

        multi = findViewById(R.id.activity_multi_choice);
        many = findViewById(R.id.activity_one_or_more);
        free = findViewById(R.id.activity_free_text);
        end = findViewById(R.id.activity_end);
        intro = findViewById(R.id.activity_intro);

        //Set initial visibility of viewgroups

        intro.setVisibility(View.VISIBLE);
        multi.setVisibility(View.INVISIBLE);
        end.setVisibility(View.INVISIBLE);
        many.setVisibility(View.INVISIBLE);
        free.setVisibility(View.INVISIBLE);


        // used to get user name

        userName = findViewById(R.id.editText_user_name);

        // used to grab email address from user

        emailAddress = findViewById(R.id.editText_email_address);


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


    }

    /**
     * The application starting point. Grab name and email address from user then select a question from the pool.
     */

    public void startApp(View view) {


        String enteredName = userName.getText().toString();
        emailTarget = emailAddress.getText().toString();
        String message = getString(R.string.startApp_thank_you) + enteredName + getString(R.string.startApp_get_ready);

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        // Clear focus and select the first question
        view.clearFocus();
        selectQuestion();


    }

    /**
     * Call this to select a question and load it into the correct view group
     */

    public void selectQuestion() {


        // Grab a question from the string array. Use "|" as a delimiter for our pseudo flat file database

        StringTokenizer splitString = new StringTokenizer((getQuestions[questionIndex]), "|");


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


        // Check to see which type of question is being selected from the question string array and call the proper method to display it.

        switch (questionType) {

            case "MULTI":

                selectMulti(multi, theQuestion, answerA, answerB, answerC, answerD);
                break;

            case "MANY":

                selectMany(many, theQuestion, answerA, answerB, answerC, answerD, theAnswer, theAnswer2, theAnswer3, theAnswer4, totalCorrectAnswers);
                break;

            case "FREE":

                selectFree(free, theQuestion);
                break;

            default:
                // Error catching
                Toast.makeText(this, R.string.error_something_wrong, Toast.LENGTH_SHORT).show();
                Log.v("IntroActivity", "Some shit went south here.");
        }

    }

    /**
     * A multiple choice question with only one possible answer was selected.
     *
     * @param view     controls which layout is loaded
     * @param question loads the question into the view
     * @param answerA  loads an answer choice into view
     * @param answerB  loads an answer choice into view
     * @param answerC  loads an answer choice into view
     * @param answerD  loads an answer choice into view
     */

    public void selectMulti(View view, String question, String answerA, String answerB, String answerC, String answerD) {


        // Set content view to multi layout

        intro.setVisibility(View.INVISIBLE);
        multi.setVisibility(View.VISIBLE);
        end.setVisibility(View.INVISIBLE);
        many.setVisibility(View.INVISIBLE);
        free.setVisibility(View.INVISIBLE);

        // Clear all the views from user selections

        clearSelections(view);

        // Set the values of the UI text to the string variable values. String theQuestionOne contains the question type.

        TextView questionTextView = findViewById(textView_question_multi);
        questionTextView.setText(question);


        answerRadio1 = findViewById(radioButton_answer_multi_1);
        answerRadio2 = findViewById(radioButton_answer_multi_2);
        answerRadio3 = findViewById(radioButton_answer_multi_3);
        answerRadio4 = findViewById(radioButton_answer_multi_4);

        // Check if answer array has a stored answer if so check the box

        if(answerKeeperArray[questionIndex][0] != null) {

            if (answerKeeperArray[questionIndex][0].equals(answerA)) {
                answerRadio1.setChecked(true);
            } else {
                answerRadio1.setChecked(false);
            }

            if (answerKeeperArray[questionIndex][0].equals(answerB)) {
                answerRadio2.setChecked(true);
            } else {
                answerRadio2.setChecked(false);
            }

            if (answerKeeperArray[questionIndex][0].equals(answerC)) {
                answerRadio3.setChecked(true);
            } else {
                answerRadio3.setChecked(false);
            }

            if (answerKeeperArray[questionIndex][0].equals(answerD)){
                answerRadio4.setChecked(true);

            } else {
                answerRadio4.setChecked(false);
            }
        }

        answerRadio1.setText(answerA);
        answerRadio2.setText(answerB);
        answerRadio3.setText(answerC);
        answerRadio4.setText(answerD);

        view.clearFocus();
    }

    /**
     * A question with many answers is selected. Passed arguments are the questions and answers to set the text of the layout.
     */

    public void selectMany(View view, String question, String answerA, String answerB, String answerC, String answerD, String theAnswer, String theAnswer2, String theAnswer3, String theAnswer4, String totalCorrectAnswers) {


        // Set content view to many layout

        intro.setVisibility(View.GONE);
        end.setVisibility(View.GONE);
        many.setVisibility(View.VISIBLE);
        free.setVisibility(View.GONE);
        multi.setVisibility(View.GONE);
        clearSelections(view);

        // Set the values of the UI text to the string variable values. String theQuestionOne contains the question type.

        TextView questionTextView = findViewById(textView_question_one_or_more);

        questionTextView.setText(question);


        answerCheck1 = findViewById(checkbox_answer_one_or_more_1);
        answerCheck2 = findViewById(checkbox_answer_one_or_more_2);
        answerCheck3 = findViewById(checkbox_answer_one_or_more_3);
        answerCheck4 = findViewById(checkbox_answer_one_or_more_4);

        // Check if answer array has a stored answer if so check the box

        if(answerKeeperArray[questionIndex][0] != null) {
          answerCheck1.setChecked(true);
        }

        if(answerKeeperArray[questionIndex][1] != null) {
            answerCheck2.setChecked(true);
        }

        if(answerKeeperArray[questionIndex][2] != null) {
            answerCheck3.setChecked(true);
        }

        if(answerKeeperArray[questionIndex][3] != null) {
            answerCheck4.setChecked(true);
        }



        // Load the checkboxes with possible answers to the question

        answerCheck1.setText(answerA);
        answerCheck2.setText(answerB);
        answerCheck3.setText(answerC);
        answerCheck4.setText(answerD);


        view.clearFocus();

    }

    /**
     * Select a free answer question.
     *
     * @param view     ~
     * @param question Is the question being asked.
     */

    public void selectFree(View view, String question) {


        // Set our layout views to have the proper one visible.

        intro.setVisibility(View.GONE);
        end.setVisibility(View.GONE);
        many.setVisibility(View.GONE);
        free.setVisibility(View.VISIBLE);
        multi.setVisibility(View.GONE);
        clearSelections(view);

        // Check the  answer keeper array to see if anything is stored in there. If so set the answer field of the question to
        // the answer stored in the array.

        if(answerKeeperArray[questionIndex][0] != null) {
            selectedFreeAnswer = findViewById(R.id.editText_free_answer_one);
            selectedFreeAnswer.setText(answerKeeperArray[questionIndex][0]);
        }

        // Set the textview with the question to be asked.

        TextView questionTextView = findViewById(textView_free_question);
        questionTextView.setText(question);

        view.clearFocus();

    }

    /**
     * Answer checking area. Pass the question type to select
     */


    public void submitAnswer(View view) {

        // Look at the question type tag from the string array that stores questions and select the proper case
        switch (questionType) {

            // If a multiple choice question is selected start here.
            case "MULTI":

                // Check to see if any radio button is selected. If none then error message,
                RadioGroup rg = findViewById(R.id.radio_answers);
                if (rg.getCheckedRadioButtonId() == -1) {

                    Toast.makeText(this, R.string.error_please_select_answer, Toast.LENGTH_SHORT).show();
                    return;
                }

                String selectedMultiAnswer = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();


                //If the selected answer matches the stored correct answer from our array of questions, increase the total score counter
                // and record the answer in the answer keeper array
                if (selectedMultiAnswer.equals(theAnswer)) {

                    answerKeeperArray[questionIndex][0] = selectedMultiAnswer;
                    totalScore = totalScore + 1;

                } else {

                    //log the answer to the array even if it is wrong
                    answerKeeperArray[questionIndex][0] = selectedMultiAnswer;

                }
                break;

            // A question with more than one possible answer will start here.
            case "MANY":
                int manyCorrectAnswerKeeper = 0;
                if (answerCheck1.isChecked() && answerCheck1.getText().toString().equals(theAnswer)) {
                    // log answer into array
                    answerKeeperArray[questionIndex][0] = theAnswer;

                    manyCorrectAnswerKeeper++;
                }

                if (answerCheck1.isChecked()) {
                    // log answer into array
                    answerKeeperArray[questionIndex][0] = theAnswer;
                }


                if (answerCheck2.isChecked() && answerCheck2.getText().toString().equals(theAnswer2)) {
                    //log answer into array
                    answerKeeperArray[questionIndex][1] = theAnswer2;

                    manyCorrectAnswerKeeper++;
                }

                if (answerCheck2.isChecked()) {
                    // log answer into array
                    answerKeeperArray[questionIndex][1] = theAnswer2;

                }


                if (answerCheck3.isChecked() && answerCheck3.getText().toString().equals(theAnswer3)) {
                    //log answer into array
                    answerKeeperArray[questionIndex][2] = theAnswer3;

                    manyCorrectAnswerKeeper++;
                }

                if (answerCheck3.isChecked()) {
                    // log answer into array
                    answerKeeperArray[questionIndex][2] = theAnswer3;
                }


                if (answerCheck4.isChecked() && answerCheck4.getText().toString().equals(theAnswer4)) {
                    //log answer into array
                    answerKeeperArray[questionIndex][3] = theAnswer4;

                    manyCorrectAnswerKeeper++;
                }

                if (answerCheck4.isChecked()) {
                    // log answer into array
                    answerKeeperArray[questionIndex][3] = theAnswer4;
                }


                //check to see if the total correct answers have been met. Since the total is stored in a string
                // we must cast the total to an integer to compare.
                int castStringTotalCorrectAnswers = Integer.parseInt(totalCorrectAnswers);
                if (manyCorrectAnswerKeeper == castStringTotalCorrectAnswers) {

                    totalScore = totalScore + 1;
                } else {

                }
                break;

            // A fill in the blank or free style question starts here.
            case "FREE":

                selectedFreeAnswer = findViewById(R.id.editText_free_answer_one);
                //Check to see if the edit text box matches the answer. Convert everything to lowercase for ease of comparison. Trim off the whitespace at end if need be.
                if (selectedFreeAnswer.getText().toString().toLowerCase().trim().equals(theAnswer.toLowerCase())) {


                    answerKeeperArray[questionIndex][0] = selectedFreeAnswer.getText().toString();
                    totalScore = totalScore + 1;
                } else {

                    answerKeeperArray[questionIndex][0] = selectedFreeAnswer.getText().toString();

                }
                break;

            default:
                Toast.makeText(this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();

        }

        // Clear focus and increment to next question
        view.clearFocus();
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
            view.clearFocus();
            selectQuestion();
        }
    }


    /**
     * Decrease the question index to go to the previous questions.
     */

    public void decrement(View view) {


        if (questionIndex <= 0) {
            // In case the index falls below zero display an error message.
            Toast.makeText(this, R.string.error_no_prior_questions, Toast.LENGTH_SHORT).show();

        } else {
            // Move backwards through the question index.
            questionIndex = questionIndex - 1;
            view.clearFocus();
            selectQuestion();
        }
    }

    /**
     * End of quiz. Total the scores and grade the quiz results then give the user an option to email the results to a pre set mailbox.
     */


    public void endQuiz(View view) {


        intro.setVisibility(View.GONE);
        end.setVisibility(View.VISIBLE);
        many.setVisibility(View.GONE);
        free.setVisibility(View.GONE);
        multi.setVisibility(View.GONE);

        StringTokenizer splitQuestions;


        //Build a string from the contents in the answer keeper array
        StringBuilder strBuilder = new StringBuilder();


        for (int i = 0; i < arrayLengthQuestions; i++) {
            splitQuestions = new StringTokenizer((getQuestions[i]), "|");

            // Parse the array into some string variables with the tokenizer
            questionType = splitQuestions.nextToken();
            theQuestion = splitQuestions.nextToken();
            answerA = splitQuestions.nextToken();
            answerB = splitQuestions.nextToken();
            answerC = splitQuestions.nextToken();
            answerD = splitQuestions.nextToken();
            theAnswer = splitQuestions.nextToken();
            theAnswer2 = splitQuestions.nextToken();
            theAnswer3 = splitQuestions.nextToken();
            theAnswer4 = splitQuestions.nextToken();
            totalCorrectAnswers = splitQuestions.nextToken();

            // Append the question to the final output string
            strBuilder.append("\n \n" + theQuestion + "\n");

            // Roll through the array and append the recorded answers
            strBuilder.append(getString(R.string.end_your_answers_were) + "\n");
            for (int j = 0; j < 4; j++) {
                if (answerKeeperArray[i][j] != null) {
                    strBuilder.append(answerKeeperArray[i][j] + "\n");
                }
            }

            // Roll through the array and append the correct answers
            strBuilder.append(getString(R.string.end_correct_answers_were) + "\n");

            //Check the string array for null answers. use "#" as a null field identifier only append the string if there is an additional correct answer.
            if (!theAnswer.equals("#")) {
                strBuilder.append(theAnswer + "\n");
            }
            if (!theAnswer2.equals("#")) {
                strBuilder.append(theAnswer2 + "\n");
            }
            if (!theAnswer3.equals("#")) {
                strBuilder.append(theAnswer3 + "\n");

            }
            if (!theAnswer4.equals("#")) {
                strBuilder.append(theAnswer4 + "\n \n");

            }


        }


        // Cast the string builder to a string and us it in the final message string
        String reviewMessage = strBuilder.toString();


        TextView endQuizReview = findViewById(R.id.textView_end_totals);
        TextView endQuizScore = findViewById(R.id.textView_score);
        TextView endQuizGrade = findViewById(R.id.textView_grade);

        // Calculate percentage of score and grade it

        String grade;
        String messageScore;
        int percent = (totalScore * 100) / arrayLengthQuestions;
        // Check percent and grade
        if (percent < 0 || percent > 100) {
            grade = getString(R.string.error_out_of_bounds);
        } else if (percent <= 100 && percent >= 90) {
            grade = getString(R.string.end_grade_a);
        } else if (percent <= 90 && percent >= 80) {
            grade = getString(R.string.end_grade_b);
        } else if (percent <= 80 && percent >= 70) {
            grade = getString(R.string.end_grade_c);
        } else if (percent <= 70 && percent >= 60) {
            grade = getString(R.string.end_grade_d);
        } else if (percent <= 60 && percent >= 0) {
            grade = getString(R.string.end_grade_f);
        } else {
            grade = getString(R.string.error_something_wrong);
        }

        // Form a message and display the results
        messageScore = getString(R.string.end_congrats) + userName.getText().toString() + getString(R.string.exclamation) + getString(R.string.end_you_made_it_to_the_end) + "\n" + getString(R.string.end_total_questions_righ) + totalScore + getString(R.string.end_out_of) + arrayLengthQuestions + "\n" + getString(R.string.end_that_is) + percent + getString(R.string.percent) + "\n \n \n";
        endQuizScore.setText(messageScore);
        endQuizGrade.setText(grade);
        endQuizReview.setText(getString(R.string.grade_theAnswersYouSelectedWere) + "\n \n" + reviewMessage);

        emailSummary = messageScore + getString(R.string.end_overall_grade) + grade + "\n" + getString(R.string.grade_theAnswersYouSelectedWere) + "\n" + reviewMessage;
    }


    /**
     * Clear all selections from view. Call this before loading a question.
     */

    public void clearSelections(View view) {


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


        if (!userName.getText().toString().equals(null)) {
            userName.setText("");
        }
    }

    // Send an email with the test results.

    public void sendMail(View view) {

        // Send summary via e mail.

        String mailTo = "mailto:" + emailTarget;

        Intent sendMail = new Intent(Intent.ACTION_SEND);
        sendMail.setData(Uri.parse(mailTo));
        sendMail.setType("*/*");

        sendMail.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_testResults));
        sendMail.putExtra(Intent.EXTRA_TEXT, emailSummary);
        if (sendMail.resolveActivity(getPackageManager()) != null) {
            startActivity(sendMail);
        }
    }


}




