package ca.cal.tp2.service.dto;
import ca.cal.tp2.modele.Document;
import lombok.Getter;

import java.time.LocalDate;


@Getter
public class DocumentDTO {
    private Long id;
    private String titre;
    private int nbExemplaires;
    private LocalDate annee;

    public DocumentDTO(Long id, String titre, int nbExemplaires, LocalDate annee) {
        this.id = id;
        this.titre = titre;
        this.nbExemplaires = nbExemplaires;
        this.annee = annee;
    }


    public static DocumentDTO toDTO(Document doc) {
        return new DocumentDTO(doc.getId_document(), doc.getTitre(), doc.getNbExemplaires(), doc.getDatePublication());
    }
}
