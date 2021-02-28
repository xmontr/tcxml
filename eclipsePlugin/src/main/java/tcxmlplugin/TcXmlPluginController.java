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
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import javax.json.JsonObject;
import javax.sound.midi.ControllerEventListener;

import org.apache.commons.lang.StringEscapeUtils;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.SessionId;

import com.kscs.util.jaxb.BoundList;

import stepWrapper.AbstractStepWrapper;
import stepWrapper.StepWrapperFactory;
import stepWrapper.TestObjectWrapper;
import tcxml.core.FfMpegWrapper;
import tcxml.core.StepFactory;
import tcxml.core.TcXmlController;
import tcxml.core.TcXmlException;
import tcxml.core.parameter.StepParameter;
import tcxml.core.parameter.TableParameter;
import tcxml.core.parameter.StepParameter.StepParameterType;
import tcxmlplugin.composite.ActionView;
import tcxmlplugin.composite.ActionsViewer;
import tcxmlplugin.composite.LibraryView;
import tcxmlplugin.composite.LibraryViewer;
import tcxmlplugin.composite.RunLogicViewer;
import tcxmlplugin.composite.TcViewer;
import tcxmlplugin.composite.VideoRecorderComposite;
import tcxmlplugin.composite.stepViewer.StepViewer;
import tcxml.model.Ident;
import tcxml.model.ImportModel;
import tcxml.model.Step;
import tcxml.model.TestObject;
import tcxml.model.TruLibrary;
import tcxml.remote.Express;
import tcxml.remote.RecordingSessionListener;
import tcxmlplugin.nature.NatureTcXml;
import util.TcxmlUtils;

public class TcXmlPluginController 

 {
	
	
	
	
    public static final String TESTCASE_FOLDER = "testcaseFolder";

	public static final String TESTCASE_ROOT_FOLDER = "testcaseRootFolder";

	private static final String TEST_CASES_PATH = "/Test Cases";

	private static final String TESTCASE_LIB = "testcaselib";
	
	
	public FfMpegWrapper getCurrentVideoRecorder() {
		return currentVideoRecorder;
	}






	private static final String TESTCASE_SNAPSHOT = "testcasesnapshot";
	
	private static final String TESTCASE_VIDEOS = "testcasevideo";

	private static TcXmlPluginController instance = null;
	
	
	private TcViewer tcviewer;

	private Properties properties;
	
	
	private Object currentBreakPoint;

	private FfMpegWrapper currentVideoRecorder;

	private ChromeDriverService chromeService;
	
	private RecordingSessionListener currentRecordingSessionListener ;

	private Express express;



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
		getTcviewer().setState(ViewerState.PLAY);
		
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
		 
		 String path2chrome = Activator.getDefault().getPreferenceStore().getString(tcxmlplugin.composite.preference.TcXmlPreference.PATH2CHROME);	 
		 chromeService = new ChromeDriverService.Builder()
				    .usingDriverExecutable(new File(path2chrome))
				    .usingAnyFreePort()
				    .build();
				try {
					chromeService.start();
					
					
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 
		 
		 
		
	}
	
	public  void openBrowser() {
		
		String ctx = "";
		
		
		String path2chrome = Activator.getDefault().getPreferenceStore().getString(tcxmlplugin.composite.preference.TcXmlPreference.PATH2CHROME);
		try {
			//tccontroller.openBrowser("firefox", path2chrome);
		 SessionId newsession = tcviewer.getController().openChromeBrowserBrowser ( TcXmlPluginController.getInstance().getChromeService());
		 
			info("chrome driver is listenning on " + tcviewer.getController().getDriverUrl()  + " selenium session =" + newsession.toString());
			
			
			this.express = new Express(9999,ctx, chromeService.getUrl(), Optional.ofNullable(newsession));
			
		} catch (TcXmlException e) {
			TcXmlPluginController.getInstance().error("Fail to open browser", e);
		}
		
	}
	
	
	public void closeBrowser() {
		
		tcviewer.getController().closeBrowser();	
		
		tcviewer.getController().dispose();
		this.express=null;
	}
	
	
	
	
	
	

    public ChromeDriverService getChromeService() {
		return chromeService;
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
		
		if(!thefile.exists()) { // 2parameters can point on the same file
			InputStream in = new FileInputStream(inputFile);
			thefile.create(in, true, monitor);
			this.info("import " + filename +  " ok ");
			
		}

		
		
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
		Activator.getDefault().log(message + " " + ex.getMessage(), IStatus.ERROR, ex);
		
	}
	
	
	public void info(String message){
		Activator.getDefault().log(message, IStatus.INFO,null);
		if(tcviewer!= null && !tcviewer.isDisposed()) {
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
    IFolder videofolder = newtc.getFolder("videos");
   
	try {
		newtc .create(true, true, null);
		libfolder.create(true, true, null);
		snapshotfolder.create(true, true, null);
		videofolder.create(true, true, null);
		
		
	    QualifiedName key = new QualifiedName("tcxmlplug", "folderType");
	    newtc.setPersistentProperty(key , TESTCASE_FOLDER);
	    libfolder.setPersistentProperty(key , TESTCASE_LIB);
	    snapshotfolder.setPersistentProperty(key, TESTCASE_SNAPSHOT);
	    videofolder.setPersistentProperty(key, TESTCASE_VIDEOS);
	    
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
	
	/**
	 * 
	 *  store the model to the disk
	 * 
	 * @throws TcXmlPluginException
	 */
	public void saveModel() {
		
		
	Job j = new Job("save model default.xml and all ibname.xml") {
			
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				
				IStatus ret = Status.OK_STATUS;
				try {
					
					// manage default.xml
					TcXmlPluginController.getInstance().SynchronizeMainFile(monitor);
					TcXmlPluginController.getInstance().saveMainFile(monitor);
					
					// manage libraries
					TcXmlPluginController.getInstance().synchronizeAllLibraries(monitor);
					TcXmlPluginController.getInstance().saveAllLibraries(monitor);
					
					
				} catch (Exception e) {
					//
					TcXmlPluginController.getInstance().error(e.getMessage(), e);	
					
				
					ret = Status.CANCEL_STATUS;
				}
				
				finally {
					monitor.done();
					
				}
				return ret;
			}
		};
		
		
			j.schedule();
			
			
			
		
		
		
	}

    
    
    private void saveAllLibraries(IProgressMonitor monitor) throws TcXmlPluginException {
		Set<String> libnames = getTcviewer().getAllLibrariesNames();
		for (Iterator iterator = libnames.iterator(); iterator.hasNext();) {
			String libname = (String) iterator.next();
			saveLibrary(monitor, libname);
		}
		
		
		
	}
    /***
     *  make a backup athe file toto.ext in toto-back.ext in the same folder. old backup is deleted
     * 
     * 
     * @param monitor
		 @param thefile 
     * @throws TcXmlPluginException
     */
    
    private void backup(IProgressMonitor monitor, IFile thefile) throws TcXmlPluginException{
		try {
    	IContainer folder = thefile.getParent();
    	String ext = thefile.getFileExtension();
    	
    	String nudename = thefile.getFullPath().removeFileExtension().lastSegment();
    	String newname = nudename + "-back" ;
    	if(ext != null) {
    		newname+= "." + ext;
    	} 	    	
    	IPath destination = folder.getLocation().append(newname).makeRelativeTo(folder.getLocation()) ;
    	 IFile backfile = folder.getFile(destination);
    	if(backfile.exists()) {//delete previous backup
				backfile.delete(IResource.FORCE, monitor);
				}
				thefile.copy(destination, true, monitor);
				info("backup " +  thefile.getName() + " in " + backfile.getName() + " in folder " + folder.getName());
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				throw new TcXmlPluginException("fail to manage backup file " ,e);
			}
    	}
    	
    	
    	
    
    
    /***
     *   marshall the graph of the model  object in the view and write the default.xml. a backup is saved in default-backup.xml
     * 
     * 
     * @param monitor
     * @throws TcXmlPluginException
     */

	private void saveMainFile(IProgressMonitor monitor) throws TcXmlPluginException{		
		
		try {
		//get inputstream of default.xml
	IFolder scriptFolder = getTcviewer().getTcfolder();
	IFile mainfile = findMainFile(scriptFolder);
	backup(monitor, mainfile);

			InputStream newin = getTcviewer().getController().marshallScript();
			
			mainfile.setContents(newin, IResource.FORCE, monitor);			
		
	} catch (Exception e) {
		throw new TcXmlPluginException("fail to save mainfile", e) ;
	}
		
		
	}
	
	
	private void saveLibrary(IProgressMonitor monitor,String  libname) throws TcXmlPluginException{		
		
		try {
		//get inputstream of libname.xml
	IFolder scriptFolder = getTcviewer().getTcfolder();
	IFile libfile = findLibraryFile(scriptFolder, libname);
	backup(monitor, libfile);
	InputStream newin = getTcviewer().getController().marshallLibrary(libname);
	
	libfile.setContents(newin, IResource.FORCE, monitor);
	
		}catch (Exception e) {
			throw new TcXmlPluginException("fail to save library " + libname, e) ;
		}
		
		
		
	}
	
	
	
	
	/**
	 * 
	 *  synchronize the model object from the data of the view 
	 * 
	 * @param monitor
	 * @throws TcXmlPluginException
	 */
	
	private void SynchronizeMainFile (IProgressMonitor monitor) throws TcXmlPluginException{
		
		 getTcviewer().synchronizeActions(  monitor) ;
		 getTcviewer().synchronizeLogic(monitor);
		
		
	}
	
	private void synchronizeAllLibraries (IProgressMonitor monitor) throws TcXmlPluginException{
		
		getTcviewer().synchronizeLibraries(monitor);
		
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
	
	/**
	 * 
	 * 
	 * 
	 * @param parent the root directory of the script
	 * @return a pointer to the default.xml 
	 * @throws TcXmlPluginException
	 */
	public IFile findMainFile( IFolder parent ) throws TcXmlPluginException {
		IFile ret = null;
    
  IFile mainfile = parent.getFile("default.xml");
   
   if(mainfile.exists()) {
	   ret = mainfile; 
	   
   }else throw new TcXmlPluginException("fail to find default.xml infolder " + parent.getName(), new FileNotFoundException()
		   );
 
   return ret;
    	
    }
	
	public IFile findLibraryFile( IFolder parent , String libname) throws TcXmlPluginException {
		IFile ret = null;	
		IFolder libfolder = getLibraryFolder(parent);
		if(!libfolder.exists()) {
			throw new TcXmlPluginException("no library folder in root folder " + parent.getName(), new IllegalStateException());
			
		}
		String libfilename = libname + ".xml";
		ret = libfolder.getFile(libfilename);
		if(!ret.exists()) {
			throw new TcXmlPluginException("no library " +  libname +" in library folder of  TC  " + parent.getName(), new IllegalStateException());	
			
		}
		
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
	
	
	
	private IFolder getVideoFolder(IFolder testCaseFolder) {
		 IFolder libfolder = testCaseFolder.getFolder("videos");
		 return libfolder;
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
 * @param exportPathDir  
 */

	public void export(RunLogicViewer runLogicViewer, LibraryViewer libraryViewer, ActionsViewer actionsViewer, java.nio.file.Path exportPathDir ) {
		
		TcXmlController controller = tcviewer.getController();
		
		
		
		//create temporary directory
		try {
		java.nio.file.Path exportPath = exportPathDir ;
		controller.getLog().info("tempdirectory for export :" + exportPath.toString() );
		//export all  the  files of the testcase ( param, extrafiles ... )
		
		
		exportAllFilesFromTestcase(exportPath);
		
		
		HashMap<String,File> linkedLib = exportLibraries(libraryViewer,exportPath);	
		
	

		// export the conf file
		controller.exportTestcentreJSresource(exportPath,"conf.js");
		//export js for parameter management
		//controller.exportTestcentreJSresource(exportPath,"parameter.js");
		//export bash script for post install
		controller.exportTestcentreJSresource(exportPath,"postInstall.sh");
		//export bash script for debugging protractor 
		controller.exportTestcentreJSresource(exportPath,"debug_protractor.sh");
		// crate script specific lib for the parameter management
		File customParamtarget = exportPath.resolve("custom-param.js").toFile();
		customParamtarget.createNewFile();
		exportCustomParam(customParamtarget);
		
		//fill the spec file
		File target = exportPath.resolve("spec.js").toFile();				
target.createNewFile();
		FileOutputStream out;
	
			out = new FileOutputStream(target);
			PrintWriter pw = new PrintWriter(out);
			// add the tescentre sript containing TC and LR symbol
			pw.println("var {TC} = require('./node_modules/testcentreJS/testcentre.js'); ");
			pw.println("var {LR} = require('./node_modules/testcentreJS/testcentre.js'); ");
			
			//basedir for the script
			pw.println("TC.init(__dirname);");
			
			exportSymbols(pw,linkedLib);
			exportActions(pw , actionsViewer);
			
		
			
			exportLogic(pw,runLogicViewer);
			

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
	
	private void exportAllFilesFromTestcase(java.nio.file.Path exportPath) {
	
	String tcname = tcviewer.getController().getName();
String path = tcviewer.getController().getScriptDir();

System.out.println("exporting from " + path);

File libdir = new File(path);
if(libdir.exists()) {
    File[] libfiles = libdir.listFiles();
    for (File file2 : libfiles) {
 
 java.nio.file.Path targetpath = (new File(exportPath + File.separator + file2.getName())).toPath();

  try {
	Files.copy(file2.toPath(), targetpath,StandardCopyOption.REPLACE_EXISTING) ;
} catch (IOException e) {
	error("failure in export", e);

}	
    	
    }	


}	
	
		
		
		
	
}

	private void exportCustomParam(File customParamtarget) throws IOException {
		FileOutputStream out;
		
		out = new FileOutputStream(customParamtarget);
		PrintWriter pw = new PrintWriter(out);
		pw.println("var {parameters , tableParameter}  = require('./parameter.js');");
		
		pw.println("parameters.init = function(thetc){\n");
		
		Map<String, StepParameter> liparam = getTcviewer().getController().getParameters();
		 Collection<StepParameter> paramset = liparam.values();
		 for (StepParameter stepParameter : paramset) {
			exportSingleParam(stepParameter, pw);	
			
		}
		 pw.println("}//end of init ");
		 
		 pw.println(" exports.paramManager=parameters;");
		
		
		
		pw.flush();
		pw.close();
		out.flush();
		out.close();
	
}

	private void exportSingleParam(StepParameter stepParameter, PrintWriter pw) {
		StringBuffer sb = new StringBuffer();
		sb.append(" // export parameter ").append(stepParameter.getName() ).append(" type is ").append(stepParameter.getType()).append("\n");
	if ( stepParameter instanceof TableParameter ) {
		
		 TableParameter tparam = (TableParameter)stepParameter ;
		 	Vector<String> arg = new Vector<String>();
		 	
		 	arg.add("thetc");
		 	arg.add( TcxmlUtils.formatAsJsString( tparam.getColumnName(), "\""));
		 	arg.add( TcxmlUtils.formatAsJsString( tparam.getDelimiter(), "\""));
		 	arg.add( TcxmlUtils.formatAsJsString( tparam.getGenerateNewVal(), "\""));
		 	arg.add( TcxmlUtils.formatAsJsString( tparam.getOriginalValue(), "\""));
		 	arg.add( TcxmlUtils.formatAsJsString( tparam.getOutOfRangePolicy(), "\""));
		 	arg.add( TcxmlUtils.formatAsJsString( tparam.getParamName(), "\""));
		 	arg.add( TcxmlUtils.formatAsJsString( tparam.getSelectnextrow(), "\""));
		 	arg.add( TcxmlUtils.formatAsJsString( tparam.getStartrow(), "\""));
		 	arg.add( TcxmlUtils.formatAsJsString( tparam.getTable(), "\""));
		 	arg.add( TcxmlUtils.formatAsJsString( tparam.getAutoAllocatBlocSize(), "\""));
		 	arg.add( TcxmlUtils.formatAsJsString( tparam.getValueForEachUser(), "\""));
		 	arg.add( TcxmlUtils.formatAsJsString( tparam.getTableLocation(), "\""));
		 
		 	
		 	
		 String[] argli = arg.toArray( new String[arg.size()]);	
	
		 
		 sb.append("parameters.addParameter(").append("\"").append(stepParameter.getName()).append("\",").append("new tableParameter(").append(String.join(",", argli)).append(") ); ").append("\n");
		
	}
		
		
		
		pw.append(sb.toString());
		
	}

	private void exportLogic(PrintWriter pw, RunLogicViewer runLogicViewer) throws TcXmlException {
		pw.println("//start  export logic  **********************************************************************");
		
		pw.println("describe('the logic of the test case ', function() {");
		
		pw.println("  it('run the Test case',  async  function() {  ");
		for (StepViewer stepViewer : runLogicViewer.getChildViewer()) {
			stepViewer.export(pw);
		}
		pw.println("//end  export logic  **********************************************************************");	
		
		pw.println("} );");
		pw.println("});");		
}

	private void exportActions(PrintWriter pw, ActionsViewer actionsViewer) throws TcXmlException {
		{
			pw.println("//start  export action  **********************************************************************");		
		Map<String, ActionView> listview = actionsViewer.getActionsView();	
		Collection<ActionView> val = listview.values();
		for (ActionView actionView : val) {
				actionView.eexport(pw);
			
		}
		
			
		}

		pw.println("//end  export action  **********************************************************************");
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
	pw.println("//start  exportsymbols  **********************************************************************");

	Set<String> symboles = linkedLib.keySet();
	for (String symbole : symboles) {
		StringBuffer sb = new StringBuffer();
		String filename = linkedLib.get(symbole).getName();
		sb.append("var {").append(symbole).append("} = require('./").append(filename).append("');");
		pw.println(sb.toString());	
	}
	
	pw.println("//end  export logic  **********************************************************************");
	
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
	// external referece to the main js resource testcentre.js
	// add the tescentre sript containing TC and LR symbol
	pw.println("var {TC , LR , evalXPath} = require('./node_modules/testcentreJS/testcentre.js'); ");

	
	StringBuffer sb = new StringBuffer();
	
	sb.append("var ").append(libname).append(  "={");
	pw.println(sb);
	
	List<StepViewer> li = libraryView.getChildViewer();
	for (StepViewer stepViewer : li) {
		
		stepViewer.export(pw);
		}
	
	
	
	pw.println("}");
	sb = new StringBuffer();
	sb.append("exports.").append(libname) .append("=").append(libname).append(";") ;
	
	pw.println( sb.toString());
	
	
	
	
sb = new StringBuffer("//// **********************end of library ").append(libname).append(" ***********************************************");
	pw.println(sb);
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

public void manageVideo(boolean selection, TcXmlController controller)  {
	try {
		
		
		
		
	String windowTitle = controller.getCurrentWindowTitle();
	String executableName = "Google Chrome";
	File outputfile = getVideoFile();
	
	String finalWindowTitle = windowTitle + " - " + executableName ;
	

	String pathffmpeg = Activator.getDefault().getPreferenceStore().getString(tcxmlplugin.composite.preference.TcXmlPreference.PATH2FFMPEG);
	
	java.nio.file.Path thepath = Paths.get(pathffmpeg);	
	




	
	if(selection == true) {
		currentVideoRecorder = 	controller.getFfMpegWrapper(thepath);
		info("video recording enabled");
		
		info("window to record is:" + finalWindowTitle);
		
		Job recordjob = new Job("recording job") {
			
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				IStatus ret =  Status.OK_STATUS;;
				try {
					currentVideoRecorder.startRecord(finalWindowTitle, executableName, outputfile);
					
					
				} catch (TcXmlException e) {
					error("unable to launch video recorder" , e);
					
					
					ret = Status.CANCEL_STATUS;
				}
				return ret;
			}
		};
	
		recordjob.schedule();
		
		
	}else {
		
		info("video recording disabled");	
		
		currentVideoRecorder.stopRecord();
		currentVideoRecorder = null;
	}
	
	
	
	
	
	
	
	
} catch (TcXmlException e) {
error("video recorder error" , e);
}
	

	
}

/**
 * 
 * 
 * 
 * 
 * @return current file that the video records in 
 */

private File getVideoFile() {
	IFolder tcfolder = getTcviewer().getTcfolder() ;
	IFolder videofolder = getVideoFolder(tcfolder);
	
	String videoname = getTcviewer().getcurrentVideoName();
IFile videofile = videofolder.getFile(videoname);	
	return new File(videofile.getLocation().toOSString());
}



	


public String getDefaultVideoName() {
	
	return getTcviewer().getTcfolder().getFullPath().lastSegment() + ".mp4" ;
	
}

public TestObject selectInBrowser(TruLibrary truLibrary) throws TcXmlException {
	TestObject ret = null;
	TcXmlController controller = getTcviewer().getController();
	
	// launch the selector
	controller.launchIdentSelector();	
	//wait the selection
	controller.waitIdentSelectorCompletion();
	
	
	String status = controller.getIdentSelectorStatus();
	
	if(status.equals("done")) {
		
	JsonObject thesel = controller.getIdentSelection();	
	
ret  = controller.generateNewTestObjectWithXpath(truLibrary, thesel.getString("xpath"));
ret.setAutoName(thesel.getString("autoName"));
ret.setFallbackName(thesel.getString("fallBackName"));
	

	

	
	
	}else { // selection not ended
		
	if(status.equals("canceled")){
		controller.getLog().fine("selection from browser has been canceled" );	
		ret = null;
		
	}
		
		
	}
	
	
	
	
	
	
	//build the wrapper and return
	
	return ret;
}


public void createAction(String newaction)  {
	
	
	
	
	try {
		info("creating actions "  + newaction);
		getTcviewer().refreshAction(getTcviewer().getController().addAction(newaction));
	} catch (TcXmlException e) {
		// TODO Auto-generated catch block
		error("failure in action creation " , e);
	}
	
	
	
	
}

public void startRecord() {
	// cretz new transaction recor_1 , start the express on the driver port and attach the recordsesson listener
	String recordingName = getTcviewer().getActionsViewer().getNextRecordingTransactionName();
	
	createAction(recordingName);

	ActionView theview = getTcviewer().getActionsViewer().getActionView(recordingName);
	this.currentRecordingSessionListener = createRecordindSessionListener(theview);
	
	this.express.registerRecordingListenner(currentRecordingSessionListener);
	
Job expressJob = new Job("express job") {
	
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		
		IStatus ret = Status.OK_STATUS ;
		try {
			express.minuteListen(30);
		} catch (TcXmlException e) {
			ret = Status.CANCEL_STATUS;
			error("failure starting proxy server", e);
		}
		return ret;
	}
};
	

expressJob.schedule();	
info(" launching proxy server ");
	
}

private RecordingSessionListener createRecordindSessionListener(ActionView theview) {
	// TODO Auto-generated method stub
	return new ActionViewRecordindListener(theview);
}

public  void stopRecord() {
	//stop the express , deregister the record session listener and refresh the action viewer
	if(this.express != null ) {
		this.express.shutDown();
		
		
	}else {
		
		error("no express found", new IllegalStateException());
	}
	
}

	
	
	
	

}