package com.example.android.courtcounter;

import android.app.ActionBar;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    int scorePlayerA = 0 ;
    int scorePlayerB = 0;
    int Aces_A = 0;
    int Aces_B = 0;
    int gamesA = 0;
    int gamesB = 0;
    int setsA = 0;
    int setsB = 0;
    boolean deuce = false;
    int advantage = 0;
  // advantage = 1 means Advantage for A, advantage = 2 means Advantage for B
    String Message ="";


    // String locale = context.getResources().getConfiguration().locale.getDisplayName();
    String  myLocale = Locale.getDefault().toString();
    //Locale myLocale = getResources().getConfiguration().locale;
    //String myLocaleString = myLocale.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
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
        TextView localeView = (TextView) findViewById(R.id.locale_text);
        localeView.setText(String.valueOf(myLocale));
    }

    /**
     * Displays the current values (Score, Games, Sets, Aces) for Team/Player A.
     */
    public void displayForTeamA() {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score);
        if (scorePlayerA == 50) {
            switch (advantage) {
                case 0:
                    scoreView.setText(String.valueOf("40"));
                    break;
                case 1:
                    scoreView.setText(String.valueOf("A"));
                    break;
                case 2:
                    scoreView.setText(String.valueOf("40"));
                    break;
            }
        }

        else
            scoreView.setText(String.valueOf(scorePlayerA));


        TextView gameView = (TextView) findViewById(R.id.team_a_games);
        gameView.setText(String.valueOf(gamesA));

        TextView setView = (TextView) findViewById(R.id.team_a_sets);
        setView.setText(String.valueOf(setsA));
    }
    /**
     * Displays the given number of Aces for Team/Player A.
     */
    public void displayACEsTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_aces);
        scoreView.setText(String.valueOf(score));
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
                scorePlayerA= -1;
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
        }
        else
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
        }
        else
            return false;
    }

    /**
     * Verifies whether Player A has won the current Set.
     */
    public boolean checkSetWonByA() {
        if (gamesA >= 6 && gamesB < 5 ) {
            return true;
        }
        else
        if (gamesA >= 7 && gamesB <6 ) {
            return true;
        }
        else
            return false;

    }

    /**
     * Performs some actions and reinitialize variables in case Player/Team A wins the current game
     */
    public void GameWonByA() {
        gamesA = gamesA + 1;
        if (checkSetWonByA() ) {
            setsA = setsA + 1;
            gamesA = 0;
            gamesB = 0;
            Message = "Game and set won by Player A";
        }
        scorePlayerA = 0;
        scorePlayerB = 0;
        advantage = 0;
        deuce = false;
        Message = "Game won by Player A";
    }

    /**
     * Verifies whether Player B has won the current Set.
     */
    public boolean checkSetWonByB() {
        if (gamesB >= 6 && gamesA < 5 ) {
            return true;
        }
        else
        if (gamesB >= 7 && gamesA <6 ) {
            return true;
        }
        else
            return false;

    }
    /**
     * Adds 1 to the Aces of the Player/teamA.
     */
    public void add1AceA(View view) {
        Aces_A = Aces_A + 1 ;
        displayACEsTeamA (Aces_A);
        Message = "Ace for Player A";
        showToast(Message);
    }


    /**
     * Displays the given score for Team B.
     */
    public void displayForTeamB() {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score);
        if (scorePlayerB == 50) {
            switch (advantage) {
                case 0:
                    scoreView.setText(String.valueOf("40"));
                    break;
                case 1:
                    scoreView.setText(String.valueOf("40"));
                    break;
                case 2:
                    scoreView.setText(String.valueOf("A"));
                    break;
            }
        }

        else
            scoreView.setText(String.valueOf(scorePlayerB));

        TextView gameView = (TextView) findViewById(R.id.team_b_games);
        gameView.setText(String.valueOf(gamesB));

        TextView setView = (TextView) findViewById(R.id.team_b_sets);
        setView.setText(String.valueOf(setsB));
    }

    /**
     * Displays the given number of Aces for Team/Player B.
     */
    public void displayACEsTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_aces);
        scoreView.setText(String.valueOf(score));
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
                scorePlayerB= -1;
        }
        displayForTeamA();
        displayForTeamB();
        showToast(Message);
    }

    /**
     * Adds 1 to the Aces of the Player/teamB.
     */
    public void add1AceB(View view) {
        Aces_B = Aces_B + 1 ;
        displayACEsTeamB (Aces_B);
        Message = "Ace for Player B";
        showToast(Message);
    }

    /**
     * Performs some actions and reinitialize variables in case Player/Team A wins the current game
     */

    public void GameWonByB() {
        gamesB = gamesB + 1;
        if (checkSetWonByB()) {
            setsB = setsB + 1;
            gamesA = 0;
            gamesB = 0;
            Message = "Game and set won by Player B";
        }
        scorePlayerA = 0;
        scorePlayerB = 0;
        advantage = 0;
        deuce = false;
        Message = "Game won by Player B";
    }
    /**
     * Reset (to 0) the score of both teams/players.
     */
    public void reset(View view) {
        scorePlayerA = 0 ;
        scorePlayerB = 0 ;
        gamesA = 0;
        gamesB = 0;
        Aces_A = 0;
        Aces_B = 0;
        setsA = 0;
        setsB = 0;
        advantage = 0;
        deuce = false;
        Message = "Reset completed";
        showToast(Message);
        displayForTeamA ();
        displayForTeamB ();
        displayACEsTeamA (Aces_A);
        displayACEsTeamB (Aces_B);

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
}