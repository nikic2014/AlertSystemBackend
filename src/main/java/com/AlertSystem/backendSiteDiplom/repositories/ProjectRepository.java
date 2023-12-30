package com.AlertSystem.backendSiteDiplom.repositories;

import com.AlertSystem.backendSiteDiplom.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    public Project findById(int idProject);
}
