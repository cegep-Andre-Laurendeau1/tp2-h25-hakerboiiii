package ca.cal.tp1.modele;
import lombok.Data;
import lombok.Setter;

@Data
public abstract class Document {

    @Setter
    private long id_document;
    private String titre;
    private int nbExemplaires;



    public Document(String titre, int nbExemplaires) {
        this.titre = titre;
        this.nbExemplaires = nbExemplaires;
    }
    public abstract String getSql();
}
