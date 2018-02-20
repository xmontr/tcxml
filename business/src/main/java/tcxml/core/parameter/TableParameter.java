package tcxml.core.parameter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;

import tcxml.core.TcXmlException;

public class TableParameter extends StepParameter {

	private String delimiter;

	private String generateNewVal;

	private String originalValue;

	private String outOfRangePolicy;

	private String selectnextrow;

	private String startrow;

	private String table;

	private String autoAllocatBlocSize;

	private String valueForEachUser;

	private String columnName;
	
	private String tableLocation ;
	
	
	private FileInputStream inputstream;
	
	private HashMap<String, String>[]  values;

	protected TableParameter(HierarchicalINIConfiguration conf ,String  secname) {
		super(conf,secname);
		this.columnName = config.getString("ColumnName");
		this.delimiter = config.getString("Delimiter");
		this.generateNewVal = config.getString("GenerateNewVal");
		this.originalValue = config.getString("OriginalValue");
		this.outOfRangePolicy = config.getString("OutOfRangePolicy");
		this.paramName = config.getString("ParamName");
		this.selectnextrow = config.getString("SelectNextRow");
		this.startrow = config.getString("StartRow");
		this.table = config.getString("Table");
		this.autoAllocatBlocSize = config.getString("auto_allocate_block_size");
		this.valueForEachUser = config.getString("value_for_each_vuser");
		this.tableLocation = config.getString("TableLocation");
		this.setName(paramName);
		
		

	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public String getGenerateNewVal() {
		return generateNewVal;
	}

	public void setGenerateNewVal(String generateNewVal) {
		this.generateNewVal = generateNewVal;
	}

	public String getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(String originalValue) {
		this.originalValue = originalValue;
	}

	public String getOutOfRangePolicy() {
		return outOfRangePolicy;
	}

	public void setOutOfRangePolicy(String outOfRangePolicy) {
		this.outOfRangePolicy = outOfRangePolicy;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getSelectnextrow() {
		return selectnextrow;
	}

	public void setSelectnextrow(String selectnextrow) {
		this.selectnextrow = selectnextrow;
	}

	public String getStartrow() {
		return startrow;
	}

	public void setStartrow(String startrow) {
		this.startrow = startrow;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getAutoAllocatBlocSize() {
		return autoAllocatBlocSize;
	}

	public void setAutoAllocatBlocSize(String autoAllocatBlocSize) {
		this.autoAllocatBlocSize = autoAllocatBlocSize;
	}

	public String getValueForEachUser() {
		return valueForEachUser;
	}

	public void setValueForEachUser(String valueForEachUser) {
		this.valueForEachUser = valueForEachUser;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	
	private FileInputStream getFileStream() throws FileNotFoundException {
		FileInputStream ret = null;
		if(isLocalFile()) {
			String path2file = conf.getBasePath()	 +File.separator +  table;
			File file = new File(path2file);
			ret = new FileInputStream(file);
		} else {//remote file
			
			
			
		}
	
		
	return ret;	
	}

	private boolean isLocalFile() {
		boolean ret = false;
		if(tableLocation.equals("Local")) {
			ret = true;
		}
		return ret;
	}

	@Override
	public String evalParameter() throws TcXmlException {
String ret ="not implemented";		
		
if(inputstream == null) {
	
	try {
		inputstream = getFileStream();
	} catch (FileNotFoundException e) {
	throw new TcXmlException("file not found when loading parameter", e);
	}	
}

//file is there


if(values == null) {
	
	values = readAllLines();
}


// values are there

ret = values[getCurrentLine()].get(getColumn());



return ret;
	}



		
		
	
	

	private Object getColumn() {
		// TODO Auto-generated method stub
		return columnName;
	}

	private int getCurrentLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	private HashMap<String, String>[] readAllLines() {
		BufferedReader br = new BufferedReader(new InputStreamReader(inputstream));
		Function<String,HashMap<String,String>> mapToParamValue= (String line ) ->{
			HashMap<String, String> re = new HashMap<String, String>();
			String[] data = line.split(delimiter);
			for (int i = 0; i < data.length; i++) {
				re.put("Col "+(i +1), data[i]);
			}
			
			return re;
		};

		List<HashMap<String, String>> val = br.lines().skip(1).map(mapToParamValue).collect(Collectors.toList());
		HashMap<String, String>[] ar =  (HashMap<String, String>[] )val.toArray( new HashMap[val.size()]);
		return ar;
	}



}
