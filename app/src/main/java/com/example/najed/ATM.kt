/*
* Developed by Najed Alseghair
* as a requirement of Android belt exam
* 30-9-2021
* */

package com.example.najed

import android.content.Context
import android.graphics.Color
import android.widget.Toast

/*
* ATM Class:
* responsible for handling money operations (deposit, withdraw...) and directly changing the UI accordingly
* takes context property to be able to display Toasts
* */

class ATM (private val context: Context) {

    /*
    * getBalance()
    * returns only the numerical amount from its TextView
    * */
     fun getBalance(): Float {
        return currentBalanceTextView.text.toString().removePrefix("Current Balance: ").toFloat()
    }

    /*
    * deposit()
    * checks whether its EditText is empty or not
    * adds the deposit amount to history list to be displayed
    * calls setBalanceView() to set current balance to the balance TextView
    * stores the current balance in the shared preferences file
    * */
     fun deposit() {
        if (depositEditText.text.isEmpty()){
            Toast.makeText(context, "Please enter a number", Toast.LENGTH_LONG).show()
            return
        }
        val depositAmount = depositEditText.text.toString().toFloat()
        historyList.add("Deposit: $depositAmount")
        depositEditText.setText("")

        val currentBalance = getBalance() + depositAmount
        setBalanceView(currentBalance)
        with(sharedPreferences.edit()) {
            putFloat("balance", currentBalance)
            apply()
        }

    }

    /*
    * withdraw()
    * checks whether its EditText is empty or not
    * checks whether the balance is sufficient for withdrawal
    * adds the withdrawal amount to history list to be displayed
    * checks if the balance is below zero to charge fee ($20) & adds fee information to history list to be displayed
    * calls setBalanceView() to set current balance to the balance TextView
    * stores the current balance in the shared preferences file
    * */
     fun withdraw() {
        if (withdrawEditText.text.isEmpty()){
            Toast.makeText(context, "Please enter a number", Toast.LENGTH_LONG).show()
            return
        }

        if (getBalance() < 0) {
            Toast.makeText(context, "Insufficient funds", Toast.LENGTH_LONG).show()
            return
        }

        val withdrawAmount = withdrawEditText.text.toString().toFloat()
        var currentBalance = getBalance() - withdrawAmount
        historyList.add("Withdrawal: $withdrawAmount")
        withdrawEditText.setText("")

        if (currentBalance < 0) {
            historyList.add("Negative Balance Fee: 20")
            currentBalance -= 20
        }

         setBalanceView(currentBalance)

        with(sharedPreferences.edit()) {
            putFloat("balance", currentBalance)
            apply()
        }

    }

    /*
    * setBalanceView()
    * sets the given balance to its TextView after adding "Current Balance: "
    * changes TextView color based on given balance (white for zero, black for positive amount, red for negative amount)
    * */
    fun setBalanceView(balance: Float) {
        currentBalanceTextView.text = "Current Balance: $balance"
        when {
            balance == 0f -> currentBalanceTextView.setTextColor(Color.WHITE)
            balance > 0f -> currentBalanceTextView.setTextColor(Color.BLACK)
            balance < 0f -> currentBalanceTextView.setTextColor(Color.RED)
        }
    }
}