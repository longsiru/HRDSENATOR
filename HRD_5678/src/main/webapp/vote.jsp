<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="java.util.*" %>
<%@page import="DTO.Member" %>
<%
request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="style.css">
<script type="text/javascript" src="script.js"></script>
</head>
<body>
	<%@ include file="top.jsp" %>
	<section>
		<div class="title">투표하기</div>
		<form name="frm" action="insert">
		<input type="hidden" id="VO" value="insert">  <!-- 这里写"insert"其实是没有用的，但是以后同一个画面可能会有insert，可能会有update和其他的所以现在养成写的习惯 -->
		<div class="wrapper">
			<table style="width:900Px">
				<tr>
					<th>주민번호</th>
					<td><input type="text" name="v_jumin"> 예: 8906153154726</td>
				</tr>
				<tr>
					<th>성명</th>
					<td><input type="text" name="v_name"></td>
				</tr>
				<tr>
					<th>투표번호</th>
					<td>
						<select name="m_no">
							<option value=""></option>
							<option value="1">[1] 김호보</option>
							<option value="2">[2] 이호보</option>
							<option value="3">[3] 박호보</option>
							<option value="4">[4] 조호보</option>
							<option value="5">[5] 최호보</option>
						</select> 
					</td>
				</tr>
				<tr>
					<th>투표시간</th>
					<td><input type="text" name="v_time"></td>
				</tr>
				<tr>
					<th>투표장소</th>
					<td><input type="text" name="v_area"></td>
				</tr>
				<tr>
					<th>유권자확인</th>
					
					<td>
						<span class="con">
							<input type="radio" name="v_confirm" value="Y">확인
							<input type="radio" name="v_confirm" value="N">미확인
						</span>
					</td>
				
					
				</tr>
				<tr>
					<td colspan="2">
						<button class="btn" type="submit" onclick="fn_submit(); return false;">투표하기</button>
						<button class="btn" type="reset" onclick="alert('정보를 지우고 처음부터 디시 입력하세요!');document.frm.v_jumin.focus();">다시하기</button>
					</td>
				</tr>	 
			</table>
		</div>
		</form>
	</section>
	<%@ include file="footer.jsp" %>
</body>
</html>