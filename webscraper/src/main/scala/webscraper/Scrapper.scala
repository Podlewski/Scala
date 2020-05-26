package webscraper

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Element

import scala.util.{Failure, Success, Try}

class Scrapper(url: String) {

  private val browser = JsoupBrowser()

  def fetch: Seq[Element] = {
    val site = Utils.retry(15)(browser.get(url))

    val rootNode = site >> elementList(".maincategories")
    val aNodes = (rootNode >> elementList(".wrapper .maincategories-list a")).flatten

    val results = aNodes map { e =>
      val result = Try(e >> element("a"))
      result match {
        case Success(a) => a
        case Failure(_) => e
      }
    }

    results.take(100)
  }
}

object Scrapper {
  def apply(url: String) = new Scrapper(url)
}
