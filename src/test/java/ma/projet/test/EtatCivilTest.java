package ma.projet.test;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

public class EtatCivilTest {

    private static HommeService hommeService = new HommeService();
    private static FemmeService femmeService = new FemmeService();
    private static MariageService mariageService = new MariageService();
    private static int safiId;
    private static int salimaId;

    @BeforeClass
    public static void init() throws Exception {
        SimpleDateFormat jour = new SimpleDateFormat("dd/MM/yyyy");

        Homme safi = new Homme("SAFI", "SAID", "0633333333", "Casa", jour.parse("25/05/1960"));
        Homme h2 = new Homme("TAZI", "Mehdi", "0644444444", "Rabat", jour.parse("10/01/1970"));
        Homme h3 = new Homme("BENNIS", "Omar", "0655555555", "Fes", jour.parse("15/06/1972"));
        Homme h4 = new Homme("EL FASSI", "Yassine", "0666666666", "Casa", jour.parse("20/09/1968"));
        Homme h5 = new Homme("CHRAIBI", "Adam", "0677777777", "Tanger", jour.parse("05/12/1975"));
        hommeService.create(safi);
        hommeService.create(h2);
        hommeService.create(h3);
        hommeService.create(h4);
        hommeService.create(h5);
        safiId = safi.getId();

        Femme salima = new Femme("RAMI", "SALIMA", "0688888888", "Casa", jour.parse("14/04/1966"));
        Femme amal = new Femme("ALI", "AMAL", "0699999999", "Rabat", jour.parse("22/08/1970"));
        Femme wafa = new Femme("ALAOUI", "WAFA", "0601010101", "Fes", jour.parse("30/01/1975"));
        Femme karima = new Femme("ALAMI", "KARIMA", "0602020202", "Casa", jour.parse("11/11/1964"));
        Femme nadia = new Femme("BENNANI", "Nadia", "0603030303", "Meknes", jour.parse("09/03/1978"));
        Femme f6 = new Femme("TAZI", "Sara", "0604040404", "Casa", jour.parse("17/07/1971"));
        Femme f7 = new Femme("LAMRANI", "Hiba", "0605050505", "Rabat", jour.parse("25/12/1973"));
        Femme f8 = new Femme("BOURKADI", "Rim", "0606060606", "Fes", jour.parse("08/05/1976"));
        Femme f9 = new Femme("IDRISSI", "Najat", "0607070707", "Casa", jour.parse("19/09/1974"));
        Femme rkia = new Femme("EL FASSI", "Rkia", "0608080808", "Safi", jour.parse("12/03/1962"));
        femmeService.create(salima);
        femmeService.create(amal);
        femmeService.create(wafa);
        femmeService.create(karima);
        femmeService.create(nadia);
        femmeService.create(f6);
        femmeService.create(f7);
        femmeService.create(f8);
        femmeService.create(f9);
        femmeService.create(rkia);
        salimaId = salima.getId();

        mariageService.create(new Mariage(jour.parse("03/09/1990"), null, 4, safi, salima));
        mariageService.create(new Mariage(jour.parse("03/09/1995"), null, 2, safi, amal));
        mariageService.create(new Mariage(jour.parse("04/11/2000"), null, 3, safi, wafa));
        mariageService.create(new Mariage(jour.parse("03/09/1989"), jour.parse("03/09/1990"), 0, safi, karima));

        mariageService.create(new Mariage(jour.parse("10/05/2000"), jour.parse("12/06/2005"), 1, h2, nadia));
        mariageService.create(new Mariage(jour.parse("20/01/2006"), null, 2, h3, nadia));

        mariageService.create(new Mariage(jour.parse("05/01/1992"), null, 1, h4, f6));
        mariageService.create(new Mariage(jour.parse("06/06/1992"), null, 0, h4, f7));
        mariageService.create(new Mariage(jour.parse("07/07/1993"), null, 2, h4, f8));
        mariageService.create(new Mariage(jour.parse("08/08/1994"), null, 1, h4, f9));
    }

    @Test
    public void testListeFemmes() {
        assertEquals(10, femmeService.findAll().size());
    }

    @Test
    public void testPlusAgee() {
        Femme agee = femmeService.plusAgee();
        assertEquals("Rkia", agee.getPrenom());
    }

    @Test
    public void testEpousesEntreDates() throws Exception {
        SimpleDateFormat jour = new SimpleDateFormat("dd/MM/yyyy");
        List<Femme> epouses = hommeService.epousesEntreDates(safiId,
                jour.parse("01/01/1989"), jour.parse("31/12/1996"));
        assertEquals(3, epouses.size());
    }

    @Test
    public void testNbEnfants() throws Exception {
        SimpleDateFormat jour = new SimpleDateFormat("dd/MM/yyyy");
        int total = femmeService.nbEnfantsEntreDates(salimaId,
                jour.parse("01/01/1985"), jour.parse("31/12/2000"));
        assertEquals(4, total);
    }

    @Test
    public void testFemmesDeuxFois() {
        List<Femme> femmes = femmeService.marieesAuMoinsDeuxFois();
        assertEquals(1, femmes.size());
        assertEquals("Nadia", femmes.get(0).getPrenom());
    }

    @Test
    public void testHommesQuatreFemmes() throws Exception {
        SimpleDateFormat jour = new SimpleDateFormat("dd/MM/yyyy");
        List<Homme> hommes = hommeService.hommesQuatreFemmes(
                jour.parse("01/01/1991"), jour.parse("31/12/1994"));
        assertEquals(1, hommes.size());
        assertEquals("Yassine", hommes.get(0).getPrenom());
    }

    @Test
    public void testAfficherMariages() {
        assertEquals(4, mariageService.mariagesDUnHomme(safiId).size());
        mariageService.afficherMariages(safiId);
    }
}
