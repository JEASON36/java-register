package book.control;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import book.model.*;
import java.util.ArrayList;



public class UserInfoController extends HttpServlet {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
doPost(request,response);
}

public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
System.out.println(request.getServletPath());
request.setCharacterEncoding("UTF-8");
String actionUrl=request.getServletPath();

if(actionUrl.equals("/view/delete.action")){
	System.out.println("成功传输到controller");
	String username=request.getParameter("username1");//删除的用户名称
	System.out.println(username);
	UserInfo ui=new UserInfo();
	 boolean result = ui.deleteUser(username);//有时候会出现延迟，导致result使用deleteUser进行判断赋值，就直接执行IF判断到相应的界面了。想要避免这种情况发生，可以先给result赋值为false
	if(result==false)
		request.getRequestDispatcher("/view/deleteFailtrue.html").forward(request, response);
	else
		request.getRequestDispatcher("/view/deleteSuccess.html").forward(request, response);
}

if(actionUrl.equals("/view/alterList.action")){
	System.out.println("成功传输到alterList.action");
	ArrayList<UserInfo> list=UserInfo.getUserList();
	request.setAttribute("list", list);
	request.getRequestDispatcher("/view/alter.jsp").forward(request, response);
}


if(actionUrl.equals("/view/caculate.action")){
	System.out.println("成功传输到caculate.controller");
	String sql = "select sum(credit) from student where coursesort==y";
	String sql1 = "select sum(credit) from student where coursesort==n";
	DBBean jdbc = new DBBean();
	int sum=jdbc.executeUpdate(sql);
	int sum1=jdbc.executeUpdate(sql1);
	request.setAttribute("sum", sum);
	request.setAttribute("sum", sum1);
	System.out.println("sum="+sum);
	request.getRequestDispatcher("/view/sum.jsp").forward(request, response);
	jdbc.close();
}



if(actionUrl.equals("/view/alter.action")) {
//	String username=request.getParameter("temp");
	System.out.println("成功传输到alter.controller");
	String username1=request.getParameter("username1");
	System.out.println("username1="+username1);
	
	String username=request.getParameter("username"+username1);//这里取值出现问题
	System.out.println("username="+username);
	String password=request.getParameter("password"+username1);//由于alter.jsp命名的问题，只能修改第一行的数值
	int courseid = Integer.parseInt(request.getParameter("courseid"+username1));
	String coursename = request.getParameter("coursename"+username1);
	String coursesort = request.getParameter("coursesort"+username1);
	int credit = Integer.parseInt(request.getParameter("credit"+username1));
	
	
	DBBean jdbc = new DBBean();
//这里，数字不能够这样写，int 类型的更新有问题
	String sql="update user set username='"+username+"',password='"+password+"',courseid='+courseid+',coursename='"+coursename+"',coursesort='"+coursesort+"',credit='+credit+' where （username='"+username1+"'）";
	
	String sql1="update student set username='"+username+"',password='"+password+"' where username='"+username1+"'";
	int rs=jdbc.executeUpdate(sql1);
	jdbc.close();
	if(rs==1)
		request.getRequestDispatcher("/view/loginSuccess.html").forward(request, response);	
	else 
		request.getRequestDispatcher("/view/alterFailtrue.html").forward(request, response);		
}
if(actionUrl.equals("/view/login.action")){
	String username=request.getParameter("username");
	String password=request.getParameter("password");
	UserInfo ui=new UserInfo();
	ui.setUsername(username);
	ui.setPassword(password);
	boolean result = ui.checkLogin();
	int len = username.length();
	if(result&&len<5 ) 
		request.getRequestDispatcher("/view/loginSuccess.html").forward(request, response);//登录成功，跳转到userinfolist.jsp
	else if(result) {
		
		System.out.println("成功传输到student.action");
		ArrayList<UserInfo> list=UserInfo.getUserList();
		request.setAttribute("list", list);
		request.getRequestDispatcher("/view/alter.jsp").forward(request, response);
	}
		
	else
		request.getRequestDispatcher("/view/loginFailtrue.html").forward(request, response);
}


//注册
if(actionUrl.equals("/view/register.action")){
String username=request.getParameter("username");
String password=request.getParameter("password");
int courseid=Integer.parseInt(request.getParameter("courseid"));//转换类型成int
String coursename=request.getParameter("coursename");
String coursesort=request.getParameter("coursesort");
int credit=Integer.parseInt(request.getParameter("credit"));
System.out.println(username);
//设置Javabean属性
UserInfo ui=new UserInfo();
ui.setUsername(username);
//调用相应业务方法，根据返回值选择合适的视图层用户
if(ui.checkName()){
request.getRequestDispatcher("/view/userExist.html").forward(request, response);
}
//else if(ui.checklogin()) {
//	request.getRequestDispatcher("/view/userInfoList.html").forward(request, response);}
else{
ui.setPassword(password);
ui.setCourseid(courseid);
ui.setCoursename(coursename);
ui.setCoursesort(coursesort);
ui.setCredit(credit);
int result=ui.registerUser();
if(result==1)
request.getRequestDispatcher("/view/regSuccess.html").forward(request, response);
else
request.getRequestDispatcher("/view/regFailure.html").forward(request, response);
}
}
else if(actionUrl.equals("/view/checkName.action")){
String username=request.getParameter("username");
UserInfo ui=new UserInfo();
ui.setUsername(username);
System.out.println(username);
boolean exist=ui.checkName();
System.out.println(exist);
if(exist)
request.getRequestDispatcher("/view/userExist.html").forward(request, response);
else
request.getRequestDispatcher("/view/userNoExist.html").forward(request, response);
}
else if(actionUrl.equals("/view/userList.action")){
ArrayList<UserInfo> list=UserInfo.getUserList();
request.setAttribute("list", list);
request.getRequestDispatcher("/view/userInfoList.jsp").forward(request, response);
}
}
}