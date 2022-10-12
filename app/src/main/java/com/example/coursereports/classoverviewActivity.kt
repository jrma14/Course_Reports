package com.example.coursereports

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import java.io.File
import java.io.FileOutputStream
import java.net.URL


class classoverviewActivity : AppCompatActivity() {

    @Throws(WriterException::class)
    fun encodeAsBitmap(str: String?): Bitmap {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(str, BarcodeFormat.QR_CODE, 400, 400)
        val w = bitMatrix.width
        val h = bitMatrix.height
        val pixels = IntArray(w * h)
        for (y in 0 until h) {
            for (x in 0 until w) {
                pixels[y * w + x] = if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
            }
        }
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h)
        return bitmap
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.classoverview)
        this.supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar_class)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#AC2B37")))
        val view: View = supportActionBar!!.customView
        val title: TextView = view.findViewById(R.id.page_title)
        title.text = intent.getStringExtra("course_number")
        val home: ImageView = view.findViewById(R.id.home)
        home.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        val qrBtn: ImageView = findViewById(R.id.qr_code_button)
        qrBtn.setOnClickListener {
            startActivity(Intent(this, ScanQRCodeActivity::class.java))
        }


        val course_title = intent.getStringExtra("course_title")
        val course_title_text: TextView = findViewById(R.id.course_title)
        course_title_text.text = course_title


        val term = intent.getStringExtra("term")
        val term_text: TextView = findViewById(R.id.course_term)
        term_text.setText(intent.getStringExtra("year") + " | " + term + " Term")

        val professor = intent.getStringExtra("professor")
        val professor_text: TextView = findViewById(R.id.professor)
        professor_text.setText(professor)

        val surveySize = intent.getStringExtra("survey_size")
        val surveySizeText: TextView = findViewById(R.id.surveySize)
        surveySizeText.setText("Survey Size: " + surveySize)


        val avg = intent.getStringExtra("average")
        val avg_text: TextView = findViewById(R.id.avg)
        avg_text.setText(avg)

        val grade = intent.getStringExtra("grade")
        val grade_text: TextView = findViewById(R.id.grade)

        grade_text.setText(grade)

        val hours = intent.getStringExtra("hours")
        val hours_text: TextView = findViewById(R.id.hours)
        hours_text.setText(hours)

        val qrGeneraterBtn: Button = findViewById(R.id.qr)
        qrGeneraterBtn.setOnClickListener {
            val qrDisp: ImageView = findViewById(R.id.qrDisplay)
            val icon: Bitmap = encodeAsBitmap("course_reports:" + intent.getStringExtra("id"))
            qrDisp.setImageBitmap(icon)
            qrDisp.setOnLongClickListener {
                saveImage(icon)
            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun saveImage(icon: Bitmap): Boolean {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME,
                "Course_Reports:" + intent.getStringExtra("course_number")
                    ?.replace(" ", "-") + ".png"
            )
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }
        val resolver = this.contentResolver
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        if (uri != null) {
            val fOut = resolver.openOutputStream(uri)
            icon.compress(Bitmap.CompressFormat.PNG, 85, fOut)
            fOut!!.flush()
            fOut.close()
        }

        Toast.makeText(applicationContext, "Saved to Downloads!", Toast.LENGTH_SHORT).show()
        return true
    }

}