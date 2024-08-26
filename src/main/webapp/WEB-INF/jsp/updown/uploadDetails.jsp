<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Upload Details</title>
</head>
<style type="text/css">
main > h3{text-align:center; }
table {width:fit-content; margin:0.5em auto; padding:1em; border:1px solid black;}
label { display:inline-block; width:5em; padding-right:1em; }
section>div{padding:0.2em;}
</style>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script type="text/javascript">
function deletee(fnum){	
	$.ajax({
		method: "POST",
        enctype: 'multipart/form-data',
        url: '/files/delete/' + fnum,  // 서버의 파일 업로드 처리 URL
        dataType:'json',            
        cache: false,
              
        success: function (res) {            
        	alert(res.deleted ?'삭제성공':'삭제실패');      
            
        },
        error: function (err) {   
        	alert('에러:' + err);
        }
	});
}
</script>
<body>
<main>
    <h3>업로드 상세 정보</h3>
    <h4>번호누르면 삭제</h4>
    <hr>
    <table>
    
        <tr>
            <th>업로드 번호</th>
            <td>${upload.unum}</td>
        </tr>
        <tr>
            <th>제목</th>
            <td>${upload.title}</td>
        </tr>
        <tr>
            <th>작성자</th>
            <td>${upload.writer}</td>
        </tr>
        <tr>
            <th>날짜</th>
            <td>${upload.rdate}</td>
        </tr>
        <tr>
            <th>설명</th>
            <td>${upload.descr}</td>
        </tr> 
        <tr>
            <th>파일 번호</th>
            <th>파일 이름</th>
            <th>파일 크기</th>
        </tr> 
        <c:forEach var="at" items="${upload.attList}"> 
             	<tr>
       			<td><button type="button" onclick="deletee('${at.fnum}')">삭제</button>${at.fnum}</td>
                <td>${at.fname1}</td>
                <td>${at.fsize}<a href='/files/download/${at.fname2}'>다운로드</a>
                <td>
                </td>
                
                </tr>
       </c:forEach>
    </table>
   <a href= '/files/List'>리스트보기</a>
</main>    
</body>
</html>
