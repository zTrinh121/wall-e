document.addEventListener("DOMContentLoaded", function() {
    // Define the subjects
    const subjects = [
        "Toán", "Lý", "Hóa", "Sinh học", "Văn", "Sử", "Địa lý", "GDCD", "Tiếng Anh", "Tin học"
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
        const urlParams = new URLSearchParams(window.location.search);
        urlParams.set('grade', grade);
        urlParams.delete('subject'); // Remove subject if grade is changed
        window.history.replaceState({}, '', `${window.location.pathname}?${urlParams}`);
        showSubjectButtons();
        highlightSelectedButton(button, 'grade');
    }

    // Show subject buttons
    function showSubjectButtons() {
        const subjectChoose = document.getElementsByClassName("button-group-subject")[0];
        const subjectButtonsContainer = document.getElementById('subject-buttons');
        subjectChoose.style.display = "block";
        subjectButtonsContainer.style.display = 'flex';
        subjectButtonsContainer.innerHTML = ''; // Clear previous buttons

        subjects.forEach(subject => {
            const button = document.createElement('button');
            button.textContent = subject;
            button.onclick = () => selectSubject(subject, button);
            subjectButtonsContainer.appendChild(button);
        });

        // Highlight selected subject if available in URL
        const urlParams = new URLSearchParams(window.location.search);
        const selectedSubject = urlParams.get('subject');
        if (selectedSubject) {
            const subjectButtons = document.querySelectorAll('#subject-buttons button');
            subjectButtons.forEach(button => {
                if (button.textContent === selectedSubject) {
                    button.classList.add('active');
                }
            });
        }
    }

    // Handle subject selection
    function selectSubject(subject, button) {
        const urlParams = new URLSearchParams(window.location.search);
        const grade = urlParams.get('grade');
        urlParams.set('subject', subject);
        window.history.replaceState({}, '', `${window.location.pathname}?${urlParams}`);
        highlightSelectedButton(button, 'subject');

        // Redirect to material-create with selected grade and subject
        window.location.href = `material-create?grade=${grade}&subject=${subject}`;
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
    const urlParams = new URLSearchParams(window.location.search);
    const grade = urlParams.get('grade');
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
