package chun.com.tw.goodeat
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//歡迎頁面
class frontActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frontactivity) //指定畫面

        // 啟動協程
        GlobalScope.launch {
            // 等待五秒
            delay(5000)

            //跳轉首页
            val intent = Intent(this@frontActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
