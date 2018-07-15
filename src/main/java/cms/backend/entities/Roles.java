package cms.backend.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Roles implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String rolename;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "ROLES_PERMISSION",
            joinColumns = @JoinColumn(name = "ROLEID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "PERMISSIONID", referencedColumnName = "ID"))
    private Set<Permission> permissions = new HashSet<>();

    public Roles() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
