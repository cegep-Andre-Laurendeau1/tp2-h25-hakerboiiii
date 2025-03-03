package ca.cal.tp2.repository;

import ca.cal.tp2.modele.Emprunt;

import java.sql.*;
import java.time.LocalDate;

public class EmpruntRepositoryJDBC extends RepositoryParentJDBC implements EmpruntRepository{


    @Override
    public long saveEmprunt(Emprunt emp){
        String sql = "INSERT INTO emprunt (id_emprunteur, date_emprunt, statut) VALUES (?, ?, ?)";

        try(PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, emp.getId_emprunteur());
            ps.setDate(2, Date.valueOf(emp.getDate_emprunt()));
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
