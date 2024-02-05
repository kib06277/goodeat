package chun.com.tw.goodeat.recyclerview

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import chun.com.tw.goodeat.AppCompatActivity.MessageBoard_Details
import chun.com.tw.goodeat.Data.MessageBoardData
import chun.com.tw.goodeat.R

//公布版調適器
class Messageboard_RecyclerView_Adapter(private val context: Context , private val mData: List<MessageBoardData>) : RecyclerView.Adapter<Messageboard_RecyclerView_Adapter.ViewHolder>() {

    //綁定介面
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.img)
        val title: TextView = view.findViewById(R.id.title)
    }

    //生成畫面
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.messageboard_recyclerview_layout, parent, false)
        return ViewHolder(v)
    }

    //給介面指派動作
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = mData[position].title

        //公告版詳細
        holder.itemView.setOnClickListener {
            val intent = Intent(context, MessageBoard_Details::class.java)
            val bundle = Bundle().apply {
                putInt("sources", position)
            }
            intent.putExtra("Data", bundle)
            context.startActivity(intent)
        }
    }

    //獲得總數
    override fun getItemCount(): Int {
        return mData.size
    }
}