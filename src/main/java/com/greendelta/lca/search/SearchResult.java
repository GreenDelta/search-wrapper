package com.greendelta.lca.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.greendelta.lca.search.aggregations.results.AggregationResult;

public class SearchResult {

	public final List<Map<String, Object>> data = new ArrayList<>();
	public final List<AggregationResult> aggregations = new ArrayList<>();
	public final ResultInfo resultInfo = new ResultInfo();

	public class ResultInfo {

		public long pageSize;
		public long count;
		public long totalCount;
		public long currentPage;
		public long pageCount;

		@Override
		public String toString() {
			String s = "totalCount=" + totalCount + ", ";
			s += "pageSize=" + pageSize + ", ";
			s += "currentPage=" + currentPage + ", ";
			s += "pageCount=" + pageCount + ", ";
			return s + "count=" + count;
		}

	}

	@Override
	public String toString() {
		String s = "{resultInfo={" + resultInfo.toString() + "}, ";
		s += "aggregations=[";
		int i = 0;
		for (AggregationResult r : aggregations) {
			s += r.toString();
			i++;
			if (i < aggregations.size()) {
				s += ", ";
			}
		}
		return s + "]}";
	}

}
