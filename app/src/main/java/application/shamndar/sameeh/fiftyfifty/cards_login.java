package application.shamndar.sameeh.fiftyfifty;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalOAuthScopes;
import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;
import com.paypal.android.sdk.payments.PayPalService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class cards_login extends AppCompatActivity {
    private static PayPalConfiguration config = new PayPalConfiguration();
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static final int REQUEST_CODE_PROFILE_SHARING = 3;
    private class SyncOperation extends AsyncTask<String, Void, String> {
        private String  authorization_code;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {

            // Synchronize code here

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(cards_login.this);
                progressDialog.setMessage("Checking information..");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);
            }
        }


    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_cards_login);


        // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
        // or live (ENVIRONMENT_PRODUCTION)
        config.environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK);

        config.clientId(getString(R.string.paypal_client_id));

        // Minimally, you will need to set three merchant information properties.
        // These should be the same values that you provided to PayPal when you registered your app.
        config.merchantName("Example Store");
        config.merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"));
        config.merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));

        Intent intent = new Intent(this, PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        startService(intent);

        final Button button = (Button) findViewById(R.id.signinbut);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myAss();
            }
        });

        final Button button2 = (Button) findViewById(R.id.faqbut);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.google.com"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }
    private void myAss(){

        Intent intent = new Intent(this, PayPalProfileSharingActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PayPalProfileSharingActivity.EXTRA_REQUESTED_SCOPES, getOauthScopes());

        startActivityForResult(intent, REQUEST_CODE_PROFILE_SHARING);

    }
    private PayPalOAuthScopes getOauthScopes() {
    /* create the set of required scopes
     * Note: see https://developer.paypal.com/docs/integration/direct/identity/attributes/ for mapping between the
     * attributes you select for this app in the PayPal developer portal and the scopes required here.
     */
        Set<String> scopes = new HashSet<String>(
                Arrays.asList(PayPalOAuthScopes.PAYPAL_SCOPE_EMAIL, PayPalOAuthScopes.PAYPAL_SCOPE_ADDRESS) );
        return new PayPalOAuthScopes(scopes);
    }


//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camara) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PayPalAuthorization auth = data
                    .getParcelableExtra(PayPalProfileSharingActivity.EXTRA_RESULT_AUTHORIZATION);
            if (auth != null) {

                String authorization_code = auth.getAuthorizationCode();
                sendAuthorizationToServer(auth);
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("ProfileSharingExample", "The user canceled.");
        } else if (resultCode == PayPalProfileSharingActivity.RESULT_EXTRAS_INVALID) {
            Log.i("ProfileSharingExample",
                    "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
        }
    }

    private void sendAuthorizationToServer(PayPalAuthorization authorization)  {
        Log.e("zebe","bedatee");
        // TODO:
        // Send the authorization response to your server, where it can exchange the authorization code
        // for OAuth access and refresh tokens.
        //
        // Your server must then store these tokens, so that your server code can use it
        // for getting user profile data in the future.
        SyncOperation syncTask=new SyncOperation();
        syncTask.execute();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//
//        }
//        Boolean Success = true;
//        if(Success) {
//            String userEmail = "khalid.awwad3@gmail.com";
//            syncTask.onPostExecute("GOOD!");
//
//        }
//        else{
//
//        }
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("authorization", authorization.getAuthorizationCode());
//        Log.e("auth",authorization.getAuthorizationCode());
//        ParseCloud.callFunctionInBackground("myMagicMushroomsFunction", map, new FunctionCallback<Object>() {
//            @Override
//            public void done(Object response, ParseException exc) {
//                Log.e("cloud code example", "response: " + response);
//            }
//        });

        /**
         * send request to payPal
         */

        Boolean Success = true;

        if(FullscreenActivity.MockEnabled){

            // select your mode to be either private or public.

// get the sharedPreference of your context.

            SharedPreferences  mySharedPreferences ;
            mySharedPreferences=getSharedPreferences("userName",0);


// retrieve an editor to modify the shared preferences

            SharedPreferences.Editor editor= mySharedPreferences.edit();

/* now store your primitive type values. In this case it is true, 1f and Hello! World  */

            editor.putString("emailAddress","kha@gmail.com");


            editor.commit();
            Intent intent = new Intent(cards_login.this, inAppExperince.class);
            intent.putExtra("email", "kha@gmail.com");
            startActivity(intent);
        }

    }
}
