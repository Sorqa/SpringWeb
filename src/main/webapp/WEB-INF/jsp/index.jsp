<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그아웃</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script type="text/javascript">
function logout() {
	   $.ajax({
	      url:'/user/logout',
	      method:'get',
	      cache:false,
	      dataType:'json',
	      success:function(res){
	         alert(res.ok ? '로그아웃 성공':'실패');
	      },
	      error:function(xhr,status,err){
	         alert('에러:' + err);
	      }
	   });
	}	
</script>
</head>
<body>
[<a href="/user/login">로그인</a>]
[<a href="javascript:logout();">로그아웃</a>]
[<a href="/user/list">목록</a>]
[<a href="/user/addForm">회원가입</a>]
</body>
</html>