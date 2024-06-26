document.addEventListener("DOMContentLoaded", () => {
    const urlParams = new URLSearchParams(window.location.search);
    const userId = urlParams.get('userId') || 1;
    const courseId = urlParams.get('courseId') || null;
    const classListModal = document.getElementById("classListModal");
    const closeClassListModal = document.getElementById("closeClassListModal");
    const feedbackBtn = document.getElementById("feedback-btn");
    const feedbackModal = document.getElementById("feedbackModal");
    const closeFeedbackModal = document.getElementById("closeFeedbackModal");
    const evaluationModal = document.getElementById("evaluationModal");
    const closeEvaluationModalBtn = document.getElementById("closeEvaluationModal");
    const attendanceModal = document.getElementById("attendanceModal");
    const closeAttendanceModalBtn = document.getElementById("closeAttendanceModal");
    const roleUser = document.getElementById("role-user").innerHTML;
    const report = document.getElementsByClassName("report")[0];
    console.log(roleUser)

    let teacherName = "";
    let courseName = "";

    function fetchPosts() {
        fetch(`/api/students/${userId}/courses`)
            .then(response => response.json())
            .then(data => {
                const courseDetail = data.find(course => course.courseId == courseId);
                if (courseDetail) {
                    teacherName = courseDetail.teacherName;
                    courseName = courseDetail.courseCode;
                    viewCourseDetails(courseDetail);
                }
            })
            .catch(error => console.error("Error fetching posts:", error));
    }

    function viewCourseDetails(courseData) {
        const courseNameElement = document.querySelector(".detail-header h3");
        const courseDesc = document.querySelector(".roadmap h3")
        const courseStartTime = document.querySelector(".start-time h3");
        let startTime = courseData.startTime.split('T')[0];
        let parts = startTime.split('-');
        let formattedDateStart = `${parts[2]}/${parts[1]}/${parts[0]}`;
        const courseEndTime = document.querySelector(" .end-time h3");
        let endTime = courseData.endTime.split('T')[0];
        let ends = endTime.split('-');
        let formattedDateEnd = `${ends[2]}/${ends[1]}/${ends[0]}`;
        const teacherName = courseData.teacherName;
        const courseName = courseData.courseName;
        courseNameElement.innerHTML = `<span style="font-weight: 600">Khóa học: </span> ${courseName} - Giáo viên ${teacherName}`;
        courseDesc.innerHTML = `<span style="font-weight: 600">Mô tả khóa học:</span> ${courseData.courseDesc}`;
        courseStartTime.innerHTML = `<span style="font-weight: 600"> Bắt đầu khóa học: </span> ${formattedDateStart}`;
        courseEndTime.innerHTML = `<span style="font-weight: 600"> Kết thúc khóa học: </span> ${formattedDateEnd}`;

    }

    fetchPosts();


    fetchClassList();


    function fetchClassList() {
        fetch(`/api/teachers/courses/${courseId}/students`)
            .then(response => response.json())
            .then(data => {
                populateClassListModal(data);
            })
            .catch(error => console.error("Error fetching class list:", error));
    }

    function populateClassListModal(classListData) {
        const classListBody = document.getElementById("classListBody");
        classListBody.innerHTML = "";

        classListData.forEach((student, index) => {
            const studentName = student.name || student.username;
            const row = `
                <tr>
                    <td>${index + 1}</td>
                    <td>${studentName}</td>
                    ${roleUser === 'TEACHER' ? `<td><a href="#" class="score-link" data-student-id="${student.id}">Chi tiết</a></td>` : ''}
                </tr>
            `;
            classListBody.insertAdjacentHTML("beforeend", row);
        });
        if (roleUser === 'TEACHER') {
            scoreHeader.style.display = 'table-cell';
            attachScoreLinksEvent();
        }
        classListModal.style.display = "block";
        closeClassListModal.addEventListener("click", () => {
            classListModal.style.display = "none";
        });

        window.addEventListener("click", (event) => {
            if (event.target == classListModal) {
                evaluationModal.style.display = "none";
            }
        });
    }




    function openFeedbackModal() {
        const teacherNameInput = document.getElementById("teacherName");
        teacherNameInput.value = teacherName;
        feedbackModal.style.display = "block";
    }

    function closeFeedbackModalFunc() {
        feedbackModal.style.display = "none";
    }

    switch (roleUser){
        case "STUDENT":
            feedbackBtn.addEventListener("click", openFeedbackModal);
            closeFeedbackModal.addEventListener("click", closeFeedbackModalFunc);
            window.addEventListener("click", (event) => {
                if (event.target == feedbackModal) {
                    closeFeedbackModalFunc();
                }
            });
            break;
        case "TEACHER":
            feedbackBtn.style.display = "none";
            report.style.display = "none";
            break;
        case "PARENT":
            feedbackBtn.style.display = "none";
            break;
    }

    document.getElementById("feedbackForm").addEventListener("submit", (event) => {
        event.preventDefault();
        const feedbackContent = document.getElementById("feedbackContent").value;

        fetch(`/api/students/${userId}/courses/${courseId}/feedback`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ feedbackContent: feedbackContent }),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log("Feedback submitted:", data);
                showToast(`<i class="fas fa-check"></i> Đánh giá giáo viên thành công`);
                closeFeedbackModalFunc();
            })
            .catch(error => console.error("Error submitting feedback:", error));
    });

    function showToast(message) {
        const toastContainer = document.getElementById("toastContainer");
        toastContainer.innerHTML = `${message}`;
        toastContainer.classList.add("show");
        setTimeout(() => {
            toastContainer.classList.remove("show");
        }, 3000);
    }

    function fetchCourseGrades(courseId) {
        fetch(`/api/students/${userId}/grades`)
            .then(response => response.json())
            .then(data => {
                console.log(data)
                if (data.length > 0) {
                    const result = data.filter(course => courseName === course.courseCode)
                    openEvaluationModal(result)
                } else {
                    console.log("No grades found for the student in this course.");
                    showToast(`<i class="fas fa-xmark"></i> Giáo viên chưa nhập điểm/báo cáo`);
                }
            })
            .catch(error => console.error("Error fetching course grades:", error));
    }

    function openEvaluationModal(grades) {
        const shortTestScores = grades.filter(grade => grade.resultType === 1).map(grade => grade.resultValue);
        const longTestScores = grades.filter(grade => grade.resultType === 2).map(grade => grade.resultValue);
        const examScores = grades.filter(grade => grade.resultType === 3).map(grade => grade.resultValue);

        // Clear previous table content
        const tableBody = document.getElementById("scoresTableBody");
        tableBody.innerHTML = '';

        const addRowToTable = (type, scores) => {
            const row = document.createElement("tr");

            const typeCell = document.createElement("td");
            typeCell.textContent = type;
            row.appendChild(typeCell);

            const scoreCell = document.createElement("td");
            scoreCell.textContent = scores.join(', ');
            row.appendChild(scoreCell);

            tableBody.appendChild(row);
        };

        const feedbackMessages = [];

        if (shortTestScores.length > 0) {
            addRowToTable('Kiểm tra 15 phút', shortTestScores);
            feedbackMessages.push(`Bạn đã đạt ${shortTestScores.join(', ')} điểm trong kiểm tra 15 phút`);
        }
        if (longTestScores.length > 0) {
            addRowToTable('Kiểm tra 1 tiết', longTestScores);
            feedbackMessages.push(`Bạn đã đạt ${longTestScores.join(', ')} điểm trong kiểm tra 1 tiết`);
        }
        if (examScores.length > 0) {
            addRowToTable('Kiểm tra cuối kỳ', examScores);
            feedbackMessages.push(`Bạn đã đạt ${examScores.join(', ')} điểm trong kiểm tra cuối kỳ`);
        }

        document.getElementById("teacherFeedback").textContent = feedbackMessages.join('. ');

        evaluationModal.style.display = "block";
    }




    function closeEvaluationModal() {
        evaluationModal.style.display = "none";
    }

    document.getElementById("btn-evaluation-details").addEventListener("click", () => {
        fetchCourseGrades(courseId);
    });

    closeEvaluationModalBtn.addEventListener("click", closeEvaluationModal);

    document.getElementById("btn-attendance-details").addEventListener("click", () => {
        fetchAttendanceDetails();
    });




});
