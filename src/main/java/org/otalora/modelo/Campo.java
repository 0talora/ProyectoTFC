package org.otalora.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="campos")
public class Campo {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="nombre",nullable=false,length = 100)
	private String nombre;
	
	@Column(name="ciudad",nullable=false,length = 100)
	private String ciudad;
	
	@Column(name="calle",nullable=false,length = 100)
	private String calle;
	
	@Column(name="codigo_postal",nullable=false)
	private int codigoPostal;
	
	@Column(name="numero_telefono")
	private int numTelefono;
	
	@Column(name="comunidad_autonoma",nullable=false,length = 100)
	private String comunidad;
	
	@ManyToOne
	@JoinColumn(name="id_admin",nullable=false)
	private Usuario usuarioAdmin;

	public Campo() {
	}

	public Campo(Long id, String nombre, String ciudad, String calle, int codigoPostal, int numTelefono,
			String comunidad, Usuario usuarioAdmin) {
		this.id = id;
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.calle = calle;
		this.codigoPostal = codigoPostal;
		this.numTelefono = numTelefono;
		this.comunidad = comunidad;
		this.usuarioAdmin = usuarioAdmin;
	}

	public Campo(String nombre, String ciudad, String calle, int codigoPostal, int numTelefono, String comunidad,
			Usuario usuarioAdmin) {
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.calle = calle;
		this.codigoPostal = codigoPostal;
		this.numTelefono = numTelefono;
		this.comunidad = comunidad;
		this.usuarioAdmin = usuarioAdmin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public int getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public int getNumTelefono() {
		return numTelefono;
	}

	public void setNumTelefono(int numTelefono) {
		this.numTelefono = numTelefono;
	}

	public String getComunidad() {
		return comunidad;
	}

	public void setComunidad(String comunidad) {
		this.comunidad = comunidad;
	}

	public Usuario getUsuarioAdmin() {
		return usuarioAdmin;
	}

	public void setUsuarioAdmin(Usuario usuarioAdmin) {
		this.usuarioAdmin = usuarioAdmin;
	}

	@Override
	public String toString() {
		return "Campo [id=" + id + ", nombre=" + nombre + ", ciudad=" + ciudad + ", calle=" + calle + ", codigoPostal="
				+ codigoPostal + ", numTelefono=" + numTelefono + ", comunidad=" + comunidad + ", usuarioAdmin="
				+ usuarioAdmin + "]";
	}
	
}

