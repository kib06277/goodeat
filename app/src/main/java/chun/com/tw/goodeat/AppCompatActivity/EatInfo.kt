package chun.com.tw.goodeat.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import chun.com.tw.goodeat.R

//飲食資訊
class EatInfo : AppCompatActivity() , View.OnClickListener {

    //基本宣告
    lateinit var ok : Button
    lateinit var back : ImageView
    lateinit var home : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.eatinfo)
        findViewById() //程式和 xml 關聯
        setOnclick() //設定點擊事件
    }

    //程式和 xml 關聯
    fun findViewById(){
        ok = findViewById(R.id.ok)
        back = findViewById(R.id.back)
        home = findViewById(R.id.home)
    }

    //設定點擊事件
    fun setOnclick(){
        ok.setOnClickListener(this)
        back.setOnClickListener(this)
        home.setOnClickListener(this)
    }

    //點擊事件
    override fun onClick(view: View?) {
        when(view?.id){
            //確定
            R.id.ok -> {
                val i = Intent(this, Diary::class.java)
                startActivity(i)
                finish()
            }
            //返回
            R.id.back -> {
                val i = Intent(this, Diary::class.java)
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