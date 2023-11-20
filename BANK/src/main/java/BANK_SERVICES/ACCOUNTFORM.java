package BANK_SERVICES;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ACCOUNTFORM
 */
@WebServlet("/ACCOUNTFORM")
public class ACCOUNTFORM extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ACCOUNTFORM() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		String acc_num=request.getParameter("accnum");
		String name=request.getParameter("name");
		String password=request.getParameter("password");
		String cpassword=request.getParameter("password");
		double amount=Double.parseDouble(request.getParameter("amount"));
		String address=request.getParameter("address");
		String mobilenum=request.getParameter("phno");
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:Oracle:thin:@localhost:1521:xe","nagaraju","welcome");
			
			
            PreparedStatement ps=con.prepareStatement("insert into bankusers values(?,?,?,?,?,?,'ACTIVATE')");
            
            ps.setString(1, acc_num);
            ps.setString(2, name);
            if(password.equals(cpassword)) {
            	ps.setString(3, password);
            }else {
            	out.print("check password....");
            }
            ps.setDouble(4, amount);
            ps.setString(5, address);
            ps.setString(6, mobilenum);
            
            int i=ps.executeUpdate();
            
            if(i==1) {
            	out.print("account open successfully");
            }else {
            	out.print("account open failed");
            }
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

}
