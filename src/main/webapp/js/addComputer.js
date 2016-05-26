// Check a date as dd-mm-yyyy
function isDateValid(myDate) {
	return new RegExp("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$").test(myDate) == true;
}

function validateForm() {
	var computerName = $("#computerName").val();
	var introduced = $("#introduced").val();
	var discontinued = $("#discontinued").val();
	var companyId = $("#companyId").val();

	$("#errorMsg").hide();

	if (computerName == "" || computerName.length > 255) {
		$("#errorMsg").html("<strong>Error!</strong> name cannot be empty or is too big!");
		$("#errorMsg").show();
		return false;
	}

	if (introduced != "" && isDateValid(introduced) == false) {
		$("#errorMsg").html("<strong>Error!</strong> introduced date must be dd-mm-yyyy!");
		$("#errorMsg").show();
		return false;
	}
	if (discontinued != "" && isDateValid(discontinued) == false) {
		$("#errorMsg").html("<strong>Error!</strong> discontinued date must be dd-mm-yyyy!");
		$("#errorMsg").show();
		return false;
	}
	
	if (computerName == "" || computerName.length > 255) {
		$("#errorMsg").html("<strong>Error!</strong> name cannot be empty or is too big!");
		$("#errorMsg").show();
		return false;
	}

	var introducedSplitted = introduced.split('-');
	var discontinuedSplitted = discontinued.split('-');

	if (introduced != "" && discontinued != "" && new Date(introducedSplitted[2]+'-'+introducedSplitted[1]+'-'+introducedSplitted[0]).getTime() > new Date(discontinuedSplitted[2]+'-'+discontinuedSplitted[1]+'-'+discontinuedSplitted[0]).getTime()) {
		$("#errorMsg").html("<strong>Error!</strong> introduced date must be less than discontinued date!");
		$("#errorMsg").show();
		return false;
	}

	return true;
}