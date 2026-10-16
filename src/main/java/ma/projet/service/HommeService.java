package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

public class HommeService extends AbstractService<Homme> {

    public HommeService() {
        super(Homme.class);
    }

    @SuppressWarnings("unchecked")
    public List<Femme> epousesEntreDates(int hommeId, Date debut, Date fin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT m.femme FROM Mariage m " +
                    "WHERE m.homme.id = :id AND m.dateDebut BETWEEN :debut AND :fin")
                    .setParameter("id", hommeId)
                    .setParameter("debut", debut).setParameter("fin", fin).list();
        }
    }

    public List<Homme> hommesQuatreFemmes(Date debut, Date fin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Homme> query = builder.createQuery(Homme.class);
            Root<ma.projet.beans.Mariage> root = query.from(ma.projet.beans.Mariage.class);
            query.select(root.get("homme"));
            query.where(builder.between(root.get("dateDebut"), debut, fin));
            query.groupBy(root.get("homme"));
            query.having(builder.equal(builder.countDistinct(root.get("femme")), 4L));
            return session.createQuery(query).getResultList();
        }
    }
}
