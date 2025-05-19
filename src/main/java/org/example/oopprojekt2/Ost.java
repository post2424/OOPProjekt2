package org.example.oopprojekt2;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ost implements Serializable {
    private List<Toode> tooted;
    private LocalDateTime ostuaeg;
    private boolean onTagasiMakstud;

    public Ost(List<Toode> tooted) {
        this.tooted = tooted;
        this.ostuaeg = LocalDateTime.now();
        this.onTagasiMakstud = false; /*ostu tegemisel eeldab, et selle eest ei ole veel tagasi makstud*/
    }

    public List<Toode> getTooted() {
        return tooted;
    }
    public LocalDateTime getOstuaeg() {
        return ostuaeg;
    }

    public boolean kasOnTagasiMakstud() {
        return onTagasiMakstud;
    }

    public void maksaTagasi() {
        onTagasiMakstud = true;
    }

    @Override
    public String toString() { //testimiseks
        return tooted.toString();
    }
}
