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
        fetch(`/api/students/course/${courseId}/students`)
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
            const row = `
                <tr>
                    <td>${index + 1}</td>
                    <td>${student.studentName}</td>
                </tr>
            `;
            classListBody.insertAdjacentHTML("beforeend", row);
        });

        classListModal.style.display = "block";
        closeClassListModal.addEventListener("click", () => {
            classListModal.style.display = "none";
        });

        window.addEventListener("click", (event) => {
            if (event.target == classListModal) {
                classListModal.style.display = "none";
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
        const shortTestScore = grades.find(grade => grade.resultType === 1);
        const longTestScore = grades.find(grade => grade.resultType === 2);

        document.getElementById("shortTestScore").textContent = shortTestScore ? shortTestScore.resultValue : "N/A";
        document.getElementById("longTestScore").textContent = longTestScore ? longTestScore.resultValue : "N/A";
        document.getElementById("teacherFeedback").textContent = grades.map(grade => `Bạn đã đạt ${grade.resultValue} điểm trong ${grade.resultType === 1 ? 'kiểm tra 15 phút' : 'kiểm tra 1 tiết'}`).join('. ');

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
