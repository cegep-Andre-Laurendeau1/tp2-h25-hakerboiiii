package ca.cal.tp1.repository;

import ca.cal.tp1.modele.Emprunteur;
import ca.cal.tp1.modele.Prepose;
import ca.cal.tp1.modele.Utilisateur;

import java.sql.*;

public class UtilisateurRepositoryJDBC extends RepositoryParentJDBC implements UtilisateurRepository {

    @Override
    public void save(Utilisateur utilisateur) {
        long id_user = saveUtilisateur(utilisateur);
        String sql = utilisateur.getSql();

        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setLong(1, id_user);

            if(utilisateur instanceof Prepose){
                Prepose prepose = (Prepose) utilisateur;
                ps.setString(2, prepose.getNom());
            }
            else if(utilisateur instanceof Emprunteur){
                Emprunteur emp = (Emprunteur) utilisateur;
                ps.setString(2, emp.getNom());
            }

            ps.executeUpdate();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public long saveUtilisateur(Utilisateur utilisateur){
        String sql = "INSERT INTO Utilisateur (nom, email, telephone) " +
                "VALUES (?, ?, ?)";
        try(PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, utilisateur.getNom());
            ps.setString(2, utilisateur.getEmail());
            ps.setString(3, utilisateur.getPhoneNumber());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                long generatedId = rs.getLong(1);
                utilisateur.setUser_id(generatedId);
                return generatedId;
            }

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
        return -1;
    }
}
