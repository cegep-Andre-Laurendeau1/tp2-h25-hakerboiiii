package ca.cal.tp2.repository;

import ca.cal.tp2.modele.Cd;
import ca.cal.tp2.modele.Document;
import ca.cal.tp2.modele.Dvd;
import ca.cal.tp2.modele.Livre;

import java.sql.*;

public class DocumentRepositoryJDBC extends RepositoryParentJDBC implements DocumentRepository {

    @Override
    public void save(Document document) {
        long id_doc = saveDocument(document);
        String sql = document.getSql();

        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setLong(1, id_doc);

            switch (document) {
                case Livre livre -> {
                    ps.setString(2, livre.getIsbn());
                    ps.setString(3, livre.getAuteur());
                    ps.setString(4, livre.getEditeur());
                    ps.setInt(5, livre.getNbPages());
                }
                case Cd cd -> {
                    ps.setString(2, cd.getArtiste());
                    ps.setInt(3, cd.getDuree());
                    ps.setString(4, cd.getGenre());
                }
                case Dvd dvd -> {
                    ps.setString(2, dvd.getDirector());
                    ps.setInt(3, dvd.getDuration());
                    ps.setString(4, dvd.getRating());
                }
                default -> {
                }
            }

            ps.executeUpdate();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public long saveDocument(Document document) {
        String sql = "INSERT INTO Document(titre, nbExemplaires) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, document.getTitre());
            ps.setInt(2, document.getNbExemplaires());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                long generatedId = rs.getLong(1);
                document.setId_document(generatedId);
                return generatedId;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }
}
