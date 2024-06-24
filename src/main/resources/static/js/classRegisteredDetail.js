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
        const courseStartTime = document.querySelector(".start-time p");
        let startTime = courseData.startTime.split('T')[0]; // Get only the date part
        let parts = startTime.split('-');
        let formattedDateStart = `${parts[2]}/${parts[1]}/${parts[0]}`;
        const courseEndTime = document.querySelector(" .end-time p");
        let endTime = courseData.endTime.split('T')[0];
        let ends = endTime.split('-');
        let formattedDateEnd = `${ends[2]}/${ends[1]}/${ends[0]}`;
        const teacherName = courseData.teacherName;
        const courseName = courseData.courseName;
        courseNameElement.textContent = `Khóa học: ${courseName} - Giáo viên ${teacherName}`;
        courseDesc.textContent = `Mô tả khóa học: ${courseData.courseDesc}`;
        courseStartTime.textContent = `Bắt đầu khóa học: ${formattedDateStart}`;
        courseEndTime.textContent = `Kết thúc khóa học: ${formattedDateEnd}`;

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
        console.log(classListData)
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

    function attachScoreLinksEvent() {
        const scoreLinks = document.querySelectorAll(".score-link");
        scoreLinks.forEach(link => {
            link.addEventListener('click', function(event) {
                event.preventDefault();
                const studentId = this.getAttribute("data-student-id");
                console.log(studentId)
                showEvaluationModal(studentId);
            });
        });
    }

    

    function showEvaluationModal(studentId) {
        document.getElementById("scoresTableBody").innerHTML = "";

        const scores = [
            { exam: "Midterm", score: 85 },
            { exam: "Final", score: 90 }
        ];

        scores.forEach(score => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${score.exam}</td>
                <td>${score.score}</td>
            `;
            document.getElementById("scoresTableBody").appendChild(row);
        });

        evaluationModal.style.display = 'block';
    }
    window.addEventListener('click', function (event) {
        if (event.target == evaluationModal) {
            evaluationModal.style.display = "none";
        }
    });


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
        const shortTestScores = grades.filter(grade => grade.resultType === 1);
        const longTestScores = grades.filter(grade => grade.resultType === 2);
        const examScores = grades.filter(grade => grade.resultType === 3);

        // Clear previous table content
        const tableBody = document.getElementById("scoresTableBody");
        tableBody.innerHTML = '';

        const addScoresToTable = (scores, type) => {
            scores.forEach(score => {
                const row = document.createElement("tr");

                const typeCell = document.createElement("td");
                typeCell.textContent = type;
                row.appendChild(typeCell);

                const scoreCell = document.createElement("td");
                scoreCell.textContent = score.resultValue;
                row.appendChild(scoreCell);

                tableBody.appendChild(row);
            });
        };

        addScoresToTable(shortTestScores, 'Kiểm tra 15 phút');
        addScoresToTable(longTestScores, 'Kiểm tra 1 tiết');
        addScoresToTable(examScores, 'Kiểm tra cuối kỳ');

        document.getElementById("teacherFeedback").textContent = grades.map(grade => `Bạn đã đạt ${grade.resultValue} điểm trong ${grade.resultType === 1 ? 'kiểm tra 15 phút' : grade.resultType === 2 ? 'kiểm tra 1 tiết' : 'kiểm tra cuối kỳ'}`).join('. ');

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
