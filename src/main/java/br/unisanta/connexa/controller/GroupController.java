package br.unisanta.connexa.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.unisanta.connexa.model.Group;
import br.unisanta.connexa.service.GroupService;

@Controller
@RequestMapping(path = "groups")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping()
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = this.groupService.findAll();
        
        return ResponseEntity
            .ok()
            .body(groups);
    }
}
