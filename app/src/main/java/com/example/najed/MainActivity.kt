/*
* Developed by Najed Alseghair
* as a requirement of Android belt exam
* 30-9-2021
* */

package com.example.najed

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

lateinit var currentBalanceTextView: TextView
lateinit var recyclerView: RecyclerView
lateinit var depositEditText: EditText
lateinit var withdrawEditText: EditText
lateinit var depositButton: Button
lateinit var withdrawButton: Button
lateinit var historyList: ArrayList<String>
lateinit var sharedPreferences: SharedPreferences
lateinit var atm: ATM

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize views & ATM object
        currentBalanceTextView = findViewById(R.id.tvBalance)
        recyclerView = findViewById(R.id.rvHistory)
        depositEditText = findViewById(R.id.etDeposit)
        withdrawEditText = findViewById(R.id.etWithdraw)
        depositButton = findViewById(R.id.btnDeposit)
        withdrawButton = findViewById(R.id.btnWithdraw)
        historyList = arrayListOf()
        atm = ATM (this)

        // Recycler view linking
        recyclerView.adapter = RecyclerViewAdapter(historyList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Shared preferences linking & setting
        sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        atm.setBalanceView(sharedPreferences.getFloat("balance", 0f))

        // set onClick listeners
        depositButton.setOnClickListener {
            atm.deposit()
            recyclerView.adapter!!.notifyDataSetChanged()
            recyclerView.scrollToPosition(historyList.size - 1)
        }

        withdrawButton.setOnClickListener {
            atm.withdraw()
            recyclerView.adapter!!.notifyDataSetChanged()
            recyclerView.scrollToPosition(historyList.size - 1)
        }
    }

    // To preserve balance in case of activity recreating
    // Note: both onSaveInstanceState() & onRestoreInstanceState() implemented as a requirement for red belt
    // But do we need them after storing balance in the shared preferences?
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putFloat("currentBalance", atm.getBalance())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        atm.setBalanceView(savedInstanceState.getFloat("currentBalance", 0f))
    }

    // Menu linking
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    // Ledger (historyList) clearing
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.clear -> {
                historyList.clear()
                recyclerView.adapter!!.notifyDataSetChanged()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}