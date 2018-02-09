package tcxml.core.parameter;

import org.apache.commons.configuration.SubnodeConfiguration;

public class TableParameter extends StepParameter {
	
	private String delimiter;
	
	
	private String generateNewVal;
	
	private String originalValue;
	
	private String outOfRangePolicy;
	
	
	
	
	
	private String selectnextrow;
	
	
	private String startrow ;
	
	private String table;
	
	
	private String autoAllocatBlocSize;
	
	
	private String valueForEachUser ;
	
	
	private String columnName ;
	
	
	
	
	
	
	
	
	

	protected TableParameter(SubnodeConfiguration c) {
		super(c);
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










	@Override
	public String evalParameter() {
		// TODO Auto-generated method stub
		return "not implemented";
	}

}
