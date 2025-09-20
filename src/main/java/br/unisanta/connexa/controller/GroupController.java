package br.unisanta.connexa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unisanta.connexa.model.Group;
import br.unisanta.connexa.request.CreateGroupRequest;
import br.unisanta.connexa.service.GroupService;
import jakarta.validation.Valid;

@RestController
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
        Group group = new Group();
        group.setName(request.name());
        group.setSubject(request.subject());
        group.setModality(request.modality());
        group.setLocation(request.location());
        group.setObjective(request.objective());

        Group createdGroup = this.groupService.save(group);
        
        return ResponseEntity
            .ok()
            .body(createdGroup);
    }
    
    @PostMapping(path = "{id}/join")
    public ResponseEntity<Void> joinGroupById(@PathVariable Long id) {
        this.groupService.join(id);
        
        return ResponseEntity
            .ok()
            .build();
    }
}
