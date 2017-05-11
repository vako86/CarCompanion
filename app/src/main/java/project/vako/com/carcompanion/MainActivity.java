package project.vako.com.carcompanion;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.math.BigDecimal;
import android.icu.text.DecimalFormat;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText milageEditText;
    private Button calcButton;
    private ImageView flag;

    private TextView showMilageTextView;
    private Switch milageTypeSwitch;
    private TextView SwithResultText;
    private final String format = "%.2f";
    //DecimalFormat round = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;



        SharedPreferences switchPrefs = getSharedPreferences("com.vako.milageconverter", MODE_PRIVATE);
        milageTypeSwitch.setChecked(switchPrefs.getBoolean("switchPosition", true));


        if (milageTypeSwitch.isChecked()) {
            SwithResultText.setText("KM/L");
            flag.setBackgroundResource(R.drawable.flag_jp);
        } else {
            SwithResultText.setText("MPG");
            flag.setBackgroundResource(R.drawable.flag_us);
        }




        milageTypeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    SwithResultText.setText("KM/L");
                    flag.setBackgroundResource(R.drawable.flag_jp);

                    SharedPreferences.Editor editor = getSharedPreferences("com.vako.milageconverter", MODE_PRIVATE).edit();
                    editor.putBoolean("switchPosition", true);
                    editor.commit();

                } else {
                    SwithResultText.setText("MPG");
                    flag.setBackgroundResource(R.drawable.flag_us);

                    SharedPreferences.Editor editor = getSharedPreferences("com.vako.milageconverter", MODE_PRIVATE).edit();
                    editor.putBoolean("switchPosition", false);
                    editor.commit();
                }
            }
        });




        //set up buttons (event listeners)
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //convert from MPG function


                String editTextVal = milageEditText.getText().toString();


                if (editTextVal.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "შეიყვანეთ მაჩვენებელი", Toast.LENGTH_SHORT).show();
                } else {
                    if (milageTypeSwitch.isChecked()) {
                        //KM/L (JDM)
                        double DoubleEditText = Double.parseDouble(editTextVal);
                        convertFromJdm(DoubleEditText);

                        double convertedVal = convertFromJdm(DoubleEditText);
                        String stringResult = String.format(format,convertedVal);//String.valueOf(round.format(convertedVal));
                        showMilageTextView.setText(stringResult + " ლ/100კმ");

                    } else {
                        //MPG
                        double DoubleEditText = Double.parseDouble(editTextVal);
                        convertFromMpg(DoubleEditText);

                        double convertedVal = convertFromMpg(DoubleEditText);
                        String stringResult = String.format(format,convertedVal);
                        showMilageTextView.setText(stringResult + " ლ/100კმ");
                    }




                    //keyboard closing
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }

            }
        });






    }

    public double convertFromMpg(double mpgVal) {
        double resultMpg;
        resultMpg = (235.215 / mpgVal);

        return resultMpg;
    }

    public double convertFromJdm(double jdmVal) {
        double resultJdm;
        resultJdm = (100 / jdmVal);

        return resultJdm;
    }
    }
}
