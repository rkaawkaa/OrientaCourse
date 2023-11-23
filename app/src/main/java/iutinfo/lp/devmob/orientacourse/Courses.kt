package iutinfo.lp.devmob.orientacourse

data class Courses(
    val title: String,
    val difficulty: String,
    val description: String,
    val length: String,
    val longPoints: List<Double>,
    val latPoints: List<Double>,
)


