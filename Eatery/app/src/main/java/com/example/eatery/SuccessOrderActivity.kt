package com.example.eatery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.eatery.models.RestaurentModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_success_order.*

class SuccessOrderActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_order)

        val restaurantModel: RestaurentModel? = intent.getParcelableExtra("RestaurantModel")
        val actionbar: ActionBar? = supportActionBar
        actionbar?.setTitle(restaurantModel?.name)
        actionbar?.setSubtitle(restaurantModel?.address)
        actionbar?.setDisplayHomeAsUpEnabled(false)

        buttonDone.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }

/*
        val sharedPref=this?.getPreferences(Context.MODE_PRIVATE)?:return
        val isLogin=sharedPref.getString("Email","1")

        if(isLogin=="1")
        {
            var email=intent.getStringExtra("email")
            if(email!=null)
            {
                setText(email)
                with(sharedPref.edit())
                {
                    putString("Email",email)
                    apply()
                }
            }
            else{
                Toast.makeText(this,"Something Went Wrong", Toast.LENGTH_LONG).show()
            }
        }
        else
        {
            setText(isLogin)
        }
    }

    private fun setText(email:String?)
    {
        db= FirebaseFirestore.getInstance()
        if (email != null) {
            db.collection("USERS").document(email).get()
                .addOnSuccessListener {
                        tasks->
                    name.text=tasks.get("Name").toString()
                    phone.text=tasks.get("Phone").toString()
                    emailLog.text=tasks.get("email").toString()

                }
        }

    }
 */

    }
}