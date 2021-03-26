package com.fedorskvortsov.companies.data.remote.api

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.fedorskvortsov.companies.data.remote.api.CompanyService.Companion.BASE_URL
import com.fedorskvortsov.companies.domain.entity.Company
import com.fedorskvortsov.companies.domain.entity.SimpleCompany
import com.google.gson.Gson
import com.google.gson.JsonParser
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CompanyServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : CompanyService {

    val imageLoader: ImageLoader by lazy {
        ImageLoader(requestQueue,
            object : ImageLoader.ImageCache {
                private val cache = LruCache<String, Bitmap>(20)
                override fun getBitmap(url: String): Bitmap {
                    return cache.get(url)
                }
                override fun putBitmap(url: String, bitmap: Bitmap) {
                    cache.put(url, bitmap)
                }
            })
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }

    override suspend fun fetchCompanies(): List<SimpleCompany> = suspendCancellableCoroutine { continuation ->
        val jsonArrayRequest = JsonArrayRequest(
            BASE_URL,
            { response ->
                val companies = Gson().fromJson(
                    response.toString(),
                    Array<SimpleCompany>::class.java
                ).toList()
                continuation.resume(companies)
            }, { exception ->
                continuation.resumeWithException(exception)
            }
        )
        continuation.invokeOnCancellation { jsonArrayRequest.cancel() }
        requestQueue.add(jsonArrayRequest)
    }

    override suspend fun fetchCompany(companyId: Int): Company = suspendCancellableCoroutine { continuation ->
        val jsonArrayRequest = JsonArrayRequest(
            "$BASE_URL?id=$companyId",
            { response ->
                val jsonObject = JsonParser.parseString(response[0].toString())
                val company = Gson().fromJson(jsonObject, Company::class.java)
                continuation.resume(company)
            }, { exception ->
                continuation.resumeWithException(exception)
            }
        )
        continuation.invokeOnCancellation { jsonArrayRequest.cancel() }
        requestQueue.add(jsonArrayRequest)
    }
}