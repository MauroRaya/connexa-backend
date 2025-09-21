package br.unisanta.connexa.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unisanta.connexa.dto.GroupDTO;
import br.unisanta.connexa.model.Group;
import br.unisanta.connexa.request.CreateGroupRequest;
import br.unisanta.connexa.service.GroupService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "groups")
@CrossOrigin(origins = "*")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping()
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        List<GroupDTO> groupsDTO = this.groupService.findAll();
        
        return ResponseEntity
            .ok()
            .body(groupsDTO);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Optional<GroupDTO>> getGroupById(@PathVariable Long id) {
        Optional<GroupDTO> groupDTO = this.groupService.findById(id);
        
        return ResponseEntity
            .ok()
            .body(groupDTO);
    }

    @PostMapping()
    public ResponseEntity<GroupDTO> createGroup(
        @Valid @RequestBody CreateGroupRequest request
    ) {
        Group group = new Group();
        group.setName(request.name());
        group.setSubject(request.subject());
        group.setModality(request.modality());
        group.setLocation(request.location());
        group.setObjective(request.objective());
        group.setStudents(new HashSet<>());

        GroupDTO createdGroupDTO = this.groupService.save(group);
        
        return ResponseEntity
            .ok()
            .body(createdGroupDTO);
    }
    
    @PutMapping(path = "{id}/join")
    public ResponseEntity<GroupDTO> joinGroupById(@PathVariable Long id) {
        GroupDTO updatedGroupDTO = this.groupService.join(id);
        
        return ResponseEntity
            .ok()
            .body(updatedGroupDTO);
    }
}
