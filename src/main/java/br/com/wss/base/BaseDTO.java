package br.com.wss.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class BaseDTO<ID extends Serializable> implements Serializable {

	private static final long serialVersionUID = 1417155564888645360L;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String uid;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Boolean deleted;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LocalDateTime createdAt;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String createdByUid;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String createdByName;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LocalDateTime updatedAt;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String updatedByUid;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String updatedByName;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LocalDateTime deletedAt;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String deletedByUid;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String deletedByName;

}
