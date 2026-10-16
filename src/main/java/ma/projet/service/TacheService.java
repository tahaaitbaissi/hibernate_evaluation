package ma.projet.service;

import ma.projet.classes.EmployeTache;
import ma.projet.classes.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public class TacheService extends AbstractService<Tache> {

    public TacheService() {
        super(Tache.class);
    }

    @SuppressWarnings("unchecked")
    public List<Tache> findPrixSuperieur(double prix) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.getNamedQuery("Tache.prixSuperieur")
                    .setParameter("prix", prix).list();
        }
    }

    @SuppressWarnings("unchecked")
    public List<EmployeTache> realiseesEntreDates(Date debut, Date fin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT e FROM EmployeTache e " +
                    "JOIN FETCH e.tache WHERE e.dateDebutReelle BETWEEN :debut AND :fin")
                    .setParameter("debut", debut).setParameter("fin", fin).list();
        }
    }
}
