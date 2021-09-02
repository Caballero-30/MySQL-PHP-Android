package com.richardcaballero.mysqltutorial

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity2 : AppCompatActivity(), View.OnClickListener {

    private lateinit var etName: EditText
    private lateinit var etPassword: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var etId: EditText

    private lateinit var btnDelete: Button
    private lateinit var btnEdit: Button
    private lateinit var id: String
    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        requestQueue = Volley.newRequestQueue(this)

        val extras = intent.extras
        id = extras?.getString("id").toString()

        readUser()

        initUI()

        btnDelete.setOnClickListener(this)
        btnEdit.setOnClickListener(this)
    }

    private fun readUser() {
        val URL = "http://IP_Address:Port/fetch.php?id=$id"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, URL, null,
            {
                val name: String
                val email: String
                val password: String
                val phone: String
                try {
                    name = it.getString("name")
                    email = it.getString("email")
                    password = it.getString("password")
                    phone = it.getString("phone")

                    etName.setText(name)
                    etEmail.setText(email)
                    etPassword.setText(password)
                    etPhone.setText(phone)
                }
                catch (e: JSONException) { e.printStackTrace() }
            }, { })

        requestQueue.add(jsonObjectRequest)
    }

    private fun initUI() {
        // EditTexts
        etName = findViewById(R.id.etName)
        etPassword = findViewById(R.id.etPassword)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        etId = findViewById(R.id.etId)

        // Buttons
        btnDelete = findViewById(R.id.btnDelete)
        btnEdit = findViewById(R.id.btnEdit)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnEdit -> {
                val name = etName.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()
                val phone = etPhone.text.toString().trim()

                updateUser(name, email, password, phone)
            }
            R.id.btnDelete -> {
                val idUser = etId.text.toString().trim()
                removeUser(idUser)
            }
        }
    }

    private fun updateUser(name: String, email: String, password: String, phone: String) {
        val URL = "http://IP_Address:Port/edit.php"
        val stringRequest = object : StringRequest(
            Method.POST,
            URL,
            Response.Listener {
                Toast.makeText(this@MainActivity2, "Updated successfully", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {  }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["id"] = id
                params["name"] = name
                params["email"] = email
                params["password"] = password
                params["phone"] = phone
                return params
            }
        }

        requestQueue.add(stringRequest)
    }

    private fun removeUser(idUser: String) {
        val URL = "http://IP_Address:Port/delete.php"
        val stringRequest = object : StringRequest(
            Method.POST,
            URL,
            Response.Listener { finish() },
            Response.ErrorListener { }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["id"] = idUser
                return params
            }
        }

        requestQueue.add(stringRequest)
    }
}