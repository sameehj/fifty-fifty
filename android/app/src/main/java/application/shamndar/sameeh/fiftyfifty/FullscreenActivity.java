package application.shamndar.sameeh.fiftyfifty;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.parse.Parse;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */

    /**
     * this mock before integrating cloud
     */
    public static final boolean MockEnabled = true;

    private boolean noUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_fullscreen);


        String parse_app_id="Fmj1JQB1MPypBh7jz53Yj6NoHpBLDMkW2P5zDGjk";
        String parse_client_id="jrAg1S76N75fxvCNuHqoo0rF8dFF2iPUdSNsX93u";
        Parse.initialize(this, parse_app_id, parse_client_id);


        this.noUser = false;
        SharedPreferences mySharedPreferences ;
        mySharedPreferences=getSharedPreferences("userName",0);
        SharedPreferences.Editor editor= mySharedPreferences.edit();
        String user= mySharedPreferences.getString("emailAddress","");
        if(user == "")
            this.noUser = true;
        final Intent intent;
        if(this.noUser){
            intent= new Intent(FullscreenActivity.this, cards_login.class);
        }else{
            intent= new Intent(FullscreenActivity.this, inAppExperince.class);
            intent.putExtra("email", user);
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);

            }
        }, 2000);

    }

    @Override
    public void onBackPressed() {
    }

}
