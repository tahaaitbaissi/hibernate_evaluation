package ma.projet.beans;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mariages")
@NamedQuery(name = "Mariage.femmesPlusieursMariages",
        query = "SELECT m.femme FROM Mariage m GROUP BY m.femme HAVING COUNT(m) >= 2")
@NamedNativeQuery(name = "Mariage.nbEnfantsFemme",
        query = "SELECT COALESCE(SUM(nbrEnfant), 0) FROM mariages " +
                "WHERE femme_id = :femmeId AND dateDebut BETWEEN :debut AND :fin")
public class Mariage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @Column(nullable = false)
    private int nbrEnfant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "homme_id", nullable = false)
    private Homme homme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "femme_id", nullable = false)
    private Femme femme;

    public Mariage() {
    }

    public Mariage(Date dateDebut, Date dateFin, int nbrEnfant, Homme homme, Femme femme) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbrEnfant = nbrEnfant;
        this.homme = homme;
        this.femme = femme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public int getNbrEnfant() {
        return nbrEnfant;
    }

    public void setNbrEnfant(int nbrEnfant) {
        this.nbrEnfant = nbrEnfant;
    }

    public Homme getHomme() {
        return homme;
    }

    public void setHomme(Homme homme) {
        this.homme = homme;
    }

    public Femme getFemme() {
        return femme;
    }

    public void setFemme(Femme femme) {
        this.femme = femme;
    }
}
