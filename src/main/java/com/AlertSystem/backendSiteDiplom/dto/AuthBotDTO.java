package com.AlertSystem.backendSiteDiplom.dto;

public class AuthBotDTO {
    private String idProject;
    private String chatId;
    private String login;
    private String password;

    public String getIdProject() {
        return idProject;
    }

    public void setIdProject(String idProject) {
        this.idProject = idProject;
    }

    public String getChatId() {
        return chatId;
    }

    public void setCharId(String charId) {
        this.chatId = charId;
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
}
