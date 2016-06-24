package cn.com.gome.manage.pageSupport;

/**
 * 
 * PageInfo 的使用： PageInfo
 * 类是作为需要用到分页查询的函数的参数使用。首先创建一个PageInfo对象，设置currentPage值（需要的第几页）
 * 和pageSize(页大小)，将对象作为参数传递到查询调用中，查询调用会将此对象传递到DAO模板中，模板根据查询条
 * 件统计出查询结果记录统计数，然后将该值写入到此对象的totalResult值中。然后调用calculate()函数
 * 计算出(totalPage)总页数、查询结果的起始行（beginResutl)。DAO模板会使用beginResult和
 * pageSize查询出相应的结果。
 * 使用PageInfo作为查询函数的参数调用，函数在返回查询结果的同时也修改了PageInfo的内容，因此显示分页信息，可以直接
 * 使用查询调用后的PageInfo对象。
 * 
 * @author liuxm
 */
public class PageInfo {

	private int pageSize;
	private int currentPage = 1;
	private int beginResult;
	private int endResult;
	private int totalResult;
	private int totalPage = 1;

	private int beginPage = 1;
	private int endPage = 1;
	private static final int step = 4;

	private int dispBeginResult = 0;
	private int dispEndResult = 0;

	public PageInfo() {
	}

	public PageInfo(int pageSize, int currentPage) {
		super();
		this.pageSize = pageSize;
		this.currentPage = currentPage;
	}

	/*
	 * get to fetch data
	 */
	public int getBeginResult() {
		return beginResult;
	}

	public int getEndResult() {
		if ((currentPage == totalPage) && (totalResult % pageSize > 0)) {
			endResult = totalResult;
		} else {
			endResult = beginResult + pageSize;
		}
		return endResult;
	}

	public void setEndResult(int endResult) {
		this.endResult = endResult;
	}

	/*
	 * get to show
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/*
	 * set to calculate beginResult and total page
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/*
	 * get to show and to fetch data
	 */
	public int getPageSize() {
		return pageSize;
	}

	/*
	 * set to calculate beginResult and totalPage
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/*
	 * get to show
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/*
	 * get to show
	 */
	public int getTotalResult() {
		return totalResult;
	}

	/*
	 * set to calculate beginResult and totalPage
	 */
	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}

	public void calculate() {
		// calculate totalPage
		totalPage = totalResult / pageSize;
		if (totalResult % pageSize > 0)
			totalPage++;

		// set the currentPage
		if (currentPage <= 0) {
			currentPage = 1;
		}

		if (currentPage * pageSize > totalResult) {
			currentPage = totalPage;
		}

		// calculate the beginResult
		beginResult = (currentPage - 1) * pageSize;
		if (beginResult < 0)
			beginResult = 0;

		beginPage = currentPage - 2;

		endPage = beginPage + step;
		if (endPage >= totalPage) {
			endPage = totalPage;
			beginPage = endPage - step;
		}
		if (beginPage < 1) {
			beginPage = 1;
		}
	}

	public void clone(PageInfo source) {
		this.pageSize = source.getPageSize();
		this.currentPage = source.getCurrentPage();
		this.beginResult = source.getBeginResult();
		this.totalResult = source.getTotalResult();
		this.totalPage = source.getTotalPage();
	}

	public int getDispBeginResult() {
		if (totalResult != 0)
			dispBeginResult = beginResult + 1;
		return dispBeginResult;
	}

	public int getDispEndResult() {
		if (totalResult != 0)
			dispEndResult = endResult;
		if (currentPage < totalPage) {
			dispEndResult += pageSize;
		}
		if (currentPage == totalPage) {
			int modResult = totalResult % pageSize;
			if (modResult != 0)
				dispEndResult = beginResult + modResult;
		}
		return dispEndResult;
	}

	public int getBeginPage() {
		return beginPage;
	}

	public int getEndPage() {
		return endPage;
	}
}
