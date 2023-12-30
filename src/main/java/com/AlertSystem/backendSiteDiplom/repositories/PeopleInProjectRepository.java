package com.AlertSystem.backendSiteDiplom.repositories;

import com.AlertSystem.backendSiteDiplom.models.PeopleInProject;
import com.AlertSystem.backendSiteDiplom.models.PeopleInProjectId;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleInProjectRepository extends JpaRepository<PeopleInProject, PeopleInProjectId> {

    List<PeopleInProject> findByPeopleInProjectIdIdPeople(int idPeople);
    List<PeopleInProject> findByPeopleInProjectIdIdProject(int idProject);
}
