package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public class FemmeService extends AbstractService<Femme> {

    public FemmeService() {
        super(Femme.class);
    }

    public int nbEnfantsEntreDates(int femmeId, Date debut, Date fin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Number total = (Number) session.getNamedQuery("Mariage.nbEnfantsFemme")
                    .setParameter("femmeId", femmeId)
                    .setParameter("debut", debut).setParameter("fin", fin)
                    .getSingleResult();
            return total.intValue();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Femme> marieesAuMoinsDeuxFois() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.getNamedQuery("Mariage.femmesPlusieursMariages").list();
        }
    }

    public Femme plusAgee() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return (Femme) session.createQuery("FROM Femme f ORDER BY f.dateNaissance ASC")
                    .setMaxResults(1).getSingleResult();
        }
    }
}
