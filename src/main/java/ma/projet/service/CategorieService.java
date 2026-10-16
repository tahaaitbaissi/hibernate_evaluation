package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class CategorieService extends AbstractService<Categorie> {

    public CategorieService() {
        super(Categorie.class);
    }

    @SuppressWarnings("unchecked")
    public List<Categorie> findByCode(String code) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Categorie c WHERE c.code = :code")
                    .setParameter("code", code).list();
        }
    }
}
