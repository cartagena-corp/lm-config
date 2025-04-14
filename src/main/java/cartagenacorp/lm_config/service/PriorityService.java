package cartagenacorp.lm_config.service;

import cartagenacorp.lm_config.entity.Priority;
import cartagenacorp.lm_config.repository.PriorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PriorityService {

    private final PriorityRepository priorityRepository;

    @Autowired
    public PriorityService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    public List<Priority> findAll(){
        return priorityRepository.findAll();
    }

    public Priority save(Priority priority) {
        return priorityRepository.save(priority);
    }

    public void delete(String name){
        Priority priority = priorityRepository.findById(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Priority not found"));
        priorityRepository.delete(priority);
    }
}
