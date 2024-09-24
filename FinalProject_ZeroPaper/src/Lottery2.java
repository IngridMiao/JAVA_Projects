import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
public class Lottery2 implements LotteryStrategy{
	private Connection conn;
	private String item = "";
	private int serialNum = 0;
	private int totalPrize = 0;
	private ArrayList<Lottery2> prizes = new ArrayList<Lottery2>();
	private ArrayList<String> text = new ArrayList<String>();;
	
	public Lottery2(Connection conn, int totalPrize, ArrayList<Lottery2> prizes, ArrayList<String> text) throws SQLException {
        this.conn = conn;
		this.prizes = prizes;
		this.text = text;
        prize();
        lottery();
    }
	public Lottery2(int lotteryNum, String lotteryItem) {
		serialNum = lotteryNum;
		item = lotteryItem;
	}
	public ArrayList<String> getArr(){
		return text;
	}

	public int getSerialNum() {
		return serialNum;
		}
		 
	public String getItem() {
		return item;
		}
		 
	public int getTotalPrize() {
		return totalPrize;
		}
	public void prize() {
		while(prizes.size()<getTotalPrize()) {
			Lottery2 prize = new Lottery2(getSerialNum(),getItem());
			prizes.add(prize);
		}
	}
	 public void lottery() throws SQLException {
		  String query = "SELECT *FROM`group1_data`";
		  
		  try (Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet result = stat.executeQuery(query)) {
		             
		        	result.last();
		        	int row = result.getRow();
		        	result.beforeFirst();

		        	while (result.next()) {
		        		int number = (int) (Math.random() * prizes.size()+1);
		        		if(text.size()>=row) {
		        			break;
		        		}
		        		text.add("抽獎2"+"恭喜 " +result.getString("學號")+" "+result.getString("姓名") + " 獲得: " + number + "獎 " + prizes.get(number - 1).item);
		       
		             }     
		       }
	 }
}
