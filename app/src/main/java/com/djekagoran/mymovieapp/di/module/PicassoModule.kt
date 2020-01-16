package com.djekagoran.mymovieapp.di.module

import android.app.Application
import com.djekagoran.mymovieapp.di.scope.ApplicationScope
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class PicassoModule {

    @Provides
    @ApplicationScope
    fun picasso(context: Application, okHttpDownloader: OkHttp3Downloader): Picasso {
        return Picasso.Builder(context)
            .downloader(okHttpDownloader)
            .build()
    }

    @Provides
    @ApplicationScope
    fun okHttpDownloader(okHttpClient: OkHttpClient): OkHttp3Downloader {
        return OkHttp3Downloader(okHttpClient)
    }
}
