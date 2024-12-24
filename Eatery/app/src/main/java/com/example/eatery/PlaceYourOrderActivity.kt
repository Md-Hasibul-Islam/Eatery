package com.example.eatery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eatery.adapter.PlaceYourOrderAdapter
import com.example.eatery.models.RestaurentModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_place_your_order.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.recycler_restautant_list_row.*

class PlaceYourOrderActivity : AppCompatActivity() {
    //private lateinit var auth: FirebaseAuth
    //private lateinit var db: FirebaseFirestore
    var placeYourOrderAdapter: PlaceYourOrderAdapter? = null
    var isDeliveryOn: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_your_order)

        val restaurantModel: RestaurentModel? = intent.getParcelableExtra("RestaurantModel")
        val actionbar: ActionBar? = supportActionBar
        actionbar?.setTitle(restaurantModel?.name)
        actionbar?.setSubtitle(restaurantModel?.address)
        actionbar?.setDisplayHomeAsUpEnabled(true)

        buttonPlaceYourOrder.setOnClickListener {
            onPlaceOrderButtonCLick(restaurantModel)
        }

        switchDelivery?.setOnCheckedChangeListener { buttonView, isChecked ->

            if(isChecked) {
                inputAddress.visibility = View.VISIBLE
                inputCity.visibility = View.VISIBLE
                inputDivision.visibility = View.VISIBLE
                inputZip.visibility = View.VISIBLE
                tvDeliveryCharge.visibility = View.VISIBLE
                tvDeliveryChargeAmount.visibility = View.VISIBLE
                isDeliveryOn = true
                calculateTotalAmount(restaurantModel)
            } else {
                inputAddress.visibility = View.GONE
                inputCity.visibility = View.GONE
                inputDivision.visibility = View.GONE
                inputZip.visibility = View.GONE
                tvDeliveryCharge.visibility = View.GONE
                tvDeliveryChargeAmount.visibility = View.GONE
                isDeliveryOn = false
                calculateTotalAmount(restaurantModel)
            }
        }

        initRecyclerView(restaurantModel)
        calculateTotalAmount(restaurantModel)

/*
        auth= FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()

        buttonPlaceYourOrder.setOnClickListener {

            if(orderChecking()) {
                var email = Email.text.toString()
                var password = Password.text.toString()
                var username = inputName.text.toString()
                var phone = Phone.text.toString()
                var restaurantname = tvRestaurantName.text.toString()
                var restaurantaddress = tvRestaurantAddress.text.toString()
                var cardnumber = inputCardNumber.text.toString()
                var cardexpiry = inputCardExpiry.text.toString()
                val order = hashMapOf(
                    "Name" to username,
                    "Phone" to phone,
                    "RestaurantName" to restaurantname,
                    "RestaurantAddress" to restaurantaddress,
                    "RestaurantAddress" to restaurantaddress,
                    "CardNumber" to cardnumber,
                    "CardExpiry" to cardexpiry
                )
                val Orders=db.collection("ORDERS")
                val query =Orders.whereEqualTo("RestaurantName",restaurantname).get()
                    .addOnSuccessListener { tasks ->
                        if (tasks.isEmpty) {
                            auth.createUserWithEmailAndPassword(email,password)
                                .addOnCompleteListener(this) { task ->
                                    if (task.isSuccessful) {
                                        Orders.document(restaurantname).set(order)
                                        val intent = Intent(this@PlaceYourOrderActivity, SuccessOrderActivity::class.java)
                                        intent.putExtra("RestaurantName", restaurantname)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(this, "Data Store Failed", Toast.LENGTH_LONG)
                                            .show()
                                    }
                                }
                        }
                    }

                if (isDeliveryOn) {
                    /*
                    var email = Email.text.toString()
                    var password = Password.text.toString()
                    var username = inputName.text.toString()
                    var phone = Phone.text.toString()
                    var address = inputAddress.text.toString()
                    var city = inputCity.text.toString()
                    var division = inputDivision.text.toString()
                    var restaurantname = tvRestaurantName.text.toString()
                    var restaurantaddress = tvRestaurantAddress.text.toString()
                    var cardnumber = inputCardNumber.text.toString()
                    var cardexpiry = inputCardExpiry.text.toString()
                    */
                    var address = inputAddress.text.toString()
                    var city = inputCity.text.toString()
                    var division = inputDivision.text.toString()
                    val order = hashMapOf(
                        "Name" to username,
                        "Phone" to phone,
                        "Address" to address,
                        "City" to city,
                        "Division" to division,
                        "RestaurantName" to restaurantname,
                        "RestaurantAddress" to restaurantaddress,
                        "RestaurantAddress" to restaurantaddress,
                        "CardNumber" to cardnumber,
                        "CardExpiry" to cardexpiry
                    )
                    val Orders=db.collection("ORDERS")
                    val query =Orders.whereEqualTo("RestaurantName",restaurantname).get()
                        .addOnSuccessListener { tasks ->
                            if (tasks.isEmpty) {
                                auth.createUserWithEmailAndPassword(email,password)
                                .addOnCompleteListener(this){
                                        task->
                                    if(task.isSuccessful)
                                    {
                                        Orders.document(restaurantname).set(order)
                                        val intent=Intent(this@PlaceYourOrderActivity, SuccessOrderActivity::class.java)
                                        intent.putExtra("RestaurantName",restaurantname)
                                        startActivity(intent)
                                        finish()
                                    }
                                    else
                                    {
                                        Toast.makeText(this,"Data Store Failed", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }

                }
            }
            else{
                Toast.makeText(this,"Enter The Details", Toast.LENGTH_LONG).show()
            }
        }
    */
    }

    private fun initRecyclerView(restaurantModel: RestaurentModel?) {
        cartItemsRecyclerView.layoutManager = LinearLayoutManager(this)
        placeYourOrderAdapter = PlaceYourOrderAdapter(restaurantModel?.menus)
        cartItemsRecyclerView.adapter =placeYourOrderAdapter
    }

    private fun calculateTotalAmount(restaurantModel: RestaurentModel?) {
        var subTotalAmount = 0f
        for(menu in restaurantModel?.menus!!) {
            subTotalAmount += menu?.price!!  * menu?.totalInCart!!

        }
        tvSubtotalAmount.text = String.format("%.2f", subTotalAmount) + "Tk."
        if(isDeliveryOn) {
            tvDeliveryChargeAmount.text = String.format("%.2f", restaurantModel.delivery_charge?.toFloat()) + "Tk."
            subTotalAmount += restaurantModel?.delivery_charge?.toFloat()!!
        }

        tvTotalAmount.text = String.format("%.2f", subTotalAmount) + "Tk."
    }

    private fun onPlaceOrderButtonCLick(restaurantModel: RestaurentModel?) {
        if(TextUtils.isEmpty(inputName.text.toString())) {
            inputName.error =  "Enter your name"
            return
        } else if(isDeliveryOn && TextUtils.isEmpty(inputAddress.text.toString())) {
            inputAddress.error =  "Enter your address"
            return
        } else if(isDeliveryOn && TextUtils.isEmpty(inputCity.text.toString())) {
            inputCity.error =  "Enter your city"
            return
        } else if(isDeliveryOn && TextUtils.isEmpty(inputDivision.text.toString())) {
            inputDivision.error =  "Enter your division"
            return
        } else if( TextUtils.isEmpty(inputCardNumber.text.toString())) {
            inputCardNumber.error =  "Enter your credit card number"
            return
        } else if( TextUtils.isEmpty(inputCardExpiry.text.toString())) {
            inputCardExpiry.error =  "Enter your credit card expiry"
            return
        } else if( TextUtils.isEmpty(inputCardPin.text.toString())) {
            inputCardPin.error =  "Enter your credit card pin"
            return
        }
        val intent = Intent(this@PlaceYourOrderActivity, SuccessOrderActivity::class.java)
        intent.putExtra("RestaurantModel", restaurantModel)
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1000) {
            setResult(RESULT_OK)
            finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
        finish()
    }

/*
    private fun orderChecking():Boolean{
        if(inputName.text.toString().trim{it<=' '}.isNotEmpty()
            && Phone.text.toString().trim{it<=' '}.isNotEmpty()
            && inputAddress.text.toString().trim{it<=' '}.isNotEmpty()
            && inputCity.text.toString().trim{it<=' '}.isNotEmpty()
            && inputDivision.text.toString().trim{it<=' '}.isNotEmpty()
            && tvRestaurantName.text.toString().trim{it<=' '}.isNotEmpty()
            && tvRestaurantAddress.text.toString().trim{it<=' '}.isNotEmpty()
            && inputCardNumber.text.toString().trim{it<=' '}.isNotEmpty()
            && inputCardExpiry.text.toString().trim{it<=' '}.isNotEmpty()
        )
        {
            return true
        }
        return false
    }
 */

}