package com.AlertSystem.backendSiteDiplom.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Project")
public class Project {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "label")
    private String label;
    @Column(name = "description")
    private String description;

    @Column(name = "telegramLink")
    private String telegramLink;
    @Column(name = "slackLink")
    private String slackLink;
    @Column(name = "emailTeamLead")
    private String emailTeamLead;

    public Project(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getTelegramLink() {
        return telegramLink;
    }

    public void setTelegramLink(String telegramLink) {
        this.telegramLink = telegramLink;
    }

    public String getSlackLink() {
        return slackLink;
    }

    public void setSlackLink(String slackLink) {
        this.slackLink = slackLink;
    }

    public String getEmailTeamLead() {
        return emailTeamLead;
    }

    public void setEmailTeamLead(String emailTeamLead) {
        this.emailTeamLead = emailTeamLead;
    }
}
