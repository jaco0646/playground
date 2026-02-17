package com.external

class ScalaMatcher2(haystack: String) {

  def scalaMatch(needle: String): String = {
    if (haystack.contains(needle)) s"Found $needle in $haystack" else s"$needle not found in $haystack"
  }

}
object ScalaMatcher2 {
  def main(args: Array[String]): Unit = {
    println("Hello, World!")
  }
}