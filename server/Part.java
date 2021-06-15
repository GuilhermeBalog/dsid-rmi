package server;

import java.util.AbstractMap;
import java.util.List;
import java.io.Serializable;

public class Part implements Serializable {
	private int code;
	private String nome;
	private String description;
	private List<AbstractMap.SimpleEntry<Part, Integer>> subParts;
	private String serverName;

	public Part(String nome, String description, List<AbstractMap.SimpleEntry<Part, Integer>> subParts, String serverName) {
		this.nome = nome;
		this.description = description;
		this.subParts = subParts;
		this.serverName = serverName;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<AbstractMap.SimpleEntry<Part, Integer>> getSubParts() {
		return subParts;
	}

	public void setSubParts(List<AbstractMap.SimpleEntry<Part, Integer>> subParts) {
		this.subParts = subParts;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	@Override
	public String toString() {
		StringBuilder subPartsString = new StringBuilder();

		for (AbstractMap.SimpleEntry<Part, Integer> line : subParts) {
			Part part = line.getKey();
			subPartsString.append("\nCódigo: ").append(part.getCode())
					.append(", nome: ").append(part.getNome())
					.append(", servidor: ").append(part.getServerName())
					.append(" quantidade: ").append(line.getValue());
		}

		return  "Código " + code +
				"\nNome: " + nome +
				"\nDescrição: " + description +
				"\nServidor: " + serverName +
				"\nSubParts: " + subPartsString;
	}
}
