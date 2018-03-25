package ua.nure.repository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ua.nure.domain.Job;
import ua.nure.domain.Task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiFunction;

public class TaskRepository implements CrudRepository<Task>{

    private static final String TABLE_NAME = "task";

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Task task) {
        String insertTask = "insert into " + TABLE_NAME + " (description, title) " +
                "values (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertTask, new String[]{"id"});
            ps.setString(1, task.getDescription());
            ps.setString(2, task.getTitle());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Task findOne(Integer id) {
        String selectOneTask = "select id, description, title from " + TABLE_NAME + " where id = ?";
        return this.jdbcTemplate.queryForObject(selectOneTask, new Object[]{id}, taskMapper::apply);
    }

    @Override
    public List<Task> findAll() {
        String selectAllTasks = "select id, description, title from " + TABLE_NAME;
        return this.jdbcTemplate.query(selectAllTasks, taskMapper::apply);
    }

    @Override
    public void update(Task task) {
        String updateTask = "update " + TABLE_NAME + " set description = ?, title = ? where id = ?";
        this.jdbcTemplate.update(updateTask, task.getDescription(), task.getTitle(), task.getId());
    }

    @Override
    public void delete(Integer id) {
        String deleteTask = "delete from " + TABLE_NAME + " where id = ?";
        this.jdbcTemplate.update(deleteTask, id);

    }

    private BiFunction<ResultSet, Integer, Task> taskMapper = (resultSet, integer) -> {
        try {
            Task task = new Task();
            task.setId(resultSet.getLong(1));
            task.setDescription(resultSet.getString(2));
            task.setTitle(resultSet.getString(3));
            task.setJobs(new HashSet<>(new ArrayList<>(this.jdbcTemplate.query("SELECT j.id, j.job_title, j.max_salary, j.min_salary, j.employee_id FROM job j INNER JOIN job_task jt ON jt.jobs_id = j.id WHERE jt.tasks_id = ?",
                    new Object[]{resultSet.getLong(1)}, (resultSet1, i) -> {
                        Job job = new Job();
                        job.setId(resultSet1.getLong(1));
                        job.setJobTitle(resultSet1.getString(2));
                        job.setMaxSalary(resultSet1.getLong(3));
                        job.setMinSalary(resultSet1.getLong(4));
                        // NOT SET EMPLOYEE
                        // NOT SET TASKS SET
                        return job;
                    }))));
            return task;
        } catch (SQLException e) {
            throw new DataIntegrityViolationException(e.getSQLState());
        }
    };

}
