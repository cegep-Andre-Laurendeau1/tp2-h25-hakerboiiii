package ca.cal.tp1.repository;

import java.sql.*;

public class RepositoryParentJDBC {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String URL = "jdbc:h2:mem:tp1;DB_CLOSE_DELAY=-1";
    static final String USER = "sa";
    static final String PASS = "";
    static Connection conn = null;

    static {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASS);
            Statement stm = conn.createStatement();

            String sql = "CREATE TABLE Document (" +
                    "id_doc INTEGER AUTO_INCREMENT PRIMARY KEY," +
                    "titre VARCHAR(32)," +
                    "nbExemplaires INTEGER)";
            String sqlLivre = "CREATE TABLE Livre(" +
                    "id_livre INTEGER PRIMARY KEY," +
                    "isbn VARCHAR(32)," +
                    "auteur VARCHAR(32)," +
                    "editeur VARCHAR(32)," +
                    "nbPages INTEGER," +
                    "FOREIGN KEY (id_livre) REFERENCES Document(id_doc) ON DELETE CASCADE)";
            String sqlCd = ("CREATE TABLE Cd(" +
                    "id_cd INTEGER PRIMARY KEY, " +
                    "artiste VARCHAR(32), " +
                    "duration INTEGER, " +
                    "genre VARCHAR(32)," +
                    "FOREIGN KEY (id_cd) REFERENCES Document(id_doc) ON DELETE CASCADE)");
            String sqlDvd = ("CREATE TABLE Dvd(" +
                    "id_dvd INTEGER PRIMARY KEY, " +
                    "directeur VARCHAR(32)," +
                    "duration INTEGER, " +
                    "rating VARCHAR(16)," +
                    "FOREIGN KEY (id_dvd) REFERENCES Document(id_doc) ON DELETE CASCADE)");


            String sqlUser = "CREATE TABLE Utilisateur (" +
                    "id_user INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                    "nom VARCHAR(32), " +
                    "email VARCHAR(32), " +
                    "telephone VARCHAR(32))";


            String sqlEmprunteur = "CREATE TABLE Emprunteur (" +
                    "id_emp INTEGER PRIMARY KEY," +
                    "nom_emp VARCHAR(32)," +
                    "FOREIGN KEY (id_emp) REFERENCES Utilisateur(id_user) ON DELETE CASCADE)";
            String sqlPrepose = "CREATE TABLE Prepose(" +
                    "id_prepose INTEGER PRIMARY KEY," +
                    "nom_prepose VARCHAR(32)," +
                    "FOREIGN KEY (id_prepose) REFERENCES Utilisateur(id_user) ON DELETE CASCADE)";

            String sqlAmende = "CREATE TABLE Amende (" +
                    "id_amende INTEGER AUTO_INCREMENT PRIMARY KEY," +
                    "id_emprunteur INTEGER NOT NULL," +
                    "montant DOUBLE NOT NULL," +
                    "dateCreation DATE DEFAULT CURRENT_DATE," +
                    "status BOOLEAN DEFAULT FALSE," +
                    "FOREIGN KEY (id_emprunteur) REFERENCES Emprunteur(id_emp) ON DELETE CASCADE)";

            String sqlEmprunt = "CREATE TABLE Emprunt(" +
                    "id_emprunt INTEGER AUTO_INCREMENT PRIMARY KEY," +
                    "id_emprunteur INTEGER NOT NULL," +
                    "date_emprunt DATE DEFAULT CURRENT_DATE," +
                    "statut VARCHAR(32) DEFAULT 'Active'," +
                    "FOREIGN KEY (id_emprunteur) REFERENCES Emprunteur (id_emp) ON DELETE CASCADE)";


            String sqlEmpruntDetails = "CREATE TABLE EmpruntDetails(" +
                    "id_lineItemId INTEGER AUTO_INCREMENT PRIMARY KEY," +
                    "id_emprunt INTEGER NOT NULL," +
                    "id_document INTEGER NOT NULL, " +
                    "date_retour_prevue DATE NOT NULL," +
                    "date_retourActuelle DATE," +
                    "statut VARCHAR(32) DEFAULT 'Active', " +
                    "FOREIGN KEY (id_emprunt) REFERENCES Emprunt(id_emprunt) ON DELETE CASCADE, " +
                    "FOREIGN KEY (id_document) REFERENCES Document(id_doc) ON DELETE CASCADE)";

            stm.executeUpdate(sql);
            stm.executeUpdate(sqlLivre);
            stm.executeUpdate(sqlCd);
            stm.executeUpdate(sqlDvd);
            stm.executeUpdate(sqlUser);
            stm.executeUpdate(sqlEmprunteur);
            stm.executeUpdate(sqlPrepose);
            stm.executeUpdate(sqlAmende);
            stm.executeUpdate(sqlEmprunt);
            stm.executeUpdate(sqlEmpruntDetails);
            stm.close();
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }
}
