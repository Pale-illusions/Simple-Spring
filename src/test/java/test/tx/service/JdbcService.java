package test.tx.service;


import com.iflove.simplespring.jdbc.support.JdbcTemplate;

import java.sql.SQLException;

public interface JdbcService {

    void saveDataWithTranslation() throws SQLException;


    void saveData(JdbcTemplate jdbcTemplate) ;
}
