package org.example;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.service.CategorieService;
import ma.projet.service.CommandeService;
import ma.projet.service.EmployeService;
import ma.projet.service.EmployeTacheService;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.service.MariageService;
import ma.projet.service.ProduitService;
import ma.projet.service.ProjetService;
import ma.projet.service.TacheService;
import ma.projet.util.HibernateUtil;

import java.text.SimpleDateFormat;

public class App {

    public static void main(String[] args) throws Exception {
        SimpleDateFormat jour = new SimpleDateFormat("dd/MM/yyyy");

        CategorieService categorieService = new CategorieService();
        ProduitService produitService = new ProduitService();
        CommandeService commandeService = new CommandeService();
        LigneCommandeService ligneService = new LigneCommandeService();

        Categorie info = new Categorie("IN", "Informatique");
        categorieService.create(info);
        Produit es12 = new Produit("ES12", 120, info);
        Produit zr85 = new Produit("ZR85", 100, info);
        Produit ee85 = new Produit("EE85", 200, info);
        produitService.create(es12);
        produitService.create(zr85);
        produitService.create(ee85);

        Commande cmd = new Commande(jour.parse("14/03/2013"));
        commandeService.create(cmd);
        ligneService.create(new LigneCommandeProduit(7, es12, cmd));
        ligneService.create(new LigneCommandeProduit(14, zr85, cmd));
        ligneService.create(new LigneCommandeProduit(5, ee85, cmd));

        System.out.println("--- Exercice 1 : stock ---");
        commandeService.afficherCommande(cmd.getId());
        System.out.println("Produits a plus de 100 DH : " + produitService.findPrixSuperieur(100).size());

        EmployeService employeService = new EmployeService();
        ProjetService projetService = new ProjetService();
        TacheService tacheService = new TacheService();
        EmployeTacheService affectationService = new EmployeTacheService();

        Employe karim = new Employe("BENNIS", "Karim", "0611111111");
        Employe salma = new Employe("BENNANI", "Salma", "0622222222");
        employeService.create(karim);
        employeService.create(salma);

        Projet projet = new Projet("Gestion de stock", jour.parse("14/01/2013"), jour.parse("30/06/2013"), karim);
        projetService.create(projet);
        Tache analyse = new Tache("Analyse", jour.parse("01/02/2013"), jour.parse("28/02/2013"), 800, projet);
        Tache conception = new Tache("Conception", jour.parse("01/03/2013"), jour.parse("31/03/2013"), 1200, projet);
        Tache dev = new Tache("Developpement", jour.parse("01/04/2013"), jour.parse("30/04/2013"), 2500, projet);
        tacheService.create(analyse);
        tacheService.create(conception);
        tacheService.create(dev);
        affectationService.create(new EmployeTache(jour.parse("10/02/2013"), jour.parse("20/02/2013"), salma, analyse));
        affectationService.create(new EmployeTache(jour.parse("10/03/2013"), jour.parse("15/03/2013"), salma, conception));
        affectationService.create(new EmployeTache(jour.parse("10/04/2013"), jour.parse("25/04/2013"), salma, dev));

        System.out.println("--- Exercice 2 : projets ---");
        projetService.afficherTachesRealisees(projet.getId());
        System.out.println("Taches a plus de 1000 DH : " + tacheService.findPrixSuperieur(1000).size());

        HommeService hommeService = new HommeService();
        FemmeService femmeService = new FemmeService();
        MariageService mariageService = new MariageService();

        Homme safi = new Homme("SAFI", "SAID", "0633333333", "Casa", jour.parse("25/05/1960"));
        hommeService.create(safi);
        Femme salima = new Femme("RAMI", "SALIMA", "0688888888", "Casa", jour.parse("14/04/1966"));
        Femme amal = new Femme("ALI", "AMAL", "0699999999", "Rabat", jour.parse("22/08/1970"));
        Femme wafa = new Femme("ALAOUI", "WAFA", "0601010101", "Fes", jour.parse("30/01/1975"));
        Femme karima = new Femme("ALAMI", "KARIMA", "0602020202", "Casa", jour.parse("11/11/1964"));
        femmeService.create(salima);
        femmeService.create(amal);
        femmeService.create(wafa);
        femmeService.create(karima);
        mariageService.create(new Mariage(jour.parse("03/09/1990"), null, 4, safi, salima));
        mariageService.create(new Mariage(jour.parse("03/09/1995"), null, 2, safi, amal));
        mariageService.create(new Mariage(jour.parse("04/11/2000"), null, 3, safi, wafa));
        mariageService.create(new Mariage(jour.parse("03/09/1989"), jour.parse("03/09/1990"), 0, safi, karima));

        System.out.println("--- Exercice 3 : etat civil ---");
        mariageService.afficherMariages(safi.getId());

        HibernateUtil.shutdown();
    }
}
