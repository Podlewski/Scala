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

    results
  }

  def getOffers(url: String): List[Element] = {
    var site = retry(15)(browser.get(url))

    val foundOffers = site >> elementList(".hasPromoted p")

    if (foundOffers.size < 1 || foundOffers.head.text.contains("Nie znaleźliśmy ogłoszeń dla tego zapytania."))
      throw ScrappingException("Nie znaleziono żadnych ofert dla takiego zapytania")

    if (foundOffers.head.text.filter(_.isDigit).toInt > 19500)
      throw ScrappingException(s"Znaleziono $foundOffers ofert - za dużo by uwzględnić wszystkie w statystykach")

    var nextPage: List[Element] = null;
    var results = List[Element]();

    do {
      results = results ::: getOffersFromSite(site)

      nextPage = site >> elementList("head link")
      nextPage = nextPage.filter(x =>
        x.hasAttr("rel") && x.attr("rel").contentEquals("next")
      )

      if (nextPage.nonEmpty) {
        val nextUrl = nextPage.map(x => x.attr("href")).head
        site = retry(15)(browser.get(nextUrl))
      }
    } while (nextPage.nonEmpty)

    results
  }

  def getOffersFromSite(site: Scrapper.this.browser.DocumentType): List[Element] = {
    var rootNode = site >> elementList("table")
    rootNode = rootNode.filter(x =>
      x.hasAttr("summary") && x.attr("summary").contentEquals("Ogłoszenia")
    )

    val childNodes = (rootNode >> elementList(".offer table tbody")).flatten

    val results = childNodes map { e =>
      val result = Try(e >> element("tr"))
      result match {
        case Success(a) => a
        case Failure(_) => e
      }
    }

    results
  }
}

object Scrapper {
  def apply() = new Scrapper()
}
