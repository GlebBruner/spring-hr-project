package ua.nure.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ua.nure.domain.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
        this.jdbcTemplate.update(insertTask, task.getDescription(), task.getTitle());
        return null; // fixme
    }

    @Override
    public Task findOne(Integer id) {
        String selectOneTask = "select * from " + TABLE_NAME + " where id = ?";
        return this.jdbcTemplate.queryForObject(selectOneTask, new TaskMapper());
    }

    @Override
    public List<Task> findAll() {
        String selectAllTasks = "select * from " + TABLE_NAME;
        return this.jdbcTemplate.query(selectAllTasks, new TaskMapper());
    }

    @Override
    public void delete(Integer id) {
        String deleteTask = "delete from " + TABLE_NAME + " where id = ?";
        this.jdbcTemplate.update(deleteTask, id);

    }

    private static final class TaskMapper implements RowMapper<Task> {
        @Override
        public Task mapRow(ResultSet resultSet, int i) throws SQLException {
            Task task = new Task();
            task.setId(resultSet.getLong("id"));
            task.setDescription(resultSet.getString("description"));
            task.setTitle(resultSet.getString("title"));
            return task;
        }
    }

    @Override
    public void update(Task task) {
        String updateTask = "update " + TABLE_NAME + " set description = ?, title = ? where id = ?";
        this.jdbcTemplate.update(updateTask, task.getDescription(), task.getTitle(), task.getId());
    }
}
