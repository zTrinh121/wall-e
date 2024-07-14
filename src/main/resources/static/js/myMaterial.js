document.addEventListener("DOMContentLoaded", function() {
    const currentUrl = window.location.href;
    const url = new URL(currentUrl);
    const gradeValue = url.searchParams.get('grade');
    const subject = url.searchParams.get('subject');
    const toast = document.getElementById("toast");
    const toastInfo = document.getElementById("toast-info");
    const userRole = document.getElementById("roleUser").innerHTML;
    const uploadBtn = document.getElementById("upload-btn");

    const subjects = [
        "Toán", "Lý", "Hóa", "Sinh học", "Văn", "Sử", "Địa lý", "GDCD", "Tiếng Anh", "Tin học"
    ];

    const gradeButtonsContainer = document.getElementById('grade-buttons');
    if (gradeButtonsContainer) {
        for (let i = 1; i <= 12; i++) {
            const button = document.createElement('button');
            button.textContent = `Khối ${i}`;
            button.onclick = () => selectGrade(i, button);
            gradeButtonsContainer.appendChild(button);
        }
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
        const urlParams = new URLSearchParams(window.location.search);
        const grade = urlParams.get('grade');
        const subject = urlParams.get('subject');

        const filteredMaterials = materials.filter(material => {
            const [materialSubject, materialGrade] = material.subjectName.split(" ");
            const matchesGrade = grade ? grade === materialGrade : true;
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
                
                <a href="/material-detail?id=${material.id}&modify=true">
                <i class="fas fa-edit edit-material-btn" data-toggle="modal" data-target="#materialModal" data-material-id="${material.id}"></i>
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

                // Add event listener for edit button
                const editButton = materialElement.querySelector('.edit-material-btn');

                editButton.addEventListener('click', () => {

                    window.location.replace(`/material-detail?id=${material.id}&modify=true`);

                });
            });
        } else {
            showToast("Không tìm thấy tài liệu phù hợp", "red", "times-circle");
            materialContainer.innerHTML = `<div class="no-result">Không tìm thấy tài liệu phù hợp</div>`;
        }
    }

    async function fetchMaterialDetails(materialId) {
        console.log(materialId)
        try {
            const response = await fetch(`/api/teacher/allMaterials`);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const materials = await response.json();
            const material = materials.find(mat => mat.id === materialId);
            console.log(material)
            if (!material) {
                throw new Error('Material not found');
            }

            populateModal(material);
        } catch (error) {
            console.error('Error fetching material details:', error);
        }
    }

    function populateModal(material) {
        const modalTitle = document.getElementById('materialModalLabel');
        modalTitle.textContent = `Edit Material: ${material.materialsName}`;
        modal.style.display = "block"
        // Example: Populate other modal fields
        const materialNameSpan = document.querySelector('#materialModal .azt-student-name span');
        if (materialNameSpan) {
            materialNameSpan.textContent = material.materialsName;
        }

        // Optionally, populate other fields as needed
    }


    function selectGrade(grade, button) {
        const urlParams = new URLSearchParams(window.location.search);
        urlParams.set('grade', grade);
        window.history.replaceState({}, '', `${window.location.pathname}?${urlParams}`);
        showSubjectButtons();
        highlightSelectedButton(button, 'grade');
        fetchMaterials();
    }

    function showSubjectButtons() {
        const subjectChoose = document.getElementsByClassName("button-group-subject")[0];
        const subjectButtonsContainer = document.getElementById('subject-buttons');
        subjectChoose.style.display = "block";
        subjectButtonsContainer.style.display = 'flex';
        subjectButtonsContainer.innerHTML = '';

        subjects.forEach(subject => {
            const button = document.createElement('button');
            button.textContent = subject;
            button.onclick = () => selectSubject(subject, button);
            subjectButtonsContainer.appendChild(button);
        });

        const selectedSubject = url.searchParams.get('subject');
        if (selectedSubject) {
            const subjectButtons = document.querySelectorAll('#subject-buttons button');
            subjectButtons.forEach(button => {
                if (button.textContent === selectedSubject) {
                    button.classList.add('active');
                }
            });
        }
    }

    function selectSubject(subject, button) {
        const urlParams = new URLSearchParams(window.location.search);
        const currentSubject = urlParams.get('subject');

        if (currentSubject === subject) {
            urlParams.delete('subject');
            button.classList.remove('active');
        } else {
            urlParams.set('subject', subject);
            highlightSelectedButton(button, 'subject');
        }

        window.history.replaceState({}, '', `${window.location.pathname}?${urlParams}`);
        fetchMaterials();
    }

    function highlightSelectedButton(button, type) {
        const buttons = document.querySelectorAll(`#${type}-buttons button`);
        buttons.forEach(btn => btn.classList.remove('active'));
        button.classList.add('active');
    }

    function fetchMaterials() {
            getAllMaterials(`/api/student/materials`);
    }

    if (gradeValue) {
        showSubjectButtons();
        const gradeButtons = document.querySelectorAll('#grade-buttons button');
        gradeButtons.forEach(button => {
            if (button.textContent === `Khối ${gradeValue}`) {
                button.classList.add('active');
            }
        });
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

