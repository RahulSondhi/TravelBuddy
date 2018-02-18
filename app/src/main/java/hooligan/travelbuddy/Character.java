package hooligan.travelbuddy;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import java.security.Timestamp;
import java.sql.Time;

import hooligan.QRScanner.QRActivity;

public class Character extends AppCompatActivity implements SensorEventListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    SensorManager sensorManager;

    TextView stepCount,healthCount,hungerCount,strengthCount,staminaCount,levelCount;

    float initStep;
    int level = 0;
    int levelmeupCheck;
    int time = new Time(System.currentTimeMillis()).getMinutes();
    boolean running = false;
    boolean starting = true;
    boolean debug = true;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCount = (TextView) findViewById(R.id.stepCount);
        healthCount = (TextView) findViewById(R.id.health);
        hungerCount = (TextView) findViewById(R.id.hunger);
        strengthCount = (TextView) findViewById(R.id.strength);
        staminaCount = (TextView) findViewById(R.id.stamina);
        levelCount = (TextView) findViewById(R.id.level);

        if(debug){
            levelmeupCheck = 10;
        }else{
            levelmeupCheck = 100;
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Character.this, QRActivity.class));
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_character, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_character, container, false);
            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1){
            }
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (sensor != null){
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
        // IF YOU PAUSE THEN STOP DETECTING STEPS
        //sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(starting){
            stepCount.setText("0/100");
            initStep = sensorEvent.values[0];
            starting = false;
        }else{
            float tempSteps = sensorEvent.values[0] - initStep;
            if(tempSteps >= levelmeupCheck){

                levelUp(sensorEvent);

            }else {
                stepCount.setText(String.valueOf((int)(sensorEvent.values[0] - initStep))+" / "+String.valueOf(levelmeupCheck));
            }
        }
    }

    public void levelUp(SensorEvent sensorEvent){
        int level = Integer.parseInt(levelCount.getText().toString()) + 1;
        int tempPower = 100;
        int tempStamina = 100;
        int tempHealth = 100;
        int levelmeup;

        if(debug){
            levelmeup = 10;
        }else{
            levelmeup = 100;
        }

        double powerup = 5;
        int staminaup = 10;
        int healthup = 15;
        double levelFactor = 1.20;
        double powerFactor = 1.05;
        double staminaFactor = 1.20;

        for(int i = 0; i < level;i++){
            levelmeup = (int)(levelmeup * levelFactor);
            tempPower = (int)(tempPower + powerup);
            powerup = powerup * powerFactor;

            if((i % 5 == 0) && (i != 0)){
                tempStamina = tempStamina + staminaup;
                staminaup = (int)(staminaup * staminaFactor);
            }

            if((i % 10 == 0) && (i != 0)){
                tempHealth = tempHealth + healthup;
                healthup = (int)(healthup * staminaFactor);
            }
        }

        initStep = sensorEvent.values[0];

        levelCount.setText(String.valueOf(level));
        strengthCount.setText(String.valueOf(tempPower));
        healthCount.setText(String.valueOf(tempHealth));
        staminaCount.setText(String.valueOf(tempStamina));
        String tempText = "0 / "+String.valueOf(levelmeup);
        stepCount.setText(tempText);
        levelmeupCheck = levelmeup;
    }

    public void starve() throws InterruptedException {
        int timeDiff;
        int timeCurr = new Time(System.currentTimeMillis()).getMinutes();
        int hunger = Integer.parseInt(hungerCount.getText().toString());

        if(debug){
            timeDiff = 1;
        }else{
            timeDiff = 15;
        }

        if((timeCurr - time) > timeDiff){
            time = timeCurr;
            hunger--;
            hungerCount.setText(String.valueOf(hunger));
        }

        Thread.sleep(1000*60);
        starve();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}
