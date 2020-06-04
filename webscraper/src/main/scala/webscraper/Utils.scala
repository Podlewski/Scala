package webscraper

import net.ruippeixotog.scalascraper.model.Element

object Utils {
  val olxUrl = "http://www.olx.pl/"
  val proxyHost = "145.239.121.218"
  val proxyPort = "3129"
//  val proxyHost = "82.119.170.106"
//  val proxyPort = "8080"

  def setProxy(host: String = proxyHost, port: String = proxyPort): Unit = {
    // https://hidemy.name/en/proxy-list/?type=hs&anon=4#list
    // https://www.scraperapi.com/blog/best-10-free-proxies-and-free-proxy-lists-for-web-scraping/
    System.setProperty("http.proxyHost", host)
    System.setProperty("http.proxyPort", port)
    System.setProperty("https.proxyHost", host)
    System.setProperty("https.proxyPort", port)
  }

  //noinspection NoTailRecursionAnnotation
  def retry[T](n: Int)(op: => T): T = {
    try op catch {
      case _ if n > 1 =>
        retry(n - 1)(op)
    }
  }

  def printOffersStats(offers: List[Element]) = {
    val offersDetails = offers.map(x => x.select("strong").map(_.text) ++ x.select("[href]").map(_.attr("href")))
      .map(x => (x.toList(0), x.toList(1).toString.replaceAll("Za darmo", "0").replaceAll("Zamienię", "-1").replaceAll("[^\\d,.-]", "").replaceAll(",", ".").toDouble, x.toList(2)))

    val paidOffers = offersDetails.filter(_._2 > 0)
    if(paidOffers.size > 0) {
      println("\nOferty: " + paidOffers.size)

      var meanValue = paidOffers.map(_._2).sum / paidOffers.size
      meanValue = BigDecimal(meanValue).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
      val minValue = paidOffers.map(_._2).min
      val maxValue = paidOffers.map(_._2).max

      println(s"\nŚrednia: $meanValue zł")
      println(s"\nNajtańsze oferty: $minValue zł")
      for (offer <- paidOffers.filter(_._2 == minValue)) {println(offer._1 + " (" + offer._3 + ")")}
      println(s"\nNajdroższe oferty: $maxValue zł")
      for (offer <- paidOffers.filter(_._2 == maxValue)) {println(offer._1 + " (" + offer._3 + ")")}
    }

    val freeOffers = offersDetails.filter(_._2 == 0)
    if (freeOffers.size > 0) {
      println("\nOferty darmowe: " + offersDetails.filter(_._2 == 0).size)
      for (offer <- freeOffers) {println(offer._1 + " (" + offer._3 + ")")}
    }

    val offersWithSwap = offersDetails.filter(_._2 == -1)
    if (offersWithSwap.size > 0) {
      println("\nOferty zamiany: " + offersWithSwap.size)
      for (offer <- offersWithSwap) {println(offer._1 + " (" + offer._3 + ")")}
    }
  }
}
