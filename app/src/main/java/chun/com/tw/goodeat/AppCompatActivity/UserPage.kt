package chun.com.tw.goodeat.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import chun.com.tw.goodeat.R

//使用者資訊
class UserPage : AppCompatActivity() , View.OnClickListener {

    //基本宣告
    lateinit var menu : ImageView
    lateinit var baseuserinfo : TextView
    lateinit var healthystate : TextView
    lateinit var eatedstate : TextView
    lateinit var schedule_sports : TextView
    lateinit var target_setting : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.userpage)
        findViewById() //程式和 xml 關聯
        setOnclick() //設定點擊事件
    }

    //程式和 xml 關聯
    fun findViewById(){
        menu = findViewById(R.id.menu)
        baseuserinfo = findViewById(R.id.baseuserinfo)
        healthystate = findViewById(R.id.healthystate)
        eatedstate = findViewById(R.id.eatedstate)
        schedule_sports = findViewById(R.id.schedule_sports)
        target_setting = findViewById(R.id.target_setting)
    }

    //設定點擊事件
    fun setOnclick(){
        menu.setOnClickListener(this)
        baseuserinfo.setOnClickListener(this)
        healthystate.setOnClickListener(this)
        eatedstate.setOnClickListener(this)
        schedule_sports.setOnClickListener(this)
        target_setting.setOnClickListener(this)
    }

    //點擊事件
    override fun onClick(view: View?) {
        when(view?.id){
            //菜單
            R.id.menu -> {
                val i = Intent(this, Menu::class.java)
                startActivity(i)
            }
            //基本簡介
            R.id.baseuserinfo -> {
                val i = Intent(this, BaseUserInfo::class.java)
                startActivity(i)
                finish()
            }
            //健康狀態
            R.id.healthystate -> {
                val i = Intent(this, HealthyState::class.java)
                startActivity(i)
                finish()
            }
            //飲食狀態
            R.id.eatedstate -> {
                val i = Intent(this, EatedState::class.java)
                startActivity(i)
                finish()
            }
            //作息與運動
            R.id.schedule_sports -> {
                val i = Intent(this, Schedule_Sports::class.java)
                startActivity(i)
                finish()
            }
            //目標設定
            R.id.target_setting -> {
                val i = Intent(this, Target_Setting::class.java)
                startActivity(i)
                finish()
            }
        }
    }
}