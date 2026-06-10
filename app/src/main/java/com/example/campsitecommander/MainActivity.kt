package com.example.campsitecommander

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.postDelayed
import com.example.campsitecommander.ui.theme.CampsiteCommanderTheme
import java.util.logging.Handler


class MainActivity : ComponentActivity() {

// global derclarations
    private lateinit var splashLayout: LinearLayout
    private lateinit var mainLayout: ScrollView
    private lateinit var detailLayout: ScrollView

    private lateinit var txtDetail: TextView
    private lateinit var txtTotalItem: TextView


    private lateinit var editItem: EditText
    private lateinit var editQuantity: EditText
    private lateinit var editComment: EditText

    private lateinit var spinnerItem: Spinner
// parallel array
    private val category = arrayOf(
        "Shelter","Food","Safety"
    )
    private val items = Array(8){""}
    private val quantities= IntArray(8){0}
    private val comments = Array(8){""}


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        splashLayout = findViewById(R.id.splashLayout)
        mainLayout = findViewById(R.id.mainLayout)
        detailLayout = findViewById(R.id.detailLayout)

        txtDetail = findViewById(R.id.txtDetail)
        txtTotalItem = findViewById(R.id.txtTotalItems)

        editQuantity =findViewById(R.id.editQuantity)
        editComment = findViewById(R.id.editComment)
        editItem = findViewById(R.id.editItem)

        spinnerItem = findViewById(R.id.spinnerCategory)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            category
        )

        spinnerItem.adapter = adapter

        val btnStart = findViewById<Button>(R.id.btnStart)
        val btnExitSplash = findViewById<Button>(R.id.btnExitSplash)

        val btnExit = findViewById<Button>(R.id.btnExit)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnTotal = findViewById<Button>(R.id.btnTotal)
        val btnList = findViewById<Button>(R.id.btnList)
        val btnClear = findViewById<Button>(R.id.btnClear)

        val btnBack = findViewById<Button>(R.id.btnBack)

        // splash screen Navigation
        btnStart.setOnClickListener {
            splashLayout.visibility= View.GONE
            mainLayout.visibility = View.VISIBLE
        }

        btnExitSplash.setOnClickListener {
            finish()
        }

        btnSave.setOnClickListener {
            if (editItem.text.isEmpty()|| editQuantity.text.isEmpty()|| editComment.text.isEmpty()){
                Toast.makeText(this,"Please fill all fields!",Toast.LENGTH_LONG).show()
            }else{

                val index = spinnerItem.selectedItemPosition


                items[index]= editItem.text.toString()
                comments[index]= editComment.text.toString()
                quantities[index]= editQuantity.text.toString().toInt()

                Toast.makeText(this,"Gear Saved Successfully!",Toast.LENGTH_LONG).show()

                editQuantity.text.clear()
                editComment.text.clear()
                editItem.text.clear()

            }
        }
        // calculate the total items
        btnTotal.setOnClickListener {

            var total = 0
            var index = 0

            while (index <quantities.size ){
                total += quantities[index]
                index++
            }


            txtTotalItem.text = " Total Items: ${quantities[index]}\n"
        }

        btnList.setOnClickListener {

            var display = ""

            for( i in category.indices) {
                display += "${category[i]}\n"
                display += "Item Name: ${items[i]}\n"
                display += "Quantity: ${quantities[i]}\n"
                display += "Comments: ${comments[i]}\n"
            }

            txtDetail.text = display

            mainLayout.visibility = View.GONE
            detailLayout.visibility = View.VISIBLE
        }

        btnBack.setOnClickListener {
            detailLayout.visibility = View.GONE
            mainLayout.visibility = View.VISIBLE
        }

        btnClear.setOnClickListener {

            for(i in items.indices){
                items[i] = ""
                quantities[i] = 0
                comments[i] = ""
            }

            txtTotalItem.text = " Total Items"

            Toast.makeText(this,"Data Cleared",Toast.LENGTH_LONG).show()

        }

        // Exit App
        btnExit.setOnClickListener {
            finish()
        }


    }

}
