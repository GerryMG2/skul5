package com.example.skul5.domain;

import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class groupStudentRecord {
	
	@Id
	private int id;
	private String nombre;
	private String apellido;
	private int materiasa;
	private int materiasr;
	private float promedio;
	
	public groupStudentRecord() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getMateriasa() {
		return materiasa;
	}
	public void setMateriasa(int materiasa) {
		this.materiasa = materiasa;
	}
	public int getMateriasr() {
		return materiasr;
	}
	public void setMateriasr(int materiasr) {
		this.materiasr = materiasr;
	}
	public float getPromedio() {
		return promedio;
	}
	public void setPromedio(float promedio) {
		this.promedio = promedio;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	

}