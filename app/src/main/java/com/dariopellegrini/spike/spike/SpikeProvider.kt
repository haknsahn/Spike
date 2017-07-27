package com.dariopellegrini.spike.spike

import android.util.Log
import com.dariopellegrini.spike.response.Spike
import com.dariopellegrini.spike.spike.response.SpikeError
import com.dariopellegrini.spike.spike.response.SpikeErrorResponse
import com.dariopellegrini.spike.spike.response.SpikeResponse

/**
 * Created by dariopellegrini on 25/07/17.
 */
class SpikeProvider<in T : TargetType> {
    fun request(target: T, onSuccess: (SpikeResponse)-> Unit, onError: (SpikeErrorResponse) -> Unit) {
        if (Spike.instance.network == null) {
            Log.i("Spike", "Spike non initiated. Run: Spike.instance.configure(context)")
            return
        }

        Spike.instance.network?.let { network ->
            network.jsonRequest(target.baseURL + target.path, target.method, target.headers, target.parameters, target.multipartEntities, {
                response, error ->
                if (response != null) {
                    onSuccess(response)
                } else if (error != null) {
                    onError(error)
                }

            })
        }
    }
}