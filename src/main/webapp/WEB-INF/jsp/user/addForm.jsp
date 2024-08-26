<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>유저 추가</title>
</head>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script type="text/javascript">
	function addUser(){
		var ser = $('#addForm').serialize();
	    $.ajax({
	        url:'/user/addUser',
	        method:'post',
	        cache:false,
	        data:ser,
	        dataType:'json',  
	        success:function(res){  
	            alert(res.added ? '추가 성공' : '추가 실패');
	            if(res.added){
	            	location.href="/user/login";
	            }
	                       
	        },
	        error:function(xhr, status, err){  // Corrected typo here
	            alert('에러:' + err);
	       
	        }
	    });
	}
	
	function checkDuplicate(){
		let obj = {};
		obj.username = $('#username').val();
		
		$.ajax({
			url:'/user/checkDuplicate',
			method:'post',
			cache:false,
			data:obj,
			dataType:'json',
			success:function(res){  
	            alert(res.result ? '추가할 수 있습니다' : '중복된 아이디입니다');	        
	                       
	        },
	        error:function(xhr, status, err){  // Corrected typo here
	            alert('에러:' + err);
	       
	        }
		});
	}
</script>
<body>
  <h2>USER ADD</h2>
    <form id="addForm">
        <div>
            <label for="username">Username:</label>
            <input name="username" id="username"/>
            <button type="button" onclick="checkDuplicate();">중복체크</button>
        </div>
        <div>
            <label for="password">Password:</label>
            <input type="password" name="password" id="password"/>
        </div>
        <div>
           <button type="reset">취소</button> 
            <button type="button" onclick="addUser();">유저 추가</button>            
        </div>
    </form>
</body>
</html>