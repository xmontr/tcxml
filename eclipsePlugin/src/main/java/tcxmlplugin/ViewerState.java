package tcxmlplugin;

public enum ViewerState {
	
	RECORD("Recording"),
	PLAY("playing"),
	STOP("stopped"),
	PAUSED_PLAY("pause on breakpoint"),
	PAUSED_RECORD("recording paused");
	
	
	private String name;
	
	
	 ViewerState( String thename) {
		
		this.name=thename;
	}
	 
		public String getName() {
			return name;
		}
	

}
