import kotlin.io.path.Path
import kotlin.io.path.readText

fun fetch(year: Int, name: String) = Path("inputs/${year}/${name}").readText()
