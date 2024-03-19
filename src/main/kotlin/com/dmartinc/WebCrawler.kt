package com.dmartinc

import jakarta.enterprise.context.ApplicationScoped
import org.jsoup.Jsoup
import java.io.IOException
import java.io.UncheckedIOException

@ApplicationScoped
class WebCrawler {

    fun crawl(url: String): String =
        try {
            Jsoup.connect(url).get().body().getElementsByClass("rh-push-content-main").first()!!.html()
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
}