package project.model;

import javax.persistence.*;

@Entity
@Table(name = "tabletest")
public class EntityTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "field")
    private int field;

    public EntityTest() {
    }

    public EntityTest(int n) {
        this.field =n;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "EntityTest{" +
                "id=" + id +
                ", N=" + field +
                '}';
    }
}
