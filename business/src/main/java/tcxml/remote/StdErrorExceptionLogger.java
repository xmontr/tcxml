package tcxml.remote;

import org.apache.http.ExceptionLogger;

public class StdErrorExceptionLogger implements ExceptionLogger  {

	@Override
	public void log(Exception ex) {
		System.out.println("---------- " + ex.getMessage());
		//ex.printStackTrace();
		
	}

}
