<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
</head>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script type="text/javascript">
function login(){
	var ser = $('#loginForm').serialize();
    $.ajax({
        url:'/user/login',
        method:'post',
        cache:false,
        data:ser,
        dataType:'json',  
        success:function(res){  
            alert(res.ok ? '로그인 성공' : '로그인 실패');
            if(res.ok){
            	location.href="/user/index";
            }
                       
        },
        error:function(xhr, status, err){  // Corrected typo here
            alert('에러:' + err);
       
        }
    });
	
}


</script>
<body>
    <h2>Login</h2>
    <form id="loginForm">
        <div>
            <label for="username">Username:</label>
            <input name="username" id="username"/>
        </div>
        <div>
            <label for="password">Password:</label>
            <input type="password" name="password" id="password"/>
        </div>
        <div>
           <button type="reset">취소</button> 
            <button type="button" onclick="login();">로그인</button>            
        </div>
    </form>
</body>
</html>