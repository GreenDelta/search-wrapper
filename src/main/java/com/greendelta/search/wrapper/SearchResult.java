package com.greendelta.search.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.greendelta.search.wrapper.aggregations.results.AggregationResult;

public class SearchResult<T> {

	public final List<T> data = new ArrayList<>();
	public final List<AggregationResult> aggregations = new ArrayList<>();
	public final ResultInfo resultInfo = new ResultInfo();

	public static class ResultInfo {

		public int pageSize;
		public long count;
		public long totalCount;
		public int currentPage;
		public int pageCount;

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
