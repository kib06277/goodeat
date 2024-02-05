package chun.com.tw.goodeat.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chun.com.tw.goodeat.Data.MessageBoardData
import chun.com.tw.goodeat.R
import chun.com.tw.goodeat.recyclerview.Messageboard_RecyclerView_Adapter

//公告版
class Messageboard : AppCompatActivity() , View.OnClickListener {

    //基本宣告
    lateinit var back : ImageView
    lateinit var menu : ImageView
    lateinit var home : ImageView
    lateinit var recyclerView: RecyclerView

    private var messageboard_recyclerView_adapter : Messageboard_RecyclerView_Adapter? = null //公告版 資料調適器
    private val messageboardarraylist = ArrayList<MessageBoardData>() //公告版資料

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.messageboard_layout)
        findViewById() //程式和 xml 關聯
        setOnclick() //設定點擊事件
        setrecyclerView() //設定 recyclerView
    }

    //程式和 xml 關聯
    fun findViewById(){
        back = findViewById(R.id.back)
        menu = findViewById(R.id.menu)
        home = findViewById(R.id.home)
        recyclerView = findViewById(R.id.recyclerView)
    }

    //設定點擊事件
    fun setOnclick(){
        back.setOnClickListener(this)
        menu.setOnClickListener(this)
        home.setOnClickListener(this)
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

    //設定 recyclerView
    fun setrecyclerView(){
        //建立容器
        var messageboarddata = MessageBoardData()

        //建立假資料
        messageboarddata.title = "最新飲食計畫"
        messageboardarraylist.add(messageboarddata)

        messageboard_recyclerView_adapter = Messageboard_RecyclerView_Adapter(this , messageboardarraylist)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = messageboard_recyclerView_adapter
    }
}