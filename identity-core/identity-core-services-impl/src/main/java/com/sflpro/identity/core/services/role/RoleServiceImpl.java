package com.sflpro.identity.core.services.role;

import com.sflpro.identity.core.db.entities.Permission;
import com.sflpro.identity.core.db.entities.Role;
import com.sflpro.identity.core.db.repositories.RoleRepository;
import com.sflpro.identity.core.services.ResourceNotFoundException;
import com.sflpro.identity.core.services.permission.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Set;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Role create(final RoleRequest roleRequest) {
        Assert.notNull(roleRequest.getName(), "roleRequest.name cannot be null");

        Role role = new Role();
        role.setName(roleRequest.getName());
        role.setCreated(LocalDateTime.now());
        role.setUpdated(LocalDateTime.now());

        Set<Permission> permissions = roleRequest.getPermission().stream()
                .map(permissionRequest -> permissionService.get(permissionRequest.getId()))
                .collect(Collectors.toSet());
        role.setPermissions(permissions);
        return roleRepository.save(role);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role update(final RoleRequest roleRequest) {
        Assert.notNull(roleRequest.getId(), "roleRequest.id cannot be null");
        Assert.notNull(roleRequest.getName(), "roleRequest.name cannot be null");

        Role role = get(roleRequest.getId());
        role.setName(roleRequest.getName());

        Set<Permission> permissions = roleRequest.getPermission().stream()
                .map(permissionRequest -> permissionService.get(permissionRequest.getId()))
                .collect(Collectors.toSet());
        role.setPermissions(permissions);
        return roleRepository.save(role);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role get(final Long roleId) {
        Assert.notNull(roleId, "roleId cannot be null");
        return roleRepository.findByDeletedIsNullAndId(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Role with id: %s not found", roleId)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Role getByName(final String name) {
        Assert.notNull(name, name);
        return roleRepository.findByNameAndDeletedIsNull(name)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Role with name:%s not found", name)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Permission> getPermissions(final Set<Role> roles) {
        return roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .collect(Collectors.toSet());
    }
}
