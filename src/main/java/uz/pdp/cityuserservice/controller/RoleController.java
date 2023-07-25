package uz.pdp.cityuserservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cityuserservice.domain.dto.RoleDto;
import uz.pdp.cityuserservice.domain.entity.user.RoleEntity;
import uz.pdp.cityuserservice.service.role.RoleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/role")
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/save")
//    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<RoleEntity>saveRole(
            @RequestBody RoleDto roleDto
    ){
        return ResponseEntity.ok(roleService.save(roleDto));
    }

    @GetMapping("/getRole")
    public ResponseEntity<List<RoleEntity>> getAllRole(){
        return ResponseEntity.ok(roleService.getAll());
    }

    @DeleteMapping("/{id}/deleteRole")
    public ResponseEntity<HttpStatus>deleteRole(
            @PathVariable UUID id
    ){
        roleService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{id}/updateRole")
    public ResponseEntity<RoleEntity>updateRole(
            @RequestBody RoleDto roleDto,
            @PathVariable UUID id
    ){
        return ResponseEntity.ok(roleService.update(id,roleDto));
    }
}
