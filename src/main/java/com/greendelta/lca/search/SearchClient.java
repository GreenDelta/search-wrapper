package com.greendelta.lca.search;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SearchClient {

	SearchResult<Map<String, Object>> search(SearchQuery searchQuery);

	void create(Map<String, Object> settings);

	void index(String type, String id, Map<String, Object> content);

	void index(String type, Map<String, Map<String, Object>> contentsById);

	void index(Map<String, Map<String, Map<String, Object>>> contentsByIdByType);

	void remove(String type, String id);

	void remove(String type, Set<String> ids);

	void remove(Map<String, Set<String>> idsByType);

	Map<String, Object> get(String type, String id);

	List<Map<String, Object>> get(String type, Set<String> ids);

	List<Map<String, Object>> get(Map<String, Set<String>> idsByType);

	void delete();

}
