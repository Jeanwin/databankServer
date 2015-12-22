// JavaScript Document
window.onload = function () {
	if (navigator.appName === "Microsoft Internet Explorer") {
		for (var i = 0; i < document.images.length; i++) {
			var img = document.images[i];
			var imgName = img.src.toUpperCase();
			if (imgName.substring(imgName.length - 3, imgName.length) === "PNG") {
				var imgID = (img.id) ? "id='" + img.id + "' " : "";
				var imgTitle = "";
				var strNewHTML = "<span " + imgID + imgTitle + " style=\"" + "width:84px; display: block; height:53px; top:0; left:0;" + "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader" + "(src='" + img.src + "', sizingMethod='scale');\"></span>";
				img.outerHTML = strNewHTML;
				i = i - 1;
			}
		}
	}
};
function searchGroupCon(username, password) {
	if (username.value.length === 0) {
		alert("Username can not be empty!");
		username.focus();
		return false;
	}
	if (password.value.length === 0) {
		alert("Password can not be empty!");
		password.focus();
		return false;
	}
	if (username.value === "admin" && password.value === "admin") {
		window.location = "vw/index.html";
		return true;
	}else{
		alert("Username/Password is incorrect! \n Please try again");
		return false;
	}
}

