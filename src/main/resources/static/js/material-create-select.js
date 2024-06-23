document.addEventListener("DOMContentLoaded", function() {
    // Define the subjects
    const subjects = [
        "Toán", "Lý", "Hóa", "Sinh học", "Văn", "Sử", "Địa lý", "GDCD",
        "Công nghệ", "Tiếng Anh", "Tiếng Pháp", "Tiếng Nhật", "Thể dục", "Âm nhạc",
        "Mỹ thuật", "GDQP-AN", "Tin học", "Khác"
    ];

    // Create grade buttons
    const gradeButtonsContainer = document.getElementById('grade-buttons');
    if (gradeButtonsContainer) {
        for (let i = 1; i <= 12; i++) {
            const button = document.createElement('button');
            button.textContent = `Khối ${i}`;
            button.onclick = () => selectGrade(i, button);
            gradeButtonsContainer.appendChild(button);
        }
    }

    // Handle grade selection
    function selectGrade(grade, button) {
        sessionStorage.setItem('selectedGrade', grade);
        console.log(button)
        showSubjectButtons();
        highlightSelectedButton(button, 'grade');
    }

    // Show subject buttons
    function showSubjectButtons() {
        const subjectButtonsContainer = document.getElementById('subject-buttons');
        subjectButtonsContainer.style.display = 'flex';
        subjectButtonsContainer.innerHTML = ''; // Clear previous buttons

        subjects.forEach(subject => {
            const button = document.createElement('button');
            button.textContent = subject;
            button.onclick = () => selectSubject(subject, button);
            subjectButtonsContainer.appendChild(button);
        });
    }

    // Handle subject selection
    function selectSubject(subject, button) {
        const grade = sessionStorage.getItem('selectedGrade');
        window.location.href = `material-create?grade=${grade}&subject=${subject}`;
        highlightSelectedButton(button, 'subject');
    }

    // Highlight selected button
    function highlightSelectedButton(button, type) {
        // Remove active class from previously selected button of the same type
        const buttons = document.querySelectorAll(`#${type}-buttons button`);
        buttons.forEach(btn => btn.classList.remove('active'));

        // Add active class to the clicked button
        button.classList.add('active');
    }

    // On page load, check if grade is already selected
    const grade = sessionStorage.getItem('selectedGrade');
    if (grade) {
        showSubjectButtons();
        const gradeButtons = document.querySelectorAll('#grade-buttons button');
        gradeButtons.forEach(button => {
            if (button.textContent === `Khối ${grade}`) {
                button.classList.add('active');
            }
        });
    }
});