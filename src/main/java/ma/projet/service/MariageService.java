package ma.projet.service;

import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.List;

public class MariageService extends AbstractService<Mariage> {

    public MariageService() {
        super(Mariage.class);
    }

    @SuppressWarnings("unchecked")
    public List<Mariage> mariagesDUnHomme(int hommeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT m FROM Mariage m " +
                    "JOIN FETCH m.femme WHERE m.homme.id = :id ORDER BY m.dateDebut")
                    .setParameter("id", hommeId).list();
        }
    }

    public void afficherMariages(int hommeId) {
        Homme homme;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            homme = session.get(Homme.class, hommeId);
        }
        SimpleDateFormat jour = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Nom : " + homme.getNom() + " " + homme.getPrenom());
        System.out.println("Mariages En Cours :");
        int cours = 0;
        int echoues = 0;
        for (Mariage mariage : mariagesDUnHomme(hommeId)) {
            if (mariage.getDateFin() == null) {
                cours++;
                System.out.println(cours + ". Femme : " + mariage.getFemme().getNom() + " "
                        + mariage.getFemme().getPrenom() + "   Date Debut : "
                        + jour.format(mariage.getDateDebut()) + "    Nbr Enfants : "
                        + mariage.getNbrEnfant());
            }
        }
        System.out.println("Mariages echoues :");
        for (Mariage mariage : mariagesDUnHomme(hommeId)) {
            if (mariage.getDateFin() != null) {
                echoues++;
                System.out.println(echoues + ". Femme : " + mariage.getFemme().getNom() + " "
                        + mariage.getFemme().getPrenom() + "   Date Debut : "
                        + jour.format(mariage.getDateDebut()) + "    Date Fin : "
                        + jour.format(mariage.getDateFin()) + "    Nbr Enfants : "
                        + mariage.getNbrEnfant());
            }
        }
    }
}
