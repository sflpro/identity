package com.sflpro.identity.core.services.permission;

import com.sflpro.identity.core.db.entities.Permission;
import com.sflpro.identity.core.db.repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

/**
 * Company: SFL LLC
 * Created on 29/11/2017
 *
 * @author Davit Harutyunyan
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Permission create(final PermissionRequest permissionRequest) {
        Assert.notNull(permissionRequest.getName(), "permissionRequest.name cannot be null");

        Permission permission = new Permission();
        permission.setName(permissionRequest.getName());
        permission.setType(permissionRequest.getType());
        permission.setCreated(LocalDateTime.now());
        permission.setUpdated(LocalDateTime.now());
        return permissionRepository.save(permission);
    }

    @Override
    public Permission update(final PermissionRequest permissionRequest) {
        Assert.notNull(permissionRequest.getId(), "permissionRequest.id cannot be null");
        Assert.notNull(permissionRequest.getName(), "permissionRequest.name cannot be null");

        Permission permission = permissionRepository.findByDeletedIsNullAndId(permissionRequest.getId());
        permission.setName(permissionRequest.getName());
        permission.setType(permissionRequest.getType());
        permission.setUpdated(LocalDateTime.now());
        return permissionRepository.save(permission);
    }

    @Override
    public Permission get(Long permissionId) {
        Assert.notNull(permissionId, "permissionId cannot be null");

        return permissionRepository.findByDeletedIsNullAndId(permissionId);
    }
}
