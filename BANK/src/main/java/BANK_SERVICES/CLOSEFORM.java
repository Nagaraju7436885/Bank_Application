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
 * Servlet implementation class CLOSEFORM
 */
@WebServlet("/CLOSEFORM")
public class CLOSEFORM extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CLOSEFORM() {
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
				
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:Oracle:thin:@localhost:1521:xe","nagaraju","welcome");
			
            PreparedStatement ps=con.prepareStatement("update bankusers set status='deactivate' where accnum=? and name=? and password=?");
            
            ps.setString(1, acc_num);
            ps.setString(2, name);
            ps.setString(3, password);
            
      
           int i=ps.executeUpdate();
            
            if(i==1) {
            	out.print("Account Closed successfully");
            	
            }else {
            	out.print("Account Close failed");
            }
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}

}
