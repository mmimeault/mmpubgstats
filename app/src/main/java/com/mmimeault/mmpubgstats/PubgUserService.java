package com.mmimeault.mmpubgstats;

import com.mmimeault.mmpubgstats.data.stats.Stats;
import com.mmimeault.mmpubgstats.data.user.Users;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PubgUserService {
    @GET("players")
    Call<Users> search(@Query("filter[playerNames]") String user);

    @GET("players/{playerId}/seasons/{seasonId}")
    Call<Stats> stats(@Path("playerId") String playerId, @Path("seasonId") String seasonId);
}
