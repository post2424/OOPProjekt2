package org.example.oopprojekt2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ost {
    private List<Toode> tooted;
    boolean onTagasiMakstud;

    public Ost(List<Toode> tooted) {
        this.tooted = tooted;
        this.onTagasiMakstud = false; /*ostu tegemisel eeldab, et selle eest ei ole veel tagasi makstud*/
    }

    public List<Toode> getTooted() {
        return tooted;
    }

    public void maksaTagasi() {
        onTagasiMakstud = true;
    }

    @Override
    public String toString() { //testimiseks
        return tooted.toString();
    }
}
