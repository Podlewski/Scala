package webscraper

object Utils {

  val url = "http://www.olx.pl"
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
}
