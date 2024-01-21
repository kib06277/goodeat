package chun.com.tw.goodeat.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import chun.com.tw.goodeat.R
import chun.com.tw.goodeat.View.SingleCalendarView

//飲食月曆
class Dietcalendar : AppCompatActivity() , View.OnClickListener {

    //基本宣告
    lateinit var back : ImageView
    lateinit var menu : ImageView
    lateinit var home : ImageView
    lateinit var calendar_view : SingleCalendarView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dietcalendar_layout)
        findViewById() //程式和 xml 關聯
        setOnclick() //設定點擊事件
    }

    //程式和 xml 關聯
    fun findViewById(){
        back = findViewById(R.id.back)
        menu = findViewById(R.id.menu)
        home = findViewById(R.id.home)
        calendar_view = findViewById(R.id.calendar_view)
    }

    //設定點擊事件
    fun setOnclick(){
        back.setOnClickListener(this)
        menu.setOnClickListener(this)
        home.setOnClickListener(this)
        calendar_view.setOnSingleDateSelectedListener {view, selectedDate ->
            val i = Intent(this, MenstruationLog::class.java)
            startActivity(i)
        }
    }

    //點擊事件
    override fun onClick(view: View?) {
        when(view?.id){
            //菜單
            R.id.menu -> {
                val i = Intent(this, Menu::class.java)
                startActivity(i)
            }
            //返回
            R.id.back -> {
                val i = Intent(this, UserPage::class.java)
                startActivity(i)
                finish()
            }
            //首頁
            R.id.home -> {
                val i = Intent(this, UserPage::class.java)
                startActivity(i)
                finish()
            }
        }
    }
}