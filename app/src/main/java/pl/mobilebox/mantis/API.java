package pl.mobilebox.mantis;

import android.provider.Settings;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import java.util.Observable;

public class API extends Observable implements Callback<Issues> {

    static final private String API_KEY = "";
    static final private String API_URL = "https://mantis.mobilebox.pl/api/rest/";
    private Retrofit retrofit;


    public interface IssuesIF {
        @Headers("Authorization: "+ API_KEY)
        @GET("issues")
        Call<Issues> IssuesIF(
                @Query("page_size") String size,
                @Query("page") String page
        );
    }

    API() {
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    void getIssues(int size, int page) {
        IssuesIF issues = retrofit.create(IssuesIF.class);
        Call<Issues> call = issues.IssuesIF(Integer.toString(size), Integer.toString(page));
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Issues> call, Response<Issues> res) {
        setChanged();
        if (res.isSuccessful())
            notifyObservers(res.body());
        else
            notifyObservers(null);
    }

    @Override
    public void onFailure(Call<Issues> call, Throwable t) {
        setChanged();
        notifyObservers(null);
        Log.d("API", t.getMessage());
    }
}