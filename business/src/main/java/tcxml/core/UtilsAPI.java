package tcxml.core;

public class UtilsAPI {
	
	TcXmlController controller;
	
	
	
	public UtilsAPI(TcXmlController tcXmlController) {
		
		
		controller = tcXmlController ;		
	
		
	}
	
	
	public void clearCookies( ) {
		
		controller.getLog().info(" clear cookies is not implemented now !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		
	}
	
	
	
	public void clearCache() {
		
		controller.getLog().info(" clear cache is not implemented now !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		
		
	}
	
	

}
