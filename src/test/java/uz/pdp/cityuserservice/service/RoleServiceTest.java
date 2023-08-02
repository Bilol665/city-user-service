package uz.pdp.cityuserservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import uz.pdp.cityuserservice.domain.dto.RoleDto;
import uz.pdp.cityuserservice.domain.entity.user.PermissionEntity;
import uz.pdp.cityuserservice.domain.entity.user.RoleEntity;
import uz.pdp.cityuserservice.repository.user.PermissionRepository;
import uz.pdp.cityuserservice.repository.user.RoleRepository;
import uz.pdp.cityuserservice.service.role.RoleService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;


class RoleServiceTest {
    private final String name = "Tester";
    private RoleDto roleDto;
    private RoleEntity roleEntity;
    @Mock
    private PermissionRepository permissionRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        initMocks(this);
        PermissionEntity tester = permissionRepository.save(PermissionEntity.builder().permission("TESTER").build());
        roleDto = new RoleDto(name, List.of("TESTER"));
        roleEntity = RoleEntity.builder()
                .role(name)
                .permissions(List.of(tester))
                .build();
    }


    @Test
    void saveRoleTest(){
        when(modelMapper.map(roleDto,RoleEntity.class)).thenReturn(roleEntity);
        when(roleRepository.save(roleEntity)).thenReturn(roleEntity);

        RoleEntity role = roleService.save(roleDto);


        assertEquals(name,role.getRole());

    }

    @Test
    void deleteTest(){
        when(roleRepository.findById(roleEntity.getRole())).thenReturn(Optional.of(roleEntity));
        doNothing().when(roleRepository).deleteById(roleEntity.getRole());

        roleService.deleteById(roleEntity.getRole());

        verify(roleRepository,times(1));
    }
}
