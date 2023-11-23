package iutinfo.lp.devmob.orientacourse

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar



class AskGeolocalisationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask_geolocalisation)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar?.setCustomView(R.layout.action_bar_layout)

        val tvGeolocalisation = findViewById<TextView>(R.id.tv_message_geolocalisation)
        val message: String = getString(R.string.besoin_geolocalisation_message)
        val name = user.userName;
        tvGeolocalisation.text = name + ", " + message





        val buttonAskGeoloc = findViewById<Button>(R.id.ask_geolocalisation);
        buttonAskGeoloc.setOnClickListener {

            val intent = Intent(this, ChooseModeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}