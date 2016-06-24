package cn.com.gome.manage.pageSupport;

public class PageInfoFactory {
	//default pageSize is 10
	public static final int DefaultPageSize = 10;
//	static{
//		int pageSize=10;//default pageSize is 10
//		try {
//			pageSize=Properties.getInstance().getIntValue("pagesize");
//		} catch (ConfigurationException e) {
//		}
//		DefaultPageSize=pageSize;
//	}
	/**
	 * 指定需要的具体页码，页尺寸通过Properties或得
	 * @param pageNo int
	 * @return PageInfo  
	 */
	public static PageInfo getPageInfo(int pageNo){
		return new PageInfo(DefaultPageSize,pageNo);
	}
	/**
	 * 指定需要的具体页码和页尺寸
	 * @param pageSize int
	 * @param pageNo int
	 * @return PageInfo
	 */
	public static PageInfo getPageInfo(int pageSize,int pageNo){
		return new PageInfo(pageSize,pageNo);
	}
}
