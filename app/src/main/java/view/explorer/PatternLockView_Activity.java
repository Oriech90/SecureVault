package view.explorer;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.securevault19.securevault2019.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.andrognito.patternlockview.PatternLockView.*;

public class PatternLockView_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_lockview);

        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.button);

        final PatternLockView patternLockView;


        patternLockView = findViewById(R.id.patternView);
        patternLockView.setDotCount(3);
        patternLockView.setCorrectStateColor(getResources().getColor(R.color.colorCorrectLine));
        patternLockView.addPatternLockListener(new PatternLockViewListener() {

            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List progressPattern) {
            }

            @Override
            public void onComplete(List pattern) {
                Log.d(getClass().getName(), "Pattern complete: " +
                        PatternLockUtils.patternToString(patternLockView, pattern));
                if (PatternLockUtils.patternToString(patternLockView, pattern).equalsIgnoreCase("012")) {
                    //Clears the Pattern from the screen
                    clearPattern(patternLockView);
                    Toast.makeText(getApplicationContext(), "Welcome back, User_Name", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), CategoryList_Activity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    mediaPlayer.start();
                    finish();
                    overridePendingTransition(0, 0);

                } else {
                    //Clears the Pattern from the screen
                    clearPattern(patternLockView);
                    patternLockView.setViewMode(PatternViewMode.WRONG); //Pattern's Color becomes red

                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(500);
                    Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCleared() {

            }
        });

    }

    //Clears the Pattern from the screen
    private void clearPattern(final PatternLockView patternLockView){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                patternLockView.clearPattern();
            }
        },1500);

    }
}
