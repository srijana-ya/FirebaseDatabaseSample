package com.example.firebasedbsample

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    lateinit var editTextName: EditText
    lateinit var ratingBar: RatingBar
    lateinit var buttonSave: Button
    lateinit var spinner: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.editTextName)
        ratingBar = findViewById(R.id.ratingBar)
        buttonSave = findViewById(R.id.buttonSave)

        spinner = findViewById(R.id.spinner)
        val waterSource
                = resources.getStringArray(R.array.WaterSource)

        val adapter
                = ArrayAdapter(this,
            android.R.layout.simple_list_item_1,waterSource)
        spinner.setAdapter(adapter)

        spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        buttonSave.setOnClickListener {
            saveResult() }
    }

    private fun saveResult() {
        val name = editTextName.text.toString().trim()
        val text = spinner.text.toString()

        if (name.isEmpty()) {
            editTextName.error = "Please enter a name"
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("result")
        val resultId = ref.push().key
        val result = resultId?.let { Result(it, name, text, ratingBar.numStars) }


        if (resultId != null) {
            ref.child(resultId).setValue(result).addOnCompleteListener {
                Toast.makeText(applicationContext, "Hero Saved Successfully", Toast.LENGTH_SHORT).show()

            }
        }

    }
}
