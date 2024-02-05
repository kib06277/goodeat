package chun.com.tw.goodeat.AppCompatActivity

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import chun.com.tw.goodeat.Dialog.BaseDialog
import chun.com.tw.goodeat.R
import java.io.File
import java.io.FileNotFoundException
import java.util.UUID

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
    var dialog1: AlertDialog? = null

    lateinit var basedialog : BaseDialog //基本款 Dialog

    private val REQUEST_CODE_ALBUM = 0x01
    private val REQUEST_CODE_CAMERA = 0x02
    private val REQUEST_CODE_PERMISSION_CAMERA = 0x03

    private var mLastPhothPath: String? = null
    private var mThread: Thread? = null

    private var mCurrentPhotoPath: String? = null
    var scanimg_number = 0 //0 側面 1 正面 2 背面

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
        basedialog = BaseDialog() //建立自訂義 Dialog
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
                scanimg_number = 0
                ShowDialog() //圖片功能選擇 Dialog
            }
            //正面
            R.id.front -> {
                scanimg_number = 1
                ShowDialog() //圖片功能選擇 Dialog
            }
            //背面
            R.id.reverse -> {
                scanimg_number = 2
                ShowDialog() //圖片功能選擇 Dialog
            }
        }
    }

    //圖片功能選擇 Dialog
    private fun ShowDialog(){
        val dialog = AlertDialog.Builder(this) //建立 AlertDialog
        val view: View = layoutInflater.inflate(R.layout.scan_layout, null) //指定 layout
        dialog.setView(view)

        //xml 和程式關聯
        var imgupload : ImageView = view.findViewById(R.id.imgupload)
        var scanimg : ImageView = view.findViewById(R.id.scanimg)
        var imgdelete : ImageView = view.findViewById(R.id.imgdelete)
        var selectgallery : TextView = view.findViewById(R.id.selectgallery)
        var scan : TextView = view.findViewById(R.id.scan)
        var reimg : TextView = view.findViewById(R.id.reimg)

        //從圖庫選擇
        imgupload.setOnClickListener {
            chooseAlbumPic() //開啟圖庫
            dialog1?.dismiss()
        }

        //從圖庫選擇
        selectgallery.setOnClickListener {
            chooseAlbumPic() //開啟圖庫
            dialog1?.dismiss()
        }

        //拍照
        scanimg.setOnClickListener {
            Photograph() //拍照
            dialog1?.dismiss()
        }

        //拍照
        scan.setOnClickListener {
            Photograph() //拍照
            dialog1?.dismiss()
        }

        //移除目前照片
        imgdelete.setOnClickListener {
            //0 側面 1 正面 2 背面
            if (scanimg_number == 0){
                side.setBackgroundResource(R.drawable.keyin_shape)
            } else if(scanimg_number == 1) {
                front.setBackgroundResource(R.drawable.keyin_shape)
            } else if(scanimg_number == 2) {
                reverse.setBackgroundResource(R.drawable.keyin_shape)
            }
            dialog1?.dismiss()
        }

        //移除目前照片
        reimg.setOnClickListener {
            //0 側面 1 正面 2 背面
            if (scanimg_number == 0){
                side.setBackgroundResource(R.drawable.keyin_shape)
            } else if(scanimg_number == 1) {
                front.setBackgroundResource(R.drawable.keyin_shape)
            } else if(scanimg_number == 2) {
                reverse.setBackgroundResource(R.drawable.keyin_shape)
            }
            dialog1?.dismiss()
        }

        //建構 dialog 並顯示
        dialog1 = dialog.create()
        dialog1!!.show()

        //設置對話框位置偏下
        val window: Window? = dialog1!!.getWindow()
        window?.setBackgroundDrawable(null) //重設 background

        val wlp = window?.attributes
        if (wlp != null) {
            wlp.gravity = Gravity.BOTTOM
        }
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        if (wlp != null) {
            wlp.width = display.width
        }
        if (window != null) {
            window.attributes = wlp
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

    private var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            takePhoto()
        }
    }

    //開啟圖庫
    private fun chooseAlbumPic() {
        val i = Intent(Intent.ACTION_GET_CONTENT)
        i.addCategory(Intent.CATEGORY_OPENABLE)
        i.type = "image/*"
        resultLauncher_chooseAlbumPic.launch(i)
    }

    //照片回傳處理動作
    private var resultLauncher_chooseAlbumPic = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val cr: ContentResolver = this.contentResolver

            // 取得照片路徑uri
            val uri: Uri? = result.data?.data
            try {
                // 讀取照片，型態為Bitmap
                val bitmap = BitmapFactory.decodeStream(uri?.let { cr.openInputStream(it) })
                //0 側面 1 正面 2 背面
                if (scanimg_number == 0){
                    side.background = BitmapDrawable(resources, bitmap)
                } else if(scanimg_number == 1) {
                    front.background = BitmapDrawable(resources, bitmap)
                } else if(scanimg_number == 2) {
                    reverse.background = BitmapDrawable(resources, bitmap)
                }
            } catch (e: FileNotFoundException) {
                Log.i("Error" , "E = $e");
            }
        }
    }

    //拍照
    private fun Photograph() {
        try {
            if (!TextUtils.isEmpty(mLastPhothPath)) {
                //上一張拍照的圖片删除
                mThread = Thread {
                    val file: File = File(mLastPhothPath)
                    if (file != null) {
                        file.delete()
                    }
                    mHandler.sendEmptyMessage(1)
                }
                mThread!!.start()
            } else {
                //請求拍照權限
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    takePhoto()
                } else {
                    ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.CAMERA),REQUEST_CODE_PERMISSION_CAMERA)
                }
            }
        } catch ( e : Exception) {
            Log.i("Error" , "E = $e");
        }
    }

    //取得照片
    private fun takePhoto() {
        val fileName = StringBuilder()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        fileName.append(UUID.randomUUID()).append("_upload.png")
        val tempFile: File = File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName.toString())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            val uri = FileProvider.getUriForFile(this, this.packageName + ".provider", tempFile)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        } else {
            val uri = Uri.fromFile(tempFile)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        }
        mCurrentPhotoPath = tempFile.absolutePath
        resultLauncher.launch(intent)
    }

    //照片回傳處理動作
    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            var imageUri: Uri? = null
            val cr: ContentResolver = this.contentResolver
            try {
                if (!TextUtils.isEmpty(mCurrentPhotoPath)) {
                    val file = File(mCurrentPhotoPath)
                    val localUri = Uri.fromFile(file)
                    val localIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri)

                    this.sendBroadcast(localIntent)
                    imageUri = Uri.fromFile(file)
                    mLastPhothPath = mCurrentPhotoPath
                    val bitmap = BitmapFactory.decodeStream(cr.openInputStream(imageUri))
                    //0 側面 1 正面 2 背面
                    if (scanimg_number == 0){
                        side.background = BitmapDrawable(resources, bitmap)
                    } else if(scanimg_number == 1) {
                        front.background = BitmapDrawable(resources, bitmap)
                    } else if(scanimg_number == 2) {
                        reverse.background = BitmapDrawable(resources, bitmap)
                    }
                }
            } catch (e: FileNotFoundException) {
            }
        }
    }
}