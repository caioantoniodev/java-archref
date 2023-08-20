package tech.api.archref.domain.entities;

public class Character {
    private String id;

    private String name;

    private String description;

    private Integer attackPoint;

    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAttackPoint() {
        return attackPoint;
    }

    public void setAttackPoint(Integer attackPoint) {
        this.attackPoint = attackPoint;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Character{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", attackPoint=" + attackPoint +
                ", address='" + address + '\'' +
                '}';
    }
}
