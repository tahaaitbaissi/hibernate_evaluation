package ma.projet.service;

import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ProjetService extends AbstractService<Projet> {

    public ProjetService() {
        super(Projet.class);
    }

    @SuppressWarnings("unchecked")
    public List<Tache> tachesPlanifiees(int projetId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Tache t WHERE t.projet.id = :id")
                    .setParameter("id", projetId).list();
        }
    }

    @SuppressWarnings("unchecked")
    public List<EmployeTache> tachesRealisees(int projetId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT e FROM EmployeTache e " +
                    "JOIN FETCH e.tache WHERE e.tache.projet.id = :id")
                    .setParameter("id", projetId).list();
        }
    }

    public void afficherTachesRealisees(int projetId) {
        Projet projet = findById(projetId);
        SimpleDateFormat debut = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
        SimpleDateFormat jour = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Projet : " + projet.getId() + "      Nom : " + projet.getNom()
                + "     Date debut : " + debut.format(projet.getDateDebut()));
        System.out.println("Liste des taches:");
        System.out.println("Num Nom            Date Debut Reelle   Date Fin Reelle");
        for (EmployeTache affectation : tachesRealisees(projetId)) {
            System.out.println(affectation.getTache().getId() + "  "
                    + affectation.getTache().getNom() + "        "
                    + jour.format(affectation.getDateDebutReelle()) + "          "
                    + jour.format(affectation.getDateFinReelle()));
        }
    }
}
