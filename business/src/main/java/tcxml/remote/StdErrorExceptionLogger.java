package tcxml.remote;

import org.apache.http.ExceptionLogger;

public class StdErrorExceptionLogger implements ExceptionLogger  {

	@Override
	public void log(Exception ex) {
		System.out.println("---------- " + ex.getMessage());
		if(! ( ex instanceof org.apache.http.ConnectionClosedException) )
	ex.printStackTrace();
		
	}

}
