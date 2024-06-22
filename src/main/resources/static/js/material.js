function displaySubjects() {
    const subjects = [
        "Công nghệ", "Giáo dục công dân", "Giáo dục thể chất",
        "Khoa học Tự nhiên", "Lịch sử và Địa lý", "Lịch sử",
        "Địa lý", "Nghệ Thuật", "Ngoại ngữ 1", "Ngoại ngữ 2",
        "Ngữ Văn", "Tin học", "Tiếng Dân tộc thiểu số", "Toán",
        "Đạo Đức", "Tiếng Việt", "Tự nhiên và Xã hội", "Âm nhạc",
        "Mỹ thuật", "Khác"
    ];

    const gradeButtons = document.getElementById('grade-buttons');
    const subjectButtons = document.getElementById('subject-buttons');

    gradeButtons.style.display = 'none';
    subjectButtons.style.display = 'flex';

    subjectButtons.innerHTML = '';  // Clear existing buttons

    subjects.forEach(subject => {
        const buttonItem = document.createElement('div');
        buttonItem.className = 'button-item';

        const button = document.createElement('button');
        button.textContent = subject;
        button.onclick = () => redirectToSubject(subject);

        buttonItem.appendChild(button);
        subjectButtons.appendChild(buttonItem);
    });
}

function redirectTo(grade) {
    window.location.href = `material?grade=${grade}`;
}

function redirectToSubject(subject) {
     window.location.href = `material?subject=${subject}`;
}

document.addEventListener("DOMContentLoaded", () => {
    const roleUser = document.getElementById("roleUser").innerHTML;
    const uploadtn = document.getElementById("upload-btn");
    console.log(uploadtn)

    //Start: add material btn
    switch (roleUser){
        case "STUDENT":
            uploadtn.style.display = "none";
            break;
        case "PARENT":
            uploadtn.style.display = "none";
            break;
    }
    //End: add material btn
})