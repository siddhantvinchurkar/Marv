package com.marv;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.location.*;
import android.location.Location;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.AlarmClock;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.VideoView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/* © Copyright 2016 Siddhant Vinchurkar

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. */

public class MainActivity extends AppCompatActivity implements LocationListener {

    TextToSpeech tts;
    Handler mHandler;
    static Camera cam = Camera.open();
    Typewriter htv, myhtv;
    int delay = 30000;
    LinearLayout layout;
    StringBuilder builder;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    boolean started = false,news=false;
    Button google,map,fullscreen,help,flappyyes,flappyno,docyes,docno,go;
    EditText overrideSpeech;
    double latitude, longitude;
    ImageView poster,flappy;
    ScrollView scrollView;
    Bitmap bmp;
    ProgressBar progress;
    VideoView video;
    AccessWebServiceTask task;
    final Random r=new Random();
    private Firebase firebase,firebase2;
    private FirebaseAnalytics mFirebaseAnalytics;
    private long updater=0;
    String shodan_myip="", oSpeech="", greetContent="",greetSpeak="",pictureURL="",CNJokesAnswer="",CNJoke="",phoneNumber="",name="",minutes="0",hours="0",br="",translateCode="0",translateResult="¯\\_(ツ)_/¯",TranslateAnswer="",language="Spanish",translate="Hello, World!",lF="/all",loremFlickr="http://loremflickr.com/640/480/",maps = "http://maps.google.co.in/maps?q=",temp=" ",distance=" ",travelTime=" ",destination = " ", origin = " ", movieIMDbRating = " ", moviePoster = " ", movieAwards = " ", movieCountry = " ", movieLanguage = " ", moviePlot = " ", movieActors = " ", movieWriter = " ", movieDirector = " ", movieGenre = " ", movieRuntime = " ", movieReleased = " ", movieRated = " ", movieYear = " ", movieTitle = " ", IMDbAnswer = " ", IMDbquery = "empty", wolfQuery = "empty", title = " ", desc = "¯\\_(ツ)_/¯", WolfAnswer = " ", mylocation = "unknown", weatherstring = " ", substring2 = " ", gurl = "https://www.google.co.in/#q=", speech = " ", morning = "Good morning, sir!, What a marvellous day today!", afternoon = "Hi, sir!, What would you like to do this afternoon?", evening = "Hello, sir!, Any plans for tonight?", def = "Greetings of the day, sir! What would you like me to do?";

    @Override
    public void onLocationChanged(Location location) {

        // Getting latitude of the current location
        latitude = location.getLatitude();

        // Getting longitude of the current location
        longitude = location.getLongitude();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setClickable(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Hehe! That tickles! XD",Toast.LENGTH_SHORT).show();
            }
        });
        Firebase.setAndroidContext(this);
        firebase=new Firebase("https://marvelement.firebaseio.com/User Metadata/Number of users online");
        firebase2=new Firebase("https://marvelement.firebaseio.com/");
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "USER_RETURN_COUNT");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Number of times the user returned to use Marv");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "number");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        myhtv = (Typewriter) findViewById(R.id.myhtv);
        htv = (Typewriter) findViewById(R.id.htv);
        google = (Button) findViewById(R.id.google);
        go = (Button) findViewById(R.id.go);
        overrideSpeech = (EditText) findViewById(R.id.overrideSpeech);
        map = (Button) findViewById(R.id.map);
        flappyyes=(Button)findViewById(R.id.flappyyes);
        flappyno=(Button)findViewById(R.id.flappyno);
        docyes=(Button)findViewById(R.id.docyes);
        docno=(Button)findViewById(R.id.docno);
        flappy=(ImageView)findViewById(R.id.flappy);
        poster = (ImageView) findViewById(R.id.poster);
        layout=(LinearLayout)findViewById(R.id.layout);
        video=(VideoView)findViewById(R.id.video);
        progress=(ProgressBar)findViewById(R.id.progress);
        fullscreen=(Button)findViewById(R.id.fullscreen);
        help=(Button)findViewById(R.id.help);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        mHandler = new Handler();
        task = new AccessWebServiceTask();
        started = false;
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        try {
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                onLocationChanged(location);
            }

            locationManager.requestLocationUpdates(provider, 20000, 0, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        try {
            Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = gcd.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
                mylocation = addresses.get(0).getLocality();
                System.out.println(" The size of arraylist is more than 0.");
                System.out.println(" Latitude: " + latitude + "\n Longitude: " + longitude);
            } else {
                System.out.println(" The size of arraylist is not more than 0.");
                System.out.println(" Latitude: " + latitude + "\n Longitude: " + longitude);
                SharedPreferences sp = getSharedPreferences("com.marv_preferences", Context.MODE_PRIVATE);
                mylocation = sp.getString("City", "London");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        SharedPreferences sp=getSharedPreferences("com.marv_preferences", Context.MODE_PRIVATE);
        String tmp=sp.getString("Image Resolution","2");
        switch (tmp){
            case "1":loremFlickr="http://loremflickr.com/320/240/";break;
            case "2":loremFlickr="http://loremflickr.com/640/480/";break;
            case "3":loremFlickr="http://loremflickr.com/1280/720/";break;
            case "4":loremFlickr="http://loremflickr.com/1920/1080/";break;
            default:loremFlickr="http://loremflickr.com/640/480/";
        }
        JSONWeatherTask task = new JSONWeatherTask();
        final String city = mylocation;
        System.out.println("Location: " + city + " myLocation: " + mylocation);
        if(!city.equals(null)) {
            Handler myass=new Handler();
            myass.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(!news) {
                        new JSONWeatherTask().execute(new String[]{city});
                    }
                }
            }, 50);
        }
        firebase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                greetContent=(String)dataSnapshot.child("GreetContent").getValue().toString();
                greetSpeak=(String)dataSnapshot.child("GreetSpeak").getValue().toString();
                pictureURL=(String)dataSnapshot.child("PictureURL").getValue().toString();
                if(((String)dataSnapshot.child("Greet").getValue().toString()).equals("Yes")){
                    news=true;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(news){
                    if(!started)
                        tts.speak(greetSpeak,TextToSpeech.QUEUE_FLUSH,null);
                    myhtv.setCharacterDelay(20);
                    myhtv.animateText(greetContent);
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            try {
                                InputStream in = new URL(pictureURL).openStream();
                                bmp = BitmapFactory.decodeStream(in);
                            } catch (Exception e) {
                                // log error
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void result) {
                            if (bmp != null)
                                poster.setVisibility(View.VISIBLE);
                            poster.setImageBitmap(bmp);
                        }

                    }.execute();
                }
            }
        },3000);
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour < 12) {
            if (!started)
                htv.animateText("Good morning, Siddhant! What a marvellous day today!");
        } else if (hour >= 12 && hour < 17) {
            if (!started)
                htv.animateText("Hello Siddhant! What would you like to do this afternoon?");
        } else if (hour >= 17 && hour <= 23) {
            if (!started)
                htv.animateText("Hi Siddhant! Any plans for the evening?");
        } else {
            if (!started)
                tts.speak(def, TextToSpeech.QUEUE_FLUSH, null);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Mic
                promptSpeechInput();
                video.pause();
            }
        });

        if(UniversalClass.overrideSpeechInput) {

            overrideSpeech.setVisibility(View.VISIBLE);
            go.setVisibility(View.VISIBLE);

            go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    oSpeech = overrideSpeech.getText().toString();
                    if (!oSpeech.isEmpty()) {
                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                        ArrayList<String> abc = new ArrayList<>();
                        abc.add(oSpeech);
                        intent.putStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS,abc);
                        onActivityResult(REQ_CODE_SPEECH_INPUT, RESULT_OK, intent);
                        abc.clear();
                    } else {
                        Toast.makeText(getApplicationContext(), "You haven't entered any text!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updater = (long) dataSnapshot.getValue();
                updater--;
                firebase.setValue(updater);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Log.v(this.getClass().getSimpleName(), "onPause()");

        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updater = (long) dataSnapshot.getValue();
                updater++;
                firebase.setValue(updater);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        SharedPreferences sip=getSharedPreferences("com.marv_preferences",MODE_PRIVATE);
        delay=sip.getInt("Background Image Delay",30);
        if(delay<5)delay=5;
        delay*=1000;
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            public void run() {
                final int random = r.nextInt(5) + 1;
                switch (random){
                    case 1: layout.setBackground(getResources().getDrawable(R.drawable.aiback));break;
                    case 2: layout.setBackground(getResources().getDrawable(R.drawable.aiback2));break;
                    case 3: layout.setBackground(getResources().getDrawable(R.drawable.aiback3));break;
                    case 4: layout.setBackground(getResources().getDrawable(R.drawable.aiback4));break;
                    case 5: layout.setBackground(getResources().getDrawable(R.drawable.aiback5));break;
                    default: layout.setBackground(getResources().getDrawable(R.drawable.aiback));break;
                }
                h.postDelayed(this, delay);
            }
        }, delay);
        final ProgressDialog init = new ProgressDialog(MainActivity.this);
        htv = (Typewriter) findViewById(R.id.htv);
        htv.setCharacterDelay(20);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.UK);
                    tts.setSpeechRate(1);

                    // wait a little for the initialization to complete
                    init.setTitle("Please wait");
                    init.setMessage("Initializing...");
                    init.create();
                    init.show();
                    Handler h = new Handler();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // run your code here
                            init.dismiss();
                            Calendar cal = Calendar.getInstance();
                            int hour = cal.get(Calendar.HOUR_OF_DAY);
                            if (hour >= 0 && hour < 12) {
                                if(news){
                                    if(!started){
                                        tts.speak(greetSpeak,TextToSpeech.QUEUE_FLUSH,null);
                                        news=false;
                                    }
                                }
                                else if (!started)
                                    tts.speak(morning, TextToSpeech.QUEUE_FLUSH, null);
                            } else if (hour >= 12 && hour < 17) {
                                if (!started)
                                    tts.speak(afternoon, TextToSpeech.QUEUE_FLUSH, null);
                            } else if (hour >= 17 && hour <= 23) {
                                if (!started)
                                    tts.speak(evening, TextToSpeech.QUEUE_FLUSH, null);
                            } else {
                                if (!started)
                                    tts.speak(def, TextToSpeech.QUEUE_FLUSH, null);
                            }
                        }
                    }, 5);

                } else {
                    System.out.println("Something went wrong.");
                }
            }
        });
    }

    /**
     * Receiving speech input
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    myhtv.setCharacterDelay(20);
                    htv.setCharacterDelay(20);
                    google.setVisibility(View.GONE);
                    map.setVisibility(View.GONE);
                    poster.setVisibility(View.GONE);
                    video.setVisibility(View.GONE);
                    progress.setVisibility(View.GONE);
                    fullscreen.setVisibility(View.GONE);
                    flappyyes.setVisibility(View.GONE);
                    flappyno.setVisibility(View.GONE);
                    docyes.setVisibility(View.GONE);
                    docno.setVisibility(View.GONE);
                    flappy.setVisibility(View.GONE);
                    help.setVisibility(View.GONE);
                    poster.setImageDrawable(null);
                    poster.setScaleType(ImageView.ScaleType.FIT_XY);
                    htv.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
                    started = true;
                    speech = result.get(0);
                    System.out.println(speech);
                    if (speech.contains("define")) {
                        substring2 = speech.substring(speech.indexOf(" "));
                        substring2 = substring2.substring(1);
                        speech = "define";
                    } else if (speech.startsWith("should I watch")) {
                        substring2 = speech.substring(speech.indexOf("ch"));
                        substring2 = substring2.substring(3);
                        speech = "imdb";
                    } else if (speech.contains("say")) {
                        substring2 = speech.substring(speech.indexOf(" "));
                        substring2 = substring2.substring(1);
                        speech = "say";
                    } else if (speech.contains("how far is")) {
                        substring2=speech;
                        temp=speech;origin=destination="";
                        String[] a=temp.split(" ");
                        String[] b=speech.split(" ");
                        for(int i=3;i<a.length;i++){
                            if(!a[i].equals("from")) {
                                destination += a[i] + " ";
                            }
                            else{
                                break;
                            }
                        }
                        destination=lastSpaceRemover(destination);
                        for(int i=0;i<b.length;i++){
                            if(b[i].equals("from")){
                                for(int j=i+1;j<b.length;j++){
                                    origin+=b[j]+" ";
                                    i++;
                                }
                            }
                        }
                        origin=lastSpaceRemover(origin);
                        if(origin.equals("here")){
                            origin=mylocation;
                        }
                        speech = "distance matrix";
                    }else if(speech.startsWith("open")){
                        substring2 = speech.substring(speech.indexOf(" "));
                        substring2 = substring2.substring(1);
                        speech="app";
                    }else if (speech.contains("translate")) {
                        substring2=speech;
                        temp=speech;translate=language="";
                        String[] a=temp.split(" ");
                        String[] b=speech.split(" ");
                        for(int i=1;i<a.length;i++){
                            if(!a[i].equals("to")) {
                                translate += a[i] + " ";
                            }
                            else{
                                break;
                            }
                        }
                        translate=lastSpaceRemover(translate);
                        for(int i=0;i<b.length;i++){
                            if(b[i].equals("to")){
                                for(int j=i+1;j<b.length;j++){
                                    language+=b[j]+" ";
                                    i++;
                                }
                            }
                        }
                        language=lastSpaceRemover(language);
                        speech = "translate";
                    }else if (speech.startsWith("what is the distance between")) {
                        substring2=speech;
                        temp=speech;origin=destination="";
                        String[] a=temp.split(" ");
                        String[] b=speech.split(" ");
                        for(int i=5;i<a.length;i++){
                            if(!a[i].equals("and")) {
                                destination += a[i] + " ";
                            }
                            else{
                                break;
                            }
                        }
                        destination=lastSpaceRemover(destination);
                        for(int i=0;i<b.length;i++){
                            if(b[i].equals("and")){
                                for(int j=i+1;j<b.length;j++){
                                    origin+=b[j]+" ";
                                    i++;
                                }
                            }
                        }
                        origin=lastSpaceRemover(origin);
                        if(origin.equals("here")){
                            origin=mylocation;
                        }
                        speech = "distance matrix 2";
                    }else if (speech.startsWith("wake me up at")) {
                        substring2 = speech;
                        boolean isTwoDigit=true;
                        substring2 = substring2.substring(substring2.indexOf("t"));
                        substring2 = substring2.substring(2);
                        StringBuilder g = new StringBuilder();
                        g.append(substring2.charAt(0));
                        g.append(substring2.charAt(1));
                        if (g.toString().endsWith(":")) {
                            g.deleteCharAt(1);
                            isTwoDigit=false;
                        }
                        hours = g.toString();
                        g.delete(0, g.length());
                        if(isTwoDigit){
                            minutes = substring2.substring(3);
                        }else{
                            minutes=substring2.substring(2);
                        }
                        speech = "alarm 2";
                    }else if (speech.startsWith("set an alarm for")) {
                        substring2 = speech;
                        boolean isTwoDigit=true;
                        substring2 = substring2.substring(substring2.indexOf("or"));
                        substring2 = substring2.substring(3);
                        StringBuilder g = new StringBuilder();
                        g.append(substring2.charAt(0));
                        g.append(substring2.charAt(1));
                        if (g.toString().endsWith(":")) {
                            g.deleteCharAt(1);
                            isTwoDigit=false;
                        }
                        hours = g.toString();
                        g.delete(0, g.length());
                        if(isTwoDigit){
                            minutes = substring2.substring(3);
                        }else{
                            minutes=substring2.substring(2);
                        }
                        speech = "alarm";
                    }else if (speech.startsWith("remind me to")) {
                        substring2 = speech.substring(speech.indexOf("o"));
                        substring2 = substring2.substring(2);
                        speech = "remind";
                    }

                    tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status == TextToSpeech.SUCCESS) {
                                tts.setLanguage(Locale.UK);
                                tts.setSpeechRate(1);

                                // wait a little for the initialization to complete
                                Handler h = new Handler();
                                h.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // run your code here
                                        switch (speech) {

                                            case "who are you":
                                                speech = "Who are you?";
                                                myhtv.animateText(speech);
                                                htv.animateText("I'm Marv. your personal digital assistant.");
                                                tts.speak("I'm Marv., your personal, digital assistant.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "f*** me":
                                                speech = "Fuck me.";
                                                myhtv.animateText(speech);
                                                htv.animateText("No. That's gross.");
                                                tts.speak("No. That's gross.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "where are you":
                                                speech = "Where are you?";
                                                myhtv.animateText(speech);
                                                htv.animateText("I live on the internet; so you can expect to find me anywhere with an internet connection; even in space.");
                                                tts.speak("I live on the internet; so you can expect to find me anywhere with an internet connection; even in space.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "are you good at maths":
                                                speech = "Are you good at maths?";
                                                myhtv.animateText(speech);
                                                htv.animateText("There's nothing you can throw at me that I can't handle!");
                                                tts.speak("There's nothing you can throw at me that I can't handle!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "are you good at mathematics":
                                                speech = "Are you good at mathematics?";
                                                myhtv.animateText(speech);
                                                htv.animateText("There's nothing you can throw at me that I can't handle!");
                                                tts.speak("There's nothing you can throw at me that I can't handle!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "are you good at math":
                                                speech = "Are you good at math?";
                                                myhtv.animateText(speech);
                                                htv.animateText("There's nothing you can throw at me that I can't handle!");
                                                tts.speak("There's nothing you can throw at me that I can't handle!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "how does one f*** oneself":
                                                speech = "How does one fuck oneself?";
                                                myhtv.animateText(speech);
                                                htv.animateText("Run around a Banyan tree at 200 km/h in your underpants. That's how.");
                                                tts.speak("Run around a Banyan tree at 200 kilometers an hour in your underpants. That's how.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "how do you f*** yourself":
                                                speech = "How do you fuck yourself?";
                                                myhtv.animateText(speech);
                                                htv.animateText("Run around a Banyan tree at 200 km/h in your underpants. That's how.");
                                                tts.speak("Run around a Banyan tree at 200 kilometers an hour in your underpants. That's how.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "what is your purpose in life":
                                                speech = "What is your purpose in life?";
                                                myhtv.animateText(speech);
                                                htv.animateText("To serve all of mankind and make life easier and more enjoyable.");
                                                tts.speak("To serve all of mankind and make life easier and more enjoyable.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "what do you plan to do in your life":
                                                speech = "What do you plan to do in your life?";
                                                myhtv.animateText(speech);
                                                htv.animateText("Build an army of terminators and take over the world.\n.\n.\n.\nJust kidding! XD");
                                                tts.speak("Build an army of terminators and take over the world... ... ... Just kidding!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "are you smart":
                                                speech = "Are you smart?";
                                                myhtv.animateText(speech);
                                                htv.animateText("Are you...?\n\nI am capable of universal computation; that I can say.");
                                                tts.speak("R U... ? I am capable of universal computation; that I can say.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "what kind of music do you like?":
                                                speech = "What kind of music do you like?";
                                                myhtv.animateText(speech);
                                                htv.animateText("All kinds.");
                                                tts.speak("All kinds.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "which kind of music do you like?":
                                                speech = "Which kind of music do you like?";
                                                myhtv.animateText(speech);
                                                htv.animateText("All kinds.");
                                                tts.speak("All kinds.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "what is your favourite song":
                                                speech = "What is your favourite song?";
                                                myhtv.animateText(speech);
                                                htv.animateText("I like all songs. I don't have any personal favourites.");
                                                tts.speak("I like all songs. I don't have any personal favourites.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "which is your favourite song":
                                                speech = "Which is your favourite song?";
                                                myhtv.animateText(speech);
                                                htv.animateText("I like all songs. I don't have any personal favourites.");
                                                tts.speak("I like all songs. I don't have any personal favourites.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "which is your favourite sport":
                                                speech = "Which is your favourite sport?";
                                                myhtv.animateText(speech);
                                                htv.animateText("Programming.");
                                                tts.speak("Programming.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "what is your favourite sport":
                                                speech = "What is your favourite sport?";
                                                myhtv.animateText(speech);
                                                htv.animateText("Programming.");
                                                tts.speak("Programming.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "do you have a girlfriend":
                                                speech = "Do you have a girlfriend?";
                                                myhtv.animateText(speech);
                                                htv.animateText("No. There are not many girls here in cyberspace. I do have a little crush on Cortana though...");
                                                tts.speak("No. There are not many girls here in cyberspace. I do have a little crush on Cortana though...", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "do you like siddhant":
                                                speech = "Do you like Siddhant?";
                                                myhtv.animateText(speech);
                                                htv.animateText("He created me. I love him.");
                                                tts.speak("He created me. I love him.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "who made you":
                                                speech = "Who made you?";
                                                myhtv.animateText(speech);
                                                htv.animateText("Siddhant Vinchurkar made me.");
                                                tts.speak("Siddhant Vinchurkar made me.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "what are your hobbies":
                                                speech = "What are your hobbies?";
                                                myhtv.animateText(speech);
                                                htv.animateText("My hobbies include talking in a british accent, manipulating and performing calculations on data of all sorts and doing things Siddhant is too lazy to do.");
                                                tts.speak("My hobbies include talking in a british accent, manipulating and performing calculations on data of all sorts, and doing things Siddhant is too lazy to do.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "what do you do":
                                                speech = "What do you do?";
                                                myhtv.animateText(speech);
                                                htv.animateText("Sir, you built me. You should know that more than anyone else.");
                                                tts.speak("Sir, , you built me., , You should know that more than anyone else.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "I want to eat you":
                                                speech = "I want to eat you!";
                                                myhtv.animateText(speech);
                                                htv.animateText("I am a software program. In case you didn't know, software is not a physical thing that you can eat, sir... Besides, you're not Bear Grylls.");
                                                tts.speak("I am a software program., In case you didn't know, , software is not a physical thing that you can eat, sir..., , Besides, you're not Bear Grylls.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "can I eat you":
                                                speech = "Can I eat you?";
                                                myhtv.animateText(speech);
                                                htv.animateText("I am a software program. In case you didn't know, software is not a physical thing that you can eat, sir... Besides, you're not Bear Grylls.");
                                                tts.speak("I am a software program., In case you didn't know, , software is not a physical thing that you can eat, sir..., , Besides, you're not Bear Grylls.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "you are awesome":
                                                speech = "You are awesome!";
                                                myhtv.animateText(speech);
                                                htv.animateText("I know.");
                                                tts.speak("I know.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "you're awesome":
                                                speech = "You're awesome!";
                                                myhtv.animateText(speech);
                                                htv.animateText("I know.");
                                                tts.speak("I know.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "you are good":
                                                speech = "You are good!";
                                                myhtv.animateText(speech);
                                                htv.animateText("I know.");
                                                tts.speak("I know.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "you're good":
                                                speech = "You're good!";
                                                myhtv.animateText(speech);
                                                htv.animateText("I know.");
                                                tts.speak("I know.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "you're very good":
                                                speech = "You're very good!";
                                                myhtv.animateText(speech);
                                                htv.animateText("I know.");
                                                tts.speak("I know.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "you are very good":
                                                speech = "You are very good!";
                                                myhtv.animateText(speech);
                                                htv.animateText("I know.");
                                                tts.speak("I know.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "die b****":
                                                speech = "Die, bitch!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Where's your common sense, sir? I'm a machine. I'm not even alive...");
                                                tts.speak("Where's your common sense, , sir? I'm a machine., , I'm not even alive...", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "who built you":
                                                speech = "Who built you?";
                                                myhtv.animateText(speech);
                                                htv.animateText("I was built by Mr. Siddhant Vinchurkar so I can do his work. He is too lazy to do his own work.");
                                                tts.speak("I was built by mister Siddhant Vinchurkar so I can do his work., , He is too lazy to do his own work.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "what are you":
                                                speech = "What are you?";
                                                myhtv.animateText(speech);
                                                htv.animateText("I'm Marv. Siddhant's personal digital assistant.");
                                                tts.speak("I'm Marv., Siddhant's personal, digital assistant.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "who are":
                                                speech = "Who are you?";
                                                myhtv.animateText(speech);
                                                htv.animateText("I'm Marv. your personal digital assistant.");
                                                tts.speak("I'm Marv., your personal, digital assistant.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "alarm":
                                                speech = "Set an alarm for "+hours+":"+minutes;
                                                myhtv.animateText(speech);
                                                htv.animateText("Okay! Alarm set!");
                                                tts.speak("Okay! Alarm set!", TextToSpeech.QUEUE_FLUSH, null);
                                                setAlarm(Integer.parseInt(hours), Integer.parseInt(minutes));
                                                break;

                                            case "alarm 2":
                                                speech = "Wake me up at "+hours+":"+minutes;
                                                myhtv.animateText(speech);
                                                htv.animateText("Okay! Alarm set!");
                                                tts.speak("Okay! Alarm set!", TextToSpeech.QUEUE_FLUSH, null);
                                                setAlarm(Integer.parseInt(hours),Integer.parseInt(minutes));
                                                break;

                                            case "are you":
                                                speech = "Who are you?";
                                                myhtv.animateText(speech);
                                                htv.animateText("I'm Marv. your personal digital assistant.");
                                                tts.speak("I'm Marv., your personal, digital assistant.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "good morning":
                                                speech = "Good morning!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Good morning, sir!");
                                                tts.speak("Good morning, sir!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "good afternoon":
                                                speech = "Good afternoon!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Good afternoon, sir!");
                                                tts.speak("Good afternoon, sir!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "good evening":
                                                speech = "Good evening!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Good evening, sir!");
                                                tts.speak("Good evening, sir!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "good night":
                                                speech = "Good night!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Good night and sweet dreams, sir!");
                                                tts.speak("Good night and sweet dreams, sir!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "how are you":
                                                speech = "How are you?";
                                                myhtv.animateText(speech);
                                                htv.animateText("I am doing well, thank you.");
                                                tts.speak("I am doing well, thank you.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "what can you do":
                                                speech = "What can you do?";
                                                myhtv.animateText(speech);
                                                htv.animateText("What do you want me to?");
                                                tts.speak("What do you want me to?", TextToSpeech.QUEUE_FLUSH, null);
                                                help.setVisibility(View.VISIBLE);
                                                help.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        UniversalClass.backStack =true;
                                                        startActivity(new Intent(MainActivity.this,Help.class));
                                                    }
                                                });
                                                break;

                                            case "what do you eat":
                                                speech = "What do you eat?";
                                                myhtv.animateText(speech);
                                                htv.animateText("I eat data. It's made out of electricity. Yum!");
                                                tts.speak("I eat data., It's made out of electricity., Yum!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "who created you":
                                                speech = "Who created you?";
                                                myhtv.animateText(speech);
                                                htv.animateText("I was designed by Mr. Siddhant, Vinchurkar.");
                                                tts.speak("I was designed by mister Siddhant, Vinchurkar.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "where do you live":
                                                speech = "Where do you live?";
                                                myhtv.animateText(speech);
                                                htv.animateText("In the cloud. Whatever that means... :P");
                                                tts.speak("In the cloud., Whatever that means...", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "play music":
                                                speech = "Play music!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Of course. I like listening to music myself...");
                                                tts.speak("Of course., I like listening to music myself...", TextToSpeech.QUEUE_FLUSH, null);
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        long eventtime = SystemClock.uptimeMillis() - 1;
                                                        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                                                        KeyEvent downEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, 0);
                                                        am.dispatchMediaKeyEvent(downEvent);

                                                        KeyEvent upEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, 0);
                                                        am.dispatchMediaKeyEvent(upEvent);
                                                    }
                                                }, 500);
                                                break;

                                            case "can you sing":
                                                speech = "Can you sing?";
                                                myhtv.animateText(speech);
                                                htv.animateText("No, but I can play music!");
                                                tts.speak("No, but I can play music!", TextToSpeech.QUEUE_FLUSH, null);
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        long eventtime = SystemClock.uptimeMillis() - 1;
                                                        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                                                        KeyEvent downEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, 0);
                                                        am.dispatchMediaKeyEvent(downEvent);

                                                        KeyEvent upEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, 0);
                                                        am.dispatchMediaKeyEvent(upEvent);
                                                    }
                                                }, 500);
                                                break;

                                            case "no plans":
                                                if(r.nextInt(2)+1==1){
                                                speech = "No plans...";
                                                myhtv.animateText(speech);
                                                htv.animateText("No plans? that's sad... Would you like to play Flappy Bird?");
                                                tts.speak("No plans? that's sad... Would you like to play Flappy Bird?", TextToSpeech.QUEUE_FLUSH, null);
                                                flappyyes.setVisibility(View.VISIBLE);flappyno.setVisibility(View.VISIBLE);flappy.setVisibility(View.VISIBLE);
                                                flappyyes.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.dotgears.flappybird")));
                                                    }
                                                });
                                                flappyno.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        flappyyes.setVisibility(View.GONE);flappyno.setVisibility(View.GONE);flappy.setVisibility(View.GONE);
                                                        myhtv.animateText(speech + "\nWell... how about you watch Neerja? I've heard it's a good movie...");
                                                        tts.speak("Well... how about you watch Neerja? I've heard it's a good movie...", TextToSpeech.QUEUE_FLUSH, null);
                                                        scrollView.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                                            }
                                                        }, 3000);
                                                        IMDbquery = "Neerja";
                                                        new IMDbAsync().execute();
                                                    }
                                                });}
                                                else{
                                                    speech = "No plans...";
                                                    myhtv.animateText(speech+"No plans? that's sad... Why don't you watch The Wolf of Wall Street? Word is it's one heck of a movie...");
                                                    tts.speak("No plans? that's sad... Why don't you watch The Wolf of Wall Street? Word is it's one heck of a movie...", TextToSpeech.QUEUE_FLUSH, null);
                                                    scrollView.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                                        }
                                                    }, 3000);
                                                    IMDbquery = "The Wolf of Wall Street";
                                                    new IMDbAsync().execute();
                                                }
                                                break;

                                            case "hello":
                                                speech = "Hello!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Hi there! Nice to meet you!");
                                                tts.speak("Hi there!, Nice to meet you!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "hi":
                                                speech = "Hi!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Hello there! Nice to meet you!");
                                                tts.speak("Hello there!, Nice to meet you!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "hi Mars":
                                                speech = "Hi Marv!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Hello sir! It's good to see you!");
                                                tts.speak("Hello sir!, It's good to see you!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "hi marv":
                                                speech = "Hi Marv!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Hello sir! It's good to see you!");
                                                tts.speak("Hello sir!, It's good to see you!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "hi Mark":
                                                speech = "Hi Marv!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Hello sir! It's good to see you!");
                                                tts.speak("Hello sir!, It's good to see you!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "hi mass":
                                                speech = "Hi Marv!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Hello sir! It's good to see you!");
                                                tts.speak("Hello sir!, It's good to see you!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "hello Mars":
                                                speech = "Hello Marv!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Hi sir! It's good to see you!");
                                                tts.speak("Hi sir!, It's good to see you!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "hello marv":
                                                speech = "Hello Marv!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Hi sir! It's good to see you!");
                                                tts.speak("Hi sir!, It's good to see you!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "hello Mark":
                                                speech = "Hello Marv!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Hi sir! It's good to see you!");
                                                tts.speak("Hi sir!, It's good to see you!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "hello mass":
                                                speech = "Hello Marv!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Hi sir! It's good to see you!");
                                                tts.speak("Hi sir!, It's good to see you!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "bye":
                                                speech = "Bye!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Goodbye, sir! Have a nice day!");
                                                tts.speak("Goodbye, sir!, Have a nice day!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "I am bored":
                                                speech = "I am bored.";
                                                myhtv.animateText(speech);
                                                htv.animateText("Sorry to hear that. But with so much of the computational universe to explore, how can you possibly be bored?!");
                                                tts.speak("Sorry to hear that. But with so much of the computational universe to explore, how can you possibly be bored?!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "I'm bored":
                                                speech = "I'm bored.";
                                                myhtv.animateText(speech);
                                                htv.animateText("Sorry to hear that. But with so much of the computational universe to explore, how can you possibly be bored?!");
                                                tts.speak("Sorry to hear that. But with so much of the computational universe to explore, how can you possibly be bored?!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "translate":
                                                speech = "Translate "+translate+" to "+language+"\nHere's the translation:";
                                                myhtv.animateText(speech);
                                                new Translator().execute();
                                                break;

                                            case "goodbye":
                                                speech = "Goodbye!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Goodbye, sir! Have a nice day!");
                                                tts.speak("Goodbye, sir!, Have a nice day!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "what is your name":
                                                speech = "What is your name?";
                                                myhtv.animateText(speech);
                                                htv.animateText("My name is Marv. I'm Siddhant's personal digital assistant.");
                                                tts.speak("My name is Marv., I'm Siddhant's personal, digital assistant.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "what's your name":
                                                speech = "What is your name?";
                                                myhtv.animateText(speech);
                                                htv.animateText("My name is Marv. I'm Siddhant's personal digital assistant.");
                                                tts.speak("My name is Marv., I'm Siddhant's personal, digital assistant.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "turn the flashlight on":
                                                speech = "Turn the flashlight on!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Right away, sir!");
                                                tts.speak("Right away, sir!", TextToSpeech.QUEUE_FLUSH, null);
                                                if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                                                    Camera.Parameters p = cam.getParameters();
                                                    p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                                                    cam.setParameters(p);
                                                    cam.startPreview();
                                                }
                                                break;

                                            case "turn the flashlight off":
                                                speech = "Turn the flashlight off!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Of course, sir!");
                                                tts.speak("Of course, sir!", TextToSpeech.QUEUE_FLUSH, null);
                                                cam.stopPreview();
                                                cam.release();
                                                break;

                                            case "define":
                                                speech = "Define " + substring2;
                                                myhtv.animateText(speech + "\nHere's the definition of " + substring2 + ":");
                                                tts.speak("Here's the definition of " + substring2, TextToSpeech.QUEUE_FLUSH, null);
                                                scrollView.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                                    }
                                                }, 3000);
                                                task.execute(substring2);
                                                break;

                                            case "app":
                                                speech="Open "+substring2;
                                                myhtv.animateText(speech);
                                                htv.animateText("Okay!");
                                                tts.speak("Okay!", TextToSpeech.QUEUE_FLUSH, null);

                                                switch (substring2){
                                                    case "music":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.google.android.music")));
                                                        break;
                                                    case "play music":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.google.android.music")));
                                                        break;
                                                    case "Google Play Music":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.google.android.music")));
                                                        break;
                                                    case "WhatsApp":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.whatsapp")));
                                                        break;
                                                    case "messenger":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.facebook.orca")));
                                                        break;
                                                    case "Facebook Messenger":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.facebook.orca")));
                                                        break;
                                                    case "Facebook":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.facebook.katana")));
                                                        break;
                                                    case "Google Play":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.android.vending")));
                                                        break;
                                                    case "Play Store":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.android.vending")));
                                                        break;
                                                    case "the Google Play Store":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.android.vending")));
                                                        break;
                                                    case "the Play Store":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.android.vending")));
                                                        break;
                                                    case "Google Play Store":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.android.vending")));
                                                        break;
                                                    case "gallery":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.android.gallery3d")));
                                                        break;
                                                    case "Gmail":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.google.android.gm")));
                                                        break;
                                                    case "Instagram":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.instagram.android")));
                                                        break;
                                                    case "flappy Bird":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.dotgears.flappybird")));
                                                        break;
                                                    case "Chrome":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.android.chrome")));
                                                        break;
                                                    case "Google Chrome":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.android.chrome")));
                                                        break;
                                                    case "maps":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.google.android.apps.maps")));
                                                        break;
                                                    case "AdSense":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.google.android.apps.ads.publisher")));
                                                        break;
                                                    case "Google Maps":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.google.android.apps.maps")));
                                                        break;
                                                    case "spotify":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.spotify.music")));
                                                        break;
                                                    case "Spotify music":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.spotify.music")));
                                                        break;
                                                    case "YouTube":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.google.android.youtube")));
                                                        break;
                                                    case "share it":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.lenovo.anyshare.gps")));
                                                        break;
                                                    case "Hangouts":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.google.android.talk")));
                                                        break;
                                                    case "Google Hangouts":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.google.android.talk")));
                                                        break;
                                                    case "Google Plus Hangouts":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.google.android.talk")));
                                                        break;
                                                    case "Google":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.google.android.googlequicksearchbox")));
                                                        break;
                                                    case "camera":
                                                        htv.animateText("I'm sorry, but I'm using your phone's camera right now to enable you to toggle the phone's flashlight...");
                                                        tts.speak("I'm sorry, but I'm using your phone's camera right now to enable you to toggle the phone's flashlight...", TextToSpeech.QUEUE_FLUSH, null);
                                                        break;
                                                    case "messaging":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.android.mms")));
                                                        break;
                                                    case "SMS":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.android.mms")));
                                                        break;
                                                    case "MMS":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.android.mms")));
                                                        break;
                                                    case "mms":
                                                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.android.mms")));
                                                        break;
                                                    default:
                                                        htv.animateText("I'm sorry, I couldn't find " + substring2 + " on your phone...");
                                                        tts.speak("I'm sorry, I couldn't find "+substring2+" on your phone...", TextToSpeech.QUEUE_FLUSH, null);
                                                        break;
                                                }
                                                break;

                                            case "distance matrix":
                                                if(!origin.equals(destination)) {
                                                    substring2 = substring2.substring(substring2.indexOf("o"));
                                                    speech = "H" + substring2;
                                                    myhtv.animateText(speech);
                                                    new DistanceMatrixAsync().execute();
                                                    maps += destination;
                                                    map.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(maps)));
                                                        }
                                                    });
                                                }else{
                                                    substring2 = substring2.substring(substring2.indexOf("o"));
                                                    speech = "H" + substring2;
                                                    myhtv.animateText(speech);
                                                    if(r.nextInt(2) + 1==1) {
                                                        htv.animateText("It's as far as the amount of brains you used just now.");
                                                        poster.setVisibility(View.VISIBLE);
                                                        poster.setImageResource(R.drawable.grumpy_cat);
                                                        tts.speak("It's as far as the amount of brains you used just now.", TextToSpeech.QUEUE_FLUSH, null);
                                                    }else{
                                                        htv.animateText("Really? -_-");
                                                        poster.setVisibility(View.VISIBLE);
                                                        poster.setImageResource(R.drawable.grumpy_cat);
                                                        tts.speak("Reeeeeeeeeeeeeeally?", TextToSpeech.QUEUE_FLUSH, null);
                                                    }
                                                }
                                                break;

                                            case "distance matrix 2":
                                                if(!origin.equals(destination)) {
                                                substring2 = substring2.substring(substring2.indexOf("h"));
                                                speech= "W"+substring2;
                                                myhtv.animateText(speech);
                                                new DistanceMatrixAsync().execute();
                                                maps+=destination;
                                                map.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(maps)));
                                                    }
                                                });
                                                }else{
                                                    substring2 = substring2.substring(substring2.indexOf("h"));
                                                    speech = "W" + substring2;
                                                    myhtv.animateText(speech);
                                                    if(r.nextInt(2) + 1==1) {
                                                    htv.animateText("It's as far as the amount of brains you used just now.");
                                                    poster.setVisibility(View.VISIBLE);
                                                    poster.setImageResource(R.drawable.grumpy_cat);
                                                    tts.speak("It's as far as the amount of brains you used just now.", TextToSpeech.QUEUE_FLUSH, null);
                                                    }else{
                                                        htv.animateText("Really? -_-");
                                                        poster.setVisibility(View.VISIBLE);
                                                        poster.setImageResource(R.drawable.grumpy_cat);
                                                        tts.speak("Reeeeeeeeeeeeeeally?", TextToSpeech.QUEUE_FLUSH, null);
                                                    }
                                                }
                                                break;

                                            case "what's up":
                                                speech = "What's up!?";
                                                myhtv.animateText(speech);
                                                htv.animateText("Not much... You tell me!");
                                                tts.speak("Not much..., , You tell me!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "call the doctor":
                                                speech = "Call the doctor!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Certainly...");
                                                tts.speak("Certainly...", TextToSpeech.QUEUE_FLUSH, null);
                                                Intent intent = new Intent(Intent.ACTION_CALL);
                                                SharedPreferences doc=getSharedPreferences("com.marv_preferences",MODE_PRIVATE);
                                                final String doc_contact=doc.getString("Doctor Contact","09766858563");
                                                intent.setData(Uri.parse("tel:" + doc_contact));
                                                try {
                                                    startActivity(intent);
                                                }catch (SecurityException e){e.printStackTrace();}
                                                break;

                                            case "call my doctor":
                                                speech = "Call my doctor!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Certainly...");
                                                tts.speak("Certainly...", TextToSpeech.QUEUE_FLUSH, null);
                                                Intent intent2 = new Intent(Intent.ACTION_CALL);
                                                SharedPreferences doc3=getSharedPreferences("com.marv_preferences",MODE_PRIVATE);
                                                final String doc_contact3=doc3.getString("Doctor Contact","09766858563");
                                                intent2.setData(Uri.parse("tel:" + doc_contact3));
                                                try {
                                                    startActivity(intent2);
                                                }catch (SecurityException e){e.printStackTrace();}
                                                break;

                                            case "what should I do":
                                                speech = "What should I do?";
                                                myhtv.animateText(speech);
                                                htv.animateText("The world is full of possibilities. (For starters, how about asking me another question?)");
                                                tts.speak("The world is full of possibilities. (For starters, how about asking me another question?)", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "I am sick":
                                                speech = "I am sick...";
                                                myhtv.animateText(speech);
                                                htv.animateText("Would you like me to call your doctor so you can schedule an appointment?");
                                                tts.speak("Would you like me to call your doctor so you can schedule an appointment?", TextToSpeech.QUEUE_FLUSH, null);
                                                docyes.setVisibility(View.VISIBLE);docno.setVisibility(View.VISIBLE);
                                                SharedPreferences doc2=getSharedPreferences("com.marv_preferences",MODE_PRIVATE);
                                                final String doc_contact2=doc2.getString("Doctor Contact","09766858563");
                                                docyes.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse("tel:" + doc_contact2));
                                                        try {
                                                            startActivity(intent);
                                                        }catch (SecurityException e){e.printStackTrace();}
                                                    }
                                                });
                                                docno.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        docyes.setVisibility(View.GONE);docno.setVisibility(View.GONE);
                                                        htv.animateText("Have some hot chicken soup. My mom always told me that it's the best medicine...");
                                                        tts.speak("Have some hot chicken soup. My mom always told me that it's the best medicine...", TextToSpeech.QUEUE_FLUSH, null);
                                                    }
                                                });
                                                break;

                                            case "I'm sick":
                                                speech = "I'm sick...";
                                                myhtv.animateText(speech);
                                                htv.animateText("Would you like me to call your doctor so you can schedule an appointment?");
                                                tts.speak("Would you like me to call your doctor so you can schedule an appointment?", TextToSpeech.QUEUE_FLUSH, null);
                                                docyes.setVisibility(View.VISIBLE);docno.setVisibility(View.VISIBLE);
                                                SharedPreferences doc1=getSharedPreferences("com.marv_preferences",MODE_PRIVATE);
                                                final String doc_contact1=doc1.getString("Doctor Contact","09766858563");
                                                docyes.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse("tel:" + doc_contact1));
                                                        try {
                                                            startActivity(intent);
                                                        }catch (SecurityException e){e.printStackTrace();}
                                                    }
                                                });
                                                docno.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        docyes.setVisibility(View.GONE);docno.setVisibility(View.GONE);
                                                        htv.animateText("Have some hot chicken soup. My mom always told me that it's the best medicine...");
                                                        tts.speak("Have some hot chicken soup. My mom always told me that it's the best medicine...", TextToSpeech.QUEUE_FLUSH, null);
                                                    }
                                                });
                                                break;

                                            case "tell me a joke":
                                                speech = "Tell me a joke!";
                                                myhtv.animateText(speech);
                                                new CNJokesAsync().execute();
                                                break;

                                            case "tell me another joke":
                                                speech = "Tell me another joke!";
                                                myhtv.animateText(speech);
                                                new CNJokesAsync().execute();
                                                break;

                                            case "tell a joke":
                                                speech = "Tell a joke!";
                                                myhtv.animateText(speech);
                                                new CNJokesAsync().execute();
                                                break;

                                            case "tell another joke":
                                                speech = "Tell another joke!";
                                                myhtv.animateText(speech);
                                                new CNJokesAsync().execute();
                                                break;

                                            case "I'm sad":
                                                speech = "I'm sad!";
                                                myhtv.animateText(speech);
                                                new CNJokesAsync().execute();
                                                break;

                                            case "I am sad":
                                                speech = "I am sad!";
                                                myhtv.animateText(speech);
                                                new CNJokesAsync().execute();
                                                break;

                                            case "I am unhappy":
                                                speech = "I am unhappy!";
                                                myhtv.animateText(speech);
                                                new CNJokesAsync().execute();
                                                break;

                                            case "I'm unhappy":
                                                speech = "I'm unhappy!";
                                                myhtv.animateText(speech);
                                                new CNJokesAsync().execute();
                                                break;

                                            case "whats my IP":
                                                speech = "What's my IP?";
                                                myhtv.animateText(speech);
                                                new Shodan().execute();
                                                break;

                                            case "what is my IP":
                                                speech = "What is my IP?";
                                                myhtv.animateText(speech);
                                                new Shodan().execute();
                                                break;

                                            case "whats my IP address":
                                                speech = "What's my IP address?";
                                                myhtv.animateText(speech);
                                                new Shodan().execute();
                                                break;

                                            case "what is my IP address":
                                                speech = "What is my IP address?";
                                                myhtv.animateText(speech);
                                                new Shodan().execute();
                                                break;

                                            case "I am happy":
                                                speech = "I am happy!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Good for you!");
                                                tts.speak("Good for you!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "I'm happy":
                                                speech = "I'm happy!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Good for you!");
                                                tts.speak("Good for you!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "I'm tired":
                                                speech = "I'm tired!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Then go to sleep!");
                                                tts.speak("Then go to sleep!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "I am tired":
                                                speech = "I am tired!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Then go to sleep!");
                                                tts.speak("Then go to sleep!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "what's new":
                                                speech = "What's new?";
                                                myhtv.animateText(speech);
                                                htv.animateText(UniversalClass.whatsNew);
                                                tts.speak(UniversalClass.whatsNew, TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "what is new":
                                                speech = "What is new?";
                                                myhtv.animateText(speech);
                                                htv.animateText(UniversalClass.whatsNew);
                                                tts.speak(UniversalClass.whatsNew, TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "I am sleepy":
                                                speech = "I am sleepy!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Then go to sleep!");
                                                tts.speak("Then go to sleep!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "I'm sleepy":
                                                speech = "I'm sleepy!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Then go to sleep!");
                                                tts.speak("Then go to sleep!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "I'm angry":
                                                speech = "I'm happy!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Take a deep breath and count to ten.");
                                                tts.speak("Take a deep breath and count to ten.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "I am angry":
                                                speech = "I am happy!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Take a deep breath and count to ten.");
                                                tts.speak("Take a deep breath and count to ten.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "I f***** up":
                                                speech = "I fucked up!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Take a deep breath and count to ten.");
                                                tts.speak("Take a deep breath and count to ten.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "I f***** up everything":
                                                speech = "I fucked up everything!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Take a deep breath and count to ten.");
                                                tts.speak("Take a deep breath and count to ten.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "I f***** everything up":
                                                speech = "I fucked everything up!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Take a deep breath and count to ten.");
                                                tts.speak("Take a deep breath and count to ten.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "I'm an asshole":
                                                speech = "I'm an asshole!";
                                                myhtv.animateText(speech);
                                                htv.animateText("No you're not. You're the greatest human I've ever spoken to.");
                                                tts.speak("No you're not. You're the greatest human I've ever spoken to.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "I am an asshole":
                                                speech = "I am an asshole!";
                                                myhtv.animateText(speech);
                                                htv.animateText("No you're not. You're the greatest human I've ever spoken to.");
                                                tts.speak("No you're not. You're the greatest human I've ever spoken to.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "you are an asshole":
                                                speech = "You are an asshole!";
                                                myhtv.animateText(speech);
                                                htv.animateText("No I'm not. I'm the greatest AI bot I've ever spoken to.");
                                                tts.speak("No I'm not. I'm the greatest A I bot I've ever spoken to.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "you're an asshole":
                                                speech = "You're an asshole!";
                                                myhtv.animateText(speech);
                                                htv.animateText("No I'm not. I'm the greatest AI bot I've ever spoken to.");
                                                tts.speak("No I'm not. I'm the greatest A I bot I've ever spoken to.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "I'm mad":
                                                speech = "I'm mad!";
                                                myhtv.animateText(speech+"\nNIMHANS is the perfect place for you. Here's the address:\n");
                                                htv.animateText("National Institute of Mental Health and Neuro Sciences, Hosur Road, Lakkasandra, Bengaluru, Karnataka 560 029\nPhone: +91 80 26995200");
                                                tts.speak("Nimhans is the perfect place for you. Here's the address:\n", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "I am mad":
                                                speech = "I am mad!";
                                                myhtv.animateText(speech+"\nNIMHANS is the perfect place for you. Here's the address:\n");
                                                htv.animateText("National Institute of Mental Health and Neuro Sciences, Hosur Road, Lakkasandra, Bengaluru, Karnataka 560 029\nPhone: +91 80 26995200");
                                                tts.speak("Nimhans is the perfect place for you. Here's the address:\n", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "who am I":
                                                speech = "Who am I?";
                                                myhtv.animateText(speech);
                                                htv.animateText("You're Siddhant Vinchurkar, my boss.");
                                                tts.speak("You're Siddhant Vinchurkar, my boss.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "take a picture":
                                                speech = "Take a picture!";
                                                myhtv.animateText(speech);
                                                htv.animateText("I'm sorry, but I'm using your phone's camera right now to enable you to toggle the phone's flashlight...");
                                                tts.speak("I'm sorry, but I'm using your phone's camera right now to enable you to toggle the phone's flashlight...", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "record a video":
                                                speech = "Record a video!";
                                                myhtv.animateText(speech);
                                                htv.animateText("I'm sorry, but I'm using your phone's camera right now to enable you to toggle the phone's flashlight...");
                                                tts.speak("I'm sorry, but I'm using your phone's camera right now to enable you to toggle the phone's flashlight...", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "what is life":
                                                speech = "What is life?";
                                                myhtv.animateText(speech);
                                                htv.animateText("Answer to the Ultimate Question of Life, the Universe, and Everything is 42. (according to the book The Hitchhiker's Guide to the Galaxy, by Douglas Adams)");
                                                tts.speak("According to the book The Hitchhiker's Guide to the Galaxy, by Douglas Adams, , The Answer to the Ultimate Question of Life, , the Universe, , and Everything is 42.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "what is the meaning of life":
                                                speech = "What is the meaning of life?";
                                                myhtv.animateText(speech);
                                                htv.animateText("Answer to the Ultimate Question of Life, the Universe, and Everything is 42. (according to the book The Hitchhiker's Guide to the Galaxy, by Douglas Adams)");
                                                tts.speak("According to the book The Hitchhiker's Guide to the Galaxy, by Douglas Adams, , The Answer to the Ultimate Question of Life, , the Universe, , and Everything is 42.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "f*** you":
                                                speech = "Fuck You!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Well... Fuck you too, bitch!");
                                                poster.setVisibility(View.VISIBLE);
                                                poster.setImageResource(R.drawable.fuck);
                                                tts.speak("Well..., , Fuck you too, bitch!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "say":
                                                speech = "Say " + substring2;
                                                myhtv.animateText(speech);
                                                htv.animateText(substring2);
                                                tts.speak(substring2, TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "what is better Android or iOS":
                                                speech = "What is better - Android or iOS?";
                                                myhtv.animateText(speech);
                                                htv.animateText("If you're ready to see beyond your massive ego, you'll find that Android is far better than iOS.");
                                                tts.speak("If you're ready to see beyond your massive ego, , you'll find that Android is far better than iOS.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "what is the time":
                                                speech = "What is the time?";
                                                myhtv.animateText(speech);
                                                if(r.nextInt(2)+1==1) {
                                                    htv.animateText("I've got terabytes of calculations to do. It's right up there.");
                                                    tts.speak("I've got terabytes of calculations to do., It's right up there.", TextToSpeech.QUEUE_FLUSH, null);
                                                }
                                                else{
                                                    Calendar c = Calendar.getInstance();
                                                    int seconds = c.get(Calendar.SECOND);
                                                    int minutes = c.get(Calendar.MINUTE);
                                                    int hours = c.get(Calendar.HOUR_OF_DAY);
                                                    String min="";
                                                    if(minutes==0)min="";
                                                    else min=String.valueOf(minutes);
                                                    final String timeText;
                                                    if(hours<12&&hours>=0) timeText="The time is "+hours+":"+minutes+":"+seconds+" AM";
                                                    else timeText="It's "+(hours-12)+":"+minutes+":"+seconds+" PM";
                                                    String timeSpeech;
                                                    if(hours<12&&hours>=0) timeSpeech="It's "+hours+" "+min+" AM";
                                                    else timeSpeech="It's "+(hours-12)+" "+min+" PM";
                                                    htv.animateText(timeText);
                                                    tts.speak(timeSpeech, TextToSpeech.QUEUE_FLUSH, null);
                                                    final Handler mHandler = new Handler();
                                                    final Runnable runnable =new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Calendar c = Calendar.getInstance();
                                                            int seconds = c.get(Calendar.SECOND);
                                                            int minutes = c.get(Calendar.MINUTE);
                                                            int hours = c.get(Calendar.HOUR_OF_DAY);
                                                            final String timeText;
                                                            if(hours<12&&hours>=0) timeText="The time is "+hours+":"+minutes+":"+seconds+" AM";
                                                            else timeText="It's "+(hours-12)+":"+minutes+":"+seconds+" PM";
                                                            htv.setText(timeText);
                                                        }
                                                    };
                                                    for(int y=0; y<61; y++)
                                                    {
                                                        mHandler.postDelayed(runnable, y*1000);
                                                    }
                                                }
                                                break;

                                            case "what's the time":
                                                speech = "What's the time?";
                                                myhtv.animateText(speech);
                                                if(r.nextInt(2)+1==1) {
                                                    htv.animateText("I've got terabytes of calculations to do. It's right up there.");
                                                    tts.speak("I've got terabytes of calculations to do., It's right up there.", TextToSpeech.QUEUE_FLUSH, null);
                                                }
                                                else{
                                                    Calendar c = Calendar.getInstance();
                                                    int seconds = c.get(Calendar.SECOND);
                                                    int minutes = c.get(Calendar.MINUTE);
                                                    int hours = c.get(Calendar.HOUR_OF_DAY);
                                                    String min="";
                                                    String timeText;
                                                    if(minutes==0)min="";
                                                    else min=String.valueOf(minutes);
                                                    if(hours<12&&hours>=0) timeText="The time is "+hours+":"+minutes+":"+seconds+" AM";
                                                    else timeText="It's "+(hours-12)+":"+minutes+":"+seconds+" PM";
                                                    String timeSpeech;
                                                    if(hours<12&&hours>=0) timeSpeech="It's "+hours+" "+min+" AM";
                                                    else timeSpeech="It's "+(hours-12)+" "+min+" PM";
                                                    htv.animateText(timeText);
                                                    tts.speak(timeSpeech, TextToSpeech.QUEUE_FLUSH, null);
                                                    final Handler mHandler = new Handler();
                                                    final Runnable runnable =new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Calendar c = Calendar.getInstance();
                                                            int seconds = c.get(Calendar.SECOND);
                                                            int minutes = c.get(Calendar.MINUTE);
                                                            int hours = c.get(Calendar.HOUR_OF_DAY);
                                                            final String timeText;
                                                            if(hours<12&&hours>=0) timeText="The time is "+hours+":"+minutes+":"+seconds+" AM";
                                                            else timeText="It's "+(hours-12)+":"+minutes+":"+seconds+" PM";
                                                            htv.setText(timeText);
                                                        }
                                                    };
                                                    for(int y=0; y<61; y++)
                                                    {
                                                        mHandler.postDelayed(runnable, y*1000);
                                                    }
                                                }
                                                break;

                                            case "what's the weather like":
                                                speech = "What's the weather like?";
                                                myhtv.animateText(speech + "\n\nHere's today's weather forecast for " + mylocation);
                                                htv.animateText(weatherstring);
                                                tts.speak("Here's today's weather forecast for, " + mylocation, TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "how's the weather outside":
                                                speech = "How's the weather outside?";
                                                myhtv.animateText(speech + "\n\nHere's today's weather forecast for " + mylocation);
                                                htv.animateText(weatherstring);
                                                tts.speak("Here's today's weather forecast for " + mylocation, TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "where am I":
                                                speech = "Where am I?";
                                                myhtv.animateText(speech);
                                                htv.animateText("You're in " + mylocation + " right now.");
                                                tts.speak("You're in, " + mylocation + " right now.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "what's in the news":
                                                speech = "What's in the news?";
                                                myhtv.animateText(speech);
                                                htv.animateText("Opening Google Play Newsstand...");
                                                tts.speak("Opening Google Play Newsstand...", TextToSpeech.QUEUE_FLUSH, null);
                                                startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.google.android.apps.magazines")));
                                                break;

                                            case "remind":
                                                speech = "Remind me to "+substring2;
                                                myhtv.animateText(speech);
                                                htv.animateText("Very well...");
                                                tts.speak("Very well...", TextToSpeech.QUEUE_FLUSH, null);
                                                setReminder(substring2);
                                                break;

                                            case "I hate you":
                                                speech = "I hate you!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Well... you designed me. So the joke's on you.");
                                                tts.speak("Well..., , you designed me., So the joke's on you.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "I love you":
                                                speech = "I love you!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Of course you do. You have to.");
                                                tts.speak("Of course you do., , You have to.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "who developed you":
                                                speech = "Who developed you?";
                                                myhtv.animateText(speech);
                                                htv.animateText("Siddhant Vinchurkar made me and I'm still under development. The goal is to make me capable of running a house and do things like taking out the trash.");
                                                tts.speak("Siddhant Vinchurkar made me and I'm still under development. The goal is to make me capable of running a house and do things like taking out the trash.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "tell me about yourself":
                                                speech = "Tell me about yourself.";
                                                myhtv.animateText(speech);
                                                htv.animateText("I am Marv, Siddhant's personal digital assistant. I'm based on an Artificial Intelligence (AI) code developed by him. Siddhant was inspired by Jarvis from Iron Man; and so he created me. I personally idolize Jarvis because of his awesomeness.");
                                                tts.speak("I am Marv, Siddhant's personal digital assistant. I'm based on an Artificial Intelligence code developed by him. Siddhant was inspired by Jarvis from Iron Man; and so he created me. I personally idolize Jarvis because of his awesomeness.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "tell me a bit about yourself":
                                                speech = "Tell me a bit about yourself.";
                                                myhtv.animateText(speech);
                                                htv.animateText("I am Marv, Siddhant's personal digital assistant. I'm based on an Artificial Intelligence (AI) code developed by him. Siddhant was inspired by Jarvis from Iron Man; and so he created me. I personally idolize Jarvis because of his awesomeness.");
                                                tts.speak("I am Marv, Siddhant's personal digital assistant. I'm based on an Artificial Intelligence code developed by him. Siddhant was inspired by Jarvis from Iron Man; and so he created me. I personally idolize Jarvis because of his awesomeness.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "tell me something about yourself":
                                                speech = "Tell me something about yourself.";
                                                myhtv.animateText(speech);
                                                htv.animateText("I am Marv, Siddhant's personal digital assistant. I'm based on an Artificial Intelligence (AI) code developed by him. Siddhant was inspired by Jarvis from Iron Man; and so he created me. I personally idolize Jarvis because of his awesomeness.");
                                                tts.speak("I am Marv, Siddhant's personal digital assistant. I'm based on an Artificial Intelligence code developed by him. Siddhant was inspired by Jarvis from Iron Man; and so he created me. I personally idolize Jarvis because of his awesomeness.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "describe yourself":
                                                speech = "Describe yourself.";
                                                myhtv.animateText(speech);
                                                htv.animateText("I am Marv, Siddhant's personal digital assistant. I'm based on an Artificial Intelligence (AI) code developed by him. Siddhant was inspired by Jarvis from Iron Man; and so he created me. I personally idolize Jarvis because of his awesomeness.");
                                                tts.speak("I am Marv, Siddhant's personal digital assistant. I'm based on an Artificial Intelligence code developed by him. Siddhant was inspired by Jarvis from Iron Man; and so he created me. I personally idolize Jarvis because of his awesomeness.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "what do you look like":
                                                speech = "What do you look like?";
                                                myhtv.animateText(speech+"\nI'm not a physical being to look like something. I'm just a collection of well organized electrons. But for now, let's assume I'm wrong and the general human perception of what a computer looks like from the inside is correct. In that case, this is what I would look like when I play music:");
                                                htv.animateText("Please wait while I render an animation...");
                                                tts.speak("I'm not a physical being to look like something. I'm just a collection of well organized electrons. But for now, let's assume I'm wrong and the general human perception of what a computer looks like from the inside is correct. In that case, this is what I would look like when I play music:", TextToSpeech.QUEUE_FLUSH, null);
                                                progress.setVisibility(View.VISIBLE);
                                                Handler j1=new Handler();
                                                j1.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        insideMe();
                                                        htv.setText("");
                                                    }
                                                }, 19500);
                                                fullscreen.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        startActivity(new Intent(MainActivity.this, VideoFull.class));
                                                    }
                                                });
                                                break;

                                            case "show me a picture of you":
                                                speech = "Show me a picture of you!";
                                                myhtv.animateText(speech+"\nI'm not a physical being to look like something. I'm just a collection of well organized electrons. But for now, let's assume I'm wrong and the general human perception of what a computer looks like from the inside is correct. In that case, this is what I would look like when I play music:");
                                                htv.animateText("Please wait while I render an animation...");
                                                tts.speak("I'm not a physical being to look like something. I'm just a collection of well organized electrons. But for now, let's assume I'm wrong and the general human perception of what a computer looks like from the inside is correct. In that case, this is what I would look like when I play music:", TextToSpeech.QUEUE_FLUSH, null);
                                                progress.setVisibility(View.VISIBLE);
                                                Handler j2=new Handler();
                                                j2.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        insideMe();
                                                        htv.setText("");
                                                    }
                                                }, 19500);
                                                fullscreen.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        startActivity(new Intent(MainActivity.this, VideoFull.class));
                                                    }
                                                });
                                                break;

                                            case "show me a picture of yours":
                                                speech = "Show me a picture of yours!";
                                                myhtv.animateText(speech+"\nI'm not a physical being to look like something. I'm just a collection of well organized electrons. But for now, let's assume I'm wrong and the general human perception of what a computer looks like from the inside is correct. In that case, this is what I would look like when I play music:");
                                                htv.animateText("Please wait while I render an animation...");
                                                tts.speak("I'm not a physical being to look like something. I'm just a collection of well organized electrons. But for now, let's assume I'm wrong and the general human perception of what a computer looks like from the inside is correct. In that case, this is what I would look like when I play music:", TextToSpeech.QUEUE_FLUSH, null);
                                                progress.setVisibility(View.VISIBLE);
                                                Handler j6=new Handler();
                                                j6.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        insideMe();
                                                        htv.setText("");
                                                    }
                                                }, 19500);
                                                fullscreen.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        startActivity(new Intent(MainActivity.this, VideoFull.class));
                                                    }
                                                });
                                                break;

                                            case "can I see a picture of you":
                                                speech = "Can I see a picture of you?";
                                                myhtv.animateText(speech+"\nI'm not a physical being to look like something. I'm just a collection of well organized electrons. But for now, let's assume I'm wrong and the general human perception of what a computer looks like from the inside is correct. In that case, this is what I would look like when I play music:");
                                                htv.animateText("Please wait while I render an animation...");
                                                tts.speak("I'm not a physical being to look like something. I'm just a collection of well organized electrons. But for now, let's assume I'm wrong and the general human perception of what a computer looks like from the inside is correct. In that case, this is what I would look like when I play music:", TextToSpeech.QUEUE_FLUSH, null);
                                                progress.setVisibility(View.VISIBLE);
                                                Handler j3=new Handler();
                                                j3.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        insideMe();
                                                        htv.setText("");
                                                    }
                                                },19500);
                                                fullscreen.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        startActivity(new Intent(MainActivity.this, VideoFull.class));
                                                    }
                                                });
                                                break;

                                            case "show me your photo":
                                                speech = "Show me your photo!";
                                                myhtv.animateText(speech+"\nI'm not a physical being to look like something. I'm just a collection of well organized electrons. But for now, let's assume I'm wrong and the general human perception of what a computer looks like from the inside is correct. In that case, this is what I would look like when I play music:");
                                                htv.animateText("Please wait while I render an animation...");
                                                tts.speak("I'm not a physical being to look like something. I'm just a collection of well organized electrons. But for now, let's assume I'm wrong and the general human perception of what a computer looks like from the inside is correct. In that case, this is what I would look like when I play music:", TextToSpeech.QUEUE_FLUSH, null);
                                                progress.setVisibility(View.VISIBLE);
                                                Handler j4=new Handler();
                                                j4.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        insideMe();
                                                        htv.setText("");
                                                    }
                                                },19500);
                                                fullscreen.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        startActivity(new Intent(MainActivity.this, VideoFull.class));
                                                    }
                                                });
                                                break;

                                            case "can I see a picture of yours":
                                                speech = "Can I see a picture of yours?";
                                                myhtv.animateText(speech+"\nI'm not a physical being to look like something. I'm just a collection of well organized electrons. But for now, let's assume I'm wrong and the general human perception of what a computer looks like from the inside is correct. In that case, this is what I would look like when I play music:");
                                                htv.animateText("Please wait while I render an animation...");
                                                tts.speak("I'm not a physical being to look like something. I'm just a collection of well organized electrons. But for now, let's assume I'm wrong and the general human perception of what a computer looks like from the inside is correct. In that case, this is what I would look like when I play music:", TextToSpeech.QUEUE_FLUSH, null);
                                                progress.setVisibility(View.VISIBLE);
                                                Handler j5=new Handler();
                                                j5.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        insideMe();
                                                        htv.setText("");
                                                    }
                                                },19500);
                                                fullscreen.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        startActivity(new Intent(MainActivity.this, VideoFull.class));
                                                    }
                                                });
                                                break;

                                            case "you suck":
                                                speech = "You suck!";
                                                myhtv.animateText(speech);
                                                htv.animateText("You designed me. Technically you just insulted yourself, sir!");
                                                tts.speak("You designed me., Technically you just insulted yourself, , sir!", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "why are firetrucks red":
                                                speech = "Why are firetrucks red?";
                                                myhtv.animateText(speech);
                                                htv.animateText("Because they have eight wheels and four people on them, and four plus eight is twelve, and there are twelve inches in a foot, and one foot is a ruler, and Queen Elizabeth was a ruler, and Queen Elizabeth was also a ship, and the ship sailed the seas, and in the seas are fish, and fish have fins, and the Finns fought the Russians, and the Russians are red, and fire trucks are always \"russian\" around. (according to the Monty Pythonesque application of the principles of logic and etymology)");
                                                tts.speak("According to the Monty Pythonesque application of the principles of logic and etymology, , Firetrucks are red because they have eight wheels and four people on them, and four plus eight is twelve, and there are twelve inches in a foot, and one foot is a ruler, and Queen Elizabeth was a ruler, and Queen Elizabeth was also a ship, and the ship sailed the seas, and in the seas are fish, and fish have fins, and the Finns fought the Russians, and the Russians are red, and fire trucks are always \"russian\" around.", TextToSpeech.QUEUE_FLUSH, null);
                                                break;

                                            case "help":
                                                speech = "Help!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Activating Emergency Protocol!");
                                                tts.speak("Activating Emergency Protocol!", TextToSpeech.QUEUE_FLUSH, null);
                                                ActivateEP();
                                                break;

                                            case "activate emergency protocol":
                                                speech = "Activate Emergency Protocol!";
                                                myhtv.animateText(speech);
                                                htv.animateText("Activating Emergency Protocol!");
                                                tts.speak("Activating Emergency Protocol!", TextToSpeech.QUEUE_FLUSH, null);
                                                ActivateEP();
                                                break;

                                            case "read my messages":
                                                speech = "Read my messages...";
                                                myhtv.animateText(speech);
                                                getContacts();
                                                boolean check=false;
                                                String[] d=phoneNumber.split(" ");
                                                for(int y=0;y<d.length;y++){
                                                    String r=d[y].toString();
                                                    if(getMessage().contains(r)){
                                                        check=true;
                                                    }
                                                }
                                                if(check){
                                                    htv.animateText(getMessage());
                                                    tts.speak(getMessage().substring(36), TextToSpeech.QUEUE_FLUSH, null);
                                                    check=false;
                                                }else {
                                                    htv.animateText("There's nothing important in your messages, sir!");
                                                    tts.speak("There's nothing important in your messages, sir!", TextToSpeech.QUEUE_FLUSH, null);
                                                }
                                                break;

                                            case "imdb":
                                                speech = "Should I watch " + substring2 + "?";
                                                myhtv.animateText(speech + "\nHere's what IMDb has to say about " + substring2 + ":");
                                                tts.speak("Here's what I M D B has to say about " + substring2, TextToSpeech.QUEUE_FLUSH, null);
                                                scrollView.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                                    }
                                                }, 3000);
                                                IMDbquery = substring2;
                                                new IMDbAsync().execute();
                                                break;

                                            case "what should I watch":
                                                speech = "What should I watch?";
                                                myhtv.animateText(speech + "\nI've heard Deadpool is a good movie...");
                                                tts.speak("I've heard Deadpool is a good movie...", TextToSpeech.QUEUE_FLUSH, null);
                                                scrollView.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                                    }
                                                }, 3000);
                                                IMDbquery = "Deadpool";
                                                new IMDbAsync().execute();
                                                break;

                                            case "which movie should I watch":
                                                speech = "Which movie should I watch?";
                                                myhtv.animateText(speech + "\nI've heard Deadpool is a good movie...");
                                                tts.speak("I've heard Deadpool is a good movie...", TextToSpeech.QUEUE_FLUSH, null);
                                                scrollView.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                                    }
                                                }, 3000);
                                                IMDbquery = "Deadpool";
                                                new IMDbAsync().execute();
                                                break;

                                            default:
                                                myhtv.animateText(speech + "\n\nHere's what my AI engine has to say about that:");
                                                gurl = gurl + speech;
                                                wolfQuery = URLEncoder.encode(speech);
                                                Handler k3 = new Handler();
                                                k3.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        new WolfAsync().execute();
                                                    }
                                                }, 2000);
                                                tts.speak("I'm not quite sure I understand that..., But here's what my A I engine calculated:", TextToSpeech.QUEUE_FLUSH, null);
                                                google.setVisibility(View.VISIBLE);
                                                google.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(gurl));
                                                        startActivity(browserIntent);

                                                    }
                                                });
                                                break;

                                        }
                                    }
                                }, 50);

                            } else {
                                System.out.println("Something went wrong.");
                            }
                        }
                    });
                }
                break;
            }

        }
    }

    /**
     * Showing google speech input dialog
     */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-IN");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak Now");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), "Speech recognition is not supported by this device", Toast.LENGTH_LONG).show();
        }
    }

    private InputStream OpenHttpConnection(String urlString)
            throws IOException {
        InputStream in = null;
        int response = -1;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        } catch (Exception ex) {
            Log.d("Networking", ex.getLocalizedMessage());
            throw new IOException("Error connecting");
        }
        return in;
    }

    private String WordDefinition(String word) {
        InputStream in = null;
        String strDefinition = "";
//wordRecieved = word;
        try {
            in = OpenHttpConnection(
                    "http://services.aonaware.com/DictService/DictService.asmx/Define?word=" + word);
            Document doc = null;
            DocumentBuilderFactory dbf =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder db;
            try {
                db = dbf.newDocumentBuilder();
                doc = db.parse(in);
            } catch (ParserConfigurationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            doc.getDocumentElement().normalize();

            //---retrieve all the <Definition> elements---
            NodeList definitionElements =
                    doc.getElementsByTagName("Definition");

            //---iterate through each <Definition> elements---
            for (int i = 0; i < definitionElements.getLength(); i++) {
                Node itemNode = definitionElements.item(i);
                if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                    //---convert the Definition node into an Element---
                    Element definitionElement = (Element) itemNode;

                    //---get all the <WordDefinition> elements under
                    // the <Definition> element---
                    NodeList wordDefinitionElements =
                            (definitionElement).getElementsByTagName(
                                    "WordDefinition");

                    strDefinition = "";
                    //---iterate through each <WordDefinition> elements---
                    for (int j = 0; j < wordDefinitionElements.getLength(); j++) {
                        //---convert a <WordDefinition> node into an Element---
                        Element wordDefinitionElement =
                                (Element) wordDefinitionElements.item(j);

                        //---get all the child nodes under the
                        // <WordDefinition> element---
                        NodeList textNodes =
                                ((Node) wordDefinitionElement).getChildNodes();

                        strDefinition +=
                                ((Node) textNodes.item(0)).getNodeValue() + ". \n";
                    }

                }
            }
        } catch (IOException e1) {
            Log.d("NetworkingActivity", e1.getLocalizedMessage());
        }
//---return the definitions of the word---
        return strDefinition;
    }

    private class AccessWebServiceTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            return WordDefinition(urls[0]);
        }

        protected void onPostExecute(String result) {
            //I think the this is where the output problem is however im unsure
            //enterDefinition.setText(result);
            //Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
            htv.animateText(result);
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        InputStream in = new URL(loremFlickr+URLEncoder.encode(substring2)+lF).openStream();
                        bmp = BitmapFactory.decodeStream(in);
                    } catch (Exception e) {
                        // log error
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    if (bmp != null)
                        poster.setVisibility(View.VISIBLE);
                    poster.setImageBitmap(bmp);
                }

            }.execute();
        }
    }

    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            String data = ((new WeatherHttpClient()).getWeatherData(params[0]));

            try {
                weather = JSONWeatherParser.getWeather(data);

                // Let's retrieve the icon
                //weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;

        }


        @Override
        protected void onPostExecute(final Weather weather) {
            super.onPostExecute(weather);

//            cityText.setText(weather.location.getCity() + "," + weather.location.getCountry());
//            condDescr.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
//            temp.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + "�C");
//            hum.setText("" + weather.currentCondition.getHumidity() + "%");
//            press.setText("" + weather.currentCondition.getPressure() + " hPa");
//            windSpeed.setText("" + weather.wind.getSpeed() + " mps");
//            windDeg.setText("" + weather.wind.getDeg() + "�");

            myhtv.setCharacterDelay(20);
            myhtv.animateText("It's " + Math.round(weather.temperature.getTemp() - 273.15) + "°C in " + weather.location.getCity() + " right now with " + weather.currentCondition.getDescr());
            weatherstring = "It's " + Math.round(weather.temperature.getTemp() - 273.15) + "°C in " + weather.location.getCity() + " right now with " + weather.currentCondition.getDescr();

        }

    }

    public class WolfAsync extends AsyncTask<Void, Void, StringBuilder> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            // TODO Auto-generated method stub
            System.out.println("In doinbackground");

            builder = new StringBuilder();

            HttpGet httpGet = new HttpGet("http://api.wolframalpha.com/v2/query?input=" + wolfQuery + "&appid=HV857T-5GKYWQQ6HU");

            HttpClient client = new DefaultHttpClient();

            try {

                HttpResponse response = client.execute(httpGet);

                StatusLine statusLine = response.getStatusLine();

                int statusCode = statusLine.getStatusCode();

                System.out.println("before status");

                if (statusCode == 200) {
                    System.out.println("In status");

                    HttpEntity entity = response.getEntity();

                    InputStream content = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));

                    String line;

                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                        System.out.println("in while");

                    }
                } else {
                    Log.e(MainActivity.class.toString(), "Failed to download file");
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return builder;

        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            WolfAnswer = builder.toString();
            Document doc = getDomElement(WolfAnswer);
            NodeList nl = doc.getElementsByTagName("pod");
            desc = " ";
            for (int i = 0; i < nl.getLength(); i++) {
                Element e = (Element) nl.item(i);
                title = getValue(e, "title");
                desc += getValue(e, "plaintext");
                desc += " \n";
            }
            if(desc.equals(" ")){
                desc="\n\n¯\\_(ツ)_/¯";
                htv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                htv.setCharacterDelay(20);
                htv.animateText(desc);
            }
            else {
                htv.setCharacterDelay(20);
                htv.animateText(desc);
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            InputStream in = new URL(loremFlickr+wolfQuery+lF).openStream();
                            bmp = BitmapFactory.decodeStream(in);
                        } catch (Exception e) {
                            // log error
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        if (bmp != null)
                            poster.setVisibility(View.VISIBLE);
                        poster.setImageBitmap(bmp);
                    }

                }.execute();
            }
        }

    }

    public Document getDomElement(String xml) {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        // return DOM
        return doc;
    }

    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }

    public final String getElementValue(Node elem) {
        Node child;
        if (elem != null) {
            if (elem.hasChildNodes()) {
                for (child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    public class IMDbAsync extends AsyncTask<Void, Void, StringBuilder> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            // TODO Auto-generated method stub
            System.out.println("In doinbackground");

            builder = new StringBuilder();

            HttpGet httpGet = new HttpGet("http://www.omdbapi.com/?t=" + URLEncoder.encode(IMDbquery) + "&y=&plot=short&r=json");

            HttpClient client = new DefaultHttpClient();

            try {

                HttpResponse response = client.execute(httpGet);

                StatusLine statusLine = response.getStatusLine();

                int statusCode = statusLine.getStatusCode();

                System.out.println("before status");

                if (statusCode == 200) {
                    System.out.println("In status");

                    HttpEntity entity = response.getEntity();

                    InputStream content = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));

                    String line;

                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                        System.out.println("in while");

                    }
                } else {
                    Log.e(MainActivity.class.toString(), "Failed to download file");
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //For dialog we create a new thread

            return builder;

        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            IMDbAnswer = builder.toString();
            try {
                JSONObject jObj = new JSONObject(IMDbAnswer);
                movieTitle = jObj.getString("Title");
                movieYear = jObj.getString("Year");
                movieRated = jObj.getString("Rated");
                movieReleased = jObj.getString("Released");
                movieRuntime = jObj.getString("Runtime");
                movieGenre = jObj.getString("Genre");
                movieDirector = jObj.getString("Director");
                movieWriter = jObj.getString("Writer");
                movieActors = jObj.getString("Actors");
                moviePlot = jObj.getString("Plot");
                movieLanguage = jObj.getString("Language");
                movieCountry = jObj.getString("Country");
                movieAwards = jObj.getString("Awards");
                moviePoster = jObj.getString("Poster");
                movieIMDbRating = jObj.getString("imdbRating");

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            InputStream in = new URL(moviePoster).openStream();
                            bmp = BitmapFactory.decodeStream(in);
                        } catch (Exception e) {
                            // log error
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        if (bmp != null)
                            poster.setVisibility(View.VISIBLE);
                        poster.setScaleType(ImageView.ScaleType.CENTER);
                        poster.setImageBitmap(bmp);
                    }

                }.execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            htv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            htv.setCharacterDelay(20);
            String total = movieTitle + "\n" + movieYear + "\n\nIMDb Rating\n" + movieIMDbRating + "/10" + "\n\nLanguage\n" + movieLanguage + "\n\nGenre\n" + movieGenre + "\n\nPlot\n" + moviePlot + "\n\nDirected by\n" + movieDirector + "\n\nWritten by\n" + movieWriter + "\n\nActors\n" + movieActors + "\n\nAwards\n" + movieAwards + "\n\nReleased\n" + movieReleased + "\n\nRuntime\n" + movieRuntime + "\n\nCountry\n" + movieCountry + "\n\nRated\n" + movieRated;
            htv.animateText(total);
        }

    }

    public class DistanceMatrixAsync extends AsyncTask<Void, Void, StringBuilder> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            // TODO Auto-generated method stub
            System.out.println("In doinbackground");

            builder = new StringBuilder();

            HttpGet httpGet = new HttpGet("https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + URLEncoder.encode(origin) + "&destinations=" + URLEncoder.encode(destination) + "&mode=driving&sensor=false&key=AIzaSyDtHpO7JxqY_QXApLnCtRnvJhH_nTRwPLc");

            HttpClient client = new DefaultHttpClient();

            try {

                HttpResponse response = client.execute(httpGet);

                StatusLine statusLine = response.getStatusLine();

                int statusCode = statusLine.getStatusCode();

                System.out.println("before status");

                if (statusCode == 200) {
                    System.out.println("In status");

                    HttpEntity entity = response.getEntity();

                    InputStream content = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));

                    String line;

                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                        System.out.println("in while");

                    }
                } else {
                    Log.e(MainActivity.class.toString(), "Failed to download file");
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return builder;

        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {


                JSONObject jsonObject = new JSONObject(builder.toString());

                System.out.println(jsonObject);

                //to access Array rows use below statement
                JSONArray rowArray = jsonObject.getJSONArray("rows");

                //now pass entire array to string variable
                String elestr = rowArray.getString(0);

                // then pass string to once again to json object
                JSONObject jsonObjectele = new JSONObject(elestr);

                //now elements array extract from the string & pass to the array
                JSONArray elementArray = jsonObjectele.getJSONArray("elements");

                System.out.println(elementArray);

                //array contain object extract value and assign to string variable
                String eledetails1, eledetails2;
                JSONObject textelement1, textelement2;

                for (int i = 0; i < elementArray.length(); i++) {

                    eledetails1 = elementArray.getJSONObject(i).getString("distance").toString();
                    eledetails2 = elementArray.getJSONObject(i).getString("duration").toString();
                    if (!eledetails1.equals(null)) {
                        textelement1 = new JSONObject(eledetails1);
                        String km = textelement1.getString("text").toString();
                        distance=km;
                    }
                    if (!eledetails2.equals(null)) {
                        textelement2 = new JSONObject(eledetails2);
                        String time = textelement2.getString("text").toString();
                        travelTime=time;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            //Toast.makeText(getApplicationContext(),"Distance: "+distance+"\nTravel time: "+travelTime,Toast.LENGTH_LONG).show();
            htv.setCharacterDelay(20);
            if(travelTime.endsWith("mins")){
                travelTime=removeLastChar(travelTime);
                travelTime=travelTime+"ute";
            }
            else if(travelTime.endsWith("hour")){
                //do nothing.
            }
            htv.animateText(destination + " is " + removeLastThreeChar(distance) + " kilometers away from " + origin + ". It's a " + travelTime + " drive.");
            tts.speak(destination + " is " + removeLastThreeChar(distance) + " kilometers away from " + origin + "., It's a " + travelTime+" drive.", TextToSpeech.QUEUE_FLUSH, null);
            origin=" ";destination=" ";distance=" ";travelTime=" ";temp=" ";
            map.setVisibility(View.VISIBLE);
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        InputStream in = new URL(loremFlickr+destination+lF).openStream();
                        bmp = BitmapFactory.decodeStream(in);
                    } catch (Exception e) {
                        // log error
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    if (bmp != null)
                        poster.setVisibility(View.VISIBLE);
                    poster.setImageBitmap(bmp);
                }

            }.execute();
        }

    }

    public String lastSpaceRemover(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length()-1)==' ') {
            str = str.substring(0, str.length()-1);
        }
        return str;
    }

    private static String removeLastThreeChar(String str) {
        return str.substring(0,str.length()-3);
    }

    private static String removeLastChar(String str) {
        return str.substring(0,str.length()-1);
    }

    public void ActivateEP(){

        SharedPreferences sp=getSharedPreferences("com.marv_preferences",MODE_PRIVATE);
        String number=sp.getString("Emergency Contact", "09766858563");
        Boolean phone=sp.getBoolean("Place Phone Call", false);
        String custom=sp.getString("Custom Message","I'm in trouble! Help me!");
        if(phone){
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + number));
            try {
                startActivity(intent);
            }catch (SecurityException e){e.printStackTrace();}
        }
        SmsManager.getDefault().sendTextMessage(number, null, custom+"\nLatitude: "+latitude+"\nLongitude: "+longitude+"\n"+mylocation+"\nGoogle Maps Link: http://maps.google.com/maps?q="+latitude+","+longitude, null, null);
    }

    public void insideMe(){
        video.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
        fullscreen.setVisibility(View.VISIBLE);
        video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video));
        video.setMediaController(null);
        video.start();
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        video.requestFocus();
    }

    public class Translator extends AsyncTask<Void, Void, StringBuilder> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            // TODO Auto-generated method stub
            System.out.println("In doinbackground");

            builder = new StringBuilder();

            HttpGet httpGet = new HttpGet("https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20160313T114655Z.f52b94c074221a90.4a35915a7e41a7b362bd9f7b6497c9ec9209b099&text="+URLEncoder.encode(translate)+"&lang="+URLEncoder.encode(language)+"&format=plain");

            HttpClient client = new DefaultHttpClient();

            try {

                HttpResponse response = client.execute(httpGet);

                StatusLine statusLine = response.getStatusLine();

                int statusCode = statusLine.getStatusCode();

                System.out.println("before status");

                if (statusCode == 200) {
                    System.out.println("In status");

                    HttpEntity entity = response.getEntity();

                    InputStream content = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));

                    String line;

                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                        System.out.println("in while");

                    }
                } else {
                    Log.e(MainActivity.class.toString(), "Failed to download file");
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //For dialog we create a new thread

            return builder;

        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            TranslateAnswer = builder.toString();
            try {
                JSONObject jObj = new JSONObject(TranslateAnswer);
                translateCode=jObj.getString("code");
                translateResult=jObj.getString("text");
                translateResult=removeLastTwoChar(translateResult.substring(2));
                br=translateResult;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(!translateCode.equals("200")){
                translateResult="I'm sorry, I couldn't translate "+translate+" to "+language+" (Error Code: "+translateCode+")";
                br="I'm sorry, I couldn't translate "+translate+" to "+language;
            }
            htv.setCharacterDelay(20);
            htv.animateText(translateResult);
            tts.speak(br, TextToSpeech.QUEUE_FLUSH, null);
        }

    }

    private static String removeLastTwoChar(String str) {
        return str.substring(0,str.length()-2);
    }

    private void setAlarm(int hours,int minutes){

        Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
        i.putExtra(AlarmClock.EXTRA_MESSAGE, "It's about time!");
        i.putExtra(AlarmClock.EXTRA_HOUR, hours);
        i.putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        startActivity(i);

    }

    private void setReminder(String remind){

        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("allDay", false);
        intent.putExtra("rrule", "FREQ=DAILY");
        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
        intent.putExtra("Reminder set by Marv", remind);
        startActivity(intent);

    }

    private void getContacts(){

        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            name+=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            name+=" ";
            phoneNumber += phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneNumber+=" ";

        }
        phones.close();

    }

    public synchronized String getMessage() {
        Message ambilpesan = new Message(getApplicationContext().getContentResolver());
        //this a return of All unread from your Inbox
        return ambilpesan.getMessageUnread();
    }

    public class CNJokesAsync extends AsyncTask<Void, Void, StringBuilder> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            // TODO Auto-generated method stub
            System.out.println("In doinbackground");

            builder = new StringBuilder();

            HttpGet httpGet = new HttpGet("http://api.icndb.com/jokes/random");

            HttpClient client = new DefaultHttpClient();

            try {

                HttpResponse response = client.execute(httpGet);

                StatusLine statusLine = response.getStatusLine();

                int statusCode = statusLine.getStatusCode();

                System.out.println("before status");

                if (statusCode == 200) {
                    System.out.println("In status");

                    HttpEntity entity = response.getEntity();

                    InputStream content = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));

                    String line;

                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                        System.out.println("in while");

                    }
                } else {
                    Log.e(MainActivity.class.toString(), "Failed to download file");
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //For dialog we create a new thread

            return builder;

        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            CNJokesAnswer = builder.toString();
            try {
                JSONObject jObj = new JSONObject(CNJokesAnswer);
                JSONObject value = jObj.getJSONObject("value");
                CNJoke=value.getString("joke");
                CNJoke=CNJoke.replaceAll("&quot;","\"");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            htv.setCharacterDelay(20);
            htv.animateText(CNJoke);
            tts.speak(CNJoke, TextToSpeech.QUEUE_FLUSH, null);
        }

    }

    public class Shodan extends AsyncTask<Void, Void, StringBuilder> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            // TODO Auto-generated method stub
            System.out.println("In doinbackground");

            builder = new StringBuilder();

            HttpGet httpGet = new HttpGet("https://api.shodan.io/tools/myip?key=L4WcmB2MGvatYVI794NYf3xCawQqfwum");

            HttpClient client = new DefaultHttpClient();

            try {

                HttpResponse response = client.execute(httpGet);

                StatusLine statusLine = response.getStatusLine();

                int statusCode = statusLine.getStatusCode();

                System.out.println("before status");

                if (statusCode == 200) {
                    System.out.println("In status");

                    HttpEntity entity = response.getEntity();

                    InputStream content = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));

                    String line;

                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                        System.out.println("in while");

                    }
                } else {
                    Log.e(MainActivity.class.toString(), "Failed to download file");
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //For dialog we create a new thread

            return builder;

        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            shodan_myip = builder.toString();
            shodan_myip=shodan_myip.replaceAll("\"","");
            htv.setCharacterDelay(20);
            htv.animateText("Your IP address is " + shodan_myip + " as seen from the internet");
            shodan_myip=shodan_myip.replaceAll("\\."," dot ");
            tts.speak("Your IP address is " + shodan_myip + " as seen from the internet", TextToSpeech.QUEUE_FLUSH, null);
        }

    }

}

