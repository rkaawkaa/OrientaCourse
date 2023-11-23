package iutinfo.lp.devmob.orientacourse

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.location.LocationServices
import com.mapbox.common.location.LocationService
import java.text.SimpleDateFormat
import java.util.*

class ResumeCourseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        val actionbar = supportActionBar
        supportActionBar!!.title= "Récapitulatif"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // On récupère ici les données passées à partir de l'activité précendente pour les afficher à l'utilisateur.
        val courseTitle = intent.getStringExtra("COURSE_TITLE")
        val courseDescription = intent.getStringExtra("COURSE_DESCRIPTION")
        val courseLength = intent.getStringExtra("COURSE_LENGTH")
        val courseDifficulty = intent.getStringExtra("COURSE_DIFFICULTY")
        val tvUser = findViewById<TextView>(R.id.recap_user_name)
        val tvCourse = findViewById<TextView>(R.id.recap_user_course)
        val tvDescr = findViewById<TextView>(R.id.recap_course_desc)
        tvUser.text = user.userName + ", voici l'épreuve dans laquelle vous vous lancez :";
        tvCourse.text = "Vous avez choisi la course: " + courseTitle + " et dans le mode ${currentCourse.currentMode}. Ce parcours est d'une difficulté " + courseDifficulty + " et d'une durée " + courseLength + ".";
        tvDescr.text= courseDescription + " Profitez bien de votre parcours !"

        val buttonStartMap = findViewById<Button>(R.id.btn_start_map);
        buttonStartMap.setOnClickListener {

            currentCourse.startTime = getCurrentTime()
            val  intent = Intent(this, MapActivity::class.java)
            startActivity(intent);

        }
    }
    fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("hh:mm:ss a")
        val date = Date()
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+1:00"))
        return sdf.format(date)
    }

}