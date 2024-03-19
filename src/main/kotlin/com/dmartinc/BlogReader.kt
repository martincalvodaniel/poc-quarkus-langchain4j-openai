package com.dmartinc

import dev.langchain4j.service.SystemMessage
import dev.langchain4j.service.UserMessage
import io.quarkiverse.langchain4j.RegisterAiService

@RegisterAiService
interface BlogReader {

    @SystemMessage("You are an assistant that receives the body of an HTML page and sum up the article in that page. Add key takeaways to the end of the sum up.")
    @UserMessage("""
        The body will be sent in parts in the next requests. Don't return anything.
        """
    )
    fun prepare(): String

    @UserMessage("""
        Here's the next part of the body page:
        ```html
        {html}
        ```
        Wait for the next parts. Don't answer anything else.
        """
    )
    fun sendBody(html: String): String

    @UserMessage("""
        That's it. You can sum up the article and add key takeaways to the end of the sum up.
        """
    )
    fun sumUp(): String
}
