package webscraper

object Main extends App {

  Utils.setProxy()

  val data = Scrapper(Utils.url).fetch

  println(data.mkString("\n"))
  println(s"\nFound entries: ${data.size}")
}
