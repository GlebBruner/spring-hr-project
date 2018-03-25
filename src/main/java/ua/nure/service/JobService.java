package ua.nure.service;

import ua.nure.domain.Job;
import ua.nure.repository.JobRepository;

import java.util.List;

public class JobService {

    private JobRepository jobRepository;

    public void setJobRepository(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Job> findAll () {
        return jobRepository.findAll();
    }

    public Job findOne (Integer id) {
        return jobRepository.findOne(id);
    }

    public Long create (Job job) {
        return jobRepository.save(job);
    }

    public void delete (Integer id) {
        this.jobRepository.delete(id);
    }

    public void update (Job job) {
        this.jobRepository.update(job);
    }
}
