package iutinfo.lp.devmob.orientacourse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import iutinfo.lp.devmob.orientacourse.user.userName
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class FinishedCourseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finished_course)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        calculateFinishTime();

        //Réinilisation des paramètres à zero.
        val buttonRestart = findViewById<Button>(R.id.btn_restart);
        buttonRestart.setOnClickListener {
            currentCourse.startTime = "";
            currentCourse.finishTime = "";
            currentCourse.currentCourse= "";
            currentCourse.currentMode ="";
            val intent = Intent(this, ChooseModeActivity::class.java);
            startActivity(intent);
            finish()
        }

        val tvFinishedCourseName = findViewById<TextView>(R.id.finished_course_name);
        tvFinishedCourseName.text =
            "Félicitations ${user.userName}, vous avez fini le parcours: ${currentCourse.currentCourse} dans le mode ${currentCourse.currentMode}.";
        val tvFinishedCourseTime = findViewById<TextView>(R.id.finished_course_time);
        val elapsedTime = calculateElapsedTime(currentCourse.startTime, currentCourse.finishTime);
        tvFinishedCourseTime.text= "Finir le parcours en totalité vaut aura pris $elapsedTime. Bien joué !";
    }
    fun calculateFinishTime() {
        val sdf = SimpleDateFormat("hh:mm:ss a")
        val date = Date()
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+1:00"))
        currentCourse.finishTime =  sdf.format(date)
    }

    fun calculateElapsedTime(finishTime: String, startTime: String): String {
        val sdf = SimpleDateFormat("hh:mm:ss a")
        val start = sdf.parse(startTime)
        val finish = sdf.parse(finishTime)
        println(start)
        println(finish)
        val diffInMillies = start.time - finish.time
        val diffInSeconds = diffInMillies / 1000 % 60
        val diffInMinutes = diffInMillies / (60 * 1000) % 60
        val diffInHours = diffInMillies / (60 * 60 * 1000) % 24

        return "$diffInHours heures $diffInMinutes minutes et $diffInSeconds seconds"

    }

}