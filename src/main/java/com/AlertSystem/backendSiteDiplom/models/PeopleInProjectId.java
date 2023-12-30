package com.AlertSystem.backendSiteDiplom.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PeopleInProjectId implements Serializable {
    @Column(name = "idProject")
    private int idProject;

    @Column(name = "idPeople")
    private int idPeople;

    public PeopleInProjectId(){}

    public PeopleInProjectId(int idProject, int idPeople){
        this.idProject = idProject;
        this.idPeople = idPeople;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public int getIdPeople() {
        return idPeople;
    }

    public void setIdPeople(int idPeople) {
        this.idPeople = idPeople;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        PeopleInProjectId that = (PeopleInProjectId) o;

        return Objects.equals(this.idProject, that.idProject) &&
                Objects.equals(this.idPeople, that.idPeople);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idProject, this.idPeople);
    }
}
