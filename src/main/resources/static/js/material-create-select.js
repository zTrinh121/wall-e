document.addEventListener("DOMContentLoaded", function() {
    // Define the subjects
    const subjects = [
        "Toán", "Lý", "Hóa", "Sinh học", "Văn", "Sử", "Địa lý", "GDCD",
        "Công nghệ", "Tiếng Anh", "Tiếng Pháp", "Tiếng Nhật", "Thể dục", "Âm nhạc",
        "Mỹ thuật", "GDQP-AN", "Tin học", "Khác"
    ];

    // Create grade buttons
    const gradeButtonsContainer = document.getElementById('grade-buttons');
    console.log(gradeButtonsContainer);
    if (gradeButtonsContainer) {
        for (let i = 1; i <= 12; i++) {
            const button = document.createElement('button');
            button.textContent = `Khối ${i}`;
            button.onclick = () => selectGrade(i);
            gradeButtonsContainer.appendChild(button);
        }
    }

    // Handle grade selection
    function selectGrade(grade) {
        sessionStorage.setItem('selectedGrade', grade);
        showSubjectButtons();
    }

    // Show subject buttons
    function showSubjectButtons() {
        const subjectButtonsContainer = document.getElementById('subject-buttons');
        subjectButtonsContainer.style.display = 'flex';
        subjectButtonsContainer.innerHTML = ''; // Clear previous buttons

        subjects.forEach(subject => {
            const button = document.createElement('button');
            button.textContent = subject;
            button.onclick = () => selectSubject(subject);
            subjectButtonsContainer.appendChild(button);
        });
    }

    // Handle subject selection
    function selectSubject(subject) {
        const grade = sessionStorage.getItem('selectedGrade');
        window.location.href = `material-create?grade=${grade}&subject=${subject}`;
    }

    // On page load, check if grade is already selected
    const grade = sessionStorage.getItem('selectedGrade');
    if (grade) {
        showSubjectButtons();
    }
});
