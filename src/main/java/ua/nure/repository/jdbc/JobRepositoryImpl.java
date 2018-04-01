package ua.nure.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
//=======
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
//>>>>>>> departs-employs
import ua.nure.domain.Job;
import ua.nure.domain.Task;
import ua.nure.repository.CrudRepository;
import ua.nure.repository.JobRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiFunction;

@Repository
public class JobRepositoryImpl implements JobRepository {

    private static final String TABLE_NAME = "job";
    private JdbcTemplate jdbcTemplate;
    private EmployeeRepositoryImpl employeeRepository;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setEmployeeRepository(EmployeeRepositoryImpl employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Long save(Job job) {
        String insertJob = "insert into " + TABLE_NAME + " (job_title, max_salary, min_salary, employee_id) values (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertJob, new String[]{"id"});
            ps.setString(1, job.getJobTitle());
            ps.setLong(2, job.getMaxSalary());
            ps.setLong(3, job.getMinSalary());
            ps.setObject(4, job.getEmployee() != null ? job.getEmployee().getId() : null);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Job findOne(Integer id) {
        String selectJob = "select id, job_title, max_salary, min_salary, employee_id from " + TABLE_NAME + " where id = ?";
        return this.jdbcTemplate.queryForObject(selectJob, new Object[]{id}, jobMapper::apply);

    }

    @Override
    public List<Job> findAll() {
        String selectAllJobs = "select id, job_title, max_salary, min_salary, employee_id from " + TABLE_NAME;
        return this.jdbcTemplate.query(selectAllJobs, jobMapper::apply);

    }

    @Override
    public void delete(Integer id) {
        String deleteJob = "delete from " + TABLE_NAME + " where id = ?";
        this.jdbcTemplate.update(deleteJob, id);
    }

    @Override
    public void update(Job job) {
        String updateJob = "update " + TABLE_NAME + " set job_title = ?, max_salary = ?, min_salary = ?, employee_id = ?";
        this.jdbcTemplate.update(updateJob, job.getJobTitle(), job.getMaxSalary(), job.getMinSalary(), job.getEmployee().getId());
//        this.jdbcTemplate.update(updateJob, job.getJobTitle(), job.getMaxSalary(), job.getEmployee(), job.getEmployee() != null ? job.getEmployee().getId() : null);
    }

    private BiFunction<ResultSet, Integer, Job> jobMapper = (resultSet, integer) -> {
        try {
            Job job = new Job();
            job.setId(resultSet.getLong(1));
            job.setJobTitle(resultSet.getString(2));
            job.setMaxSalary(resultSet.getLong(3));
            job.setMinSalary(resultSet.getLong(4));
            job.setEmployee(employeeRepository.findOne(Math.toIntExact(resultSet.getLong(5)))); // using java 8 goods
            job.setTasks(new HashSet<>(new ArrayList<>(this.jdbcTemplate.query("SELECT t.id, t.description, t.title FROM task t INNER JOIN job_task jt ON jt.tasks_id = t.id WHERE jt.jobs_id = ?",
                    new Object[]{resultSet.getLong(1)}, (resultSet1, i) -> {
                        Task task = new Task();
                        task.setId(resultSet1.getLong(1));
                        task.setDescription(resultSet1.getString(2));
                        task.setTitle(resultSet1.getString(3));
                        // NOT SET JOBS-SET
                        return task;
                    }))));
            return job;

        } catch (SQLException e) {
            throw new DataIntegrityViolationException(e.getSQLState());
        }
    };
}
