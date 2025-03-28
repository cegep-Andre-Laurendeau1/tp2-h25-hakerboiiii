package ca.cal.tp2.service.dto;
import ca.cal.tp2.modele.Document;
import lombok.Getter;

import java.time.LocalDate;


@Getter
public class DocumentDTO {
    private String titre;
    private int nbExemplaires;
    private LocalDate annee;

    public DocumentDTO(String titre, int nbExemplaires, LocalDate annee) {
        this.titre = titre;
        this.nbExemplaires = nbExemplaires;
        this.annee = annee;
    }


    public static DocumentDTO toDTO(Document doc) {
        return new DocumentDTO(doc.getTitre(), doc.getNbExemplaires(), doc.getDatePublication());
    }
}
