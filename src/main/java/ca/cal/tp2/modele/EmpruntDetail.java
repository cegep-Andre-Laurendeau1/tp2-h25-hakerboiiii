package ca.cal.tp1.modele;

import java.util.Date;
//todo: Fixer: changer Date pour LocalDate;
//todo: Fixer: faire le lien avec classe Emprunt et Document. Utiliser collection.

public class EmpruntDetail {
    private int lineItemId;
    private int emprunt_id;
    private int document_id;
    private Date dateRetourPrevue;
    private Date dateRetourActuelle;
    private String status;

    public EmpruntDetail(int emprunt_id, int document_id, int nb_semaines) {
        this.emprunt_id = emprunt_id;
        this.document_id = document_id;
        this.dateRetourPrevue = new Date(System.currentTimeMillis() +
                (nb_semaines * 7 * 24 * 60 * 60 * 1000));
        this.status = "Emprunte";
    }
}
