package com.zaihua.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by carl on 28/03/2017.
 */
@Component
public class JdbcBase {
    private static JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());;

    public static JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    private static DataSource getDataSource() {
        String url = "jdbc:mysql://localhost:3306/myDb";
        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, "root", "");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");

        return dataSource;
    }

    public static Map<String, Object> getK_day(String symbol, String time) {
        String k_day = String.format("select * from k_day where symbol = '%s';", symbol);
        return jdbcTemplate.queryForMap(k_day);
    }

    public static List<Map<String, Object>> getK_days(String symbol) {
        String k_days = String.format("select * from k_day where symbol = '%s';", symbol);
        return jdbcTemplate.queryForList(k_days);
    }

    public static void main(String[] args) throws InterruptedException {
        List<Map<String, Object>> maps = getK_days("SH600036");
        System.out.println(maps.size());
    }
}
