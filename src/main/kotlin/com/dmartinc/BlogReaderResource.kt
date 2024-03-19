package com.dmartinc

import com.dmartinc.Utils.logger
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/blog-reader")
class BlogReaderResource(
    private val blogReader: BlogReader,
    private val webCrawler: WebCrawler,
    private val requestSplitter: RequestSplitter,
) {

    @Path("/read")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    fun read(url: String): String {
        // Read the HTML from the specified URL
        val content = webCrawler.crawl(url)

        logger.info(content)

        // Prepare the model
        logger.info("> Preparing analysis of {}", url)
        blogReader.prepare()

        // Split the HTML into small pieces
        val split = requestSplitter.split(content)

        // Send each piece of HTML to the LLM
        for (i in split.indices) {
            logger.info("> Analyzing article... Part {} out of {}.", (i + 1), split.size)
            blogReader.sendBody(split[i])
        }

        // Ask the model to sum up the article
        logger.info("> Preparing response...")
        val sumUp: String = blogReader.sumUp()

        // Return the result to the user
        logger.info("> Response for {} ready", url)
        return sumUp
    }

    companion object {
        val logger = logger()
    }
}