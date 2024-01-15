package com.AlertSystem.backendSiteDiplom.controllers;

import com.AlertSystem.backendSiteDiplom.dto.ProjectDTO;
import com.AlertSystem.backendSiteDiplom.models.People;
import com.AlertSystem.backendSiteDiplom.models.PeopleInProject;
import com.AlertSystem.backendSiteDiplom.models.PeopleInProjectId;
import com.AlertSystem.backendSiteDiplom.models.Project;
import com.AlertSystem.backendSiteDiplom.services.PeopleInProjectService;
import com.AlertSystem.backendSiteDiplom.services.PeopleService;
import com.AlertSystem.backendSiteDiplom.services.ProjectService;
import com.AlertSystem.backendSiteDiplom.util.JWTUtil;
import com.AlertSystem.backendSiteDiplom.util.MyLogger;
import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
@RestController
public class ProjectController {
    private final ProjectService projectService;
    private final PeopleInProjectService peopleInProjectService;
    private final PeopleService peopleService;
    private final JWTUtil jwtUtil;
    private final MyLogger myLogger;

    public ProjectController(ProjectService projectService,
                             PeopleInProjectService peopleInProjectService,
                             PeopleService peopleService,
                             JWTUtil jwtUtil, MyLogger myLogger) {
        this.projectService = projectService;
        this.peopleInProjectService = peopleInProjectService;
        this.peopleService = peopleService;
        this.jwtUtil = jwtUtil;
        this.myLogger = myLogger;
    }

    @PostMapping("/createProject")
    public ResponseEntity createProject(@RequestBody ProjectDTO projectDTO) {
        Project project = new Project();
        project.setLabel(projectDTO.getProjectName());
        project.setDescription(projectDTO.getProjectDescription());
        projectService.saveProject(project);

        String messageForLogs = "Пользователи: ";
        for (var i :projectDTO.getParticipants()) {
            messageForLogs += i.getParticipantId() + ", ";
//            System.out.println(i.getParticipantId() + " " + i.getParticipantRole());
        }
        messageForLogs += " добавлены в новый проект " +project.getId();
        myLogger.sendInfo(messageForLogs);

        for (var i :projectDTO.getParticipants()) {
            PeopleInProject peopleInProject = new PeopleInProject();
            PeopleInProjectId peopleInProjectId =
                    new PeopleInProjectId(project.getId(),
                            Integer.parseInt(i.getParticipantId()));
            peopleInProject.setRole(i.getParticipantRole());
            peopleInProject.setPeopleInProjectId(peopleInProjectId);
            peopleInProjectService.savePeopleInProject(peopleInProject);
        }

        return ResponseEntity.ok("Проект успешно создан");
    }

    @GetMapping("/getAllProjects/{id}")
    public ResponseEntity getProjects(@PathVariable Integer id) {
        List<PeopleInProject> idProjects =
                this.peopleInProjectService.findProjectsByPeopleId(id);
        List<Project> projects = new ArrayList<>();

        if (idProjects.isEmpty()){
            return ResponseEntity.ok(Map.of("massage","Пока нет проектов"));
        }
        else {
            for (var i : idProjects) {
                int idProject = i.getPeopleInProjectId().getIdProject();
                projects.add(this.projectService.findByIdProject(idProject));
            }
        }

        return ResponseEntity.ok(projects);
    }

    public boolean haveRool(String jwtCookie, Integer id) {
        String username = jwtUtil.validateToken(jwtCookie);
        People people = peopleService.getByLogin(username);
        List<PeopleInProject> peopleInProjects =
                this.peopleInProjectService.findPeopleByProjectId(id);

        for(var i:peopleInProjects){
            if (i.getPeopleInProjectId().getIdPeople() == people.getId())
                return true;
        }

        return false;
    }


    @GetMapping("/getProjectInfo/{id}")
    public ResponseEntity getProjectInfo(@CookieValue(value = "jwt-token") String jwtCookie,
                                         @PathVariable Integer id) {
        if (!haveRool(jwtCookie, id))
            return (ResponseEntity) ResponseEntity.badRequest();

        Project project = this.projectService.findByIdProject(id);
        Gson gson = new Gson();
        return ResponseEntity.ok(gson.toJson(project, Project.class));
    }

    @GetMapping("/getAllPeople/{id}")
    public ResponseEntity getAllPeople(@CookieValue(value = "jwt-token") String jwtCookie,
                                       @PathVariable Integer id) {
        if (!haveRool(jwtCookie, id))
            return (ResponseEntity) ResponseEntity.badRequest();

        List<PeopleInProject> peopleInProjects =
                this.peopleInProjectService.findPeopleByProjectId(id);
        List<Map> response = new ArrayList<>();

        for (var i : peopleInProjects){
            int idPeople = i.getPeopleInProjectId().getIdPeople();
            People people = this.peopleService.getById(idPeople);
            response.add(Map.of("people", people, "role", i.getRole()));
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/exitOfProject/{id}")
    public ResponseEntity exitOfProject(@CookieValue(value = "jwt-token") String jwtCookie,
                                        @PathVariable Integer id){
        String username = jwtUtil.validateToken(jwtCookie);
        People people = peopleService.getByLogin(username);
        PeopleInProjectId peopleInProjectId =
                new PeopleInProjectId(id, people.getId());

        myLogger.sendInfo("Пользователь " + people.getLogin() + " покинул " +
                "проект " + id);

        this.peopleInProjectService.removeByIdId(peopleInProjectId);

        return ResponseEntity.ok("Пользователь покинул проект");
    }
}
