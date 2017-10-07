package de.pharos_solutions.pharoscitiestask.controllers;

import retrofit2.Response;

/**
 * Created by muhammadkorany on 10/5/17.
 */

public interface APIResponseCallback {
    // Called when data loaded properly
    void onSuccess(Response response);

    // Called when connection to server done, but server responds with error messages
    void onFailure(Response response);
}
