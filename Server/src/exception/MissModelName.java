package exception;

@SuppressWarnings("serial")
public class MissModelName extends Exception{

	private String errormsg;
	private int errorno;

	public MissModelName(int errorno,String errormsg){
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