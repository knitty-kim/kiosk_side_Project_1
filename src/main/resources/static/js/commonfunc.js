//판매자 Name 중복 검사
function validateName(dbId, type, id, text) {

    let clientId = dbId;
    let clientType = type;
    let clientName = $("#" + id);
    let clientNameText = $("#" + text);

    //영문자 또는 숫자만 가능
    let namePattern = /^[a-zA-Z0-9]+$/;

    if (!namePattern.test(clientName.val())) {
        clientNameText.removeClass('text-success');
        clientNameText.removeClass('text-danger');
        clientName.removeClass('is-invalid');

        clientNameText.addClass('text-danger');
        clientName.addClass('is-invalid');
        clientNameText.text("아이디는 영문자 또는 숫자만 가능합니다");
        return;
    }

    if (clientType == 'seller' || clientType == 'team') {

        const url = clientType === 'seller' ? '/seller/validate' : '/team/validate';

        $.ajax({
            url: url,
            type: 'GET',
            data: {
                id: clientId,
                name: clientName.val()
            },
            success: function (response) {
                clientNameText.removeClass('text-success text-danger');
                clientName.removeClass('is-valid is-invalid');

                if (response[0]) {
                    clientNameText.addClass('text-success');
                    clientName.addClass('is-valid');
                } else {
                    clientNameText.addClass('text-danger');
                    clientName.addClass('is-invalid');
                    clientName.focus();
                }
                clientNameText.text(response[1]);
            },
            error: function (error) {
                console.error('Error sending data', error);
            }
        });

    }

}

//연락처 형식 검사
function validatePhNumber(id, text) {

    let phNumber = $("#" + id);
    let phNumberText = $("#" + text);
    let phNumberPattern = /^\d{3,4}-\d{3,4}-\d{3,4}$/;

    if (!phNumberPattern.test(phNumber.val())) {
        phNumberText.removeClass('text-success');
        phNumber.removeClass('is-valid');

        phNumberText.addClass('text-danger');
        phNumber.addClass('is-invalid');
        phNumberText.text("연락처 형식이 올바르지 않습니다");

    } else {

        phNumberText.removeClass('text-danger');
        phNumber.removeClass('is-invalid');

        phNumberText.addClass('text-success');
        phNumber.addClass('is-valid');
        phNumberText.hide();
    }

}

//이메일 형식 검사
function validateEmail(id, text) {

    let email = $("#" + id);
    let emailText = $("#" + text);
    let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!emailPattern.test(email.val())) {
        emailText.removeClass('text-success');
        email.removeClass('is-valid');

        emailText.addClass('text-danger');
        email.addClass('is-invalid');
        emailText.text("이메일 형식이 올바르지 않습니다");

    } else {

        emailText.removeClass('text-danger');
        email.removeClass('is-invalid');

        emailText.addClass('text-success');
        email.addClass('is-valid');
        emailText.hide();
    }

}