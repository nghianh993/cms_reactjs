package cms.backend.servicesimpl;

import cms.backend.entities.Permission;
import cms.backend.models.PermissionModel;
import cms.backend.repositories.PermissionRepository;
import cms.backend.services.IPermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl implements IPermissionService {

	@Autowired
	PermissionRepository permissionRepository;

	@Override
	public List<PermissionModel> getPermissionByUrl(String url) {
		List<PermissionModel> permissionModels = new ArrayList<>();
		List<Permission> permissions = permissionRepository.findByUrl(url);
		for (Permission permission : permissions) {
			PermissionModel permissionModel = new PermissionModel();
			BeanUtils.copyProperties(permission, permissionModel);
			permissionModels.add(permissionModel);
		}
		return permissionModels;
	}
}
