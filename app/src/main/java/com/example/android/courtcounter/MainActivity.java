package com.example.android.courtcounter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static int SETS_TO_WIN = 1;
    int scorePlayerA = 0;
    int scorePlayerB = 0;
    int Aces_A = 0;
    int Aces_B = 0;
    int gamesA = 0;
    int gamesB = 0;
    int setsA = 0;
    int setsB = 0;
    boolean deuce = false;
    int advantage = 0;
    TextView scoreViewA;
    TextView gameViewA;
    TextView setViewA;
    TextView aceViewA;
    TextView scoreViewB;
    TextView gameViewB;
    TextView setViewB;
    TextView aceViewB;
    Button myButtonAddPointA;
    Button myButtonAddPointB;
    Button myButtonAddAceA;
    Button myButtonAddAceB;
    // advantage = 1 means Advantage for A, advantage = 2 means Advantage for B
    String Message = "";
    //Locale myLocale = getResources().getConfiguration().locale;
    //String myLocaleString = myLocale.toString();
    // String locale = context.getResources().getConfiguration().locale.getDisplayName();
    String myLocale = Locale.getDefault().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        scoreViewA = findViewById(R.id.team_a_score);
        gameViewA = findViewById(R.id.team_a_games);
        setViewA = findViewById(R.id.team_a_sets);
        aceViewA = findViewById(R.id.team_a_aces);
        scoreViewB = findViewById(R.id.team_b_score);
        gameViewB = findViewById(R.id.team_b_games);
        setViewB = findViewById(R.id.team_b_sets);
        aceViewB = findViewById(R.id.team_b_aces);
        // All textView containing scores are initialized to 0
        scoreViewA.setText(String.valueOf("0"));
        gameViewA.setText(String.valueOf("0"));
        setViewA.setText(String.valueOf("0"));
        aceViewA.setText(String.valueOf("0"));
        scoreViewB.setText(String.valueOf("0"));
        gameViewB.setText(String.valueOf("0"));
        setViewB.setText(String.valueOf("0"));
        aceViewB.setText(String.valueOf("0"));

        myButtonAddPointA = findViewById(R.id.button_3ptsA);
        myButtonAddPointB = findViewById(R.id.button_3ptsB);
        myButtonAddAceA = findViewById(R.id.button_AceA);
        myButtonAddAceB = findViewById(R.id.button_AceB);

        displayLocale();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);


        // Restore state members from saved instance
        scorePlayerA = savedInstanceState.getInt("Score_Player_A_ID");
        scorePlayerB = savedInstanceState.getInt("Score_Player_B_ID");
        Aces_A = savedInstanceState.getInt("Aces_A_ID");
        Aces_B = savedInstanceState.getInt("Aces_B_ID");
        gamesA = savedInstanceState.getInt("gamesA_ID");
        gamesB = savedInstanceState.getInt("gamesB_ID");
        setsA = savedInstanceState.getInt("setsA_ID");
        setsB = savedInstanceState.getInt("setsB_ID");
        deuce = savedInstanceState.getBoolean("deuce_ID");
        advantage = savedInstanceState.getInt("advantage_ID");

        displayForTeamA();
        displayForTeamB();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt("Score_Player_A_ID", scorePlayerA);
        savedInstanceState.putInt("Score_Player_B_ID", scorePlayerB);
        savedInstanceState.putInt("Aces_A_ID", Aces_A);
        savedInstanceState.putInt("Aces_B_ID", Aces_B);
        savedInstanceState.putInt("gamesA_ID", gamesA);
        savedInstanceState.putInt("gamesB_ID", gamesB);
        savedInstanceState.putInt("setsA_ID", setsA);
        savedInstanceState.putInt("setsB_ID", setsB);
        savedInstanceState.putBoolean("deuce_ID", deuce);
        savedInstanceState.putInt("advantage_ID", advantage);
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }


    public void displayLocale() {
        TextView localeView = findViewById(R.id.locale_text);
        localeView.setText(String.valueOf(myLocale));
    }

    /**
     * Displays the current values (Score, Games, Sets, Aces) for Team/Player A.
     */
    public void displayForTeamA() {
        if (scorePlayerA == 50) {
            switch (advantage) {
                case 0:
                    scoreViewA.setText(String.valueOf("40"));
                    break;
                case 1:
                    scoreViewA.setText(String.valueOf("A"));
                    break;
                case 2:
                    scoreViewA.setText(String.valueOf("40"));
                    break;
            }
        } else
            scoreViewA.setText(String.valueOf(scorePlayerA));

        gameViewA.setText(String.valueOf(gamesA));
        setViewA.setText(String.valueOf(setsA));
    }

    /**
     * Displays the given number of Aces for Team/Player A.
     */
    public void displayACEsTeamA(int score) {
        aceViewA.setText(String.valueOf(score));
    }

    /**
     * Adds 1 point to the score of Player A.
     */
    public void addPointsA(View view) {
        Message = "Point for Player A";
        switch (scorePlayerA) {
            case 0:
                scorePlayerA = 15;
                break;
            case 15:
                scorePlayerA = 30;
                break;
            case 30:
                scorePlayerA = 40;
                if (scorePlayerB == 40) {
                    deuce = true;
                }
                break;
            case 40:
                scorePlayerA = 50;
                if (checkGameWonByA()) {
                    GameWonByA();
                }
                break;
            case 50:
                if (checkGameWonByA()) {
                    GameWonByA();
                }
                break;
            default:
                scorePlayerA = -1;
        }
        displayForTeamA();
        displayForTeamB();
        showToast(Message);
    }

    /**
     * Verifies whether Player A has won the current Game.
     */
    public boolean checkGameWonByA() {
        if (scorePlayerA == 50 && !deuce) {
            return true;
        }
        if (scorePlayerA == 50 && deuce && advantage == 0) {
            advantage = 1;
            Message = "Advantage Player A";
            return false;
        }
        if (scorePlayerA == 50 && advantage == 1) {
            return true;
        }
        if (scorePlayerA == 50 && advantage == 2) {
            deuce = true;
            advantage = 0;
            Message = "Deuce";
            return false;
        } else
            return false;
    }

    /**
     * Verifies whether Player B has won the current Game.
     */
    public boolean checkGameWonByB() {
        if (scorePlayerB == 50 && !deuce) {
            return true;
        }
        if (scorePlayerB == 50 && deuce && advantage == 0) {
            advantage = 2;
            Message = "Advantage Player B";
            return false;
        }
        if (scorePlayerB == 50 && advantage == 2) {
            return true;
        }
        if (scorePlayerB == 50 && advantage == 1) {
            deuce = true;
            advantage = 0;
            Message = "Deuce";
            return false;
        } else
            return false;
    }

    /**
     * Verifies whether Player A has won the current Set.
     */
    public boolean checkSetWonByA() {
        if (gamesA >= 6 && gamesB < 5) {
            return true;
        } else if (gamesA >= 7 && gamesB < 6) {
            return true;
        } else
            return false;
    }

    /**
     * Performs some actions and reinitialize variables in case Player/Team A wins the current game
     */
    public void GameWonByA() {
        gamesA = gamesA + 1;
        Message = getResources().getString(R.string.msg_game_won_by_A);
        if (checkSetWonByA()) {
            setsA = setsA + 1;
            gamesA = 0;
            gamesB = 0;
            Message = getResources().getString(R.string.msg_game_set_won_by_A);
        }
        if (checkMatchEnd() == 1) {
            setButtonsInvisible();
            Message = getResources().getString(R.string.msg_match_won_by_A);
            showAlert();
        }
        scorePlayerA = 0;
        scorePlayerB = 0;
        advantage = 0;
        deuce = false;
    }

    /**
     * Verifies whether Player B has won the current Set.
     */
    public boolean checkSetWonByB() {
        if (gamesB >= 6 && gamesA < 5) {
            return true;
        } else if (gamesB >= 7 && gamesA < 6) {
            return true;
        } else
            return false;
    }

    /**
     * Adds 1 to the Aces of the Player/teamA.
     */
    public void add1AceA(View view) {
        Aces_A = Aces_A + 1;
        displayACEsTeamA(Aces_A);
        Message = getResources().getString(R.string.msg_ace_for_A);
        showToast(Message);
    }

    /**
     * Displays the given score for Team B.
     */
    public void displayForTeamB() {
        if (scorePlayerB == 50) {
            switch (advantage) {
                case 0:
                    scoreViewB.setText(String.valueOf("40"));
                    break;
                case 1:
                    scoreViewB.setText(String.valueOf("40"));
                    break;
                case 2:
                    scoreViewB.setText(String.valueOf("A"));
                    break;
            }
        } else
            scoreViewB.setText(String.valueOf(scorePlayerB));

        gameViewB.setText(String.valueOf(gamesB));
        setViewB.setText(String.valueOf(setsB));
    }

    /**
     * Displays the given number of Aces for Team/Player B.
     */
    public void displayACEsTeamB(int score) {

        aceViewB.setText(String.valueOf(score));
    }

    /**
     * Adds 1 point to the score of Player B.
     */
    public void addPointsB(View view) {
        Message = "Point for Player B";
        switch (scorePlayerB) {
            case 0:
                scorePlayerB = 15;
                break;
            case 15:
                scorePlayerB = 30;
                break;
            case 30:
                scorePlayerB = 40;
                if (scorePlayerA == 40) {
                    deuce = true;
                }
                break;
            case 40:
                scorePlayerB = 50;
                if (checkGameWonByB()) {
                    GameWonByB();
                }
                break;
            case 50:
                if (checkGameWonByB()) {
                    GameWonByB();
                }
                break;

            default:
                scorePlayerB = -1;
        }
        displayForTeamA();
        displayForTeamB();
        showToast(Message);
    }

    /**
     * Adds 1 to the Aces of the Player/teamB.
     */
    public void add1AceB(View view) {
        Aces_B = Aces_B + 1;
        displayACEsTeamB(Aces_B);
        Message = getResources().getString(R.string.msg_ace_for_B);
        showToast(Message);
    }

    /**
     * Performs some actions and reinitialize variables in case Player/Team B wins the current game
     */
    public void GameWonByB() {
        gamesB = gamesB + 1;
        Message = getResources().getString(R.string.msg_game_won_by_B);
        if (checkSetWonByB()) {
            setsB = setsB + 1;
            gamesA = 0;
            gamesB = 0;
            Message = getResources().getString(R.string.msg_game_set_won_by_B);
        }
        if (checkMatchEnd() == 2) {
            setButtonsInvisible();
            Message = getResources().getString(R.string.msg_match_won_by_B);
            showAlert();
        }
        scorePlayerA = 0;
        scorePlayerB = 0;
        advantage = 0;
        deuce = false;
    }

    /**
     * Reset (to 0) the score of both teams/players and sets as visible the buttons
     */
    public void reset(View view) {
        scorePlayerA = 0;
        scorePlayerB = 0;
        gamesA = 0;
        gamesB = 0;
        Aces_A = 0;
        Aces_B = 0;
        setsA = 0;
        setsB = 0;
        advantage = 0;
        deuce = false;
        myButtonAddPointA.setVisibility(View.VISIBLE);
        myButtonAddPointB.setVisibility(View.VISIBLE);
        myButtonAddAceA.setVisibility(View.VISIBLE);
        myButtonAddAceB.setVisibility(View.VISIBLE);
        Message = getResources().getString(R.string.msg_reset);
        showToast(Message);
        displayForTeamA();
        displayForTeamB();
        displayACEsTeamA(Aces_A);
        displayACEsTeamB(Aces_B);
    }

    /**
     * Verifies whether either Player A or B has won the match
     */
    public int checkMatchEnd() {
        if (setsA >= SETS_TO_WIN) {
            return 1;
        } else if (setsB >= SETS_TO_WIN) {
            return 2;
        } else
            return 0;
    }

    /**
     * Sets the buttons to add Points and Aces as not clickable
     */
    public void setButtonsInvisible() {
        myButtonAddPointA.setVisibility(View.INVISIBLE);
        myButtonAddPointB.setVisibility(View.INVISIBLE);
        myButtonAddAceA.setVisibility(View.INVISIBLE);
        myButtonAddAceB.setVisibility(View.INVISIBLE);
    }

    /**
     * Display the given Message in a toast
     * Params: @toastMessage
     */
    public void showToast(String toastMessage) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, toastMessage, duration);
        toast.show();
    }

    /**
     * Display an alert at the end of the match
     * It's my first attempt at implementing an alert dialog based on:
     * https://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
     */
    public void showAlert() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("End of Match");
        builder1.setMessage("Tap on the Reset Button to start a new match");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog myAlert = builder1.create();
        myAlert.show();
}
}