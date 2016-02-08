package exception;

@SuppressWarnings("serial")
public class MissOptionName extends Exception{

	private String errormsg;
	private int errorno;

	public MissOptionName(int errorno,String errormsg){
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