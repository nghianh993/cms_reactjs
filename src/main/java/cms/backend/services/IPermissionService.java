package cms.backend.services;

import cms.backend.models.PermissionModel;

import java.util.List;

public interface IPermissionService {

	List<PermissionModel> getPermissionByUrl(String url);
}
