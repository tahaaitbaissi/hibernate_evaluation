package ma.projet.test;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.service.CategorieService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.service.ProduitService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class StockTest {

    private static CategorieService categorieService = new CategorieService();
    private static ProduitService produitService = new ProduitService();
    private static CommandeService commandeService = new CommandeService();
    private static LigneCommandeService ligneService = new LigneCommandeService();
    private static int commandeId;

    @BeforeClass
    public static void init() throws Exception {
        SimpleDateFormat jour = new SimpleDateFormat("dd/MM/yyyy");

        Categorie info = new Categorie("IN", "Informatique");
        Categorie bureau = new Categorie("BR", "Bureautique");
        categorieService.create(info);
        categorieService.create(bureau);

        Produit es12 = new Produit("ES12", 120, info);
        Produit zr85 = new Produit("ZR85", 100, info);
        Produit ee85 = new Produit("EE85", 200, info);
        Produit st40 = new Produit("ST40", 80, bureau);
        produitService.create(es12);
        produitService.create(zr85);
        produitService.create(ee85);
        produitService.create(st40);

        Commande cmd = new Commande(jour.parse("14/03/2013"));
        commandeService.create(cmd);
        commandeId = cmd.getId();
        ligneService.create(new LigneCommandeProduit(7, es12, cmd));
        ligneService.create(new LigneCommandeProduit(14, zr85, cmd));
        ligneService.create(new LigneCommandeProduit(5, ee85, cmd));

        Commande cmd2 = new Commande(jour.parse("20/06/2013"));
        commandeService.create(cmd2);
        ligneService.create(new LigneCommandeProduit(3, st40, cmd2));
    }

    @Test
    public void testProduitsParCategorie() {
        List<Categorie> cats = categorieService.findByCode("IN");
        assertEquals(1, cats.size());
        List<Produit> produits = produitService.findByCategorie(cats.get(0).getId());
        assertEquals(3, produits.size());
    }

    @Test
    public void testProduitsCommandesEntreDates() throws Exception {
        SimpleDateFormat jour = new SimpleDateFormat("dd/MM/yyyy");
        Date debut = jour.parse("01/01/2013");
        Date fin = jour.parse("31/03/2013");
        List<Produit> produits = produitService.findCommandesEntreDates(debut, fin);
        assertEquals(3, produits.size());
    }

    @Test
    public void testAfficherCommande() {
        List<LigneCommandeProduit> lignes = commandeService.lignesDeCommande(commandeId);
        assertEquals(3, lignes.size());
        commandeService.afficherCommande(commandeId);
    }

    @Test
    public void testPrixSuperieur() {
        List<Produit> produits = produitService.findPrixSuperieur(100);
        assertEquals(2, produits.size());
    }
}
