package iutinfo.lp.devmob.orientacourse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChooseCourse : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_course)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        val actionbar = supportActionBar
        supportActionBar!!.title= "Choix du parcours"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //Création des instances de la data class Courses
        val courseList = listOf(
            Courses("Près de la mer", "Facile", "Ce parcours vous emmenera le long des côtes.", "Courte",
                listOf<Double>(
                -1.1711449610489808,
                -1.1738225916938063,
                -1.169557374119762,
                -1.1660739097734734,
                -1.1636797949869901,
                -1.160781379220765,
                -1.1543326441627073,
                -1.1523953994323222,
                -1.1514841019392748,
                ),
                listOf<Double>(
                    46.14055007393148,
                    46.13861224994852,
                    46.13860978120687,
                    46.13849399519296,
                    46.137973212389284,
                    46.13752172612695,
                    46.13747311352566,
                    46.13607582455208,
                    46.134906564269045,
                )),
            Courses("Autour de l'Université", "Moyenne", "Visitez l'Université de La Rochelle tout en jouant.",  "Moyenne",
                listOf<Double>(
                    -1.151493195118121,
                    -1.1501990385775684,
                    -1.1518714547821673,
                    -1.1573051049037701,
                    -1.15591478094683,
                    -1.1595728606906732,
                    -1.1570236559830391,
                    -1.152536685402282,
                    -1.1533042180646476,
                    -1.1504313934895833,
                    -1.153764838388156,
                    -1.1503992511777312),
                listOf<Double>(
                    46.14078178777848,
                    46.139817734414834,
                    46.13834745255417,
                    46.14152453026139,
                    46.14519025001624,
                    46.14431456718495,
                    46.14671562868014,
                    46.14589859555494,
                    46.14302564807255,
                    46.14256766876005,
                    46.13980000330076,
                    46.1390413671451
                )),
            Courses("Parcours historique", "Facile", "Découvrez la vieille ville de La Rochelle.", "Moyenne",
                listOf<Double>(
                    -1.1502054201913836,
                    -1.15594999489727,
                    -1.1464355663432002,
                    -1.1480499579889738,
                    -1.1517735720332212,
                    -1.1542220831890688,
                    -1.1489870072230985,
                    -1.1441347239592972,
                    -1.1471911488763737,
                    -1.1529357008915895,
                    -1.1567197318073852,
                    -1.1586519870245695),
                listOf<Double>(
                    46.16347538449719,
                    46.1569620391287,
                    46.161348449205036,
                    46.15668233119379,
                    46.16070179084764,
                    46.15773003565849,
                    46.15865901092036,
                    46.161597374772526,
                    46.162993351285564,
                    46.162107219392,
                    46.161179072553665,
                    46.15732609342166
                )),
            Courses("Le parcours du soldat", "Difficile", "Parcours très difficile qui vous fera visiter toute la ville.",  "Longue",
                listOf<Double>(
                            -1.174676077573821,
                            -1.1680025626887698,
                            -1.1606294685046237,
                            -1.1548368655887202,
                            -1.1530407546919719,
                            -1.1477349566388,
                            -1.148733232716836,
                            -1.1475266658645467,
                            -1.1468349470073917,
                            -1.1483559424496548,
                            -1.1546914583487364,
                            -1.151790955412423,
                            -1.1563971741665284,
                            -1.1528088825946838,
                            -1.1470314580464844,
                            -1.1461628792544332,
                            -1.1440405600325505,
                            -1.1429636795391218,
                            -1.1592154512515833,
                            -1.1504048666554922,
                            -1.1561783609820395),
                listOf<Double>(
                    46.15449549937989,
                    46.156403958082336,
                    46.156839545655856,
                    46.157092014724725,
                    46.161208128159984,
                    46.161766175391335,
                    46.15925900171382,
                    46.15724720333637,
                    46.15499846210028,
                    46.152469199024324,
                    46.1525835492717,
                    46.1491393245351,
                    46.149155661288006,
                    46.146400127132864,
                    46.14900046194313,
                    46.147069963343085,
                    46.154522022075184,
                    46.1611428075133,
                    46.16278086320139,
                    46.15884332646206,
                    46.14742748648055
                )),
        )


        val adapter = CourseAdapter(courseList)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter;

    }


}

//Implémentation du recyclerView pour afficher les parcours.
class CourseAdapter(private val courses: List<Courses>) : RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(course: Courses) {
// Pour chaque carte cliquable, on execute ce code.
            itemView.findViewById<TextView>(R.id.text_title).text = course.title
            itemView.findViewById<TextView>(R.id.text_difficulty).text= "Difficulté : " + course.difficulty
            itemView.findViewById<TextView>(R.id.text_description).text = course.description
            itemView.findViewById<TextView>(R.id.text_length).text = "Durée: " + course.length
            itemView.setOnClickListener {
                currentCourse.currentCourse = course.title
                currentCourse.currentLatPoints = course.latPoints
                currentCourse.currentLongPoints = course.longPoints
                val intent = Intent(itemView.context, ResumeCourseActivity::class.java)
                intent.putExtra("COURSE_TITLE", course.title)
                intent.putExtra("COURSE_DESCRIPTION", course.description)
                intent.putExtra("COURSE_LENGTH", course.length)
                intent.putExtra("COURSE_DIFFICULTY", course.difficulty)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_course_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(courses[position])
    }

    override fun getItemCount() = courses.size
}
