package com.bossky.ocean.ext;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ResultPages {
	public static <E> ResultPage<E> toResultPage(List<? super E> paramList) {
		return new ResultPage<E>(paramList);
	}

	public static <E> List<E> toList(ResultPage<E> paramResultPage, int paramInt) {
		return paramResultPage.a;
	}

	public static <E> ResultPage<E> toSortResultPage(ResultPage<E> rp,
			Comparator<E> comparator, int limitNone) {
		Collections.sort(rp.a, comparator);
		return rp;
	}
}
