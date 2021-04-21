package DBO;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class DBO {
 //SQLServer
 private String driverName = "com.microsoft.jdbc.sqlserver.SQLServerDriver";//������������  
 private String url = "jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=1";//�������ݿ����Ӵ�  masterΪ���ݿ���
 private String user = "sa";//���ݿ��¼�û���  
 private String password = "123456";//���ݿ��¼����  
 public Connection getConnection() {  
  try {  
   Class.forName(driverName);  
   return DriverManager.getConnection(url, user, password);  
  } catch (Exception e) {  
   e.printStackTrace();  
   return null;  
  }  
 }  
 public static void main(String[] args) {
  DBO dcm = new DBO();  
  String sql = "insert into temperature(group,layer,one,two,three,four) values(?,?,?,?,?,?)";
  Connection conn=null;
  PreparedStatement ps=null;
  BufferedReader br=null;
  try {
   conn = dcm.getConnection(); 
   br=new BufferedReader(new FileReader("sourcefile.txt"));
   String s="";
   String regex="(\\d+)\\s+(\\d+)\\s+(\\d+\\.\\d+)\\s+(\\d+\\.\\d+)\\s+(\\d+\\.\\d+)\\s+(\\d+\\.\\d+)";
   while((s=br.readLine())!=null){
    s=s.trim();
    Pattern p=Pattern.compile(regex);
    Matcher m=p.matcher(s);
    if(m.matches()){
     //System.out.println(m.group(1)+" "+m.group(2)+" "+m.group(3)+" "+m.group(4)+" "+m.group(5)+" "+m.group(6));
     ps=conn.prepareStatement(sql);
     ps.setInt(1, Integer.parseInt(m.group(1))); 
     ps.setInt(2, Integer.parseInt(m.group(2))); 
     ps.setFloat(3, Float.parseFloat(m.group(3))); 
     ps.setFloat(4, Float.parseFloat(m.group(4))); 
     ps.setFloat(5, Float.parseFloat(m.group(5))); 
     ps.setFloat(6, Float.parseFloat(m.group(6))); 
     ps.executeUpdate();
    }
   }
   System.out.println("���ݲ�����ϣ�");
  } catch (FileNotFoundException e) {
   e.printStackTrace();
  } catch (IOException e) {
   e.printStackTrace();
  }catch (SQLException e) { 
    e.printStackTrace();
   }finally{
   try {
    ps.close();
    conn.close();
    br.close();
   } catch (Exception e) {
    e.printStackTrace();
   }
  }
 }
}
