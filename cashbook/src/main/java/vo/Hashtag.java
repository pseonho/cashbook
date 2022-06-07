package vo;

public class Hashtag {
	private int cashBookNo;
	private String tag;
	
	
	public int getCashBookNo() {
		return cashBookNo;
	}
	public void setCashBookNo(int cashBookNo) {
		this.cashBookNo = cashBookNo;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	@Override
	public String toString() {
		return "Hashtag [cashBookNo=" + cashBookNo + ", tag=" + tag + "]";
	}
	
	
}