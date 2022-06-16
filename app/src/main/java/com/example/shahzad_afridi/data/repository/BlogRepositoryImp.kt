package com.example.shahzad_afridi.data.repository

import com.example.shahzad_afridi.data.model.Blog
import com.example.shahzad_afridi.util.Constant.BLOG_URL
import com.example.shahzad_afridi.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class BlogRepositoryImp : BlogRepository {

    override suspend fun getBlogContent(result: (UiState<Blog>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val doc: Document = Jsoup.connect(BLOG_URL).get()
                val body: Element = doc.body()
                result.invoke(
                    UiState.Success(
                        Blog(
                            content = body.text()
                        )
                    )
                )
            } catch (e: java.lang.Exception) {
                result.invoke(
                    UiState.Failure(
                        e.message
                    )
                )
            }
        }
    }
}