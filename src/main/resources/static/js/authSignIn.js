document.addEventListener("DOMContentLoaded", function() {
    const loginForm = document.querySelector("form.login");
    const signupForm = document.querySelector("form.signup");
    const loginText = document.querySelector(".title-text .login");
    const loginBtn = document.querySelector("label.login");
    const signupBtn = document.querySelector("label.signup");
    const signupLink = document.querySelector("form .signup-link a");

    signupBtn.onclick = () => {
        loginForm.style.marginLeft = "-50%";
        loginText.style.marginLeft = "-50%";
    };

    loginBtn.onclick = () => {
        loginForm.style.marginLeft = "0%";
        loginText.style.marginLeft = "0%";
    };

    signupLink.onclick = () => {
        signupBtn.click();
        return false;
    };

    loginForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const username = loginForm.querySelector('input[name="username"]').value;
        const password = loginForm.querySelector('input[name="password"]').value;
        const roleId = loginForm.querySelector('select[name="roleId"]').value;

        if (username && password && roleId) {
            loginForm.submit();
        } else {
            alert('Vui lòng nhập đầy đủ thông tin.');
        }
    });

    signupForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const fullname = signupForm.querySelector('input[name="fullname"]').value;
        const username = signupForm.querySelector('input[name="username"]').value;
        const email = signupForm.querySelector('input[name="email"]').value;
        const password = signupForm.querySelector('input[name="password"]').value;
        const confirmPassword = signupForm.querySelector('input[name="confirmPassword"]').value;
        const phone = signupForm.querySelector('input[name="phone"]').value;
        const userCode = signupForm.querySelector('input[name="userCode"]').value;
        const roleId = signupForm.querySelector('select[name="roleId"]').value;

        if (fullname && username && email && password && confirmPassword && phone && userCode && roleId) {
            if (password === confirmPassword) {
                signupForm.submit();
            } else {
                alert('Mật khẩu xác nhận không khớp.');
            }
        } else {
            alert('Vui lòng nhập đầy đủ thông tin.');
        }
    });
});
var registerForm = document.getElementById("registerForm");
var verifyModal = document.getElementById("verifyModal");
var closeModal = document.getElementsByClassName("close")[0];
var timeLeft = 29;
var timerId;

registerForm.onsubmit = function(event) {
    event.preventDefault(); // Ngăn chặn việc submit form
    verifyModal.style.display = "block"; // Hiển thị modal
    startTimer();
}

closeModal.onclick = function() {
    verifyModal.style.display = "none"; // Ẩn modal
    clearTimeout(timerId);
}

window.onclick = function(event) {
    if (event.target == verifyModal) {
        verifyModal.style.display = "none"; // Ẩn modal nếu click ngoài vùng modal
        clearTimeout(timerId);
    }
}

function startTimer() {
    timerId = setInterval(countdown, 1000);
}

function countdown() {
    if (timeLeft == 0) {
        clearTimeout(timerId);
        // Hết thời gian, có thể thực hiện các hành động cần thiết
    } else {
        document.getElementById("timer").innerHTML = timeLeft;
        timeLeft--;
    }
}

