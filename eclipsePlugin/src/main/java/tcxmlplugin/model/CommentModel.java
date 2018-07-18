package tcxmlplugin.model;

public class CommentModel extends AbstractModel {
	
	
	
	private String comment;
	
	
	
	public CommentModel( ) {
		super();
		
		
	}
	
	

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		propertyChangeSupport.firePropertyChange("comment", this.comment,
				this.comment = comment);
		
	}
	
	
	
	
	
	

}
