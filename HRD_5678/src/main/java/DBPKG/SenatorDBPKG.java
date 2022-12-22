package DBPKG;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DTO.Count;
import DTO.Member;
import DTO.Vote;

public class SenatorDBPKG {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public static Connection getConnection() throws Exception{
		Class.forName("oracle.jdbc.OracleDriver");
		Connection con = DriverManager
				.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "sys1234");
		
		return con;
	}
	
	public String selectAll(HttpServletRequest request, HttpServletResponse response) {
		
		ArrayList<Member> list = new ArrayList<Member>();
		
		try {
			conn = getConnection();
			String  sql= "SELECT T1.M_NO, T1.M_NAME, T2.P_NAME "
					+ ", DECODE(P_SCHOOL, '1', '고졸', '2', '학사', '3', '석사', '박사') P_SCHOOL "
					+ ", SUBSTR(T1.M_JUMIN,1,6)||'-'||substr(T1.M_JUMIN,7) M_JUMIN,  T1.M_CITY, SUBSTR(T2.P_TEL1,1,2)||'-'||T2.P_TEL2||'-'|| "
					+ "	(substr(T2.P_TEL3,4)|| "
					+ "	substr(T2.P_TEL3,4)|| "
					+ "	substr(T2.P_TEL3,4)|| "
					+ "	substr(T2.P_TEL3,4)) P_TEL "
					+ "FROM TBL_MEMBER_202005 T1 "
					+ "JOIN TBL_PARTY_202005 T2 "
					+ "ON (T1.P_CODE = T2.P_CODE) ";
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()){
				Member member = new Member();
				member.setM_no(rs.getString(1));
				member.setM_name(rs.getString(2));
				member.setP_name(rs.getString(3));
				member.setP_school(rs.getString(4));
				member.setM_jumin(rs.getString(5));
				member.setM_city(rs.getString(6));
				member.setTel(rs.getString(7));
//				System.out.println(rs.getString(1) +":"+rs.getString(2));
				list.add(member);
			}
			
			request.setAttribute("list", list);
			
			conn.close();
			ps.close();
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "list.jsp";
	}
	
	public int insert(HttpServletRequest request, HttpServletResponse response) {
		String v_jumin = request.getParameter("v_jumin");
		String v_name = request.getParameter("v_name");
		String m_no = request.getParameter("m_no");
		String v_time = request.getParameter("v_time");
		String v_area = request.getParameter("v_area");
		String v_confirm = request.getParameter("v_confirm");
		int result = 0;
		
		try {
			conn = getConnection();
			String sql = "INSERT INTO TBL_VOTE_202005 VALUES(?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, v_jumin);
			ps.setString(2, v_name);
			ps.setString(3, m_no);
			ps.setString(4, v_time);
			ps.setString(5, v_area);
			ps.setString(6, v_confirm);
			
			result = ps.executeUpdate();
			System.out.println(result);
			
			conn.close();
			ps.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
public String selectList(HttpServletRequest request, HttpServletResponse response) {
	ArrayList<Vote> list = new ArrayList<Vote>();
	
	try {
		conn = getConnection();
		String sql = "select v_name "
				+ ", to_char(to_date(case when substr(v_jumin, 7,1) = 1 or substr(v_jumin, 7,1) = 2 then '19' else '20' end||substr(v_jumin, 0, 6), 'YYYYMMDD'),'YYYY\"년\"MM\"월\"DD\"일생\"') v_jumin "
				+ ", trunc(months_between(trunc(sysdate), to_date(19||substr(v_jumin, 0,6),'YYYYMMDD'))/12) 나이 "
				+ ", case when substr(v_jumin, 7,1) = 1 then '남' else '여' end v_jumin "
				+ ", m_no "
				+ ", substr(v_time,0,2) || ':' ||substr(v_time,3,2) v_time "
				+ ", case when v_confirm = 'Y' then '확인' else '미확인' end v_confirm "
				+ "from tbl_vote_202005 ";
		
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		
		while(rs.next()) {
			Vote vote = new Vote();
			
			vote.setV_name(rs.getString(1));
			vote.setV_jumin(rs.getString(2));
			vote.setV_age(rs.getString(3));
			vote.setV_gender(rs.getString(4));
			vote.setM_no(rs.getString(5));
			vote.setV_time(rs.getString(6));
			vote.setV_confirm(rs.getString(7));
			
			list.add(vote);
			
		}
		
		request.setAttribute("list", list);
		conn.close();
		ps.close();
		rs.close();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return "votelist.jsp";
	
}

public String rankList(HttpServletRequest request, HttpServletResponse response) {
	ArrayList<Count> list = new ArrayList<Count>();
	try {
		conn = getConnection();
		
		String sql = "SELECT V1.M_NO, V2.M_NAME, V1.COUNTS  "
				+ "FROM (SELECT M_NO, COUNT(M_NO) COUNTS "
				+ "FROM TBL_VOTE_202005 "
				+ "GROUP BY M_NO) V1 "
				+ "JOIN TBL_MEMBER_202005 V2 ON (V1.M_NO =  V2.M_NO) ";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		
		
	while(rs.next()) {
		Count count = new Count();
		
		count.setM_no(rs.getString(1));
		count.setM_name(rs.getString(2));
		count.setCounts(rs.getString(3));
		
		list.add(count);
		
		
	}
		request.setAttribute("list", list);
		conn.close();
		ps.close();
		rs.close();
		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return "rank.jsp";
}
		
		
		
	
}
