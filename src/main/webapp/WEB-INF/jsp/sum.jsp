<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>덧셈 구하기</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script type="text/javascript">
function sum(){	
	let obj ={};
	obj.a = $('#a').val();
	obj.b = $('#b').val();
    $.ajax({
        url:'/add',
        method:'post',
        cache:false,
        data:obj,
        dataType:'json',  
        success:function(res){  
            $('#ans').val(res.ans);
        },
        error:function(xhr, status, err){  // Corrected typo here
            alert('에러:' + err);
        }
    });
}
</script>
</head>
<body>
<h3>덧셈</h3>

<input type="number" name="a" id="a"> + <input type="number" name="b" id="b"> =
<input type="number" id="ans">

<button type="button" onclick="sum();">계산</button>

</body>
</html>