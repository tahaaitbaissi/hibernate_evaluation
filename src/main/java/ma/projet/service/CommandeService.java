package ma.projet.service;

import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CommandeService extends AbstractService<Commande> {

    public CommandeService() {
        super(Commande.class);
    }

    @SuppressWarnings("unchecked")
    public List<LigneCommandeProduit> lignesDeCommande(int commandeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT l FROM LigneCommandeProduit l " +
                    "JOIN FETCH l.produit WHERE l.commande.id = :id")
                    .setParameter("id", commandeId).list();
        }
    }

    public void afficherCommande(int commandeId) {
        Commande commande = findById(commandeId);
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
        System.out.println("Commande : " + commande.getId() + "     Date : " + format.format(commande.getDate()));
        System.out.println("Liste des produits :");
        System.out.println("Reference   Prix    Quantite");
        for (LigneCommandeProduit ligne : lignesDeCommande(commandeId)) {
            System.out.println(ligne.getProduit().getReference() + "        "
                    + (int) ligne.getProduit().getPrix() + " DH  "
                    + ligne.getQuantite());
        }
    }
}
