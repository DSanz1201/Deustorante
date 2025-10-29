
public class Cliente {
	private String email,contrasenia;
	private TipoPersona tipo;
	public Cliente() {
		super();
		
		// TODO Auto-generated constructor stub
	}
	public Cliente(String email, String contrasenia, TipoPersona tipo) {
		super();
		this.email = email;
		this.contrasenia = contrasenia;
		this.tipo = tipo;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the contrasenia
	 */
	public String getContrasenia() {
		return contrasenia;
	}
	/**
	 * @param contrasenia the contrasenia to set
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	/**
	 * @return the tipo
	 */
	public TipoPersona getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(TipoPersona tipo) {
		this.tipo = tipo;
	}
	@Override
	public String toString() {
		return "Cliente [email=" + email + ", contrasenia=" + contrasenia + ", tipo=" + tipo + "]";
	}
	
	
	

}
