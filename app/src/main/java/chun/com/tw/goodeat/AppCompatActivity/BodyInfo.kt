package chun.com.tw.goodeat.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import chun.com.tw.goodeat.R

//身體資訊
class BodyInfo : AppCompatActivity() , View.OnClickListener {

    //基本宣告
    lateinit var ok : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bodyinfo)
        findViewById() //程式和 xml 關聯
        setOnclick() //設定點擊事件
    }

    //程式和 xml 關聯
    fun findViewById(){
        ok = findViewById(R.id.ok)
    }

    //設定點擊事件
    fun setOnclick(){
        ok.setOnClickListener(this)
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
        }
    }
}