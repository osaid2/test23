package com.example.myapplication23

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() , SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var magSensor: Sensor? = null
    private var text1: TextView? = null
    private var text2: TextView? = null
    private var red: TextView? = null
    private var blue: TextView? = null
    private var green: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text1 = findViewById(R.id.textView)
        text2 = findViewById(R.id.textView2)
        red = findViewById(R.id.red)
        blue = findViewById(R.id.blue)
        green = findViewById(R.id.green)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        for (sensor in deviceSensors) {
            Log.d("sensorsList", sensor.name)
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            Log.d("isSensorFound", "Sensor found")
            magSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        } else {
            Log.d("isSensorFound", "Sensor not found")
        }

    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, magSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {

            val x = Math.abs(event.values[0])
            val y = Math.abs(event.values[1])
            val z = Math.abs(event.values[2])

            text1!!.text = "x = $x \n y = $y \n z = $z \n Max Value:${event.sensor.maximumRange}"

            if (x > y && x > z) {
                text2!!.text = "X"

            } else if (y > x && y > z) {

                text2!!.text = "Y"
            } else {
                text2!!.text = "Z"

            }

            if( x >= 50 && x < 200){
                green?.visibility = View.VISIBLE
                red?.visibility = View.INVISIBLE
                blue?.visibility = View.INVISIBLE
            } else if (x >= 200 && x < 500){
                blue?.visibility = View.VISIBLE
                red?.visibility = View.INVISIBLE
                green?.visibility = View.INVISIBLE
            } else if (x >= 500 ){
                red?.visibility = View.VISIBLE
                blue?.visibility = View.INVISIBLE
                green?.visibility = View.INVISIBLE
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        // Do something here if sensor accuracy changes.
    }
}
//ui texy .layer.cornerRadius = 25
// uiText.leftView = UIView(Frame: CGRect( width = 20 ) )
// uiText.leftViewMode = .always