package com.sflpro.identity.core.services.role;

import com.sflpro.identity.core.db.entities.Permission;
import com.sflpro.identity.core.db.entities.Role;
import com.sflpro.identity.core.db.repositories.RoleRepository;
import com.sflpro.identity.core.services.ResourceNotFoundException;
import com.sflpro.identity.core.services.permission.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Company: SFL LLC
 * Created on 20/11/2017
 *
 * @author Davit Harutyunyan
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final PermissionService permissionService;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, PermissionService permissionService) {
        this.roleRepository = roleRepository;
        this.permissionService = permissionService;
    }

    @Override
    public Role create(final RoleRequest roleRequest) {
        Assert.notNull(roleRequest.getName(), "roleRequest.name cannot be null");

        Role role = new Role();
        role.setName(roleRequest.getName());
        role.setCreated(LocalDateTime.now());
        role.setUpdated(LocalDateTime.now());

        List<Permission> permissions = roleRequest.getPermission().stream()
                .map(permissionRequest -> permissionService.get(permissionRequest.getId()))
                .collect(Collectors.toList());
        role.setPermissions(permissions);
        return roleRepository.save(role);
    }

    @Override
    public Role update(final RoleRequest roleRequest) {
        Assert.notNull(roleRequest.getId(), "roleRequest.id cannot be null");
        Assert.notNull(roleRequest.getName(), "roleRequest.name cannot be null");

        Role role = get(roleRequest.getId());
        role.setName(roleRequest.getName());

        List<Permission> permissions = roleRequest.getPermission().stream()
                .map(permissionRequest -> permissionService.get(permissionRequest.getId()))
                .collect(Collectors.toList());
        role.setPermissions(permissions);
        return roleRepository.save(role);
    }

    @Override
    public Role get(final Long roleId) {
        Assert.notNull(roleId, "roleId cannot be null");
        return roleRepository.findByDeletedIsNullAndId(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Role with id: %s not found", roleId)));
    }

}
