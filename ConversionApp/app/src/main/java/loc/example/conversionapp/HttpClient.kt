package loc.example.conversionapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URl = "http://data.fixer.io"

object HttpClient {
    fun getCurrencyService(): CurrencyService {
        return Retrofit.Builder()
            .baseUrl(BASE_URl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyService::class.java)
    }
}