package work.funcke.data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@NamedQuery(name="User.findAll", query="select id, username from User c order by id")
@javax.persistence.Table(name="User")
public class User {
    @Id
    @GeneratedValue(strategy= SEQUENCE)
    protected int id;
    @Column(name="username")
    @NotBlank
    private String username;

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
}
