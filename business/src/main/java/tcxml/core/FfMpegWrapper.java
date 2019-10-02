package tcxml.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.Line;

import org.apache.commons.lang.StringEscapeUtils;

public class FfMpegWrapper {
	
	
	private Path ffmpeg ;
	private Process currentProcess;

	public FfMpegWrapper(Path ffmpeg) {
		super();
		this.ffmpeg = ffmpeg;
	}
	
	
	
	public void startRecord( String windowTitle, String executableName, File  outputfile) throws TcXmlException {
		
		
		List<String> command = buildCommand(windowTitle, executableName, outputfile.getName());
		ProcessBuilder builder = new ProcessBuilder(command);
		
 builder.redirectErrorStream(false) ;
 builder.directory(outputfile.getParentFile());

	if(outputfile.exists()) {
		outputfile.delete();
		
	}else {
		
		
		
	}

	StringBuffer bufferError = new StringBuffer();
		try {
			
			System.out.println( " *************command line used***********************");
			System.out.println(builder.command());
			currentProcess = builder.start();
			
			BufferedReader output = new BufferedReader(new InputStreamReader(currentProcess.getErrorStream() ) );
			
			String line =null;
			do {
			 line = output.readLine() ;
			
			bufferError.append(line);
			 
			} 
			while( line != null) ;
			
		
		} catch (IOException e) {
		throw new TcXmlException("failure when starting video recording process", e);
			
		}
		
	
	if( bufferError.length() > 0)	{
		
		//throw new TcXmlException("failure in videorecorder: " +bufferError.toString(), new IllegalStateException());
	}
		
		
		
	}
	
	
	private List<String> buildCommand(String windowTitle, String executableName, String  outputfile) {
		List<String> ret = new ArrayList<String>();
		// path to exe
		ret.add(this.ffmpeg.toString());
		//format for input
		ret.add("-f");ret.add("gdigrab"); // format for windows
		//set frame rate (Hz value, fraction or abbreviation)
		ret.add("-r");ret.add("30");
		// window tiltle of the window to record
		ret.add("-i");
		
		//.replace(" ", (char) 92 + " ")
		ret.add("title=" + windowTitle  );
		// output file for the video
		ret.add(outputfile);		
		return ret;
	}



	public void stopRecord() throws TcXmlException {
		
		if(currentProcess == null) {
			
			throw new TcXmlException("failure in stopping video recording proces : no process found", new IllegalStateException());
		}
		
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(currentProcess.getOutputStream()));
		
			
		writer.write("q\n");
		writer.flush();
		writer.close();
		
		System.out.println("stoping video recorder ...");
		
		
	}
	

}



