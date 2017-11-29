
package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int OLETUSKAPASITEETTI = 5; // taulukon koko alussa
    public final static int OLETUSKASVATUS = 5;  // kasvatetava koko

    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] taulukko;      // Joukon luvut säilytetään taulukon alkupäässä.
    private int koko;    // Tyhjässä joukossa alkioiden_määrä on nolla.

    public IntJoukko() {
        this(OLETUSKAPASITEETTI, OLETUSKASVATUS);
    }

    public IntJoukko(int kapasiteetti) {
        this(kapasiteetti, OLETUSKASVATUS);
    }


    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("Negatiivinen kapasiteetti");
        }
        if (kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("Negatiivinen kasvatuskoko");
        }
        this.taulukko = new int[kapasiteetti];
        this.koko = 0;
        this.kasvatuskoko = kasvatuskoko;

    }

    public boolean lisaa(int luku) {

        if (!kuuluu(luku)) {
            taulukko[koko] = luku;
            koko++;
            if (koko % taulukko.length == 0) {
                int[] taulukkoVanha = taulukko;
                taulukko = new int[koko + kasvatuskoko];
                kopioiTaulukko(taulukkoVanha, taulukko);
            }
            return true;
        }
        return false;
    }

    public boolean kuuluu(int luku) {
        for (int i = 0; i < koko; i++) {
            if (luku == taulukko[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean poista(int luku) {
        int indeksi = etsi(luku);
        if (indeksi != -1) {
            poistaIndeksi(indeksi);
            return true;
        }
        return false;
    }

    private int etsi(int luku) {
        int indeksi = -1;
        for (int i = 0; i < koko; i++) {
            if (luku == taulukko[i]) {
                indeksi = i; //siis luku löytyy tuosta kohdasta :D
                taulukko[indeksi] = 0;
                break;
            }
        }
        return indeksi;
    }

    private void poistaIndeksi(int indeksi) {
        for (int j = indeksi; j < koko - 1; j++) {
            int apu = taulukko[j];
            taulukko[j] = taulukko[j + 1];
            taulukko[j + 1] = apu;
        }
        koko--;
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        for (int i = 0; i < vanha.length; i++) {
            uusi[i] = vanha[i];
        }

    }

    public int koko() {
        return koko;
    }


    @Override
    public String toString() {
        if (koko == 0) {
            return "{}";
        } else if (koko == 1) {
            return "{" + taulukko[0] + "}";
        } else {
            String tuloste = "{";
            for (int i = 0; i < koko - 1; i++) {
                tuloste += (taulukko[i] + ", ");
            }
            tuloste += (taulukko[koko - 1] + "}");
            return tuloste;
        }
    }

    public int[] toIntArray() {
        int[] taulu = new int[koko];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = taulukko[i];
        }
        return taulu;
    }


    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko uusiJoukko = new IntJoukko();
        for (int i = 0; i < a.toIntArray().length; i++) {
            uusiJoukko.lisaa(a.toIntArray()[i]);
        }
        for (int i = 0; i < b.toIntArray().length; i++) {
            uusiJoukko.lisaa(b.toIntArray()[i]);
        }
        return uusiJoukko;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko uusiJoukko = new IntJoukko();
        for (int i = 0; i < a.toIntArray().length; i++) {
            for (int j = 0; j < b.toIntArray().length; j++) {
                if (a.toIntArray()[i] == b.toIntArray()[j]) {
                    uusiJoukko.lisaa(b.toIntArray()[j]);
                }
            }
        }
        return uusiJoukko;

    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        IntJoukko uusiJoukko = new IntJoukko();
        for (int i = 0; i < a.toIntArray().length; i++) {
            uusiJoukko.lisaa(a.toIntArray()[i]);
        }
        for (int i = 0; i < b.toIntArray().length; i++) {
            uusiJoukko.poista(i);
        }
        return uusiJoukko;
    }

}