package ua.nure.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.nure.domain.Job;

import java.util.List;

@Repository
public class JobRepository implements CrudRepository<Job> {

    private static final String TABLE_NAME = "job";

    private JdbcTemplate jdbcTemplate;

    @Autowired
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
