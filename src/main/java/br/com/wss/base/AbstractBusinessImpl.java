package br.com.wss.base;

import br.com.wss.exception.BusinessException;
import br.com.wss.filters.JwtToken;
import br.com.wss.filters.JwtToken.UserTokenDetails;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

/**
 * @author Willians Silva Santos
 *
 * @param <E>
 * @param <ID>
 */
@Getter(value = AccessLevel.PROTECTED)
@Transactional
@Slf4j
public abstract class AbstractBusinessImpl<E extends BaseEntity<?>, ID extends Serializable>
		implements BaseBusiness<E, ID> {

	private static final long serialVersionUID = 5398011752218105464L;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private JwtToken jwtToken;

	@Override
	public E insert(E entity) {

		final UserTokenDetails userDetails = getUserDetails();

		if (entity.getUid() != null)
			throw new BusinessException(HttpStatus.BAD_REQUEST, "Id is not permitted on insert method");

		entity = this.setDependencies(entity, TransactionType.INSERT);

		this.validate(entity, TransactionType.INSERT);

		entity.setCreatedAt(LocalDateTime.now());
		entity.setUpdatedAt(null);
		entity.setUpdatedByUid(null);
		entity.setDeletedAt(null);
		entity.setDeletedByUid(null);
		entity.setDeleted(false);

		if (userDetails.getAccount() != null) {
			entity.setCreatedByUid(userDetails.getAccount().getUid());
			entity.setCreatedByName(userDetails.getAccount().getName());
		}

		E inserted = getRepository().save(entity);

		this.afterOperation(entity, inserted, TransactionType.INSERT);

		return inserted;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E update(E entity) {

		final UserTokenDetails userDetails = getUserDetails();

		entity = this.setDependencies(entity, TransactionType.UPDATE);

		this.validate(entity, TransactionType.UPDATE);

		final String uid = entity.getUid();

		E e = this.findByUid((ID) entity.getUid())
				.orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Entity with id " + uid + " not found"));

		entity.setCreatedAt(e.getCreatedAt());
		entity.setCreatedByUid(e.getCreatedByUid());
		entity.setCreatedByName(e.getCreatedByName());
		entity.setUpdatedAt(LocalDateTime.now());
		entity.setDeletedAt(e.getDeletedAt());
		entity.setDeletedByUid(e.getDeletedByUid());
		entity.setDeleted(e.getDeleted());

		if (userDetails.getAccount() != null) {
			entity.setUpdatedByUid(userDetails.getAccount().getUid());
			entity.setUpdatedByName(userDetails.getAccount().getName());
		}

		E updated = getRepository().save(entity);

		this.afterOperation(entity, updated, TransactionType.UPDATE);

		return updated;

	}

	@Override
	public E delete(ID id) {

		final UserTokenDetails userDetails = getUserDetails();

		return getRepository().findById(id).map(e -> {
			e.setDeleted(true);
			e.setDeletedAt(LocalDateTime.now());

			if (userDetails.getAccount() != null) {
				e.setDeletedByUid(userDetails.getAccount().getUid());
				e.setDeletedByName(userDetails.getAccount().getName());
			}

			return getRepository().save(e);
		}).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Entity with id " + id + " not found"));
	}

	protected void validate(final E entity, final TransactionType transactionType) {
	}

	protected E setDependencies(final E entity, final TransactionType transactionType) {
		return entity;
	}

	protected E afterOperation(final E entity, final E returned, final TransactionType transactionType) {
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E merge(E entity) {
		if (entity.getUid() == null)
			return this.insert(entity);
		else if (entity.getDeleted() != null && entity.getDeleted())
			return this.delete((ID) entity.getUid());
		else
			return this.update(entity);
	}

	@Override
	public Optional<E> findByUid(ID id) {
		return (Optional<E>) getRepository().findById(id).map(e -> {
			if (e.getDeleted() != null && Boolean.TRUE.equals(e.getDeleted()))
				throw new BusinessException(HttpStatus.NOT_FOUND, "Entity " + e.getUid() + " is deleted");
			return Optional.of(e);
		}).orElse(Optional.empty());
	}

	@Override
	public Optional<E> findDependencyByUid(ID id) {
		return getRepository().findById(id).map(e -> {
			return Optional.of(e);
		}).orElse(Optional.empty());
	}

	protected String getMessage(final String key) {
		return messageSource.getMessage(key, null, Locale.getDefault());
	}

	protected UserTokenDetails getUserDetails() {
		return jwtToken.getUserDetails();
	}

	protected String getToken() {
		return jwtToken.getToken();
	}

	protected abstract BaseRepository<E, ID> getRepository();
}
