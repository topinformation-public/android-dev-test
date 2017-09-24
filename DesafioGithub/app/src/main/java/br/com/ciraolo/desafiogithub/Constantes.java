package br.com.ciraolo.desafiogithub;

import static br.com.ciraolo.desafiogithub.Constantes.Urls.Ambiente.HML;

/**
 * Created by henriqueciraolo on 21/09/17.
 */

public class Constantes {
    public static class Urls {

        public final static Constantes.Urls.Ambiente AMBIENTE = HML;
        public final static String URL = AMBIENTE.toString();

        public enum Ambiente {
            LOC("https://api.github.com/search/"),
            DEV("https://api.github.com/search/"),
            TST("https://api.github.com/search/"),
            HML("https://api.github.com/search/"),
            PRD("https://api.github.com/search/"),;

            private final String endpoint;

            Ambiente(String s) {
                endpoint = s;
            }

            public String toString() {
                return this.endpoint;
            }
        }
    }
}
