package GameServer.Users;

public class Report {

	private int ID;
	private String log;
	
	/**
	 * @param id Report id
	 * @param log Text log taken from chat box
	 */
	
	public Report(int id, String log)
	{
		ID = id;
		this.log = log;
	}
	
	/**
	 * @return report id
	 */
	
	public int getID()
	{
		return ID;
	}
	
	/**
	 * @return text log
	 */
	
	public String getLog()
	{
		return log;
	}
	
}
