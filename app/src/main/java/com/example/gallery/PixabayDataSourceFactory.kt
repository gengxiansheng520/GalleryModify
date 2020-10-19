package com.example.gallery

import androidx.paging.DataSource

class PixabayDataSourceFactory: DataSource.Factory<Int,PhotoItem>() {
    override fun create(): DataSource<Int, PhotoItem> {
        return PixabayDataSource()
    }
}