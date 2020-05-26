package webscraper

import webscraper.Utils.{setProxy, url}

object Main extends App {

  setProxy()

  val data = Scrapper(url).fetch
//  println(data.mkString("\n"))
  println(s"\nFound entries: ${data.size}")

//  val asd = for (d <- data) yield (d.text, d.attr("href"))
  val asd = data.map(x => (x.text, x.attr("href")))
  println(asd.mkString("\n"))
}
