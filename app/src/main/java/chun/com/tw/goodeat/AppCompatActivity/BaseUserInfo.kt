package chun.com.tw.goodeat.AppCompatActivity

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import chun.com.tw.goodeat.R

//基本簡介
class BaseUserInfo : AppCompatActivity() , View.OnClickListener {

    //基本宣告
    lateinit var ok : Button
    lateinit var back : ImageView
    lateinit var home : ImageView
    lateinit var man_img : ImageView
    lateinit var woman_img : ImageView
    lateinit var Birthday_keyin : TextView
    lateinit var side : TextView
    lateinit var front : TextView
    lateinit var reverse : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.baseuserinfo)
        findViewById() //程式和 xml 關聯
        setOnclick() //設定點擊事件
    }

    //程式和 xml 關聯
    fun findViewById(){
        ok = findViewById(R.id.ok)
        back = findViewById(R.id.back)
        home = findViewById(R.id.home)
        man_img = findViewById(R.id.man_img)
        woman_img = findViewById(R.id.woman_img)
        Birthday_keyin = findViewById(R.id.Birthday_keyin)
        side = findViewById(R.id.side)
        front = findViewById(R.id.front)
        reverse = findViewById(R.id.reverse)
    }

    //設定點擊事件
    fun setOnclick(){
        ok.setOnClickListener(this)
        back.setOnClickListener(this)
        home.setOnClickListener(this)
        man_img.setOnClickListener(this)
        woman_img.setOnClickListener(this)
        Birthday_keyin.setOnClickListener(this)
        side.setOnClickListener(this)
        front.setOnClickListener(this)
        reverse.setOnClickListener(this)
    }

    //點擊事件
    override fun onClick(view: View?) {
        when(view?.id){
            //確定
            R.id.ok -> {
                val i = Intent(this, UserPage::class.java)
                startActivity(i)
                finish()
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
            //點選男
            R.id.man_img -> {
                man_img.setImageResource(R.mipmap.circleclick)
                woman_img.setImageResource(R.mipmap.circle)
            }
            //點選女
            R.id.woman_img -> {
                man_img.setImageResource(R.mipmap.circle)
                woman_img.setImageResource(R.mipmap.circleclick)
            }
            //生日
            R.id.Birthday_keyin -> {
                //日曆
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                //時間選擇器
                DatePickerDialog(this, { _, year, month, day ->
                    run {
                        val format = "${setDateFormat(year, month, day)}"
                        Birthday_keyin.text = format
                    }
                }, year, month, day).show()
            }
            //側面
            R.id.side -> {

            }
            //正面
            R.id.front -> {

            }
            //背面
            R.id.reverse -> {

            }
        }
    }

    //時間格式化
    private fun setDateFormat(year: Int, month: Int, day: Int): String {
        var dateString = ""
        //月份小於 10 補 0
        if(month + 1 < 10) {
            //日 小於 10 補 0
            if(day < 10) {
                dateString = "$year/0${month + 1}/0$day"
            } else {
                dateString = "$year/0${month + 1}/$day"
            }
        } else {
            //日 小於 10 補 0
            if(day < 10) {
                dateString = "$year/${month + 1}/0$day"
            } else {
                dateString = "$year/${month + 1}/$day"
            }
        }
        return dateString
    }
}