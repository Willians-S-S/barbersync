/**
 * Base repository class to be implemented by the repository layer.
 */
package br.com.wss.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * @author Willians Silva Santos
 * @param <E>
 * @param <ID>
 *
 */
public interface BaseRepository<E extends BaseEntity<?>, ID extends Serializable>
        extends PagingAndSortingRepository<E, ID>, JpaSpecificationExecutor<E>, JpaRepository<E, ID> {

}