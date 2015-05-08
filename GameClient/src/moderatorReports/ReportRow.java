package moderatorReports;

import javafx.beans.property.SimpleIntegerProperty;

public class ReportRow {
	
	public SimpleIntegerProperty id = new SimpleIntegerProperty();
	
	/**
	 * @return report id
	 */
	public int getId(){
		return id.get();
	}

}
