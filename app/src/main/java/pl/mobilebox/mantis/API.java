package pl.mobilebox.mantis;

import android.os.NetworkOnMainThreadException;
import android.util.Log;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.*;
import retrofit2.http.*;
import java.util.List;

public class API implements Callback<Issues> {

    public static final String API_URL = "https://mantis.mobilebox.pl/api/rest/";
    private IssuesIF issues;
    public Issues response;

    public interface IssuesIF {
        @Headers("Authorization: ")
        @GET("issues")
        Call<Issues> IssuesIF(
                @Query("page_size") String size,
                @Query("page") String page);
    }

    public API() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        issues = retrofit.create(IssuesIF.class);
    }

    public void getIssues(int size, int page) {
        Call<Issues> call = issues.IssuesIF(Integer.toString(size), Integer.toString(page));
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Issues> call, Response<Issues> res) {
        if (res.isSuccessful())
            response = res.body();
    }

    @Override
    public void onFailure(Call<Issues> call, Throwable t) {
        Log.d("API", t.getMessage());
    }
}