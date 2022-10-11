package com.example.coursereports

import android.R.attr.bitmap
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Display
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter


class classoverviewActivity: AppCompatActivity() {

    @Throws(WriterException::class)
    fun encodeAsBitmap(str: String?): Bitmap? {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.classoverview)
        this.supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar)
        val view: View = supportActionBar!!.customView
        val title: TextView = view.findViewById(R.id.page_title)
        title.text = intent.getStringExtra("course_number")

        val course_title = intent.getStringExtra("course_title")
        val course_title_text: TextView = findViewById(R.id.course_title)
        course_title_text.text = course_title


        val term = intent.getStringExtra("term")
        val term_text: TextView = findViewById(R.id.course_term)
        term_text.setText(term + " Term")

        val professor = intent.getStringExtra("professor")
        val professor_text: TextView = findViewById(R.id.professor)
        professor_text.setText(professor)

        val surveySize = intent.getStringExtra("survey_size")
        val surveySizeText: TextView = findViewById(R.id.surveySize)
        surveySizeText.setText("Survey Size: " + surveySize)


        val avg = intent.getStringExtra("average")
        val avg_text: TextView = findViewById(R.id.avg)
        avg_text.setText("Overall Rating:\n" + avg)

        val grade = intent.getStringExtra("grade")
        val grade_text: TextView = findViewById(R.id.grade)

        grade_text.setText("Expected Grade:\n" + grade)

        val hours = intent.getStringExtra("hours")
        val hours_text: TextView = findViewById(R.id.hours)
        hours_text.setText(hours + "hrs/week")

        val qrGeneraterBtn: Button = findViewById(R.id.qr)
        qrGeneraterBtn.setOnClickListener {
            val qrDisp: ImageView = findViewById(R.id.qrDisplay)
            qrDisp.setImageBitmap(encodeAsBitmap("course_reports:"+intent.getStringExtra("id")))
        }

    }

}