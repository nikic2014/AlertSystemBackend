package com.AlertSystem.backendSiteDiplom.dto;

import jakarta.persistence.Column;

public class PeopleDTO {
    private String login;
    private String password;
    private String email;

    private String telephone;

    private String tgLink;

    public String getTgLink() {
        return tgLink;
    }

    public void setTgLink(String tgLink) {
        this.tgLink = tgLink;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
