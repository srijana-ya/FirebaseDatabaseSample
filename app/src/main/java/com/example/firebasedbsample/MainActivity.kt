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
    lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.editTextName)
        ratingBar = findViewById(R.id.ratingBar)
        buttonSave = findViewById(R.id.buttonSave)

        spinner = findViewById(R.id.spinner)
        val languages: MutableList<String> = ArrayList()
        languages.add("Java")
        languages.add("Kotlin")

        val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, languages)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                Toast.makeText(this@MainActivity,
                        getString(R.string.selected_item) + " " +
                                "" + languages[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        buttonSave.setOnClickListener {
            saveHero() }
    }

    private fun saveHero() {
        val name = editTextName.text.toString().trim()
        val text = spinner.selectedItem.toString()

        if (name.isEmpty()) {
            editTextName.error = "Please enter a name"
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("heroes").child(text)
        val heroId = ref.push().key
        val hero = heroId?.let { Hero(it, name, ratingBar.numStars) }


        if (heroId != null) {
            ref.child(heroId).setValue(hero).addOnCompleteListener {
                Toast.makeText(applicationContext, "Hero Saved Successfully", Toast.LENGTH_SHORT).show()

            }
        }

    }
}
