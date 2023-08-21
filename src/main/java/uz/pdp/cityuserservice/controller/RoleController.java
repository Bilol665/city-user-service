package uz.pdp.cityuserservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cityuserservice.domain.dto.RoleDto;
import uz.pdp.cityuserservice.domain.entity.user.RoleEntity;
import uz.pdp.cityuserservice.service.role.RoleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/api/v1/role")
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/save")
    public ResponseEntity<RoleEntity>saveRole(
            @RequestBody RoleDto roleDto
    ){
        return ResponseEntity.ok(roleService.save(roleDto));
    }

    @GetMapping("/getRole")
    public ResponseEntity<List<RoleEntity>> getAllRole(){
        return ResponseEntity.ok(roleService.getAll());
    }

    @DeleteMapping("/{name}/delete")
    public ResponseEntity<HttpStatus>deleteRole(
            @PathVariable String name
    ){
        roleService.deleteById(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{name}/update")
    public ResponseEntity<RoleEntity>updateRole(
            @RequestBody RoleDto roleDto,
            @PathVariable String name
    ){
        return ResponseEntity.ok(roleService.update(name,roleDto));
    }
}
