package br.unisanta.connexa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.unisanta.connexa.model.Group;
import br.unisanta.connexa.request.CreateGroupRequest;
import br.unisanta.connexa.service.GroupService;
import jakarta.validation.Valid;

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

    @GetMapping(path = "{id}")
    public ResponseEntity<Optional<Group>> getGroupById(@PathVariable Long id) {
        Optional<Group> group = this.groupService.findById(id);
        
        return ResponseEntity
            .ok()
            .body(group);
    }

    @PostMapping()
    public ResponseEntity<Group> createGroup(
        @Valid @RequestBody CreateGroupRequest request
    ) {
        Group group = this.groupService.save(request);
        
        return ResponseEntity
            .ok()
            .body(group);
    }
    
    @PostMapping(path = "{id}")
    public ResponseEntity<Void> joinGroupById(@PathVariable Long id) {
        this.groupService.join(id);
        
        return ResponseEntity
            .ok()
            .body(null);
    }
}
