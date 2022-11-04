package com.fincare.shaadikaro.data.network.utils

import com.fincare.support.logger.log
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class NetworkCall {

    suspend fun<T: Any> apiRequest(call: suspend () -> Response<T>) : T{
        val response = call.invoke()
        if(response.isSuccessful){
            log("RESPONSE | SUCCESS | ${response.body()}")
            return response.body()!!
        }else{
            val error = response.errorBody()?.toString()
            log("RESPONSE | ERROR | $error")
            val message = StringBuilder()
            error?.let{
                try{
                    message.append(JSONObject(it).getString("message"))
                }catch(e: JSONException){ }
                message.append("\n")
            }
            message.append("Error Code: ${response.code()}")
            throw ApiException(message.toString())
        }
    }
}
