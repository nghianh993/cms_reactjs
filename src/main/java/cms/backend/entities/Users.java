package cms.backend.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String password;
    private String fullname;
    private String phone;
    private String email;
    private boolean islock;
    private String images;
    private Date lastpasswordresetdate;
    private Date createdate;
    private Date modifydate;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "USERS_ROLES",
            joinColumns =  @JoinColumn(name = "USERID", referencedColumnName = "ID"),
            inverseJoinColumns =  @JoinColumn(name = "ROLEID", referencedColumnName = "ID"))
    private List<Roles> roles = new ArrayList<>();

    public Users() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isIslock() {
        return islock;
    }

    public void setIslock(boolean islock) {
        this.islock = islock;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Date getLastpasswordresetdate() {
        return lastpasswordresetdate;
    }

    public void setLastpasswordresetdate(Date lastpasswordresetdate) {
        this.lastpasswordresetdate = lastpasswordresetdate;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }
}
