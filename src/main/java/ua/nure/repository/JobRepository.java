package ua.nure.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.nure.domain.Job;

import java.util.List;

public class JobRepository implements CrudRepository<Job> {

    private static final String TABLE_NAME = "job";

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Job job) {
        return null;
    }

    @Override
    public Job findOne(Integer id) {
        return null;
    }

    @Override
    public List<Job> findAll() {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void update(Job job) {

    }
}
