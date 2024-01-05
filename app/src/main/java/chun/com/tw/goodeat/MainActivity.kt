package chun.com.tw.goodeat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//首頁-登入頁面
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //跳轉首页
        val intent = Intent(this@MainActivity, BodyInfo::class.java)
        startActivity(intent)
        finish()
    }
}