package tcxmlplugin;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;

import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxmlplugin.composite.ActionView;
import tcxmlplugin.composite.ActionsViewer;
import tcxmlplugin.composite.LibraryView;
import tcxmlplugin.composite.LibraryViewer;
import tcxmlplugin.composite.RunLogicViewer;
import tcxmlplugin.composite.TcViewer;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxml.model.ImportModel;
import tcxml.model.TruLibrary;
import tcxmlplugin.nature.NatureTcXml;
import util.TcxmlUtils;

public class TcXmlPluginController 

 {
	
	
	
	
    public static final String TESTCASE_FOLDER = "testcaseFolder";

	public static final String TESTCASE_ROOT_FOLDER = "testcaseRootFolder";

	private static final String TEST_CASES_PATH = "/Test Cases";

	private static final String TESTCASE_LIB = "testcaselib";
	
	
	private static final String TESTCASE_SNAPSHOT = "testcasesnapshot";

	private static TcXmlPluginController instance = null;
	
	
	private TcViewer tcviewer;

	private Properties properties;
	
	
	private Object currentBreakPoint;



	public Object getCurrentBreakPoint() {
		return currentBreakPoint;
	}

	public void setCurrentBreakPoint(Object currentBreakPoint) {
		this.currentBreakPoint = currentBreakPoint;
	}
	
	
	
	
	public boolean isOnBreakpoint() {
		boolean ret = false;
		if(currentBreakPoint != null) {
			ret = true;
			
			
		}
		
		return ret;
		
	}
	
	
	
	public void releaseBreakpoint() {
		
		synchronized (currentBreakPoint) {
			currentBreakPoint.notify();
			info("releasing breakpoint");
		}
		
	}
	

	public TcViewer getTcviewer() {
		return tcviewer;
	}

	public void setTcviewer(TcViewer tcviewer) {
		this.tcviewer = tcviewer;
	}

	private TcXmlPluginController()  {
		
		  URL url = getClass().getResource("/config.properties");
		 properties = new Properties();
		 try {
			
			 InputStream in = url.openStream();
			 properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
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
    

	

	


	/**
	 * 
	 *  import the  file from path2import to the root of the project
	 * 
	 * 
	 * @param newproject
	 * @param testCaseFolder 
	 * @param path2import
	 * @param monitor 
	 * @throws CoreException 
	 * @throws FileNotFoundException 
	 */
	
	private void importFile(IProject newproject, IFolder testCaseFolder, String path2import, IProgressMonitor monitor) throws CoreException, FileNotFoundException {
		
		String filename = new Path(path2import).lastSegment();		
		
		IFile thefile = testCaseFolder.getFile(filename);		
		File inputFile = new File(path2import);
		InputStream in = new FileInputStream(inputFile);
		thefile.create(in, true, monitor);
		this.info("import " + filename +  " ok ");
		
		
	}
	
	
	
	private void importFileAndRename(IProject newproject, IFolder testCaseFolder, String path2import, IProgressMonitor monitor, String newName) throws CoreException, FileNotFoundException  {
		
			
		
		IFile thefile = testCaseFolder.getFile(newName);		
		File inputFile = new File(path2import);
		InputStream in = new FileInputStream(inputFile);
		thefile.create(in, true, monitor);
		this.info("import " + newName +  " ok ");
		
		
		
		
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
		if(tcviewer!= null) {
		tcviewer.displayStatus(message);
		}
		
	}
	

	
	
	
	
	

	


	

	
	
	
	
	


	
	


	
	
	/**
	 * 
	 *  create a new Testcase in the project
	 * 
	 * @param project
	 * @param string 
	 * @return 
	 */
	
	public IFolder addTestCase2project(IProject project, String name) {
	IFolder	testcasefolder = getTestcaseFolder(project);
    IFolder newtc = testcasefolder.getFolder(name);
    IFolder libfolder = newtc.getFolder("Libraries");
    IFolder snapshotfolder= newtc.getFolder("snapshots");
   
	try {
		newtc .create(true, true, null);
		libfolder.create(true, true, null);
		snapshotfolder.create(true, true, null);
		
		
	    QualifiedName key = new QualifiedName("tcxmlplug", "folderType");
	    newtc.setPersistentProperty(key , TESTCASE_FOLDER);
	    libfolder.setPersistentProperty(key , TESTCASE_LIB);
	    snapshotfolder.setPersistentProperty(key, TESTCASE_SNAPSHOT);
	return    newtc	;
		
	} catch (CoreException e) {
		error("exception when creating test case", e);
		return null;
	}

		
	}

	private IFolder getTestcaseFolder(IProject project) {
		// TODO Auto-generated method stub
		return project.getFolder(TEST_CASES_PATH);
	}
	
	private IFolder getLibraryFolder(IFolder testcase) {
		
		 IFolder libfolder = testcase.getFolder("Libraries");
		 return libfolder;
		
		
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
	
	
	/***
	 * 
	 *  return the path of the .prm file and path of  all associated parameters of type 'file' included inside
	 * 
	 * @param rootdir
	 * @return
	 * @throws TcXmlPluginException 
	 */
	
	public List<String> findParameterFiles(String rootdir) throws TcXmlPluginException {
		List<String> ret =new ArrayList<String>() ;
		IPath mainpath = new Path(rootdir);
		String dirname = mainpath.lastSegment();
		IPath parampath = mainpath.append("/").append(dirname  + ".prm");
		File paramfile = parampath.toFile() ;
		
		if(paramfile.exists()) {
			ret.add(parampath.toOSString()) ;
		try {
		List<String> li = TcxmlUtils.listFilesinParameterfiles(paramfile);
		for (String foundedfile : li) {
			ret.add(foundedfile);
			
		}
			
			
			
		} catch (TcXmlException e) {
			throw new TcXmlPluginException("failure when processing parameter file " + parampath, e);
			
		
		}
			
		}
		
		
		
		return ret;
	}

    
    
    public IPath findMainFile( String rootdir ) throws TcXmlPluginException {
    	IPath ret = null;
    
   
    IPath mainpath = new Path(rootdir).append("/default.xml");
   if(mainpath.toFile().exists()) {
	   ret = mainpath; 
	   
   }else throw new TcXmlPluginException("fail to find default.xml infolder " + rootdir, new FileNotFoundException()
		   );
 
   return ret;
    	
    }
    
    
    public List<String> getLibraries (  String rootdir) {
    	
    	List<String> ret = new ArrayList<String>() ;
    IPath libdirpath = new Path(rootdir).append("/Libraries");	
    File libdir = new File(libdirpath.toOSString());
    if(libdir.exists()) {
        File[] libfiles = libdir.listFiles();
        for (File file2 : libfiles) {
     
     
     ret.add(file2.getAbsolutePath());
        	
        	
        }	
    
    }

return ret;
    }

	public void importTestcase(String tcname, ImportModel model, IProject currentProject, IFolder testCaseFolder, IProgressMonitor monitor) throws FileNotFoundException, CoreException, TcXmlException {
		
		// create the folder structure
		IFolder newTC = addTestCase2project(currentProject, tcname);
		File source = new File(model.getMainScript());
		 //File withNS  = TcxmlUtils.addNameSpaceToXmlFile(source); adding namespace is done on the fly
		importFileAndRename(currentProject, newTC , source.getAbsolutePath(), monitor,source.getName());
		
		
		
		importParameters( model.getParameters(),  currentProject,  newTC,monitor);
		importLibrary( model.getLibraries(),  currentProject,  newTC,monitor);
		importSnapshot(model.getSnapshots(),currentProject,  newTC,monitor);
		importExtraFiles(model.getExtrafiles(), currentProject, newTC, monitor);
		
	}



	

	private void importSnapshot(List<String> snapshots, IProject currentProject, IFolder testCaseFolder,
			IProgressMonitor monitor) throws FileNotFoundException, CoreException {
		
	for (String snap : snapshots) {
			
			IFolder libsnapshot = getSnapshotFolder(testCaseFolder);
			
			String filename = new Path(snap).lastSegment();
			
			File source = new File(snap);
		 
	
			
			
			
			IFile thefile = libsnapshot.getFile(filename);		
			
			InputStream in = new FileInputStream(source);
			thefile.create(in, true, monitor);
			this.info("snapshot " + filename + " imported");
			
			
			
		}		
	}
	
	
	private void importExtraFiles(List<String> snapshots, IProject currentProject, IFolder testCaseFolder,
			IProgressMonitor monitor) throws FileNotFoundException, CoreException {
		
	for (String snap : snapshots) {
			
			IFolder extrapathfolder = testCaseFolder;
			
			String filename = new Path(snap).lastSegment();
			
			File source = new File(snap);
		 
	
			
			
			
			IFile thefile = extrapathfolder.getFile(filename);		
			
			InputStream in = new FileInputStream(source);
			thefile.create(in, true, monitor);
			this.info("extra file " + filename + " imported");
			
			
			
		}		
	}
	
	
	
	
	
	

	private IFolder getSnapshotFolder(IFolder testCaseFolder) {
		 IFolder libfolder = testCaseFolder.getFolder("snapshots");
		 return libfolder;
	}

	private void importLibrary(List<String> libraries, IProject currentProject, IFolder testCaseFolder,
			IProgressMonitor monitor) throws FileNotFoundException, CoreException, TcXmlException {
		
		for (String lib : libraries) {
			
			IFolder libfolder = getLibraryFolder(testCaseFolder);
			
			String filename = new Path(lib).lastSegment();
			
			File source = new File(lib);
			// File withNS  = TcxmlUtils.addNameSpaceToXmlFile(source); 
	
			
			
			
			IFile thefile = libfolder.getFile(filename);		
			
			InputStream in = new FileInputStream(source);
			thefile.create(in, true, monitor);
			this.info("library " + filename + " imported");
			
			
			
		}
		
		

		
		
	}

	private void importParameters(List<String> parameters, IProject currentProject, IFolder testCaseFolder,IProgressMonitor monitor) throws FileNotFoundException, CoreException {
		for (String fileparam : parameters) {
			importFile(currentProject,testCaseFolder, fileparam, monitor);
			
		}
		
		
	
		
	}

	public Properties getProperties() {
		return properties;
	}

	public List<String> getSnapshots(String selectedDirectory) {
    	List<String> ret = new ArrayList<String>() ;
    IPath libdirpath = new Path(selectedDirectory).append("/snapshots");	
    File libdir = new File(libdirpath.toOSString());
    if(libdir.exists()) {
        File[] libfiles = libdir.listFiles();
        for (File file2 : libfiles) {
     
     
     ret.add(file2.getAbsolutePath());
        	
        	
        }	
    
    }

return ret;
	}


	
	/** ceeate SWT image from an absolute path 
	 * 
	 * 
	 * 
	 * @param absolutepath
	 * @param display
	 * @return
	 * @throws TcXmlException
	 */
	
	
	
public Image createImage(String absolutepath, Display display) throws TcXmlException {
	
	File f = new File(absolutepath);
	
	FileInputStream in;
	try {
		in = new FileInputStream(f);
	} catch (FileNotFoundException e) {
		throw new TcXmlException("failure finding  snapshot" , e);
	}
	Image img = new Image(display, in);
	return img;
	
	
	
	
}

/**
 * 
 *  export as protractor script the step list
 * 
 * @param runLogicViewer
 * @param libraryViewer 
 * @param actionsViewer 
 */

	public void export(RunLogicViewer runLogicViewer, LibraryViewer libraryViewer, ActionsViewer actionsViewer) {
		
		TcXmlController controller = tcviewer.getController();
		//create temporary directory
		try {
		java.nio.file.Path exportPath = Files.createTempDirectory("protractor-");
		controller.getLog().info("tempdirectory for export :" + exportPath.toString() );
		
		HashMap<String,File> linkedLib = exportLibraries(libraryViewer,exportPath);		
		File target = exportPath.resolve("protractor.js").toFile();				
target.createNewFile();
		FileOutputStream out;
	
			out = new FileOutputStream(target);
			PrintWriter pw = new PrintWriter(out);
			exportSymbols(pw,linkedLib);
			exportActions(pw , actionsViewer);
			for (StepViewer stepViewer : runLogicViewer.getChildViewer()) {
				stepViewer.export(pw);
			}
			pw.flush();
			pw.close();
			out.flush();
			out.close();
		
		
		
		
		
		
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
		error("fail to create temporary export directory", e1);
		} catch (TcXmlException e) {
			// TODO Auto-generated catch block
			error("fail to export current script ", e);
		}
		

			
			
	
		
		
		
	}
	
	private void exportActions(PrintWriter pw, ActionsViewer actionsViewer) throws TcXmlException {
		{
			
		Map<String, ActionView> listview = actionsViewer.getActionsView();	
		Collection<ActionView> val = listview.values();
		for (ActionView actionView : val) {
				actionView.eexport(pw);
			
		}
		
			
		}
	
}

	/**
	 * 
	 *  write the require line that import symbole in the js script  i.e var {toto} = require ('toto');
	 * 
	 * 
	 * @param pw
	 * @param linkedLib 
	 */

private void exportSymbols(PrintWriter pw, HashMap<String, File> linkedLib) {
	// TODO Auto-generated method stub
	
}

private HashMap<String, File> exportLibraries(LibraryViewer libraryViewer, java.nio.file.Path exportPath) {
	HashMap<String, File> ret = new HashMap<>() ;
	
	
	
	Map<String, LibraryView> libviews = libraryViewer.getLibrariesView();
	
	Set<String> libnames = libviews.keySet();
	for (String libname : libnames) {
		File exportedLib = exportLib(  exportPath , libname  , libviews.get(libname) ) ;
		ret.put(libname, exportedLib);
	}
	
	
	return ret;
}

private File exportLib(java.nio.file.Path exportPath, String libname, LibraryView libraryView) {
	File ret = null;
	try {
	
	java.nio.file.Path targetFileName = exportPath.resolve(libname + ".js");
	 ret = targetFileName.toFile() ;
	ret.createNewFile();
	FileOutputStream out;
	
	out = new FileOutputStream(ret);
	PrintWriter pw = new PrintWriter(out);
	List<StepViewer> li = libraryView.getChildViewer();
	for (StepViewer stepViewer : li) {
		
		stepViewer.export(pw);
		}
		pw.flush();
		pw.close();
		out.flush();
		out.close();
	
	
	
	
	
	
	} catch (IOException e) {
		// TODO Auto-generated catch block
	error("fail to export library "+libname, e);
	} catch (TcXmlException e) {
		error("fail to export stepviewer in library" + libname, e);
	}
	
	return ret;
}

public List<String> getExtraFiles(String selectedDirectory) throws TcXmlPluginException {
	List<String> ret =new ArrayList<String>() ;
	IPath mainpath = new Path(selectedDirectory);
	String dirname = mainpath.lastSegment();
	IPath usrpath = mainpath.append("/").append(dirname  + ".usr");
	File usrfile = usrpath.toFile() ;
	
	if(usrfile.exists()) {
		
	try {
	List<String> li = TcxmlUtils.listExtraFilesinUsrfiles(usrfile);
	for (String foundedfile : li) {
		ret.add(foundedfile);
		
	}
		
		
		
	} catch (TcXmlException e) {
		throw new TcXmlPluginException("failure when processing usr file " + usrpath, e);
		
	
	}
		
	}
	
	
	
	return ret;
}
	
	

	
	
	
	

}