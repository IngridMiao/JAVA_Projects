import java.sql.*;
import java.util.ArrayList;
public class Lottery1 implements LotteryStrategy{
	private Connection conn;
	//private Statement stat;
	//private ResultSet result;
	private ArrayList<Integer> winNum;
	private ArrayList<String> text=new ArrayList<String>();
	private int winners;// 獲獎人數自設
	public Lottery1(Connection conn, int winners) throws SQLException {
		this.conn = conn;
		//stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		this.winners = winners;
		winNum = new ArrayList<>();
		//this.result = stat.executeQuery("SELECT * FROM `data2`");
		getWinner();
		lottery();
		//result.close();
		//stat.close();
	}
	public ArrayList<String> getArr(){
		return text;
	}
	public int getWinner() {
		return winners;
	}
	public void lottery() throws SQLException {
        String query = "SELECT * FROM `group1_data`";
        try (Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet result = stat.executeQuery(query)) {
            
            result.last();
            int row = result.getRow();
            result.beforeFirst();
            
            while (winNum.size() < winners) {
                int number = (int) (Math.random() * row) + 1;
                if (!winNum.contains(number)) {
                    winNum.add(number);
                }
            }

            //System.out.println("中獎號碼:" + winNum);

            while (result.next()) {
                int serialNum = result.getRow();
                if (winNum.contains(serialNum)) {

       	         	if (text.size() >= winners) {
       	         		break;}
                    text.add("抽獎1 " + result.getString("學號") + " " + result.getString("姓名"));


                }
            }
        }
    }
}
