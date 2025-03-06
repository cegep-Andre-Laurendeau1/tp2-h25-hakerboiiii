package ca.cal.tp2.repository;

import java.sql.Connection;

public class RepositoryParent {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String URL = "jdbc:h2:mem:tp2mohamedshahed;DB_CLOSE_DELAY=-1";
    static final String USER = "sa";
    static final String PASS = "";
    static Connection conn = null;



}
