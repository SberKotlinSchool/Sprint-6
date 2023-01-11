
function confirmDeleted(e) {
	if (confirm('Удаление нельзя отменить! Удалить?')) {
	return true;
	} else {
	e.preventDefault();
	}
};

function confirmBan(e) {
    if (confirm('Подтвердить бан?')) {
        return true;
    } else {
        e.preventDefault();
    }
};
function confirmDeleteBan(e) {
    if (confirm('Подтвердить разбан?')) {
        return true;
    } else {
        e.preventDefault();
    }
};

function confirmRecreateProcedure(e) {
    if (confirm('Пересоздание удалит ранее созданную процедуру. Продолжить?')) {
        return true;
    } else {
        e.preventDefault();
    }
};

function confirmLogout(e) {
	if (confirm('Вы действительно хотите выйти?')) {
	return true;
	} else {
	e.preventDefault();
	}
};

function confirmAccept(e) {
	if (confirm('Вы действительно хотите подтвердить процедуру?')) {
	return true;
	} else {
	e.preventDefault();
	}
};

function confirmDecline(e) {
	if (confirm('Вы действительно хотите отменить процедуру?')) {
	return true;
	} else {
	e.preventDefault();
	}
};


window.addEventListener('load',
	function (e) {
	var d = new Date();
	var day = d.getDate(); if (day<10) day='0'+day;
	var month = d.getMonth() + 1; if (month<10) month='0'+month;
	var year = d.getFullYear();
	document.getElementById("created_procedure_date").value = year+"-"+month+"-"+day+"T12:00";
}, false);

function checkPasswords() {
	let password = document.getElementById("newPassword");
	let repeated = document.getElementById("repeatedPassword");
	if (password.value !== "" && repeated.value && password.value !== repeated.value) {
		alert("Введенные пароли не совпадают!");
		password.value = "";
		repeated.value = "";
		password.focus();
	}
}