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
 * Servlet implementation class BANKTRANSFERFORM
 */
@WebServlet("/BANKTRANSFERFORM")
public class BANKTRANSFERFORM extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BANKTRANSFERFORM() {
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
		String uname=request.getParameter("name");
		String pass=request.getParameter("password");
		String tacc_num=request.getParameter("taccnum");
		
		double money=Double.parseDouble(request.getParameter("amount"));
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:Oracle:thin:@localhost:1521:xe","nagaraju","welcome");
			
			PreparedStatement ps1=con.prepareStatement("select * from bankusers where accnum=? and name=? and password=?");
			ps1.setString(1, acc_num);
			ps1.setString(2, uname);
			ps1.setString(3, pass);
			
			ResultSet rs=ps1.executeQuery();
			double mybal=0.0;
			
			if(rs.next()) {
				mybal=rs.getDouble(4);
			}else {
				out.print("enter valid details");
			}
			if(mybal<money) {
				out.print("check transfer money.........insufficient funds...");
			}else {
				
				mybal=mybal-money;
				PreparedStatement ps2=con.prepareStatement("select * from bankusers where accnum=?");
				ps2.setString(1, tacc_num);
				
				ResultSet rs2=ps2.executeQuery();
				double tbal=0.0;
				
				if(rs2.next()) {
					tbal=rs2.getDouble(4);
				}else {
					out.print("check transfer account details");
				}
				
				tbal=tbal+money;
				
				PreparedStatement ps3=con.prepareStatement("update bankusers set amount=? where accnum=? and name=? and password=?");
				ps3.setDouble(1, mybal);
				ps3.setString(2, acc_num);
				ps3.setString(3, uname);
				ps3.setString(4, pass);
				
				int i=ps3.executeUpdate();
				
				PreparedStatement ps4=con.prepareStatement("update bankusers set amount=? where accnum=?");
				ps4.setDouble(1, tbal);
				ps4.setString(2, tacc_num);
				
				int j=ps4.executeUpdate();
				
				if(i==1 && j==1) {
					out.print("Transfered Successfully");
				}else {
					out.print("Transfer Failed");
				}
				
				
			}
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}

}
