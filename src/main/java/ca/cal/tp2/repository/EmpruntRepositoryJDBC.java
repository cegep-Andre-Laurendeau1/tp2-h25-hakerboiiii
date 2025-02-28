package ca.cal.tp1.repository;

import ca.cal.tp1.modele.Emprunt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmpruntRepositoryJDBC extends RepositoryParentJDBC implements EmpruntRepository{


    @Override
    public long saveEmprunt(Emprunt emp){
        String sql = "INSERT INTO emprunt (id_emprunteur, date_emprunt, statut) VALUES (?, ?, ?)";

        try(PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, emp.getId_emprunteur());
            ps.setDate(2, new java.sql.Date(emp.getDate_emprunt().getTime()));
            ps.setString(3, emp.getStatuts());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                return rs.getLong(1);
            }
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }

        return -1;
    }

}
