package com.bossky.data.search;

/**
 * 搜索条件
 * 
 * @author bo
 *
 */
public interface SearchCondition {
	/** 相等 　 */
	public final static int COMP_OPTION_EQUALS = 1;
	/** 不等 */
	public final static int COMP_OPTION_NO_EQUALS = 2;
	/** 大于 OPTION_EQUALS&OPTION_GREATER 则为大于等于 其它类似 */
	public final static int COMP_OPTION_GREATER = 2 << 1;
	/** 小于 OPTION_EQUALS&OPTION_LESS 则为大于等于　 其它类似 */
	public final static int COMP_OPTION_LESS = 2 << 2;
	/** 前方一致 */
	public final static int COMP_OPTION_STARTWITH = 2 << 3;
	/** 后方一致 */
	public final static int COMP_OPTION_ENDWITH = 2 << 4;
	/** 模糊匹配 */
	public final static int COMP_OPTION_LIKE = 2 << 4;

}
