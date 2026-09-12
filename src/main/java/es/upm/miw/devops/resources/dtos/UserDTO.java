package es.upm.miw.devops.resources.dtos;

public class UserDTO {
    private Long id;
    private String nombre;

    public UserDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
}