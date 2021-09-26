package com.ozgurerdogan.kotlin_artbooktest.model

import com.ozgurerdogan.kotlin_artbooktest.model.ImageResult

data class ImageResponse(
    val total:Int,
    val totalHits:Int,
    val hits:List<ImageResult>
)
