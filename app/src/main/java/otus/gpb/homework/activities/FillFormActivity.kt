package otus.gpb.homework.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.AppCompatEditText

class FillFormActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        val name = findViewById<AppCompatEditText>(R.id.edit_name)
        val surname = findViewById<AppCompatEditText>(R.id.edit_surname)
        val age = findViewById<AppCompatEditText>(R.id.edit_age)
        val button = findViewById<Button>(R.id.set_button)

        val user = intent.getParcelableExtra<Transit>("AAA")
        name.setText(user?.name)
        surname.setText(user?.secondName)
        age.setText(user?.age)


        button.setOnClickListener(){
            val intent = Intent()
            intent.putExtra(
                "AAA", Transit(
                    name.text.toString(),
                    surname.text.toString(),
                    age.text.toString()
                )
            )
            setResult(RESULT_OK, intent)
            finish()
        }
    }



}