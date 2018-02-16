package com.greendelta.search.wrapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SearchClient {

	SearchResult<Map<String, Object>> search(SearchQuery searchQuery);

	void create(Map<String, String> settings);

	void index(String id, Map<String, Object> content);

	void index(Map<String, Map<String, Object>> contentsById);

	void remove(String id);

	void remove(Set<String> ids);

	boolean has(String id);

	Map<String, Object> get(String id);

	List<Map<String, Object>> get(Set<String> ids);

	void delete();

}
