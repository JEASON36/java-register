package book.model;
import java.sql.*;
import java.util.ArrayList;
public class UserInfo {
private String username="";
private String password="";
private  int courseid=0;
private String coursename="";
private String coursesort="";
private int credit=0;
public String getUsername() {
return username;
}
public void setUsername(String username) {
this.username = username;
}
public String getPassword() {
return password;
}
public void setPassword(String password) {
this.password = password;
}

public int getCourseid() {
	return courseid;
}
public String getCoursename() {
	return coursename;
}
public String getCoursesort() {
	return coursesort;
}
public int getCredit() {
	return credit;
}
public void setCoursename(String coursename) {
	this.coursename=coursename;
}
public void setCoursesort(String coursesort) {
	this.coursesort=coursesort;
}
public void setCourseid(int courseid) {
	this.courseid=courseid;
}
public void setCredit(int credit) {
	this.credit= credit;
}

//检查用户是否存在的方法
public boolean checkName(){
System.out.print(username);
boolean exist=false;
String sql="select * from student where username='"+username+"'";
DBBean jdbc=new DBBean();
ResultSet rs=jdbc.executeQuery(sql);
try {
if(rs!=null && rs.next())
exist=true;
} catch (SQLException e) {
e.printStackTrace();
}
jdbc.close();
return exist;
}

//在检查用户是否存在的基础上加上匹配密码
public boolean checkLogin() {
	System.out.print(username);
	System.out.println(password);
	boolean exist=false;
	String sql="select * from student where username='"+username+"' and password= '"+password+"'";
	DBBean jdbc=new DBBean();
	ResultSet rs=jdbc.executeQuery(sql);
	try {
		if(rs!=null&& rs.next())
			exist=true;
	}catch(SQLException e) {
		e.printStackTrace();
	}
	jdbc.close();
	return exist;
}



//将注册用户信息添加到数据库的方法 
public int registerUser(){
//String sql="insert into student(username,password) values('"+username+"','"+password+"')";

//注册语句有问题	
String sql="insert into student values('"+username+"','"+password+"','"+courseid+"','"+coursename+"','"+coursesort+"','"+credit+"')";
	
DBBean jdbc=new DBBean();
int result=jdbc.executeUpdate(sql);
jdbc.close();
return result;
}

//删除用户信息
public boolean deleteUser(String s) {
	System.out.print("删除-----"+s);
	System.out.print("\n");
//	ArrayList<UserInfo> list=new ArrayList<UserInfo>();//这行什么意思，添加中也没有这一行
//	String sql = "delete from user where username = 's'";//确定正确
	String sql = "delete from student where username = '"+s+"'";
	
//	System.out.println(sql);//作为验证
//	验证
//	String sql1 = "delete from user where username = "+s;
//	System.out.println(sql1);
	DBBean jdbc = new DBBean();
	int rs=jdbc.executeUpdate(sql);
	jdbc.close();
	boolean p=false;
	if(rs==1)
		p=true;
	return p;
}


//返回数据库所有用户信息的方法
public static ArrayList<UserInfo> getUserList(){
ArrayList<UserInfo> list=new ArrayList<UserInfo>();
String sql="select * from student";
DBBean jdbc=new DBBean();
ResultSet rs=jdbc.executeQuery(sql);
try {
while(rs.next()){
UserInfo ui=new UserInfo();
ui.setUsername(rs.getString("username"));
ui.setPassword(rs.getString("password"));
ui.setCourseid(rs.getInt("courseid"));
ui.setCoursename(rs.getString("coursename"));
ui.setCoursesort(rs.getString("coursesort"));
ui.setCredit(rs.getInt("credit"));
list.add(ui);
}
} catch (SQLException e) {
e.printStackTrace();
}
jdbc.close();
return list;
}
}
