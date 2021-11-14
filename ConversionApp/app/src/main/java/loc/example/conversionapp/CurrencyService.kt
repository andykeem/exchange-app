package loc.example.conversionapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {

    @GET(value = "api/latest")
    fun getRates(@Query(value = "access_key") accessKey: String): Call<Currency>
}