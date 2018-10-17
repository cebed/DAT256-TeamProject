package com.semcon.oil.carpoc;

import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.car.*;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.CarSensorEvent;
import android.car.hardware.CarSensorManager;
import android.car.hardware.CarVendorExtensionManager;
import android.car.hardware.cabin.CarCabinManager;
import android.car.hardware.hvac.CarHvacManager;
import android.content.ComponentName;

import com.semcon.oil.carpoc.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

import static android.car.hardware.CarSensorEvent.IGNITION_STATE_OFF;
import static android.car.hardware.CarSensorEvent.IGNITION_STATE_ON;

// Main activity displaying total points, passengers and current trip points.
public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView totalScoreTxt, tripScoreTxt;
    private static int count = 0;
    private int totalScore = 0;
    Car car;
   // CounterSingleton Csingleton;
    Handler handler;
    ServiceConnection serviceConnection;
    CarSensorManager sensorManager;
    CarHvacManager carHvacManager;
    CarVendorExtensionManager carVendorExtensionManager;
    CarCabinManager carCabinManager;

    CarSensorManager.OnSensorChangedListener ignitionStateChangedListener;

    CarCabinManager.CarCabinEventCallback beltChangedListener;
    CarHvacManager.CarHvacEventCallback carHvacEventCallback;

    private static final int speedDataPermissionMagicNumber = 42;
    private static final int NUM_SEATS = 4;
    private boolean useSpeedData = false;
    private boolean isDriving = false;
    private static List<Boolean> seatBelts;

    private Thread tripPtsThread;

    @Override
    public void onStart() {
        super.onStart();
        totalScore = FileUtils.loadScore(getFilesDir());
    }

    @Override
    public void onStop() {
        super.onStop();
        //saveScore();
        FileUtils.saveScore(getFilesDir(), totalScore + count);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //totalScore = loadScore();

        tripScoreTxt = findViewById(R.id.mainText);
        totalScoreTxt=(TextView) findViewById(R.id.totalScore);
        totalScoreTxt.setText("Total Score: " + totalScore);
        totalScoreTxt.setTextColor(Color.BLACK);

        seatBelts = new ArrayList<>();
        for (int i = 0; i < NUM_SEATS; i++)
            seatBelts.add(false);

        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain2Activity();
            }
        });

        tripPtsThread = getTripPtsThread();
        //t.start();

        ignitionStateChangedListener = new CarSensorManager.OnSensorChangedListener() {
            @Override
            public void onSensorChanged(CarSensorEvent carSensorEvent) {
                Log.d("CAR", "Ignition changed event...");
                for (int i=0; i < carSensorEvent.intValues.length; i++) {
                    Log.d("CAR", "Ignition state values= " + carSensorEvent.intValues[i]);
                    if (carSensorEvent.intValues[i] == IGNITION_STATE_OFF) {
                        for (int p = 0; p < seatBelts.size(); p++)
                            seatBelts.set(p, false);
                        setIsDriving(false);
                        setPassengersImage(0);
                        //saveScore();
                        totalScore = totalScore + count;
                        count = 0;
                        tripScoreTxt.setText("" + 0);
                        totalScoreTxt.setText("Total Score: " + totalScore);
                        FileUtils.saveScore(getFilesDir(), totalScore);
                    } else if (carSensorEvent.intValues[i] == IGNITION_STATE_ON && !getIsDriving
                            () && !tripPtsThread.isAlive()) {
                        setIsDriving(true);
                        tripPtsThread.interrupt();
                        tripPtsThread = getTripPtsThread();
                        tripPtsThread.start();
                    }
                }
            }
        };

        beltChangedListener = new CarCabinManager.CarCabinEventCallback() {
            @Override
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                Log.d("CAR", "Cabin onChangeEvent with ID: " + carPropertyValue.getPropertyId());
                ImageView i = findViewById(R.id.carImageView);
                boolean updated = false;
                boolean beltBuckled = false;
                if (carPropertyValue.getValue() instanceof Boolean)
                    beltBuckled = (boolean) carPropertyValue.getValue();

                switch (carPropertyValue.getPropertyId()) {
                    case CarCabinManager.ID_DOOR_LOCK:
                        Log.d("TAG", "Belt 4 changed to: " +
                                carPropertyValue.getValue());
                        updated = true;
                        seatBelts.set(3, beltBuckled);
                        break;
                }
                if (updated) {
                    int nPassengers = getNumPassengers();
                    Log.d("CAR", "num passengers: " + nPassengers);
                    setPassengersImage(nPassengers);
                }
            }

            @Override
            public void onErrorEvent(int i, int i1) {

            }
        };

        carHvacEventCallback = new CarHvacManager.CarHvacEventCallback() {
            @Override
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                ImageView i = findViewById(R.id.carImageView);
                boolean beltBuckled = false;
                if (carPropertyValue.getValue() instanceof Boolean)
                    beltBuckled = (boolean) carPropertyValue.getValue();

                boolean updated = false;
                switch (carPropertyValue.getPropertyId() ) {
                    case CarHvacManager.ID_ZONED_AC_ON:
                        Log.d("CAR", "Belt 1 changed to: " +
                                carPropertyValue.getValue());
                        updated = true;
                        seatBelts.set(0, beltBuckled);
                        break;
                    case CarHvacManager.ID_ZONED_AUTOMATIC_MODE_ON:
                        Log.d("CAR", "Belt 2 changed to: " +
                                carPropertyValue.getValue());
                        updated = true;
                        seatBelts.set(1, beltBuckled);
                        break;
                    case CarHvacManager.ID_ZONED_HVAC_POWER_ON:
                        Log.d("CAR", "Belt 3 changed to: " +
                                carPropertyValue.getValue());
                        updated = true;
                        seatBelts.set(2, beltBuckled);
                        break;
                }
                if (updated) {
                    int nPassengers = getNumPassengers();
                    Log.d("CAR", "num passengers: " + nPassengers);
                    setPassengersImage(nPassengers);
                }

            }

            @Override
            public void onErrorEvent(int i, int i1) {

            }
        };

        // get permissions
        if (ContextCompat.checkSelfPermission(this, Car.PERMISSION_SPEED)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("CAR", "Requesting permission to use speed events.");
            ActivityCompat.requestPermissions(this,
                    new String[] {Car.PERMISSION_SPEED}, speedDataPermissionMagicNumber);
        } else {
            // permission already given
            Log.d("CAR", "Permission available to use speed events.");
            useSpeedData = true;
        }

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.d("CAR", "Service connected");

                try {
                    // connect
                    sensorManager = (CarSensorManager) car.getCarManager(Car.SENSOR_SERVICE);
                    carHvacManager = (CarHvacManager) car.getCarManager(Car.HVAC_SERVICE);
                    carVendorExtensionManager = (CarVendorExtensionManager) car.getCarManager(Car
                            .VENDOR_EXTENSION_SERVICE);
                    carCabinManager = (CarCabinManager) car.getCarManager(Car.CABIN_SERVICE);

                    // report connections
                    if (sensorManager != null)
                        Log.d("CAR","Sensor manager received connected");
                    if (carHvacManager != null)
                        Log.d("CAR", "HVAC manager received connected");
                    if (carVendorExtensionManager != null)
                        Log.d("CAR","carVendor manager received connected");
                    if (carCabinManager != null)
                        Log.d("CAR", "cabin manager received connected");

                    // hook up handlers
                    carHvacManager.registerCallback(carHvacEventCallback);

                    carVendorExtensionManager.registerCallback(new CarVendorExtensionManager.CarVendorExtensionCallback() {
                        @Override
                        public void onChangeEvent(CarPropertyValue carPropertyValue) {
                            Log.d("TAG", "Vendor extension onChangeEvent: " + carPropertyValue
                                    .toString());
                        }

                        @Override
                        public void onErrorEvent(int i, int i1) {

                        }
                    });

                    carCabinManager.registerCallback(beltChangedListener);

                    sensorManager.registerListener(ignitionStateChangedListener,
                            CarSensorManager.SENSOR_TYPE_IGNITION_STATE,
                            CarSensorManager.SENSOR_RATE_NORMAL);
                } catch (CarNotConnectedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.d("CAR", "Service disconnected");
                //saveScore();
                FileUtils.saveScore(getFilesDir(), totalScore + count);
            }
        };

        car = Car.createCar(this, serviceConnection, handler);
        Log.d("CAR", "Car created");

        car.connect();
        Log.d("CAR", "Car connected");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == speedDataPermissionMagicNumber
                && ContextCompat.checkSelfPermission(this, Car.PERMISSION_SPEED)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d("CAR", "Permission granted to use speed events (CAR_SPEED).");
            useSpeedData = true;
        } else {
            Log.d("CAR", "Permission NOT granted to use speed events.");
        }
    }

    private void setPassengersImage(int nPassengers) {
        ImageView i = findViewById(R.id.carImageView);
        switch (nPassengers) {
            case 0:
                i.setImageDrawable(getDrawable(R.mipmap.car_0));
                break;
            case 1:
                i.setImageDrawable(getDrawable(R.mipmap.car_1_new));
                break;
            case 2:
                i.setImageDrawable(getDrawable(R.mipmap.car_2_new));
                break;
            case 3:
                i.setImageDrawable(getDrawable(R.mipmap.car_3_new));
                break;
            case 4:
                i.setImageDrawable(getDrawable(R.mipmap.car_4_new));
                break;
        }
    }

    // Returns number of passengers currently in the car.
    public static int getNumPassengers() {
        int nPassengers = 0;
        for (boolean belt: seatBelts)
            nPassengers += belt ? 1 : 0;
        return nPassengers;
    }

    // Returns a background thread updating current trip points.
    private Thread getTripPtsThread() {
        return new Thread(){
            @Override
            public void run(){
                while(!isInterrupted() && getIsDriving()){

                    try{
                        Thread.sleep(1000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                CounterSingleton c =  CounterSingleton.getInstance();
                                count = c.getCounter();
                                //count++;
                                if (getIsDriving())
                                    tripScoreTxt.setText(String.valueOf(count));
                            }
                        });
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();}
                }
            }
        };
    }

    // Returns if engine is on or not.
    private synchronized boolean getIsDriving() {
        return isDriving;
    }

    private synchronized void setIsDriving(boolean isDriving) {
        MainActivity.this.isDriving = isDriving;
    }

    // Opens Store-MainActivity.
    public void openMain2Activity () {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

}

