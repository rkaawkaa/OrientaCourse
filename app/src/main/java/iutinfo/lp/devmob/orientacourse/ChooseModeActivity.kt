package iutinfo.lp.devmob.orientacourse

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import com.google.android.gms.location.LocationServices

class ChooseModeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_mode)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        val actionbar = supportActionBar
        actionbar!!.title = "Choix du mode"


        // demande la géolocalisation
        if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) ||shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION )) {
            requestPermission()
        } else {
            requestPermission()
        }


                val cardModeEtoile = findViewById<CardView>(R.id.card_mode_etoile)
                cardModeEtoile.setOnClickListener {
                   currentCourse.currentMode = "Etoile"
                    goToChooseCourse()
                }

                val cardModeParcours = findViewById<CardView>(R.id.card_mode_course)
                cardModeParcours.setOnClickListener {
                    currentCourse.currentMode = "Grande Course"
                    goToChooseCourse()
                }

                val cardModePapillon = findViewById<CardView>(R.id.card_mode_papillon)
                cardModePapillon.setOnClickListener {
                    currentCourse.currentMode = "Papillon"
                    goToChooseCourse()
                }







    }

    private fun goToChooseCourse() {
        val intent = Intent(this, ChooseCourse::class.java)
        startActivity(intent)
    }


    fun requestPermission() {
        val locationPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        {
                permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) ->
                {
                    Toast.makeText(this, "Géolocalisation Fine accordée.", Toast.LENGTH_SHORT).show()
                    activateLocation()
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) ->
                {
                    activateLocation()
                    Toast.makeText(this, "Géolocalisation Coarse accordée.", Toast.LENGTH_SHORT).show()
                } else -> {
                Toast.makeText(this, "Accès refusé. Votre géolocalisation est nécessaire.", Toast.LENGTH_SHORT).show()
            }
            }
        }
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ))
    }

    //Affiche la position de l'utilisateur si celui-ci a accepté la demande de géolocalisation.
    fun activateLocation() {
            if((checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) || (checkSelfPermission(
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                fusedLocationClient.lastLocation.addOnSuccessListener {
                        location: Location? ->
                    Toast.makeText(this, "Dernière position connue: " + location?.latitude + ", " + location?.longitude, Toast.LENGTH_SHORT).show()
                }
            }

    }


}