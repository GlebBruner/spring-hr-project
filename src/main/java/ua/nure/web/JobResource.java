package ua.nure.web;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.domain.Job;
import ua.nure.service.JobService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class JobResource {

    private JobService jobService;

    public void setJobService(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> getJobs() {
        return ResponseEntity.ok(this.jobService.findAll());
    }

    @GetMapping("/jobs/{job_id}")
    public ResponseEntity<Job> getJob (@PathVariable Integer job_id) {
        return ResponseEntity.ok(this.jobService.findOne(job_id));
    }

    @PostMapping("/jobs")
    public ResponseEntity<Void> createJob (@RequestBody Job job) {
        Long createdJobId = this.jobService.create(job);
        return ResponseEntity.created(URI.create("/api/jobs/" + createdJobId)).build();
    }

    @DeleteMapping("/jobs/{job_id}")
    public ResponseEntity<Job> deleteJob (@PathVariable Integer job_id) {
        this.jobService.delete(job_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/jobs")
    public ResponseEntity<Void> updateJob (@RequestBody Job job) {
        Job job1 = this.jobService.findOne(job.getId().intValue());

        if (job1 == null) {
            return ResponseEntity.notFound().build();
        }
        this.jobService.update(job);
        return ResponseEntity.noContent().build();
    }
}
