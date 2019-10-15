package util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;

public class CharsetDetector {
	 public Charset detectCharset(File f, String[] charsets) {

	        Charset charset = null;

	        for (String charsetName : charsets) {
	            try {
					charset = detectCharset(f, Charset.forName(charsetName));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	            
	            
	            
	            if (charset != null) {
	                break;
	            }
	        }

	        return charset;
	    }

	    private Charset detectCharset(File f, Charset charset) throws IOException {
	        try {
	            BufferedInputStream input = new BufferedInputStream(new FileInputStream(f));

	            CharsetDecoder decoder = charset.newDecoder();
	            decoder.reset();

	            byte[] buffer = new byte[2048];
	            boolean identified = false;
	            while ((input.read(buffer) != -1)) {
	                identify(buffer, decoder);
	            }

	            input.close();
	            return charset ;

	        } catch (CharacterCodingException e) {
	            return null;
	        }
	    }

	    private void identify(byte[] bytes, CharsetDecoder decoder) throws CharacterCodingException {	    	
	    	
	    	
	    	 
	 
	       
	        decoder.decode(ByteBuffer.wrap(bytes));
	      
	
	    }

	    public static void main(String[] args) throws TcXmlException, FileNotFoundException {
	        File f = new File("c:\\trash\\default.xml");
	        
	        

	        String[] charsetsToBeTested = { "ISO-8859-1", "windows-1253", "ISO-8859-7","US-ASCII","UTF-16BE","UTF-16LE","UTF-16","UTF-8"};

	        CharsetDetector cd = new CharsetDetector();
	        Charset charset = cd.detectCharset(f, charsetsToBeTested);
	        System.out.println("found charset:" + charset.displayName());

	        if (charset != null) {
	           
	        	FileInputStream  in  = new FileInputStream(f);
	                
	                TcXmlController controller = new TcXmlController("essai");
	                controller.loadScript(controller.addExpectedNamespace("truScript", "xmlns=\"http://www.example.org/tcxml\"", in));
	                
	                
	                
	                

	    }
	        
	    }
}
	
	


