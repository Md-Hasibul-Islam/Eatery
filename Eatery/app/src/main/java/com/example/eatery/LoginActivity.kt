package com.example.eatery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        auth= FirebaseAuth.getInstance()
        Register.setOnClickListener {
            val intent =Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }


        Login.setOnClickListener {
                if(checking()){
                    val email=Email.text.toString()
                    val password= Password.text.toString()
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                               val intent =Intent(this@LoginActivity, MainActivity::class.java)
                                intent.putExtra("email",email)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "Wrong Details", Toast.LENGTH_LONG).show()
                            }
                        }
                }
                else{
                    Toast.makeText(this,"Enter The Details",Toast.LENGTH_LONG).show()
                }
        }
    }
    private fun checking():Boolean
    {
        if(Email.text.toString().trim{it<=' '}.isNotEmpty()
            && Password.text.toString().trim{it<=' '}.isNotEmpty())
        {
            return true
        }
        return false
    }

}