/**
 * 
 */
package fr.mcgivrer.applications.angulargames.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author FDELORME
 * 
 */
@Entity
@Table(name = "games")
public class Game implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2994333080034615958L;

	/**
	 * UID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Game name.
	 */
	@Size(min = 4, max = 50)
	private String name;

	@Size(min = 2, max = 10)
	private String platform;

	/**
	 * url to cover image
	 */
	@NotNull
	private String cover;

	public Game() {
		this.id = null;
	}

	public Game(String name, String platform, String cover) {
		super();
		this.name = name;
		this.platform = platform;
		this.cover = cover;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

}
