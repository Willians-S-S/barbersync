package br.com.wss.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Vinicius
 *
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder()
public class PageImpl<T> {

	private List<T> content;
	private int pageNumber;
	private int pageSize;
	private int totalPages;
	private int numberOfElements;
	private Long totalElements;
	private final Map<String, Object> attribute = new HashMap<>();

	public PageImpl(Page<T> page) {
		this.pageNumber = page.getNumber();
		this.pageSize = page.getSize();
		this.totalPages = page.getTotalPages();
		this.numberOfElements = page.getNumberOfElements();
		this.totalElements = page.getTotalElements();
		this.content = page.getContent();
	}

}
