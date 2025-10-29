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
@Table(name="reservas")
public class Reserva {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "numero_personas", nullable = false)
    private int numeroPersonas;

    @Column(name = "fecha_hora_reserva",insertable=false, nullable = false)
    private LocalDateTime fechaHoraReserva;
    
    @Column(name ="estado",insertable=false,nullable =false, columnDefinition="ENUM('confirmada','cancelada') DEFAULT 'confirmada'")
    private String estado;
	
    public Reserva() {}
	
	public Reserva(Evento evento, Usuario usuario, int numeroPersonas) {
		this.evento = evento;
		this.usuario = usuario;
		this.numeroPersonas = numeroPersonas;
	}

	public Reserva(Evento evento, Usuario usuario, int numeroPersonas, String estado) {
		super();
		this.evento = evento;
		this.usuario = usuario;
		this.numeroPersonas = numeroPersonas;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getNumeroPersonas() {
		return numeroPersonas;
	}

	public void setNumeroPersonas(int numeroPersonas) {
		this.numeroPersonas = numeroPersonas;
	}

	public LocalDateTime getFechaHoraReserva() {
		return fechaHoraReserva;
	}

	public void setFechaHoraReserva(LocalDateTime fechaHoraReserva) {
		this.fechaHoraReserva = fechaHoraReserva;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Reserva [id=" + id + ", evento=" + evento + ", usuario=" + usuario + ", numeroPersonas="
				+ numeroPersonas + ", fechaHoraReserva=" + fechaHoraReserva + ", estado=" + estado + "]";
	}


    
}
