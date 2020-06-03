package webscraper

import webscraper.Utils.{setProxy, url}

object Main extends App {

  setProxy()
  val scrapper = Scrapper()

  val categories = scrapper.getCategories(url)
  val categoriesLinks = categories.map(x => (x.text, x.attr("href"))).toList
  println(categoriesLinks.mkString("\n"))

  try{
    var offers = scrapper.getOffers("https://www.olx.pl/oferty/q-scala/")
    println("Oferty: " + offers.size);
    offers = scrapper.getOffers(categoriesLinks.head._2)
    println("Oferty: " + offers.size);
  }
  catch {
    case e: ToManyOffersException => println(e.message)
  }
}
