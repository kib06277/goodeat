package chun.com.tw.goodeat.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import chun.com.tw.goodeat.R

//菜單
class Menu : AppCompatActivity() , View.OnClickListener {

    //基本宣告
    lateinit var back : ImageView
    lateinit var dietcalendar : TextView
    lateinit var diary : TextView
    lateinit var messageboard : TextView
    lateinit var reserve : TextView
    lateinit var portionsize : TextView
    lateinit var aboutwe : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_layout)
        findViewById() //程式和 xml 關聯
        setOnclick() //設定點擊事件
    }

    //程式和 xml 關聯
    fun findViewById(){
        back = findViewById(R.id.back)
        dietcalendar = findViewById(R.id.dietcalendar)
        diary = findViewById(R.id.diary)
        messageboard = findViewById(R.id.messageboard)
        reserve = findViewById(R.id.reserve)
        portionsize = findViewById(R.id.portionsize)
        aboutwe = findViewById(R.id.aboutwe)
    }

    //設定點擊事件
    fun setOnclick(){
        back.setOnClickListener(this)
        dietcalendar.setOnClickListener(this)
        diary.setOnClickListener(this)
        messageboard.setOnClickListener(this)
        reserve.setOnClickListener(this)
        portionsize.setOnClickListener(this)
        aboutwe.setOnClickListener(this)
    }

    //點擊事件
    override fun onClick(view: View?) {
        when(view?.id){
            //返回
            R.id.back -> {
                val i = Intent(this, UserPage::class.java)
                startActivity(i)
                finish()
            }
            //飲食日曆
            R.id.dietcalendar -> {
                val i = Intent(this, Dietcalendar::class.java)
                startActivity(i)
                finish()
            }
            //每天日記
            R.id.diary -> {
                val i = Intent(this, Diary::class.java)
                startActivity(i)
                finish()
            }
            //公告版
            R.id.messageboard -> {
                val i = Intent(this, Messageboard::class.java)
                startActivity(i)
                finish()
            }
            //預約跟進
            R.id.reserve -> {
                val i = Intent(this, Reserve::class.java)
                startActivity(i)
                finish()
            }
            //飲食份量
            R.id.portionsize -> {
                val i = Intent(this, Portionsize::class.java)
                startActivity(i)
                finish()
            }
            //關於我們
            R.id.aboutwe -> {
                val i = Intent(this, Aboutwe::class.java)
                startActivity(i)
                finish()
            }
        }
    }
}