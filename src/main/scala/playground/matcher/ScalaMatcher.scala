package playground.matcher

class ScalaMatcher(haystack: String) {

  def scalaMatch(needle: String): String = {
    if (haystack.contains(needle)) s"Found $needle in $haystack" else s"$needle not found in $haystack"
  }

}
object ScalaMatcher {
  def main(args: Array[String]): Unit = {
    println("Hello, World!")
  }
}