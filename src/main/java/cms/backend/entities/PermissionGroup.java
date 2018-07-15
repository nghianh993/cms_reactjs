package cms.backend.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class PermissionGroup implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private int parentid;

    @OneToMany(mappedBy="permissionGroup", cascade = CascadeType.REMOVE)
    private List<Permission> permissions;
    public PermissionGroup() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
