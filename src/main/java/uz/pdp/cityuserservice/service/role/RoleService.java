package uz.pdp.cityuserservice.service.role;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.cityuserservice.domain.dto.RoleDto;
import uz.pdp.cityuserservice.domain.entity.user.PermissionEntity;
import uz.pdp.cityuserservice.domain.entity.user.RoleEntity;
import uz.pdp.cityuserservice.exceptions.DataNotFoundException;
import uz.pdp.cityuserservice.repository.user.PermissionRepository;
import uz.pdp.cityuserservice.repository.user.RoleRepository;

import java.util.List;
@Transactional
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    private final ModelMapper modelMapper;

    public RoleEntity save(RoleDto roleDto){
        RoleEntity role = modelMapper.map(roleDto, RoleEntity.class);
//        role.setPermissions(getPermission(roleDto.getPermission()));
        role.setRole("ROLE_" + roleDto.getRole());
        return roleRepository.save(role);
    }

    public List<PermissionEntity> getPermission(List<String>permissions){
        return permissionRepository.findPermissionEntitiesByPermissionIn(permissions);
    }

    public List<RoleEntity>getAll(){
        return roleRepository.findAll();
    }

    public void deleteById(String name){
        RoleEntity role = roleRepository.findById(name)
                .orElseThrow(()->new DataNotFoundException("Role not found"));
        roleRepository.delete(role);
    }

    public RoleEntity update(String name ,RoleDto roleDto){
        RoleEntity role = roleRepository.findById(name)
                .orElseThrow(()->new DataNotFoundException("Role not found"));
        modelMapper.map(roleDto,role);
        return roleRepository.save(role);
    }
}
