package ca.cal.tp1.repository;

import ca.cal.tp1.modele.Amende;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AmendeRepositoryJDBC extends RepositoryParentJDBC implements AmendeRepository
{
    @Override
    public void save(Amende montant){
        String sql = "INSERT INTO amende (id_emprunteur, montant) VALUES (?,?)";
        try(PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, montant.getId_emprunteur());
            ps.setDouble(2, montant.getMontant());
            ps.executeUpdate();
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }

    }
}
