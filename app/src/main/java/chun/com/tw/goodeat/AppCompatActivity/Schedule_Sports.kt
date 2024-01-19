package chun.com.tw.goodeat.AppCompatActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import chun.com.tw.goodeat.R

//作息與運動
class Schedule_Sports : AppCompatActivity() , View.OnClickListener {

    //基本宣告
    lateinit var ok : Button
    lateinit var back : ImageView
    lateinit var home : ImageView
    lateinit var sleep_spinner : Spinner
    lateinit var getup_spinner : Spinner
    lateinit var sleephours_spinner : Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.schedule_sports_layout)
        findViewById() //程式和 xml 關聯
        setOnclick() //設定點擊事件
        setSpiner() //設定下拉式選單
    }

    //程式和 xml 關聯
    fun findViewById(){
        ok = findViewById(R.id.ok)
        back = findViewById(R.id.back)
        home = findViewById(R.id.home)
        sleep_spinner = findViewById(R.id.sleep_spinner)
        getup_spinner = findViewById(R.id.getup_spinner)
        sleephours_spinner = findViewById(R.id.sleephours_spinner)
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
        }
    }

    //設定 Spinner
    @SuppressLint("ResourceType")
    fun setSpiner(){
        try {
            val adapter = ArrayAdapter.createFromResource(this , R.array.time , R.layout.spinner_layout)
            adapter.setDropDownViewResource(R.layout.spinner_layout)
            sleep_spinner.adapter = adapter
            getup_spinner.adapter = adapter
            sleephours_spinner.adapter = adapter
        } catch ( e : Exception) {
            Log.i("Error" , "e = $e")
        }
    }
}