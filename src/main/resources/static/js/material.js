document.addEventListener("DOMContentLoaded", function() {
    const currentUrl = window.location.href;
    const url = new URL(currentUrl);
    const toast = document.getElementById("toast");
    const toastInfo = document.getElementById("toast-info");
    const userRole = document.getElementById("roleUser").innerHTML;
    const uploadBtn = document.getElementById("upload-btn");

    const subjects = [
        "Toán", "Lý", "Hóa", "Sinh học", "Văn", "Sử", "Địa lý", "GDCD", "Tiếng Anh", "Tin học"
    ];

    const gradeSelect = document.getElementById('grade-select');
    if (gradeSelect) {
        for (let i = 1; i <= 12; i++) {
            const option = document.createElement('option');
            option.value = i;
            option.textContent = `Khối ${i}`;
            gradeSelect.appendChild(option);
        }

        gradeSelect.addEventListener('change', function() {
            const selectedGrade = this.value;
            selectGrade(selectedGrade);
        });
    }

    const subjectSelect = document.getElementById('subject-select');
    if (subjectSelect) {
        subjects.forEach(subject => {
            const option = document.createElement('option');
            option.value = subject;
            option.textContent = subject;
            subjectSelect.appendChild(option);
        });

        subjectSelect.addEventListener('change', function() {
            const selectedSubject = this.value;
            selectSubject(selectedSubject);
        });
    }

    async function getAllMaterials(apiUrl) {
        try {
            const response = await fetch(apiUrl);
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            const materials = await response.json();
            filterAndDisplayMaterials(materials);
        } catch (error) {
            console.error('Error fetching materials:', error);
        }
    }

    function filterAndDisplayMaterials(materials) {
        const grade = gradeSelect.value;
        const subject = subjectSelect.value;

        const filteredMaterials = materials.filter(material => {
            const [materialSubject, materialGrade] = material.subjectName.split(" ");
            const matchesGrade = grade ? grade.toString() === materialGrade : true;
            const matchesSubject = subject ? subject === materialSubject : true;
            return matchesGrade && matchesSubject;
        });

        displayMaterials(filteredMaterials);
    }

    function displayMaterials(materials) {
        const materialContainer = document.getElementById("materials-container");
        materialContainer.innerHTML = '';

        if (materials.length !== 0) {
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
            });
        } else {
            showToast("Không tìm thấy tài liệu phù hợp", "red", "times-circle");
            materialContainer.innerHTML = `<div class="no-result">Không tìm thấy tài liệu phù hợp</div>`;
        }
    }

    function selectGrade(grade) {
        const urlParams = new URLSearchParams(window.location.search);
        urlParams.set('grade', grade);
        window.history.replaceState({}, '', `${window.location.pathname}?${urlParams}`);
        showSubjectSelect();
        fetchMaterials();
    }

    function showSubjectSelect() {
        const subjectChoose = document.getElementsByClassName("button-group-subject")[0];
        const subjectSelectContainer = document.getElementById('subject-select');
        subjectChoose.style.display = "block";
        subjectSelectContainer.style.display = 'block';

        const selectedSubject = url.searchParams.get('subject');
        if (selectedSubject) {
            subjectSelect.value = selectedSubject;
        }
    }

    function selectSubject(subject) {
        const urlParams = new URLSearchParams(window.location.search);
        const currentSubject = urlParams.get('subject');

        if (currentSubject === subject) {
            urlParams.delete('subject');
            subjectSelect.value = "";
        } else {
            urlParams.set('subject', subject);
        }

        window.history.replaceState({}, '', `${window.location.pathname}?${urlParams}`);
        fetchMaterials();
    }

    function fetchMaterials() {
        if (userRole === "PARENT" || userRole === "STUDENT") {
            getAllMaterials(`/api/student/allMaterials`);
        } else if (userRole === "TEACHER") {
            getAllMaterials(`/api/teacher/allMaterials`);
        }
    }

    switch (userRole) {
        case "PARENT":
            uploadBtn.style.display = "none";
            getAllMaterials(`/api/student/allMaterials`);
            break;
        case "STUDENT":
            getAllMaterials(`/api/student/allMaterials`);
            uploadBtn.style.display = "none";
            break;
        case "TEACHER":
            getAllMaterials(`/api/teacher/allMaterials`);
            uploadBtn.style.display = "block";
            break;
    }

    function showToast(message, backgroundColor, icon) {
        toastInfo.innerHTML = `<i class="fas fa-${icon}"></i> ${message}`;
        toast.style.backgroundColor = backgroundColor;
        toast.style.display = "block";
        setTimeout(function () {
            toast.style.display = "none";
        }, 3000);
    }
});
