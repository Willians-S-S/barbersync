package br.com.wss.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Willians Silva Santos
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class BaseDTO<ID extends Serializable> implements Serializable {

	private static final long serialVersionUID = 1417155564888645360L;

	private String uid;

	private Boolean deleted;

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
