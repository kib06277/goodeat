package chun.com.tw.goodeat.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
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
    var dialog1: AlertDialog? = null

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
            ShowDialog() //圖片功能選擇 Dialog
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

    //圖片功能選擇 Dialog
    private fun ShowDialog(){
        val dialog = AlertDialog.Builder(this) //建立 AlertDialog

        val view: View = layoutInflater.inflate(R.layout.calendardialog_layout, null) //指定 layout

        dialog.setView(view)

        //xml 和程式關聯
        var transparentlove : ImageView = view.findViewById(R.id.transparentlove)
        var calendar : ImageView = view.findViewById(R.id.calendar)
        var menstruationlog : TextView = view.findViewById(R.id.menstruationlog)
        var diarylook : TextView = view.findViewById(R.id.diarylook)

        //設置按鈕監聽事件
        //紀錄月經
        transparentlove.setOnClickListener {
            val i = Intent(this, MenstruationLog::class.java)
            startActivity(i)
            finish()
        }

        //每天日記總覽
        calendar.setOnClickListener {
            val i = Intent(this, Diary::class.java)
            startActivity(i)
            finish()
        }

        //紀錄月經
        menstruationlog.setOnClickListener {
            val i = Intent(this, MenstruationLog::class.java)
            startActivity(i)
            finish()
        }

        //每天日記總覽
        diarylook.setOnClickListener {
            val i = Intent(this, Diary::class.java)
            startActivity(i)
            finish()
        }

        //建構 dialog 並顯示
        dialog1 = dialog.create()
        dialog1!!.show()

        //設置對話框位置偏下
        val window: Window? = dialog1!!.getWindow()
        window?.setBackgroundDrawable(null) //重設 background

        val wlp = window?.attributes
        if (wlp != null) {
            wlp.gravity = Gravity.BOTTOM
        }
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        if (wlp != null) {
            wlp.width = display.width
        }
        if (window != null) {
            window.attributes = wlp
        }
    }
}