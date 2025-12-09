/**
 * Base business class to be implemented by the business layer.
 */
package br.com.wss.base;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author Willians Silva Santos
 * @param <E>
 * @param <ID>
 *
 */
public interface BaseBusiness<E extends BaseEntity<?>, ID extends Serializable> extends Serializable {

	E insert(E entity);

	E update(E entity);

	E delete(ID id);

	E merge(E entity);

	Optional<E> findByUid(ID id);

	Optional<E> findDependencyByUid(ID id);
}