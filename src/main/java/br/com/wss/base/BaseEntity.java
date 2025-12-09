/**
 * Base entity class to be implemented by the entities of the application.
 */
package br.com.wss.base;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Willians Silva Santos
 *
 */
@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = false, of = "uid")
public abstract class BaseEntity<ID extends Serializable> implements Serializable {

	private static final long serialVersionUID = 1417155564888645360L;

	@Id
    @UuidGenerator
	private String uid;

	private Boolean deleted;

    @Column(updatable = false)
	private LocalDateTime createdAt;

	private String createdByUid;

	private String createdByName;

	private LocalDateTime updatedAt;

	private String updatedByUid;

	private String updatedByName;

	private LocalDateTime deletedAt;

	private String deletedByUid;

	private String deletedByName;
}