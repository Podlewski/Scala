package webscraper

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

import scala.util.{Failure, Success, Try}

class Scrapper(url: String) {

  def fetch: Seq[String] = {
    val browser = JsoupBrowser()
    val site = Utils.retry(15)(browser.get(url))

    val start = site >> elementList(".mw-parser-output")
    val items = (start >> elementList(".mw-parser-output > ul > li")).flatten

    val bands = items map { e =>
      val band = Try(e >> element("a"))
      band match {
        case Success(a) => a.text
        case Failure(_) => e.text
      }
    }

    val webpage = new Webpage(site.title)
    bands.take(10000)
  }
}

object Scrapper {
  def apply(url: String) = new Scrapper(url)
}
