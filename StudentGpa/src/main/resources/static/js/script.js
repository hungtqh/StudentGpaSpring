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
		$("#updateUserInfoButton").prop('disabled', true);
	} else if(newPassword == confirmPassword && password != "") {
			$("#checkPasswordMatch").html("Khớp mật khẩu!");
			$("#updateUserInfoButton").prop('disabled', false);
	} else {
			$("#checkPasswordMatch").html("Mật khẩu không trùng khớp!");
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