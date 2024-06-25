document.addEventListener("DOMContentLoaded", function() {
    // Define the subjects
    const userRole = document.getElementById("roleUser").innerHTML;
    const uploadBtn = document.getElementById("upload-btn");
    console.log(userRole + " vai tro user")
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

    async function getAllMaterials(url){
        try {
            const response = await fetch(url);
            if(!response.ok){
                throw new Error('Network response was not ok ' + response.statusText);
            }
            materials = await response.json()
            displayMaterials(materials)
            console.log(materials)
        }catch (error){
            console.error('Error fetching materials:', error);
        }
    }

    function displayMaterials(materials){
        const materialContainer = document.getElementById("materials-container");
        materialContainer.innerHTML = '';

        materials.forEach(material => {
            const materialElement = document.createElement('div');
            materialElement.classList.add('student-exam-result-up');
            materialElement.innerHTML = `
                <a href="/material-detail?id=${material.id}">
                 <div class="image-fit">
                                    <img alt="pdf-icon" src="https://239114911.e.cdneverest.net/cdnazota/storage_public/azota_assets/images/type_file/pdf.svg">
                                </div>
                                <div class="text-left">
                                    <div class="azt-student-name">
                                        <span class="font-medium truncate">${material.materialsName}</span>
                                    </div>
                                    <div class="info">
                                        <span class="text-slate-500 font-medium">Phân loại: </span>
                                        <span class="text-black font-medium">${material.subjectName}</span>
                                    </div>
                                    <div class="info">
                                        <span class="text-slate-500 font-medium">Người soạn: </span>
                                        <span class="text-black font-medium">${material.teacher.name}</span>
                                    </div>
                                </div>
                </a>
               
            `;
            materialContainer.appendChild(materialElement);
        })
    }

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
        urlParams.set('subject', subject);
        window.history.replaceState({}, '', `${window.location.pathname}?${urlParams}`);
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

    switch (userRole){
        case "PARENT":
            uploadBtn.style.display = "none";
            getAllMaterials(`api/students/allMaterials`);
            break;
        case "STUDENT":
            getAllMaterials(`api/students/allMaterials`);
            uploadBtn.style.display = "none";
            break;
        case "TEACHER":
            getAllMaterials(`/api/teachers/allMaterials`);
            uploadBtn.style.display = "block";


    }
});
