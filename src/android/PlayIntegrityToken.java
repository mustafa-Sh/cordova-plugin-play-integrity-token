package com.azentio.playintegritytoken;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.android.play.core.integrity.IntegrityManager;
import com.google.android.play.core.integrity.IntegrityManagerFactory;
import com.google.android.play.core.integrity.IntegrityTokenRequest;
import com.google.android.play.core.integrity.IntegrityTokenResponse;
import com.google.android.play.core.integrity.model.IntegrityErrorCode;

/**
 * This class echoes a string called from JavaScript.
 */
public class PlayIntegrityToken extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("integToken")) {
            String projectNumber = args.getString(0);
            long projectNumberL = Long.parseLong(projectNumber);
			String hashedNonce = args.getString(1);
            this.getToken(projectNumberL, hashedNonce, callbackContext);
            return true;
        }
        return false;
    }

    private void getToken(long projectNumber, String hashedNonce, CallbackContext callbackContext) {

        Context context = this.cordova.getActivity().getApplicationContext();

        // Create an instance of a manager.
        IntegrityManager integrityManager = IntegrityManagerFactory.create(context);

        // Request the integrity token by providing a nonce.
        Task<IntegrityTokenResponse> integrityTokenResponse = integrityManager.requestIntegrityToken(
                IntegrityTokenRequest.builder()
                        .setNonce(hashedNonce)
                        .setCloudProjectNumber(projectNumber)
                        .build());

        integrityTokenResponse.addOnSuccessListener(integrityTokenResponse1 -> {
            callbackContext.success(integrityTokenResponse1.token());
        });
    }
}
