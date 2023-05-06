//IT21318320 - Silva T.U.D
package com.example.proplanner.activities

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.proplanner.R

class BudgetTipsActivity : AppCompatActivity() {
    //Declaring variables
    private lateinit var svTips : ScrollView
    private lateinit var ibBack : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_tips)

        //Initializing variables
        svTips = findViewById(R.id.svTips)
        ibBack = findViewById(R.id.ibBack)

        //Redirecting to budget home page on button click
        ibBack.setOnClickListener{
            var intent = Intent(this, BudgetHomeActivity::class.java)
            startActivity(intent)
        }

        getLinks()
    }

    private fun getLinks() {
        val topics = arrayOf(
            "Small Business Basics: Financial Management",
            "8 Bookkeeping Tips To Simplify Small Business Finances",
            "Accounting Basics For Small Business Owners"
        )

        val links = arrayOf(
            "https://www.youtube.com/watch?v=Wzwpou8d7v4&pp=ygUwaW5jb21lIGFuZCBleHBlbnMgbWFuYWdlbWVudCB0aXBzIGZvciBidXNpbmVzc2Vz",
            "https://youtu.be/rrSOKWI9k7Q",
            "https://youtu.be/pqkdCM47hpg"
        )

        val imageLinks = arrayOf(
            "https://img.freepik.com/free-photo/map-lying-wooden-table_53876-23515.jpg?size=626&ext=jpg&ga=GA1.1.280052515.1678786237&semt=robertav1_2_sidr",
            "https://img.freepik.com/free-photo/account-assets-audit-bank-bookkeeping-finance-concept_53876-132604.jpg?size=626&ext=jpg&ga=GA1.1.280052515.1678786237&semt=robertav1_2_sidr",
            "https://img.freepik.com/free-photo/business-plan-strategy-goals-concept_53876-120043.jpg?size=626&ext=jpg&ga=GA1.1.280052515.1678786237&semt=robertav1_2_sidr"
        )

        val container = LinearLayout(this@BudgetTipsActivity)
        container.orientation = LinearLayout.VERTICAL

        //Display video links
        for (i in links.indices) {
            val linearLayout = LinearLayout(this@BudgetTipsActivity)
            linearLayout.orientation = LinearLayout.VERTICAL
            val tvTips = TextView(this@BudgetTipsActivity)
            val imgView = ImageView(this@BudgetTipsActivity)

            Glide.with(this@BudgetTipsActivity)
                .load(imageLinks[i])
                .into(imgView)

            linearLayout.setBackgroundResource(R.drawable.btn_rounded_corner)
            tvTips.text = topics[i]

            // Convert dp to pixels
            val displayMetrics = resources.displayMetrics
            val widthInDp = 380 // width in dp
            val heightInDp = 280 // height in dp
            val widthInPx = (widthInDp * displayMetrics.density).toInt()
            val heightInPx = (heightInDp * displayMetrics.density).toInt()

            // Set width and height
            val params = LinearLayout.LayoutParams(
                widthInPx,
                heightInPx
            )

            // Set margins
            params.setMargins(30, 30, 16, 16)

            linearLayout.layoutParams = params

            tvTips.textSize = 20F
            tvTips.setTextColor(Color.WHITE)
            val tvParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            val tvLeftMargin = 30
            val tvTopMargin = 30
            val tvRightMargin = 16
            val tvBottomMargin = 30
            tvParams.setMargins(tvLeftMargin, tvTopMargin, tvRightMargin, tvBottomMargin)
            tvTips.layoutParams = tvParams



            linearLayout.addView(tvTips)
            linearLayout.addView(imgView)
            linearLayout.setOnClickListener{
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(links[i]))
                startActivity(intent)
            }
            container.addView(linearLayout)
        }

        svTips.addView(container)
    }
}

