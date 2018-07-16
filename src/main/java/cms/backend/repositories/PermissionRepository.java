package cms.backend.repositories;

import cms.backend.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    /*Tìm danh sách chức năng theo đường dẫn*/
    List<Permission> findByUrl(String url);
}
