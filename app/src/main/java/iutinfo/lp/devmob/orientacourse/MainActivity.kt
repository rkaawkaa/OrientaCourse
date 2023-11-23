package iutinfo.lp.devmob.orientacourse

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN


        val buttonStart = findViewById<Button>(R.id.btn_start);
        buttonStart.setOnClickListener {
            val editName = findViewById<EditText>(R.id.et_name);
            if(editName.text.toString().length < 3) {
                Toast.makeText(this, "Veuillez renseigner votre prénom. (minimum 3 lettres.)", Toast.LENGTH_SHORT).show()
            } else {
                lateinit var intent: Intent;
                val userName = editName.text.toString()
                val userNameCapitalized = userName[0].uppercaseChar() + userName.substring(1)
                user.userName = userNameCapitalized

                // Si la géolocalisation a déjà été accordée, on passe directement à l'activité chooseMode, sinon on va à AskGeolocalisation
                if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    intent = Intent(this, ChooseModeActivity::class.java)
                } else if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    intent = Intent(this, ChooseModeActivity::class.java)
                } else {

                    intent = Intent(this, AskGeolocalisationActivity::class.java)
                }
                startActivity(intent)
                finish()
            }
        }







    }


}


