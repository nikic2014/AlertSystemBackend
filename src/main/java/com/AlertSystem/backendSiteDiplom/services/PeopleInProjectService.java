package com.AlertSystem.backendSiteDiplom.services;

import com.AlertSystem.backendSiteDiplom.models.PeopleInProject;
import com.AlertSystem.backendSiteDiplom.models.PeopleInProjectId;
import com.AlertSystem.backendSiteDiplom.models.Project;
import com.AlertSystem.backendSiteDiplom.repositories.PeopleInProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeopleInProjectService {
    private PeopleInProjectRepository peopleInProjectRepository;

    public PeopleInProjectService(PeopleInProjectRepository peopleInProjectRepository) {
        this.peopleInProjectRepository = peopleInProjectRepository;
    }

    @Transactional
    public void savePeopleInProject(PeopleInProject peopleInProject){
        this.peopleInProjectRepository.save(peopleInProject);
    }

    public List<PeopleInProject> findProjectsByPeopleId(int idPeople){
        return this.peopleInProjectRepository.findByPeopleInProjectIdIdPeople(idPeople);
    }
    public List<PeopleInProject> findPeopleByProjectId(int idProject){
        return this.peopleInProjectRepository.findByPeopleInProjectIdIdProject(idProject);
    }

    public void removeByIdId(PeopleInProjectId peopleInProjectId){
        Optional<PeopleInProject> people =
                this.peopleInProjectRepository.findById(peopleInProjectId);
        if (!people.isEmpty())
            this.peopleInProjectRepository.delete(people.get());
        else
            throw new NullPointerException("Человек не состоит в данном " +
                    "проекте");
    }
}
