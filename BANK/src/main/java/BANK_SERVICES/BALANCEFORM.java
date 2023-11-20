	package BANK_SERVICES;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BALANCEFORM
 */
@WebServlet("/BALANCEFORM")
public class BALANCEFORM extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BALANCEFORM() {
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
			
			PreparedStatement ps1=con.prepareStatement("select * from bankusers where accnum=? and name=? and password=?");
			
			ps1.setString(1, acc_num);
            ps1.setString(2, name);
            ps1.setString(3, password);
            
            ps1.executeUpdate();
            
            ResultSet rs=ps1.executeQuery();
           
            
            ResultSetMetaData rsmd= ps1.getMetaData();
          
            if(rs.next()) {
            	if(!rs.getString(7).equals("deactivate")) {
            		out.print("<table border='1'>");
                	out.print("<tr>");
                	for(int i=1;i<=7;i++) {
                		
                		if(i==3) {
                			continue;
                		}else {          			
                    		out.print("<th>"+rsmd.getColumnName(i)+"</th>");         		           			
                		}
                	}
                	out.print("</tr>");
                	
                	out.print("<tr>");
                	for(int i=1;i<=7;i++) {
                		
                		if(i==3) {
                			continue;
                		}else { 		
                			out.print("<td>"+rs.getString(i)+"</td>");            			
                		}
                	}
                	out.print("</tr>");
                	
                	out.print("</table>");
                }else {
                	out.print("Account is Deactivated");
                }
            	}
            }
            
            	
            	
		
		catch(Exception e) {
			System.out.println(e);
		}
	}

}
