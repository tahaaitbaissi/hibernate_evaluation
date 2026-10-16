package ma.projet.test;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.service.EmployeService;
import ma.projet.service.EmployeTacheService;
import ma.projet.service.ProjetService;
import ma.projet.service.TacheService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

public class ProjetTest {

    private static EmployeService employeService = new EmployeService();
    private static ProjetService projetService = new ProjetService();
    private static TacheService tacheService = new TacheService();
    private static EmployeTacheService affectationService = new EmployeTacheService();
    private static int salmaId;
    private static int karimId;
    private static int projetId;

    @BeforeClass
    public static void init() throws Exception {
        SimpleDateFormat jour = new SimpleDateFormat("dd/MM/yyyy");

        Employe karim = new Employe("BENNIS", "Karim", "0611111111");
        Employe salma = new Employe("BENNANI", "Salma", "0622222222");
        employeService.create(karim);
        employeService.create(salma);
        karimId = karim.getId();
        salmaId = salma.getId();

        Projet projet = new Projet("Gestion de stock", jour.parse("14/01/2013"), jour.parse("30/06/2013"), karim);
        projetService.create(projet);
        projetId = projet.getId();

        Tache analyse = new Tache("Analyse", jour.parse("01/02/2013"), jour.parse("28/02/2013"), 800, projet);
        Tache conception = new Tache("Conception", jour.parse("01/03/2013"), jour.parse("31/03/2013"), 1200, projet);
        Tache dev = new Tache("Developpement", jour.parse("01/04/2013"), jour.parse("30/04/2013"), 2500, projet);
        tacheService.create(analyse);
        tacheService.create(conception);
        tacheService.create(dev);

        affectationService.create(new EmployeTache(jour.parse("10/02/2013"), jour.parse("20/02/2013"), salma, analyse));
        affectationService.create(new EmployeTache(jour.parse("10/03/2013"), jour.parse("15/03/2013"), salma, conception));
        affectationService.create(new EmployeTache(jour.parse("10/04/2013"), jour.parse("25/04/2013"), salma, dev));
    }

    @Test
    public void testTachesParEmploye() {
        List<EmployeTache> taches = employeService.tachesRealisees(salmaId);
        assertEquals(3, taches.size());
    }

    @Test
    public void testProjetsGeres() {
        List<Projet> projets = employeService.projetsGeres(karimId);
        assertEquals(1, projets.size());
        assertEquals("Gestion de stock", projets.get(0).getNom());
    }

    @Test
    public void testTachesPlanifiees() {
        List<Tache> taches = projetService.tachesPlanifiees(projetId);
        assertEquals(3, taches.size());
    }

    @Test
    public void testAfficherRealisees() {
        List<EmployeTache> realisees = projetService.tachesRealisees(projetId);
        assertEquals(3, realisees.size());
        projetService.afficherTachesRealisees(projetId);
    }

    @Test
    public void testPrixSuperieur() {
        List<Tache> taches = tacheService.findPrixSuperieur(1000);
        assertEquals(2, taches.size());
    }

    @Test
    public void testRealiseesEntreDates() throws Exception {
        SimpleDateFormat jour = new SimpleDateFormat("dd/MM/yyyy");
        List<EmployeTache> taches = tacheService.realiseesEntreDates(jour.parse("01/02/2013"), jour.parse("28/02/2013"));
        assertEquals(1, taches.size());
        assertEquals("Analyse", taches.get(0).getTache().getNom());
    }
}
