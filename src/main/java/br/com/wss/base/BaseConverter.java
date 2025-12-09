/**
 * Base converter class to be implemented by the converters of the application.
 */
package br.com.wss.base;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Willians Silva Santos
 *
 */
public abstract class BaseConverter<E extends BaseEntity<?>, DTO> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6686583539363813903L;
	
	/**
	 * @param entities
	 * @return
	 */
	public List<DTO> convertToDTOList(final List<E> entities) {

        return entities
                .stream()
                .filter(e -> e != null && !Boolean.TRUE.equals(e.getDeleted()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
	}
	
	/**
	 * @param dtos
	 * @return
	 */
	public List<E> convertToEntityList(final List<DTO> dtos) {
        return dtos.stream().map(this::convertToEntity)
                .collect(Collectors.toList());
	}
	
	/**
	 * @param entity
	 * @return
	 */
	public abstract DTO convertToDTO(final E entity);
	
	/**
	 * @param dto
	 * @return
	 */
	public abstract E convertToEntity(final DTO dto);
	
	public boolean isEntityValidForConversion(final BaseEntity<?> entity) {
		return (entity != null && !Boolean.TRUE.equals(entity.getDeleted()));
	}
}
