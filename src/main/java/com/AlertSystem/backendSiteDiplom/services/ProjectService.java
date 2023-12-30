package com.AlertSystem.backendSiteDiplom.services;


import com.AlertSystem.backendSiteDiplom.models.Project;
import com.AlertSystem.backendSiteDiplom.repositories.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void saveProject(Project project){
        this.projectRepository.save(project);
    }

    public Project findByIdProject(int idProject){
        return this.projectRepository.findById(idProject);
    }
}
