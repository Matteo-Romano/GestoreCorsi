package it.polito.tdp.corsi.model;

public class Corso {

	private String codins;
	private String nome;
	private Integer crediti;
	private Integer pd;
	
	public Corso(String codins, String nome, Integer crediti, Integer pd) {
		super();
		this.codins = codins;
		this.nome = nome;
		this.crediti = crediti;
		this.pd = pd;
	}

	public String getCodins() {
		return codins;
	}

	public void setCodins(String codins) {
		this.codins = codins;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCrediti() {
		return crediti;
	}

	public void setCrediti(Integer crediti) {
		this.crediti = crediti;
	}

	public Integer getPd() {
		return pd;
	}

	public void setPd(Integer pd) {
		this.pd = pd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codins == null) ? 0 : codins.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Corso other = (Corso) obj;
		if (codins == null) {
			if (other.codins != null)
				return false;
		} else if (!codins.equals(other.codins))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Corso [codins=" + codins + ", nome=" + nome + ", crediti=" + crediti + ", pd=" + pd + "]";
	}
}
