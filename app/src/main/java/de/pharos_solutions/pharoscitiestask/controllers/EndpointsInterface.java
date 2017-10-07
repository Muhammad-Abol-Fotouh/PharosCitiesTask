package de.pharos_solutions.pharoscitiestask.controllers;

import de.pharos_solutions.pharoscitiestask.models.APIUrls;
import de.pharos_solutions.pharoscitiestask.models.CityModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by muhammadkorany on 10/5/17.
 */

public interface EndpointsInterface {

    // Get cities from the web service
    @GET(APIUrls.CITIES_URL)
    Call<CityModel[]> getCities(@Query("page") int page);
}
