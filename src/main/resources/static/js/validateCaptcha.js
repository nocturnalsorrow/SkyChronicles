document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('submitBtn').addEventListener('click', function (event) {
        const response = grecaptcha.getResponse();
        if (response.length === 0) {
            event.preventDefault(); // Останавливаем отправку формы
            alert('Please complete the captcha.');
        }
    });
});