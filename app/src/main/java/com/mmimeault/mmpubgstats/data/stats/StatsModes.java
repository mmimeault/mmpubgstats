package com.mmimeault.mmpubgstats.data.stats;

import com.google.gson.annotations.SerializedName;

public class StatsModes {

    @SerializedName("solo-fpp")
    StatsMode soloFpp;

    @SerializedName("duo-fpp")
    StatsMode duoFpp;

    @SerializedName("squad-fpp")
    StatsMode squadFpp;

    public StatsMode getSoloFpp() {
        return soloFpp;
    }

    public void setSoloFpp(StatsMode soloFpp) {
        this.soloFpp = soloFpp;
    }

    public StatsMode getDuoFpp() {
        return duoFpp;
    }

    public void setDuoFpp(StatsMode duoFpp) {
        this.duoFpp = duoFpp;
    }

    public StatsMode getSquadFpp() {
        return squadFpp;
    }

    public void setSquadFpp(StatsMode squadFpp) {
        this.squadFpp = squadFpp;
    }
}
