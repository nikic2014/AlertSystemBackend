package com.AlertSystem.backendSiteDiplom.dto;

import java.util.List;
import java.util.Map;

public class ProjectDTO {
    private String projectName;
    private String projectDescription;
    private List<ParticipantDTO> participants;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public List<ParticipantDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantDTO> participants) {
        this.participants = participants;
    }
}
