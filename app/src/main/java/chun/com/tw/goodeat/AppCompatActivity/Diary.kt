package chun.com.tw.goodeat.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import chun.com.tw.goodeat.R

//每天日記
class Diary : AppCompatActivity() , View.OnClickListener {

    //基本宣告
    lateinit var back : ImageView
    lateinit var menu : ImageView
    lateinit var home : ImageView
    lateinit var breakfast_allkindsportionsize : TextView
    lateinit var Lunch_allkindsportionsize : TextView
    lateinit var dinner_allkindsportionsize : TextView
    lateinit var other_eat_allkindsportionsize : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.diary_layout)
        findViewById() //程式和 xml 關聯
        setOnclick() //設定點擊事件
    }

    //程式和 xml 關聯
    fun findViewById(){
        back = findViewById(R.id.back)
        menu = findViewById(R.id.menu)
        home = findViewById(R.id.home)
        breakfast_allkindsportionsize = findViewById(R.id.breakfast_allkindsportionsize)
        Lunch_allkindsportionsize = findViewById(R.id.Lunch_allkindsportionsize)
        dinner_allkindsportionsize = findViewById(R.id.dinner_allkindsportionsize)
        other_eat_allkindsportionsize = findViewById(R.id.other_eat_allkindsportionsize)
    }

    //設定點擊事件
    fun setOnclick(){
        back.setOnClickListener(this)
        menu.setOnClickListener(this)
        home.setOnClickListener(this)
        breakfast_allkindsportionsize.setOnClickListener(this)
        Lunch_allkindsportionsize.setOnClickListener(this)
        dinner_allkindsportionsize.setOnClickListener(this)
        other_eat_allkindsportionsize.setOnClickListener(this)
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
            //早餐
            R.id.breakfast_allkindsportionsize -> {
                val i = Intent(this, EatInfo::class.java)
                startActivity(i)
            }
            //午餐
            R.id.Lunch_allkindsportionsize -> {
                val i = Intent(this, EatInfo::class.java)
                startActivity(i)
                finish()
            }
            //晚餐
            R.id.dinner_allkindsportionsize -> {
                val i = Intent(this, EatInfo::class.java)
                startActivity(i)
                finish()
            }
            //額外
            R.id.other_eat_allkindsportionsize -> {
                val i = Intent(this, EatInfo::class.java)
                startActivity(i)
                finish()
            }
        }
    }
}