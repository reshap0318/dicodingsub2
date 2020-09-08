package com.example.dicodsub2.Activity

import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dicodsub2.R
import com.example.dicodsub2.receiver.AlarmReceiver
import kotlinx.android.synthetic.main.activity_setting.*
import java.text.SimpleDateFormat
import java.util.*

class SettingActivity : AppCompatActivity(), View.OnClickListener {
    var timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    var sharedPreferences : SharedPreferences? = null
    private lateinit var alarmReceiver: AlarmReceiver

    companion object {
        private const val DATE_PICKER_TAG = "DatePicker"
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        sharedPreferences = getSharedPreferences("setting_app", Context.MODE_PRIVATE)

        tv_waktu.text =  sharedPreferences!!.getString("WAKTU","16:00")
        sw_status.setChecked(sharedPreferences!!.getBoolean("STATUS",false))
        tv_waktu.setOnClickListener(this)
        btn_set_setting.setOnClickListener(this)

        alarmReceiver = AlarmReceiver()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_waktu -> {
//                val stringDate = changeTimeFormat(tv_waktu.text.toString())
//                val timeArray = stringDate.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//                var hour = Integer.parseInt(timeArray[0])
//                val minute = Integer.parseInt(timeArray[1])
//                showTimePicker(hour, minute)
                  showTimePicker()
            }
            R.id.btn_set_setting -> {
                setSetting()
            }
        }
    }

    fun showTimePicker(hour: Int = 0, minute: Int = 0) {
        val formatHour24 = true
        val tp = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val selectedTime = Calendar.getInstance()
            selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
            selectedTime.set(Calendar.MINUTE,minute)
            tv_waktu.text = timeFormat.format(selectedTime.time).toString()
        }, hour, minute, formatHour24)
        tp.show()
    }

    fun setSetting() {
        var waktu = tv_waktu.text.toString().trim()
        var status = sw_status.isChecked()

        val editor = sharedPreferences!!.edit()
        editor.putString("WAKTU",waktu)
        editor.putBoolean("STATUS",status)
        editor.apply()

        waktu = changeTimeFormat(waktu)
        if(status){
//            alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING)
            alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING,
                waktu, "Buka Aplikasi di Waktu $waktu")
        }else{
            alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING)
        }
        Toast.makeText(this, "Setting Berhasil", Toast.LENGTH_SHORT).show()
    }

    private fun changeTimeFormat(time: String): String{
        val date24Format = SimpleDateFormat("HH:mm",Locale.getDefault())
        return date24Format.format(timeFormat.parse(time))
    }
}