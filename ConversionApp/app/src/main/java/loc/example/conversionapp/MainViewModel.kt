package loc.example.conversionapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainViewModel"
private const val ACCESS_KEY = "4f0dc3bc9195c6cdab266125b65f7809"

class MainViewModel : ViewModel() {

    val _currencyData = MutableLiveData<Currency>()
    val currencyData: LiveData<Currency> = _currencyData
    val _updatedRateMap = MutableLiveData<Map<String, Double>>()
    val updatedRateMap: LiveData<Map<String, Double>> = _updatedRateMap

    fun fetchCurrencies() {
        HttpClient.getCurrencyService().getRates(ACCESS_KEY).enqueue(object : Callback<Currency> {
            override fun onResponse(call: Call<Currency>, response: Response<Currency>) {
                Log.d(TAG, "onResponse: ${response.body()}")
                _currencyData.value = response.body()
            }

            override fun onFailure(call: Call<Currency>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}", t)
            }
        })
    }

    fun getEuro(input: String, rateVal: Double?): Double {
        val euro = input.toDouble().div(rateVal ?: 1.00)
        Log.d(TAG, "getEuro, EUR rate: ${euro}")
        return euro
    }

    fun getConvertedRates(euro: Double, rates: MutableMap<String, Double>) {
        val newRates = mutableMapOf<String, Double>()
        rates.map {
            newRates[it.key] = it.value.times(euro)
        }
        _updatedRateMap.value = newRates
    }
}