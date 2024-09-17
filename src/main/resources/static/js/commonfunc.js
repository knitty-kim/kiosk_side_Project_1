//가입 유형 검사
function validateTypes(id, text) {

    let types = $("#" + id);
    let typesText = $("#" + text);

    if (types.val() == '') {
        typesText.removeClass('text-success');
        types.removeClass('is-valid');

        typesText.addClass('text-danger');
        types.addClass('is-invalid');
        typesText.text("가입 유형이 선택되지 않았습니다").show();

    } else {
        typesText.removeClass('text-danger');
        types.removeClass('is-invalid');

        typesText.addClass('text-success');
        types.addClass('is-valid');
        typesText.hide();

    }

}

//팀/판매자 Name 중복 검사
function validateName(dbId, type, id, text) {

    let clientId = dbId;
    let clientType = type;
    let clientName = $("#" + id);
    let clientNameText = $("#" + text);

    if (!clientType) {
        clientName.val('');
        validateTypes('types', 'validateTypesResult');
        return;
    }

    //영문자 또는 숫자만 가능
    let namePattern = /^[a-zA-Z0-9]+$/;

    if (!namePattern.test(clientName.val())) {
        clientNameText.removeClass('text-success');
        clientNameText.removeClass('text-danger');
        clientName.removeClass('is-invalid');

        clientNameText.addClass('text-danger');
        clientName.addClass('is-invalid');
        clientNameText.text("아이디는 영문자 또는 숫자만 가능합니다").show();
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
                clientNameText.text(response[1]).show();
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
        phNumberText.text("연락처 형식이 올바르지 않습니다").show();

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
        emailText.text("이메일 형식이 올바르지 않습니다").show();

    } else {

        emailText.removeClass('text-danger');
        email.removeClass('is-invalid');

        emailText.addClass('text-success');
        email.addClass('is-valid');
        emailText.hide();
    }

}

//수정 확정
function confirmEdit() {

    let hasInvalidField = $('form').find('input').is('.is-invalid');

    if (hasInvalidField) {
        alert('올바르지 않은 값이 있습니다!');
        return;
    }

    let emptyFields = $('form').find('input').filter(function() {
        return $(this).val().trim() === '';  // 빈 값 또는 공백만 포함된 값
    });

    if (emptyFields.length > 0) {
        alert('입력되지 않은 값이 있습니다!');
        return;
    }

    if (confirm("수정을 확정하시겠습니까?")) {
        //document.querySelector('form').submit();
        //위 코드로 submit하는 경우, submit 이벤트 핸들러가 호출되지 않는다
        $('form').submit();
    }
}