package com.richardcaballero.mysqltutorial

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var etName: EditText
    private lateinit var etPassword: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var etId: EditText

    private lateinit var btnCreate: Button
    private lateinit var btnFetch: Button

    private lateinit var requestQueue: RequestQueue
    private val URL1 = "http://IP_Address:Port/save.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestQueue = Volley.newRequestQueue(this)

        initUI()

        btnCreate.setOnClickListener(this)
        btnFetch.setOnClickListener(this)
    }

    private fun initUI() {
        // EditTexts
        etName = findViewById(R.id.etName)
        etPassword = findViewById(R.id.etPassword)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        etId = findViewById(R.id.etId)

        // Buttons
        btnCreate = findViewById(R.id.btnCreate)
        btnFetch = findViewById(R.id.btnFetch)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnCreate -> {
                val name = etName.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()
                val phone = etPhone.text.toString().trim()

                createUser(name, email, password, phone)
            }
            R.id.btnFetch -> {
                val intent = Intent(this, MainActivity2::class.java)
                intent.putExtra("id", etId.text.toString().trim())
                startActivity(intent)
            }
        }
    }

    private fun createUser(name: String, email: String, password: String, phone: String) {
        val stringRequest = object : StringRequest(
            Method.POST, URL1,
            Response.Listener {
                Toast.makeText(this@MainActivity, "Correct", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {}) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["name"] = name
                params["email"] = email
                params["password"] = password
                params["phone"] = phone
                return params
            }
        }

        requestQueue.add(stringRequest)
    }
}