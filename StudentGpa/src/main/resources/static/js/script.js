function alertFadeOut() {
	if (document.querySelector(".alert") != null) {
		setTimeout(() => {
			document.querySelector(".alert").style.visibility = "hidden";
			}, 2000
		);
	}
}

function checkPasswordMatch() {

	let password = $("#password").val();
	let newPassword = $("#newPassword").val();
	let confirmPassword = $("#confirmPassword").val();
	
	if(password == "" || newPassword == "" || confirmPassword == "") {
		$("#checkPasswordMatch").html("");
	} else if(newPassword == confirmPassword && password != "") {
		$("#checkPasswordMatch").html("Khớp mật khẩu!");
		$("#checkPasswordMatch").css("color", "green");
		$("#updateUserInfoButton").prop('disabled', false);
		
		let pattern=  /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,30}$/;
		
		if (newPassword.length > 0) {
			if (password != "" && newPassword == password) {
				$("#checkPasswordMatch").html("Mật khẩu mới không được trùng mật khẩu cũ!");
				$("#checkPasswordMatch").css("color", "red");
				$("#updateUserInfoButton").prop('disabled', true);
			} else if(!newPassword.match(pattern)) {
				$("#checkPasswordMatch").html
				("Mật khẩu từ 8 - 30 ký tự, bao gồm 1 ký tự số, 1 ký tự thường, 1 ký tự hoa và một ký tự đặc biệt");
				$("#checkPasswordMatch").css("color", "red");
				$("#updateUserInfoButton").prop('disabled', true);
			} else if (newPassword.length >= 8 && newPassword.length <= 12) {
				$("#checkPasswordMatch").html("Mật khẩu yếu");
				$("#checkPasswordMatch").css("color", "red");
			} else if (newPassword.length >= 13 && newPassword.length <= 18) {
				$("#checkPasswordMatch").html("Mật khẩu trung bình");
				$("#checkPasswordMatch").css("color", "orange");
			} else if (newPassword.length >= 19 && newPassword.length <= 24) {
				$("#checkPasswordMatch").html("Mật khẩu khá");
				$("#checkPasswordMatch").css("color", "blue");
			} else if (newPassword.length >= 25 && newPassword.length <= 30) {
				$("#checkPasswordMatch").html("Mật khẩu tốt");
				$("#checkPasswordMatch").css("color", "green");
			}
		}
	} else {
		$("#checkPasswordMatch").html("Mật khẩu không trùng khớp!");
		$("#checkPasswordMatch").css("color", "red");
		$("#updateUserInfoButton").prop('disabled', true);
	}

}

$(document).ready(function() {
	alertFadeOut();
	checkPasswordMatch();
	$("#password").keyup(checkPasswordMatch);
	$("#confirmPassword").keyup(checkPasswordMatch);
	$("#newPassword").keyup(checkPasswordMatch);
});