package ma.projet.service;

import ma.projet.classes.Produit;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public class ProduitService extends AbstractService<Produit> {

    public ProduitService() {
        super(Produit.class);
    }

    @SuppressWarnings("unchecked")
    public List<Produit> findByCategorie(int categorieId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Produit p WHERE p.categorie.id = :id")
                    .setParameter("id", categorieId).list();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Produit> findCommandesEntreDates(Date debut, Date fin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT DISTINCT l.produit FROM LigneCommandeProduit l " +
                    "WHERE l.commande.date BETWEEN :debut AND :fin")
                    .setParameter("debut", debut).setParameter("fin", fin).list();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Produit> findPrixSuperieur(float prix) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.getNamedQuery("Produit.prixSuperieur")
                    .setParameter("prix", prix).list();
        }
    }
}
