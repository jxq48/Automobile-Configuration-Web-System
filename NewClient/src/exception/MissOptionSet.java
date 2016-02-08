package exception;

@SuppressWarnings("serial")
public class MissOptionSet extends Exception{

	private String errormsg;
	private int errorno;

	public MissOptionSet(int errorno,String errormsg){
		super();
		this.errorno = errorno;
		this.errormsg = errormsg;
	}
	
	public String getErrormsg() {
		return errormsg;
	}
	public int getErrorno() {
		return errorno;
	}

}