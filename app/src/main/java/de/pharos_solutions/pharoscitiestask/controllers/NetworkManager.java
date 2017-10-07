package de.pharos_solutions.pharoscitiestask.controllers;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import de.pharos_solutions.pharoscitiestask.R;
import de.pharos_solutions.pharoscitiestask.models.APIUrls;
import de.pharos_solutions.pharoscitiestask.models.CityModel;
import de.pharos_solutions.pharoscitiestask.utils.MessagesUtils;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by muhammadkorany on 10/5/17.
 */
public class NetworkManager {

    private static Context mContext;
    private static NetworkManager networkManager;
    private OkHttpClient.Builder httpClient;
    private Retrofit.Builder builder;
    private Retrofit retrofit;
    private EndpointsInterface endpointsInterface;

    // Private constructor to make the network manager not reachable using the getInstance constructor
    private NetworkManager() {
        initialization();
    }

    // Using singleton
    public static synchronized NetworkManager getInstance(final Context context) {
        mContext = context;

        if (networkManager == null) {
            networkManager = new NetworkManager();
        }
        return networkManager;
    }

    // Initialization of retrofit instance to perform API calls
    private void initialization() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });

        builder = new Retrofit.Builder()
                .baseUrl(APIUrls.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson));
        retrofit = builder.client(httpClient.build()).build();

        endpointsInterface = retrofit.create(EndpointsInterface.class);
    }

    // Retrieve cities from API
    public void getCities(int page, final APIResponseCallback apiResponseCallback){
        Call<CityModel[]> call = endpointsInterface.getCities(page);

        call.clone().enqueue(new Callback<CityModel[]>() {
            @Override
            public void onResponse(Call<CityModel[]> call, Response<CityModel[]> response) {
                if (response.code()==200){

                    CityModel[] cityModels = (CityModel[])response.body();

                    if (cityModels!=null && cityModels.length>0){
                        apiResponseCallback.onSuccess(response);
                    }else{
                        apiResponseCallback.onFailure(response);
                    }
                }
            }

            @Override
            public void onFailure(Call<CityModel[]> call, Throwable t) {
                MessagesUtils.showToast(mContext, mContext.getString(R.string.server_error));
            }

        });
    }
}
