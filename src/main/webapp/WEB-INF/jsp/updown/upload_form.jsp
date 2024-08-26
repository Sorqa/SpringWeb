<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Upload Form</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script type="text/javascript">

$(document).ready(function() {
    $('#btnUpload').on('click', function(event) {
        event.preventDefault();
        
        var form = $('#uploadForm')[0];  // jQuery 오브젝트로부터 JavaScript 표준 오브젝트 추출
        var data = new FormData(form);
        
        $('#btnUpload').prop('disabled', true);
       
        $.ajax({
            method: "POST",
            enctype: 'multipart/form-data',
            url: "/files/upload",  // 서버의 파일 업로드 처리 URL
            data: data,
            dataType: 'json',  // 서버에서 응답할 데이터 타입
            processData: false,  // Query String 변환 하지 않도록 설정
            contentType: false,  // "application/x-www-form-urlencoded; charset=UTF-8"로 설정하지 않음
            cache: false,
            timeout: 600000,  // 시간 제한 없음
            success: function (response) {
                $('#btnUpload').prop('disabled', false);
                if (response.status === 'success') {
                    alert('파일 업로드 성공!');
                    location.href = '/files/uploadDetails?unum=' + response.unum;  // 서버에서 반환한 unum 사용
                    
                } else {
                    alert('파일 업로드 실패: ' + response.message);
                }
            },
            error: function (e) {
                $('#btnUpload').prop('disabled', false);
                alert('파일 업로드 실패!');
            }
        });
    });
});

</script>
</head>
<body>
<h3>Spring boot 파일 업로드 테스트</h3>
<form id="uploadForm" enctype="multipart/form-data">
   작성자 <input type="text" name="author" value="smith"><br>
   File <input type="file" name="files" multiple="multiple"><br>
   제목 <input type="text" name="title"><br>
   설명	<input type="text" name="filedesc"><br>
   <button type="button" id="btnUpload">업로드</button>
   <a href= '/files/List'>리스트보기</a>
</form>
</body>
</html>