package tcxmlplugin;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;

import tcxmlplugin.nature.NatureTcXml;

public class TcXmlPluginController 

 {
	
	
	
	
    public static final String TESTCASE_FOLDER = "testcaseFolder";

	public static final String TESTCASE_ROOT_FOLDER = "testcaseRootFolder";

	private static final String TEST_CASES_PATH = "/Test Cases";

	private static final String TESTCASE_LIB = "testcaselib";

	private static TcXmlPluginController instance = null;

	



	private TcXmlPluginController() {
		
		
		
	}

    public static synchronized TcXmlPluginController getInstance() {
        if (instance == null) {
            instance = new TcXmlPluginController();
        }

        return instance;
    }
	
	/***
	 * 
	 *  create the project and linked structure in the workspace
	 * 
	 * 
	 * 
	 * 
	 * @param projectName  the name of the project to create
	 * @param monitor 
	 * @return 
	 * @throws CoreException
	 * @throws IOException 
	 */
	
    public IProject createSkeletonProject(String projectName, IProgressMonitor monitor) throws CoreException, IOException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IProject proj = this.getProjectByName(projectName);	
		
		//create project and structure
		proj.create(null, monitor);		
		proj.open(monitor);
		IProjectDescription description=proj.getDescription();
		
				
		String[] natures = description.getNatureIds();
	      String[] newNatures = new String[natures.length + 1];
	      System.arraycopy(natures, 0, newNatures, 0, natures.length);
	      newNatures[natures.length] = NatureTcXml.NATUREID;
	      description.setNatureIds(newNatures);
	      
	      proj.setDescription(description, null);
	      
	      //folder for test cases
	      IFolder tcfolder = getTestcaseFolder(proj);
	      tcfolder.create(true, true, monitor);
	      QualifiedName key = new QualifiedName("tcxmlplug", "folderType");
		tcfolder.setPersistentProperty(key , TESTCASE_ROOT_FOLDER);
	      

	      
	      
	      
	      return proj;
	      
	      
	}
    
    
    public boolean isTypeFolder( IFolder folder , String folderType){
    	
    	boolean ret= false;
    	 QualifiedName key = new QualifiedName("tcxmlplug", "folderType");
    	 String actualFolderType = null;
    try {
		 actualFolderType = folder.getPersistentProperty(key);
	} catch (CoreException e) {
		// TODO Auto-generated catch block
		error("exception when retrieving persistence properties", e);
	}
    
    if(actualFolderType == null) return false;
    
    	
    	if( actualFolderType != null &&  !  folderType.equals(actualFolderType) ){
    		ret = false;
    	} else {
    		
    		ret = true;
    	}
    	return ret;
    	
    	
    }
    
    
     
    
    

    
    
    
    
    
    
    
    
    
    
    

    
    
  
    
    
    
    




	public IProject getProjectByName( String projectName){
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IProject proj = root.getProject(projectName);
  	return (proj)  ;
  	  
    }
    
    
    /**
     * 
     *  test if a project named projectName already exist in the workspace
     * 
     * 
     * @param projectName
     * @return
     */
    public boolean isAlreadyExistingProject( String projectName){
    	
    	return this.getProjectByName(projectName).exists();
    	
    }
    
    /***
     * 
     *  tell if the the structure of the project allready exist ( the main.xml, the logfile of the firefox extension are created by the firefox extension the first time it is started
     *  in the specified directory )
     * 
     * @param project
     * @return
     */
    
    public boolean isNewEmptyTestCase(IFolder tc) {
    	boolean ret = true;
    	ret = tc.getFile("/main.xml").exists();  
    	info("isNewEmptyTestCase " +  tc.getName() + ":" +ret);
    	return ret;
    	
    	
    }
    
    
    

    /**
     * 
     *  create a new project project2create in the workspace and import the ressources ( main.xml and snapshots into the project.
     * 
     * 
     * @param project2create name of the new project
     * @param path2import absolute path to main.xml
     * @return
     * @throws CoreException 
     */
    
	public boolean importNewProject(String project2create, String path2import, IProgressMonitor monitor) {
		
		 try {
			 
			IProject newproject = this.createSkeletonProject(project2create , monitor);
			this.importMainFile(newproject , path2import,null);
	
			
			
		} catch (Exception e) {
			this.error("Exception during import", e);
		}
		return true;
	}
	

	


	/**
	 * 
	 *  import the main file from path2import
	 * 
	 * 
	 * @param newproject
	 * @param path2import
	 * @param monitor 
	 * @throws CoreException 
	 * @throws FileNotFoundException 
	 */
	
	private void importMainFile(IProject newproject, String path2import, IProgressMonitor monitor) throws CoreException, FileNotFoundException {
		IFile mainFile = newproject.getFile("main.xml");		
		File inputFile = new File(path2import);
		InputStream in = new FileInputStream(inputFile);
		mainFile.create(in, true, monitor);
		this.info("main.xml imported");
		
	}
	






	/***
	 * 
	 *  log the error message and associated exception
	 * 
	 * @param message
	 * @param ex
	 */
	public void error(String message, Throwable ex){
		Activator.getDefault().log(message, IStatus.ERROR, ex);
		
	}
	
	
	public void info(String message){
		Activator.getDefault().log(message, IStatus.INFO,null);
		
	}
	

	
	
	
	
	

	


	

	
	
	
	
	


	
	


	
	
	/**
	 * 
	 *  create a new Testcase in the project
	 * 
	 * @param project
	 * @param string 
	 */
	
	public void addTestCase2project(IProject project, String name) {
	IFolder	testcasefolder = getTestcaseFolder(project);
    IFolder newtc = testcasefolder.getFolder(name);
    IFolder libfolder = newtc.getFolder("Libraries");
	try {
		newtc .create(true, true, null);
		libfolder.create(true, true, null);
	    QualifiedName key = new QualifiedName("tcxmlplug", "folderType");
	    newtc.setPersistentProperty(key , TESTCASE_FOLDER);
	    libfolder.setPersistentProperty(key , TESTCASE_LIB);
		
		
	} catch (CoreException e) {
		error("exception when creating test case", e);
	}

		
	}

	private IFolder getTestcaseFolder(IProject project) {
		// TODO Auto-generated method stub
		return project.getFolder(TEST_CASES_PATH);
	}

	public boolean isAlreadyExistingTestCase(String el, IFolder targetFolder) {
		
		return targetFolder.getFolder(el).exists();
	}


/****
 * 
 *  delete project in the workspace .
 *  
 *     each montrigen  project is associated with a FF profile. this profile is deleted weh deleteing the project.
 * 
 * @param res
 * @throws ConfigurationException 
 */
	public void deleteProject(IProject res)   {




		
		
		
	}
	
	
	
	
	public IPath findParameterFile(String rootdir) {
		IPath ret = null;
		IPath mainpath = new Path(rootdir);
		String dirname = mainpath.lastSegment();
		IPath parampath = mainpath.append("/").append(dirname  + ".prm");
		if(parampath.toFile().exists()) {
			ret= parampath;
			
		}
		
		
		
		return ret;
	}

    
    
    public IPath findMainFile( String rootdir ) {
    	IPath ret = null;
    
   
    IPath mainpath = new Path(rootdir).append("/default.xml");
   if(mainpath.toFile().exists()) {
	   ret = mainpath; 
	   
   }
 
   return ret;
    	
    }
    
    
    public List<String> getLibraries (  String rootdir) {
    	
    	List<String> ret = new ArrayList<String>() ;
    IPath libdirpath = new Path(rootdir).append("/Libraries");	
    File libdir = new File(libdirpath.toOSString());
    File[] libfiles = libdir.listFiles();
    for (File file2 : libfiles) {
 
 
 ret.add(file2.getAbsolutePath());
    	
    	
    }
return ret;
    }
	

	
	
	
	

}