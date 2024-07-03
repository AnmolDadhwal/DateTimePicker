package com.task.datetimepicker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.task.datetimepicker.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding?.btnPickDate?.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog=DatePickerDialog(this, { _, year, month, dateOfMonth ->
                val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())
                calendar.set(year, month, dateOfMonth)
                binding?.tvDatePicker?.setText(simpleDateFormat.format(calendar.time)) },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE))
            calendar.add(Calendar.DAY_OF_YEAR,-10)
            datePickerDialog.datePicker.minDate = calendar.timeInMillis
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.add(Calendar.DAY_OF_YEAR, 10)
            datePickerDialog.datePicker.maxDate = calendar.timeInMillis
            datePickerDialog.show()
        }
        binding?.btnPickTime?.setOnClickListener {
            val calendar = Calendar.getInstance()
            val timeFormat = SimpleDateFormat("HH:mm:ss a", Locale.getDefault())
            val hour=calendar.get(Calendar.HOUR_OF_DAY)
            val minute=calendar.get(Calendar.MINUTE)
            val startingHour=9    //9am
            val finishingHour=18 //6pm
            val timeSetListener=TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                if (hourOfDay<startingHour||(hourOfDay==startingHour && minute<0)){
                    Toast.makeText(this,"Select Valid Time",Toast.LENGTH_SHORT).show()
                }else  if (hourOfDay>finishingHour||(hourOfDay==finishingHour && minute>=0)){
                    Toast.makeText(this,"Select Valid Time",Toast.LENGTH_SHORT).show()
                }else{
                    calendar.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    calendar.set(Calendar.MINUTE,minute)
                    binding?.tvTimePicker?.setText(timeFormat.format(calendar.time))
                }
            }
            val timePickerDialog=TimePickerDialog(this,timeSetListener, hour, minute,false
            )
            timePickerDialog.show()
        }
    }
}