package tcxml.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.DurationFormatUtils;

import util.TcxmlUtils;



public class FfMpegWrapper {
	
	
	private Path ffmpeg ;
	private Process currentProcess;
	
	boolean isRecording = false ;
	private LocalTime startVideoTime;
	private PrintWriter subtitleprintwriter;
	private int subtitleSectionNumber = 1 ;
	private DateTimeFormatter dateTimeFormater;
	private String format;

	private File currentRecordedFile;

	public FfMpegWrapper(Path ffmpeg) {
		super();
		this.ffmpeg = ffmpeg;
		this.format = "HH:mm:ss,S" ;
		dateTimeFormater = DateTimeFormatter.ofPattern(this.format);
	}
	
	
	
	public void startRecord( String windowTitle, String executableName, File  outputfile) throws TcXmlException {
		
		
 setrecordedFile(outputfile);
		
		List<String> command = buildRecordCommand(windowTitle, executableName, outputfile.getName());
		ProcessBuilder builder = new ProcessBuilder(command);
		
		
		System.out.println("********* local for the process builder *****:" +builder.environment().get("LANG") ) ;
		;
		builder.environment().put("LANG", "en_EN.UTF-8");
		
File subtitlefile = createSubTitleFile(outputfile)	;	

 try {
	 subtitleprintwriter = createSubtitleWriter(subtitlefile);
} catch (IOException e1) {
throw new TcXmlException("failure in subtitle management" , e1) ;
}
		
 builder.redirectErrorStream(false) ;
 builder.directory(outputfile.getParentFile());

	if(outputfile.exists()) {
		outputfile.delete();
		
	}else {
		
		
		
	}

	StringBuffer bufferError = new StringBuffer();
		try {
			
			System.out.println( " *************ffmpeg line used***********************");
			System.out.println(builder.command());
			currentProcess = builder.start();
			
			startVideoTime = LocalTime.now() ;
			
			
			isRecording = true ;
			
			BufferedReader output = new BufferedReader(new InputStreamReader(currentProcess.getErrorStream() ) );
			
			String line =null;
			do {
			 line = output.readLine() ;
			
			bufferError.append(line + System.lineSeparator());
			 
			} 
			while( line != null) ;
			
		
		} catch (IOException e) {
		throw new TcXmlException("failure when starting video recording process", e);
			
		}
		
	
	if( bufferError.length() > 0)	{
		
		System.out.println("*** ffmpeg error **************************************");
		System.out.println(bufferError.toString());
		
		//throw new TcXmlException("failure in videorecorder: " +bufferError.toString(), new IllegalStateException());
	}
		
		
		
	}
	
	
	private void setrecordedFile(File outputfile) {
		this.currentRecordedFile = outputfile;
		
	}







	private PrintWriter createSubtitleWriter(File subtitlefile) throws IOException {
	FileInputStream in = new FileInputStream(subtitlefile) ;
	
	
	PrintWriter pw = new PrintWriter(subtitlefile);	
		return pw ;
	}



	private File createSubTitleFile(File outputfile)  throws TcXmlException{
		File srtfile = null;
		try {

Path srtfilepath = Paths.get(outputfile.getParent()).resolve("video.srt") ;
 srtfile = new File(srtfilepath.toUri()) ;
if( srtfile.exists()) {
	
	srtfile.delete();
	srtfile.createNewFile();
}else {
	
	
		srtfile.createNewFile();
	} 
		}catch (IOException e) {
		// TODO Auto-generated catch block
		throw new TcXmlException("failure in subtitle file management", e);
	}
		return srtfile;		
		
}


		
	



	private List<String> buildRecordCommand(String windowTitle, String executableName, String  outputfile) {
		List<String> ret = new ArrayList<String>();
		// path to exe
		ret.add(this.ffmpeg.toString());
		//format for input
		ret.add("-f");ret.add("gdigrab"); // format for windows
		//set frame rate (Hz value, fraction or abbreviation)
		ret.add("-r");ret.add("60");
		// window tiltle of the window to record
		ret.add("-i");
		
		//.replace(" ", (char) 92 + " ")
		ret.add("title=" + windowTitle  );
		//timestamp for thr video
		// YUV chroma subsampling for firefox compatibility
		ret.add("-pix_fmt");ret.add("yuv420p");
		ret.add("-vf");ret.add("pad=\"width=ceil(iw/2)*2:height=ceil(ih/2)*2\"");

		// output file for the video
		ret.add(outputfile);		
		return ret;
	}



	public void stopRecord() throws TcXmlException {
		
		if(currentProcess == null) {
			
			throw new TcXmlException("failure in stopping video recording proces : no process found", new IllegalStateException());
		}
		
		isRecording = false ;
		
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(currentProcess.getOutputStream()));
		
			
		writer.write("q\n");
		writer.flush();
		writer.close();
		
		System.out.println("stoping video recorder ...");
		
		subtitleprintwriter.flush();
		
	
	
	burnSubtitles(this.currentRecordedFile);	
	
	setrecordedFile(null);
		
	}
	
	
	
	public void  burnSubtitles(File inputputfile)  throws TcXmlException {
		
		File subtitledFile = buildSubtitledFile(inputputfile);
		
		List<String> command = builGenerateSubtitleCommand(inputputfile.getName() , subtitledFile.getName());
		ProcessBuilder builder = new ProcessBuilder(command);
		

		
		

				
		 builder.redirectErrorStream(false) ;
		 builder.directory(subtitledFile.getParentFile());

			if(subtitledFile.exists()) {
				subtitledFile.delete();
				
			}else {
				
				
				
			}

			StringBuffer bufferError = new StringBuffer();
				try {
					
					System.out.println( " *************ffmpeg line used for subtitle management ***********************");
					System.out.println(builder.command());
					Process subtitleprocess = builder.start();					
			
					
					BufferedReader output = new BufferedReader(new InputStreamReader(subtitleprocess.getErrorStream() ) );
					
					String line =null;
					do {
					 line = output.readLine() ;
					
					bufferError.append(line+System.lineSeparator());
					 
					} 
					while( line != null) ;
					
				
				} catch (IOException e) {
				throw new TcXmlException("failure when starting video recording process", e);
					
				}
				
			
			if( bufferError.length() > 0)	{
				
				System.out.println("*** ffmpeg error **************************************");
				System.out.println(bufferError.toString());
				
			
			}
		
		
		
		
		
		
		
		
	}



	private List<String> builGenerateSubtitleCommand(String inputFimename, String outputfilename) {
		// ffmpeg.exe -i testApi.mp4 -f srt -i video.srt  -c:v copy  -c:s mov_text  testApi-subtitled.mp4	
		
		List<String> ret = new ArrayList<String>();
		// path to exe
		ret.add(this.ffmpeg.toString());
		// src video without subtitles
		ret.add("-i");ret.add(inputFimename); 
		//set format off subtitle src
		ret.add("-f");ret.add("srt");
		// src file for subtitle
		ret.add("-i");ret.add("video.srt");
		
		// copy video
		ret.add("-c:v");ret.add("copy");
		//copy sub title 
		ret.add("-c:s");ret.add("mov_text");

		// output file for the video with subtitle
		ret.add(outputfilename);		
		return ret;
	}



	
	
	
	
	private File buildSubtitledFile(File inputputfile) throws TcXmlException {
		String namewithoutext = TcxmlUtils.stripExtension(inputputfile.getName()) ;
		
		String finalname= namewithoutext +"-subtitled.mp4" ;

		File srtfile = null;
		try {

Path srtfilepath = Paths.get(inputputfile.getParent()).resolve(finalname) ;
 srtfile = new File(srtfilepath.toUri()) ;
if( srtfile.exists()) {
	
	srtfile.delete();
	srtfile.createNewFile();
}else {
	
	
		srtfile.createNewFile();
	} 
		}catch (IOException e) {
		// TODO Auto-generated catch block
		throw new TcXmlException("failure in subtitle file management", e);
	}
		return srtfile;	
		
	}



	public boolean isRecording() {
		return isRecording;
	}
	

	
	public void addSubtitle( LocalTime t, String text, long interva) throws TcXmlException {
		
		if( ! isRecording) {
			
			throw new TcXmlException("video recording is not strated", new IllegalStateException()) ;
		}
		 Duration relativeTime = Duration.between(startVideoTime, t);
		String start =  DurationFormatUtils.formatDuration(relativeTime.toMillis(), this.format);
		
		
		String stop =  DurationFormatUtils.formatDuration(relativeTime.toMillis() + interva, this.format);
		 
		
		subtitleprintwriter.println(subtitleSectionNumber);
		subtitleSectionNumber++;
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(start);
		buffer.append(" --> ").append(stop);
		subtitleprintwriter.println(buffer);
		subtitleprintwriter.println(text);
		subtitleprintwriter.println();
		
		
		
	}
	
	
}



