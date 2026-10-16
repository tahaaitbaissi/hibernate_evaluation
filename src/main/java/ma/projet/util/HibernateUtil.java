package ma.projet.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Properties props = new Properties();
            InputStream in = HibernateUtil.class.getClassLoader().getResourceAsStream("application.properties");
            props.load(in);
            in.close();

            Configuration config = new Configuration();
            config.setProperties(props);
            config.addAnnotatedClass(ma.projet.classes.Categorie.class);
            config.addAnnotatedClass(ma.projet.classes.Produit.class);
            config.addAnnotatedClass(ma.projet.classes.Commande.class);
            config.addAnnotatedClass(ma.projet.classes.LigneCommandeProduit.class);
            config.addAnnotatedClass(ma.projet.classes.Employe.class);
            config.addAnnotatedClass(ma.projet.classes.Projet.class);
            config.addAnnotatedClass(ma.projet.classes.Tache.class);
            config.addAnnotatedClass(ma.projet.classes.EmployeTache.class);
            config.addAnnotatedClass(ma.projet.beans.Personne.class);
            config.addAnnotatedClass(ma.projet.beans.Homme.class);
            config.addAnnotatedClass(ma.projet.beans.Femme.class);
            config.addAnnotatedClass(ma.projet.beans.Mariage.class);
            return config.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
