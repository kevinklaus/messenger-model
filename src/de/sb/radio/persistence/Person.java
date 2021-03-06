package de.sb.radio.persistence;

import static de.sb.radio.persistence.Person.Group.USER;
import static javax.persistence.EnumType.STRING;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.json.bind.annotation.JsonbVisibility;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.eclipse.persistence.annotations.CacheIndex;
import de.sb.toolbox.Copyright;
import de.sb.toolbox.bind.JsonProtectedPropertyStrategy;


/**
 * This class models person entities.
 */
@XmlType
@XmlRootElement
@JsonbVisibility(JsonProtectedPropertyStrategy.class)
@Entity
@Table(schema = "radio", name = "Person")
@PrimaryKeyJoinColumn(name = "personIdentity")
@Copyright(year=2005, holders="Sascha Baumeister")
public class Person extends BaseEntity {
	static public enum Group { USER, ADMIN }

	@NotNull @Size(min = 1, max = 127) @Email
	@Column(nullable = false, updatable = true, length = 128, unique = true)
	@CacheIndex(updateable = true)
	private String email;

	@NotNull @Size(min = 32, max = 32)
	@Column(nullable = false, updatable = true, length = 32)
	private byte[] passwordHash;

	@NotNull
	@Enumerated(STRING)
	@Column(name = "groupAlias", nullable = false, updatable = true)
	private Group group;

	@NotNull
	@Column(nullable = false, updatable = true)
	private String forename;

	@NotNull
	@Column(nullable = false, updatable = true)
	private String surname;

	@ManyToOne(optional = false)
	@JoinColumn(name = "avatarReference", nullable = false, updatable = true)
	private Document avatar;


	/**
	 * Default constructor for JPA, JSON-B and JAX-B.
	 */
	protected Person () {
		this(null);
	}


	/**
	 * Creates a new instance with the given avatar, an empty password and group USER.
	 * @param avatar the avatar, or {@code null} for none
	 */
	public Person (final Document avatar) {
		this.passwordHash = HashTools.sha256HashCode("");
		this.group = USER;
		this.avatar = avatar;
	}


	/**
	 * Returns the email.
	 * @return the email address
	 */
	@JsonbProperty @XmlAttribute
	public String getEmail () {
		return this.email;
	}


	/**
	 * Sets the email.
	 * @param email the email address
	 */
	public void setEmail (final String email) {
		this.email = email;
	}


	/**
	 * Returns the password hash.
	 * @return the password hash
	 */
	@JsonbTransient @XmlTransient
	public byte[] getPasswordHash () {
		return this.passwordHash;
	}


	/**
	 * Sets the password hash.
	 * @param passwordHash the SHA-256 password hash
	 */
	public void setPasswordHash (final byte[] passwordHash) {
		this.passwordHash = passwordHash;
	}


	/**
	 * Returns the group.
	 * @return the group
	 */
	@JsonbProperty @XmlAttribute
	public Group getGroup () {
		return this.group;
	}


	/**
	 * Sets the group.
	 * @param group the group
	 */
	public void setGroup (final Group group) {
		this.group = group;
	}


	/**
	 * Returns the forename.
	 * @return the forename
	 */
	@JsonbProperty @XmlElement
	public String getForename () {
		return this.forename;
	}


	/**
	 * Sets the forename.
	 * @param forename the forename
	 */
	public void setForename (final String forename) {
		this.forename = forename;
	}


	/**
	 * Returns the surname.
	 * @return the surname
	 */
	@JsonbProperty @XmlElement
	public String getSurname () {
		return this.surname;
	}


	/**
	 * Sets the surname.
	 * @param surname the surname
	 */
	public void setSurname (final String surname) {
		this.surname = surname;
	}


	/**
	 * Returns the avatar reference. This operation is provided solely for marshaling purposes.
	 * @return the identity of the *:1 related avatar, or {@code 0} for none
	 */
	@JsonbProperty @XmlTransient
	protected long getAvatarReference () {
		return this.avatar == null ? 0 : this.avatar.getIdentity();
	}


	/**
	 * Returns the avatar.
	 * @return the *:1 related avatar
	 */
	@JsonbTransient @XmlAttribute(name = "avatarReference") @XmlIDREF
	public Document getAvatar () {
		return this.avatar;
	}


	/**
	 * Sets the avatar.
	 * @param avatar the *:1 related avatar
	 */
	public void setAvatar (final Document avatar) {
		this.avatar = avatar;
	}
}