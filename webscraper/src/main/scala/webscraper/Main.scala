package webscraper

import webscraper.Utils.{setProxy, url}

object Main extends App {

  setProxy()
  val scrapper = Scrapper()

  val categories = scrapper.getCategories(url)
  val categoriesLinks = categories.map(x => (x.text, x.attr("href"))).toList
  println(categoriesLinks.mkString("\n"))

  val offers = scrapper.getOffers(categoriesLinks.head._2)
}
