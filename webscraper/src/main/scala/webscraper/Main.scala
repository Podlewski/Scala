package webscraper

import webscraper.Utils.{olxUrl, printOffersStats, setProxy}

object Main extends App {

  setProxy()
  val scrapper = Scrapper()

  if (args.length == 0) {
    val categories = scrapper.getCategories(olxUrl)
    val categoriesLinks = categories.map(x => (x.text, x.attr("href"))).toList
    var index = 0

    println("Kategorie:\n")
    for (category <- categoriesLinks) {
      println(index + ": " + category._1 + " (" + category._2 + ")")
      index = index + 1
    }
  }

  else if (args.length > 0) {
    var urlBase = olxUrl
    var query = args.mkString("-")
    var infoLog: String = ""

    if (args.length > 1 && args(0).forall(_.isDigit)) {
      val categories = scrapper.getCategories(olxUrl)
      val categoriesLinks = categories.map(x => (x.text, x.attr("href")))

      query = args.tail.mkString("-")
      urlBase = categoriesLinks(args(0).toInt)._2
      infoLog += ", kategoria '" + categoriesLinks(args(0).toInt)._1 + "'"
    }

    val locations = List(("Cała Polska", "oferty"), ("Województwo Łódzkie", "lodzkie"), ("Łódź", "lodz"))
    infoLog = "Wyniki dla zapytania '" + query.replace('-', ' ') + infoLog + "':"
    println(infoLog)

    for (location <- locations) {
      val url = urlBase + location._2 + "/q-" + query + "/"

      try {
        println("\n" + location._1.toUpperCase)
        val offers = scrapper.getOffers(url)
        printOffersStats(offers)
      }
      catch {
        case e: ScrappingException => println(e.message)
      }
    }
  }
}
