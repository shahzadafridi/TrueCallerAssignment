package com.example.shahzad_afridi.data.repository

import com.example.shahzad_afridi.data.model.Blog
import com.example.shahzad_afridi.util.UiState

interface BlogRepository {
    suspend fun getBlogContent(result: (UiState<Blog>) -> Unit)
}