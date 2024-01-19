package chun.com.tw.goodeat.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import chun.com.tw.goodeat.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//歡迎頁面
class FrontActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frontactivity) //指定畫面

        // 啟動協程
        GlobalScope.launch {
            // 等待五秒
            delay(1000)

            //跳轉首页
            val intent = Intent(this@FrontActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
