package ca.cal.tp2.service.dto;

import ca.cal.tp2.modele.EmpruntDetail;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter @Setter
public class EmprunteurDetailDTO {
    private String titreDocument;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;
    private String nomEmprunteur;

    public EmprunteurDetailDTO(String titreDocument, LocalDate dateEmprunt,
                               LocalDate dateRetour, String nomEmprunteur) {
        this.titreDocument = titreDocument;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
        this.nomEmprunteur = nomEmprunteur;
    }


    public static EmprunteurDetailDTO toDTO(EmpruntDetail empruntDetail) {
        return new EmprunteurDetailDTO(
                empruntDetail.getDocument().getTitre(),
                empruntDetail.getEmprunt().getDate_emprunt(),
                empruntDetail.getDateRetourPrevue(),
                empruntDetail.getEmprunt().getEmprunteur().getNom()
        );
    }
}
