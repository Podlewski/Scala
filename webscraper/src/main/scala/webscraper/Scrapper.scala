package webscraper

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Element
import webscraper.Utils.retry

import scala.util.{Failure, Success, Try}

class Scrapper {

  private val browser = JsoupBrowser()

  def getCategories(url: String): List[Element] = {
    val site = retry(15)(browser.get(url))

    val rootNode = site >> elementList(".maincategories")
    val childNodes = (rootNode >> elementList(".wrapper .maincategories-list a")).flatten

    val results = childNodes map { e =>
      val result = Try(e >> element("a"))
      result match {
        case Success(a) => a
        case Failure(_) => e
      }
    }

    results.take(100)
  }

  def getOffers(url: String) = {
    val site = retry(15)(browser.get(url))

    var rootNode = site >> elementList("table")
    rootNode = rootNode.filter(x => x.hasAttr("summary") && x.attr("summary").contentEquals("Ogłoszenia"))

    val childNodes = (rootNode >> elementList("table tbody")).flatten

    var a = 1
  }
}

object Scrapper {
  def apply() = new Scrapper()
}
