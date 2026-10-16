package ma.projet.service;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class EmployeService extends AbstractService<Employe> {

    public EmployeService() {
        super(Employe.class);
    }

    @SuppressWarnings("unchecked")
    public List<EmployeTache> tachesRealisees(int employeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT e FROM EmployeTache e " +
                    "JOIN FETCH e.tache WHERE e.employe.id = :id")
                    .setParameter("id", employeId).list();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Projet> projetsGeres(int employeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Projet p WHERE p.chefProjet.id = :id")
                    .setParameter("id", employeId).list();
        }
    }
}
