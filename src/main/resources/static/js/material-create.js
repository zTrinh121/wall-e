document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const grade = urlParams.get('grade');
    const subject = decodeURIComponent(urlParams.get('subject'));
    const status = urlParams.get("status");
    console.log("Trang thai upload tai lieu: " + status);

    document.getElementById('subjectInput').value = `${subject} ${grade}`;

    function showToast(message) {
        var toastContainer = document.getElementById("toastContainer");
        toastContainer.innerHTML = `<i class="fas fa-check-circle"></i> ${message}`;
        toastContainer.classList.add("show");
        setTimeout(() => {
            toastContainer.classList.remove("show");
        }, 3000);
    }
});
