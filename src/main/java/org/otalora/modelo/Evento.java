package org.otalora.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_campo", nullable = false)
    private Campo idCampo;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;

    @Column(name = "numero_plazas", nullable = false)
    private int numeroPlazas;

    @Column(name = "numero_plazas_restantes", nullable = false)
    private int numeroPlazasRestantes;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "cancelado", nullable = false)
    private boolean cancelado = false;

    public Evento() {
    }

    public Evento(Long id, Campo idCampo, LocalDateTime fechaHora, String nombre, int numeroPlazas,
                  int numeroPlazasRestantes, String descripcion, boolean cancelado) {
        this.id = id;
        this.idCampo = idCampo;
        this.fechaHora = fechaHora;
        this.nombre = nombre;
        this.numeroPlazas = numeroPlazas;
        this.numeroPlazasRestantes = numeroPlazasRestantes;
        this.descripcion = descripcion;
        this.cancelado = cancelado;
    }

    public Evento(Campo idCampo, LocalDateTime fechaHora, String nombre, int numeroPlazas,
                  int numeroPlazasRestantes, String descripcion) {
        this.idCampo = idCampo;
        this.fechaHora = fechaHora;
        this.nombre = nombre;
        this.numeroPlazas = numeroPlazas;
        this.numeroPlazasRestantes = numeroPlazasRestantes;
        this.descripcion = descripcion;
        this.cancelado = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Campo getIdCampo() {
        return idCampo;
    }

    public void setIdCampo(Campo idCampo) {
        this.idCampo = idCampo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroPlazas() {
        return numeroPlazas;
    }

    public void setNumeroPlazas(int numeroPlazas) {
        this.numeroPlazas = numeroPlazas;
    }

    public int getNumeroPlazasRestantes() {
        return numeroPlazasRestantes;
    }

    public void setNumeroPlazasRestantes(int numeroPlazasRestantes) {
        this.numeroPlazasRestantes = numeroPlazasRestantes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isCancelado() {
        return cancelado;
    }

    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }

    @Override
    public String toString() {
        return "Evento [idCampo=" + idCampo + ", fechaHora=" + fechaHora + ", nombre=" + nombre
                + ", numeroPlazas=" + numeroPlazas + ", numeroPlazasRestantes=" + numeroPlazasRestantes
                + ", descripcion=" + descripcion + ", cancelado=" + cancelado + "]";
    }
}
