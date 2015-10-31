package application.shamndar.sameeh.fiftyfifty;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalOAuthScopes;
import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;
import com.paypal.android.sdk.payments.PayPalService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class inAppExperince extends AppCompatActivity {

    private Boolean paid;
    private int friendsOfFriendsofFriends;
    private int friendsOfFriends;
    private int friends;
    private double credit;
    private String sourceId;
    private String email;
    private String objectId;
    private static ProgressDialog dialog;
    public class MyLovelyOnClickShare implements View.OnClickListener
    {

        String userId;
        public MyLovelyOnClickShare(String userId) {
            this.userId = userId;

        }

        @Override
        public void onClick(View v)
        {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my id for fifty-fifty!" + userId + "\n" + "http://unknowntraffic.com");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "Share your Id for fifty-fifty!"));
        }

    };

    public class MyLovelyOnClickCopy implements View.OnClickListener
    {

        String userId;
        public MyLovelyOnClickCopy(String userId) {
            this.userId = userId;
        }

        @Override
        public void onClick(View v)
        {
            Snackbar.make(v, "Referance Id coppied to clipboard :)", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("http://google.com/", userId);
            clipboard.setPrimaryClip(clip);
        }

    };


    public class MyLovelyOnFacebook implements View.OnClickListener
    {

        String userId;
        public MyLovelyOnFacebook(String userId) {
            this.userId = userId;
        }

        @Override
        public void onClick(View v)
        {
            String urlToShare = "http://stackoverflow.com/questions/7545254";
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
// intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB: has no effect!
            intent.putExtra(Intent.EXTRA_TEXT, urlToShare);

// See if official Facebook app is found
            boolean facebookAppFound = false;
            List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
            for (ResolveInfo info : matches) {
                if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                    intent.setPackage(info.activityInfo.packageName);
                    facebookAppFound = true;
                    break;
                }
            }

// As fallback, launch sharer.php in a browser
            if (!facebookAppFound) {
                String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
            }

            startActivity(intent);
        }

    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_in_app_experince);

                inAppExperince.dialog = ProgressDialog.show(inAppExperince.this, "",
                        "Loading information. Please wait.", false, false);

        Bundle bundle = getIntent().getExtras();
        this.email = bundle.getString("email");

        Button signout = (Button) findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mySharedPreferences;
                mySharedPreferences = getSharedPreferences("userName", 0);


// retrieve an editor to modify the shared preferences

                SharedPreferences.Editor editor = mySharedPreferences.edit();

/* now store your primitive type values. In this case it is true, 1f and Hello! World  */

                editor.putString("emailAddress", "");
                editor.commit();
                Intent intent = new Intent(inAppExperince.this, cards_login.class);
                startActivity(intent);
                finish();
            }
        });

        Button register = (Button) findViewById (R.id.register);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(FullscreenActivity.MockEnabled){
                    onRegisterFinish(true,null);
                }
                else {
                    register();
                }
            }
        });

        Button recive = (Button) findViewById (R.id.getMoney);
        recive.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(FullscreenActivity.MockEnabled){
                    onReceiveFinish(true,null);
                }
                else {
                    register();
                }
            }
        });
        /**
         * loads information
         */
        refreshFunction();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabRefresh);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inAppExperince.dialog = ProgressDialog.show(inAppExperince.this, "",
                        "Refreshing information. Please wait.", false, false);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        refreshFunction();
                    }
                }, 0);//just mention the time when you want to launch your action

            }
        });

//        FloatingActionButton fab4 = (FloatingActionButton) findViewById(R.id.fabFacebook);
//        fab4.setOnClickListener(new MyLovelyOnFacebook(this.objectId));
//
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fabShare);
        fab2.setOnClickListener(new MyLovelyOnClickShare(this.objectId));

        FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.fabCopy);
        fab3.setOnClickListener(new MyLovelyOnClickCopy(this.objectId));

    }

/*
please call this function for register and for receive money, with boolean success, and error msg if needed.
 */

    private void register(){
        onRegisterFinish(true,null);
    }

    private void recieveMoney(){
        onRegisterFinish(true,null);
    }


    private void onRegisterFinish(Boolean success,String errorMsg){
        if(success){
            new AlertDialog.Builder(inAppExperince.this)
                    .setTitle("Register Successful")
                    .setMessage("Start using the app!")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // whatever...
                        }
                    }).create().show();

        }
        else{
            new AlertDialog.Builder(inAppExperince.this)
                    .setTitle("Register Failed")
                    .setMessage("Please contact us. Error "+errorMsg)
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // whatever...
                        }
                    }).create().show();

        }

        refreshFunction();
    }

    private void onReceiveFinish(Boolean success, String errorMsg){
        if(success){
            new AlertDialog.Builder(inAppExperince.this)
                    .setTitle("Sending the money was successful")
                    .setMessage("Keep referring more friends!")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // whatever...
                        }
                    }).create().show();

        }
        else{
            new AlertDialog.Builder(inAppExperince.this)
                    .setTitle("Paid Failed")
                    .setMessage("Please contact us. Error "+errorMsg)
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // whatever...
                        }
                    }).create().show();

        }

        refreshFunction();
    }

    private void refreshFunction(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("Email", this.email);




//        mDialog.setMessage("Loading information..");
//        mDialog.setCancelable(false);
//        mDialog.show();

        try {
            List<ParseObject> objects = query.find();
            int friendsOfFriendsofFriends = objects.get(0).getInt("friendsOfFriendsofFriends");
            String playerName = objects.get(0).getString("playerName");
            boolean cheatMode = objects.get(0).getBoolean("cheatMode");

            this.paid = objects.get(0).getBoolean("paid");

            this.friendsOfFriendsofFriends=  objects.get(0).getInt("friendsOfFriendsofFriends");
            this.friendsOfFriends = objects.get(0).getInt("friendsOfFriends");
            this.friends = objects.get(0).getInt("friends");
            this.credit = objects.get(0).getDouble("credit");
            this.sourceId = objects.get(0).getString("sourceId");
            this.objectId = objects.get(0).getObjectId();

        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }

        if(this.paid) {
            TextView refIdText = (TextView) findViewById(R.id.refId);
            refIdText.setText("Referer Id: " + objectId);
            Button registerBut = (Button) findViewById(R.id.register);
            registerBut.setVisibility(View.INVISIBLE);
            TextView credit = (TextView) findViewById(R.id.credit);
            Button getBut = (Button) findViewById(R.id.getMoney);
            getBut.setEnabled(true);
            credit.setText("Credit: " + this.credit);
            TextView friends = (TextView) findViewById(R.id.rf);
            friends.setText("Refered friends: "+this.friends);
            TextView friendsof = (TextView) findViewById(R.id.frf);
            friendsof.setText("Friends of friends: "+this.friendsOfFriends);
            TextView friendsofof = (TextView) findViewById(R.id.frfrf);
            friendsofof.setText("Friends of friends of friends: "+this.friendsOfFriendsofFriends);

        }
        else{
            TextView refIdText = (TextView) findViewById(R.id.refId);
            refIdText.setText("Referer Id: " + "Please register first");
            Button registerBut = (Button) findViewById(R.id.register);
            registerBut.setVisibility(View.VISIBLE);
            Button getBut = (Button) findViewById(R.id.getMoney);
            getBut.setEnabled(false);
            TextView credit = (TextView) findViewById(R.id.credit);
            credit.setText("Credit: " + 0);
            TextView friends = (TextView) findViewById(R.id.rf);
            friends.setText("Refered friends: "+0);
            TextView friendsof = (TextView) findViewById(R.id.frf);
            friendsof.setText("Friends of friends: "+0);
            TextView friendsofof = (TextView) findViewById(R.id.frfrf);
            friendsofof.setText("Friends of friends of friends: "+0);
            ((FloatingActionButton) findViewById(R.id.fabRefresh)).setVisibility(View.INVISIBLE);
            ((FloatingActionButton) findViewById(R.id.fabCopy)).setVisibility(View.INVISIBLE);
            ((FloatingActionButton) findViewById(R.id.fabShare)).setVisibility(View.INVISIBLE);

        }

        inAppExperince.dialog.dismiss();


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

    @Override
    public void onBackPressed() {
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
//        // for getting user profile data in the future.
//        SyncOperation syncTask=new SyncOperation();
//        syncTask.execute();
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

        }

    }

}
