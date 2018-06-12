package com.mmimeault.mmpubgstats;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PubgUserService {
    @GET("players")
    Call<Users> search(@Query("filter[playerNames]") String user);
}
