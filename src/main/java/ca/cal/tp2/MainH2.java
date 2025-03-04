package ca.cal.tp2;
import ca.cal.tp2.TcpServeur;

import java.sql.SQLException;

public class MainH2 {
    public static void main(String[] args) throws SQLException {
        TcpServeur.startTcpServer();

    }
}
