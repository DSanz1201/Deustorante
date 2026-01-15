package domain;

public class Plato {
    private String nombre;
    private double precio;
    private String categoria;

    public Plato(String nombre, double precio, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    public String getNombre() {
    	return nombre; 
    }
    
    public double getPrecio() {
    	return precio;
    }
    
    public String getCategoria() {
    	return categoria; 
    }
    
    @Override
    public String toString() {
    	return nombre;
    }
}