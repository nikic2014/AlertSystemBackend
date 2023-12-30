package com.AlertSystem.backendSiteDiplom.models;

import jakarta.persistence.*;

@Entity
@Table(name = "PeopleInProject")
public class PeopleInProject {

    @EmbeddedId
    PeopleInProjectId peopleInProjectId;

    @Column(name = "role")
    private String role;

    public PeopleInProject(){}

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public PeopleInProjectId getPeopleInProjectId() {
        return peopleInProjectId;
    }

    public void setPeopleInProjectId(PeopleInProjectId peopleInProjectId) {
        this.peopleInProjectId = peopleInProjectId;
    }
}
