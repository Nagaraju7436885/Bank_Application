package BANK_SERVICES;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DEPOSITFORM
 */
@WebServlet("/DEPOSITFORM")
public class DEPOSITFORM extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DEPOSITFORM() {
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
		
		double amount=Double.parseDouble(request.getParameter("amount"));
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:Oracle:thin:@localhost:1521:xe","nagaraju","welcome");
			
			PreparedStatement ps1=con.prepareStatement("select * from bankusers where accnum=? and name=? and password=?");
			
			ps1.setString(1, acc_num);
            ps1.setString(2, name);
            ps1.setString(3, password);
            
            ps1.executeUpdate();
            
            ResultSet rs=ps1.executeQuery();
            
            double oldbal=0.0;
            double dep=amount;
            if(rs.next()) {
            	oldbal=Double.parseDouble(rs.getString(4));
            }
            
            //System.out.println("old bal"+oldbal);
            amount = oldbal+amount;
			
            PreparedStatement ps2=con.prepareStatement("update bankusers set amount=? where accnum=? and name=? and password=?");
            
            ps2.setString(2, acc_num);
            ps2.setString(3, name);
            ps2.setString(4, password);
            
            ps2.setDouble(1, amount);
            
            
            
           int i=ps2.executeUpdate();
            
            if(i==1) {
            	out.print("deposited successfully"+"<br>");
            	out.print("your balance is increased by"+dep+"<br>");
            	
            	out.print("your balance is "+oldbal+"<br>");
            	out.print("deposite amount is "+dep+"<br>");
            	out.print("after deposite your balance is "+amount+"<br>");
            }else {
            	out.print("deposite failed");
            }
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

}
