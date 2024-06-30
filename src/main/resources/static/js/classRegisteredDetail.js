document.addEventListener("DOMContentLoaded", async () => {
    const urlParams = new URLSearchParams(window.location.search);
    const userId = parseInt(urlParams.get('userId'));
    const courseId = parseInt(urlParams.get('courseId'));
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
    let apiGradeUrl;
    let apiCourseDetail;
    let studentParentId;
    console.log(roleUser)

    let teacherName = "";
    let courseName = "";

    switch (roleUser) {
        case "STUDENT":
            apiGradeUrl = `/api/student/${userId}/grades`;
            apiCourseDetail = `/api/student/${userId}/courses`;
            break;
        case "TEACHER":
            apiGradeUrl = ``;
            apiCourseDetail = `api/teacher/${userId}/courses`;
            break;
        case "PARENT":
            await fetchStudents();
            apiGradeUrl = `/api/student/${studentParentId}/grades`;
            apiCourseDetail = `/api/student/${studentParentId}/courses`;
            break;
    }


    async function fetchStudents() {
            try {
                const response = await fetch(`/api/parent/studentsByParent`);
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                const data = await response.json();
                studentParentId = data[0].id;
                console.log(studentParentId + " id student")
                // After fetching students, proceed to fetch posts
                await fetchPosts();
            } catch (error) {
                console.error("Error fetching students:", error);
                header.style.display = "none";
                noResultDiv.style.display = "block";
                noResultDiv.innerHTML = `Hãy <a class="mapping" href="/mapping"> kết nối </a> với con bạn để truy cập vào khóa học`;
            }
        }

        async function fetchPosts() {
            try {
                await fetch(apiCourseDetail)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Network response was not ok');
                        }
                        return response.json();
                    })
                    .then(data => {
                        const courseDetail = data.filter(course => course.courseId == courseId);
                        if (courseDetail[0]) {
                            teacherName = courseDetail.teacherName;
                            courseName = courseDetail.courseName;
                            viewCourseDetails(courseDetail[0]);
                        } else {
                            console.log(`Course with courseId ${courseId} not found.`);
                        }
                    })
                    .catch(error => console.error("Error fetching course details:", error));
            } catch (error) {
                console.error("Error in fetchPosts:", error);
            }
        }
        function viewCourseDetails(courseData) {
            const courseNameElement = document.querySelector(".detail-header h3");
            const courseDesc = document.querySelector(".roadmap h3")
            const courseStartTime = document.querySelector(".start-time h3");
            let startTime, endTime, formattedDateStart, parts, courseEndTime, ends, formattedDateEnd, courseDetailDescription;
            if(roleUser === "TEACHER"){
                 startTime = courseData.courseEndDate.split('T')[0];
                 endTime = courseData.courseEndDate.split('T')[0];
                 courseDetailDescription = courseData.courseDescription;
            }else{
                startTime = courseData.startTime.split('T')[0];
                endTime = courseData.endTime.split('T')[0];
                courseDetailDescription = courseData.courseDesc
            }
            parts = startTime.split('-');
            formattedDateStart = `${parts[2]}/${parts[1]}/${parts[0]}`;
            courseEndTime = document.querySelector(" .end-time h3");
            ends = endTime.split('-');
            formattedDateEnd = `${ends[2]}/${ends[1]}/${ends[0]}`;

            const teacherName = courseData.teacherName;
            const courseName = courseData.courseName;
            courseNameElement.innerHTML = `<span style="font-weight: 600">Khóa học: </span> ${courseName}`;
            if(roleUser !== "TEACHER"){
                courseNameElement.innerHTML += ` - Giáo viên ${teacherName}`;
            }
            courseDesc.innerHTML = `<span style="font-weight: 600">Mô tả khóa học:</span> ${courseDetailDescription}`;
            courseStartTime.innerHTML = `<span style="font-weight: 600"> Bắt đầu khóa học: </span> ${formattedDateStart}`;
            courseEndTime.innerHTML = `<span style="font-weight: 600"> Kết thúc khóa học: </span> ${formattedDateEnd}`;

        }

        fetchPosts();

        fetchClassList();
        function fetchClassList() {
            fetch(`/api/teacher/courses/${courseId}/students`)
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

            fetch(`/api/student/${userId}/courses/${courseId}/feedback`, {
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

        function fetchCourseGrades() {
            fetch(`/api/student/${userId}/grades`)
                .then(response => response.json())
                .then(data => {
                    console.log(data)

                    if (data.length > 0) {

                        const result = data.filter(course => courseId === course.courseId)
                        console.log(result)
                        openEvaluationModal(result)
                    } else {
                        console.log("No grades found for the student in this course.");
                        showToast(`<i class="fas fa-xmark"></i> Giáo viên chưa nhập điểm/báo cáo`);
                    }
                })
                .catch(error => console.error("Error fetching course grades:", error));
        }

        function attachScoreLinksEvent() {
            const scoreLinks = document.querySelectorAll(".score-link");
            scoreLinks.forEach(link => {
                link.addEventListener('click', function(event) {
                    event.preventDefault();
                    const studentId = this.getAttribute("data-student-id");
                    apiGradeUrl = `api/teacher/courses/${courseId}/students/${studentId}/results`

                        fetch(apiGradeUrl)
                            .then(response => response.json())
                            .then(data => {
                                if(data.length > 0){
                                    console.log(data)
                                    openEvaluationStudentForTeacher(data)
                                }else{
                                    //Create new bang diem
                                }
                        })
                            .catch(error => console.error("Error fetching course grades:", error));
                });
            });
        }

    function openEvaluationStudentForTeacher(grades) {
        const tableBody = document.getElementById("scoresTableBody");
        const shortTestScores = grades.filter(grade => grade.type === 1);
        const longTestScores = grades.filter(grade => grade.type === 2);
        const examScores = grades.filter(grade => grade.type === 3);

        tableBody.innerHTML = '';

        const addRowToTable = (type, scores) => {
            const row = document.createElement("tr");

            const typeCell = document.createElement("td");
            typeCell.textContent = type;
            row.appendChild(typeCell);

            scores.forEach(score => {
                const scoreCell = document.createElement("td");
                scoreCell.textContent = score.value;
                scoreCell.dataset.id = score.Id;
                scoreCell.dataset.type = score.type;
                scoreCell.contentEditable = true; // Cho phép chỉnh sửa nội dung trực tiếp
                scoreCell.classList.add("editable-score");
                row.appendChild(scoreCell);
            });

            tableBody.appendChild(row);
        };

        const feedbackMessages = [];

        if (shortTestScores.length > 0) {
            addRowToTable('Kiểm tra 15 phút', shortTestScores);
            feedbackMessages.push(`Bạn đã đạt ${shortTestScores.map(score => score.value).join(', ')} điểm trong kiểm tra 15 phút`);
        }
        if (longTestScores.length > 0) {
            addRowToTable('Kiểm tra 1 tiết', longTestScores);
            feedbackMessages.push(`Bạn đã đạt ${longTestScores.map(score => score.value).join(', ')} điểm trong kiểm tra 1 tiết`);
        }
        if (examScores.length > 0) {
            addRowToTable('Kiểm tra cuối kỳ', examScores);
            feedbackMessages.push(`Bạn đã đạt ${examScores.map(score => score.value).join(', ')} điểm trong kiểm tra cuối kỳ`);
        }

        const teacherFeedback = document.getElementById("teacherFeedback");
        teacherFeedback.innerHTML = feedbackMessages.join('<br>');

        const evaluationModal = document.getElementById("evaluationModal");
        evaluationModal.style.display = "block";

        // Lưu trữ giá trị hiện tại của từng ô điểm số vào mảng currentValues
        const currentValues = [];
        document.querySelectorAll('.editable-score').forEach(cell => {
            const scoreId = cell.dataset.id;
            const scoreType = cell.dataset.type;
            const currentValue = cell.textContent.trim();
            currentValues.push({ id: scoreId, value: currentValue, type: scoreType });
        });

        document.getElementById("saveScoresButton").addEventListener("click", function() {
            const updatedScores = [];

            document.querySelectorAll('.editable-score').forEach(cell => {
                const scoreId = cell.dataset.id;
                const scoreType = cell.dataset.type;
                const newValue = cell.textContent.trim();

                // Tìm giá trị hiện tại trong mảng currentValues dựa vào scoreId
                const currentValueObj = currentValues.find(item => item.id === scoreId);
                console.log(currentValueObj)
                const currentValue = currentValueObj ? currentValueObj.value : '';

                // So sánh giá trị hiện tại và giá trị mới để xác định cần cập nhật hay không
                if (currentValue !== newValue) {
                    updatedScores.push({ id: scoreId, value: newValue, type: scoreType });
                }
                console.log(updatedScores)
            });

            // Gửi yêu cầu cập nhật điểm lên server
            saveScores(updatedScores);
        });
    }

    function saveScores(scores) {
        const promises = [];

        scores.forEach(score => {
            const intValue = parseInt(score.value, 10);
            const intType = parseInt(score.type, 10);
            const promise = fetch(`/api/teacher/results/${score.id}`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ value: intValue, type: intType })
            }).then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            }).then(data => {
                console.log('Update successful:', data);
            }).catch(error => {
                console.error('Update failed:', error);
                alert('Cập nhật điểm thất bại');
            });

            promises.push(promise);
        });

        // Đợi cho tất cả các promise hoàn thành
        Promise.all(promises)
            .then(() => {
                showToast(`<div class="success-toast"><i class="fas fa-check"></i> Cập nhật điểm thành công</div>`);
            })
            .catch(() => {

                showToast(`<div class="error-toast">
                                <i class="fas fa-xmark"></i> Cập nhật điểm thất bại
                            </div>`);
            });
    }


    function openEvaluationModal(grades) {
        const shortTestScores = grades.filter(grade => grade.resultType === 1).map(grade => grade.resultValue);
        const longTestScores = grades.filter(grade => grade.resultType === 2).map(grade => grade.resultValue);
        const examScores = grades.filter(grade => grade.resultType === 3).map(grade => grade.resultValue);
        const maxColumns = Math.max(shortTestScores.length, longTestScores.length, examScores.length);

        const tableBody = document.getElementById("scoresTableBody");
        const scoresTable = document.getElementById('scoresTable');

        tableBody.innerHTML = '';

        const addRowToTable = (type, scores) => {
            const row = document.createElement("tr");

            const typeCell = document.createElement("td");
            typeCell.textContent = type;
            row.appendChild(typeCell);

            for (let i = 0; i < maxColumns; i++) {
                const scoreCell = document.createElement("td");
                scoreCell.classList.add('editable-score');
                scoreCell.setAttribute('contenteditable', 'true');
                if (scores[i] !== undefined) {
                    scoreCell.textContent = scores[i];
                    scoreCell.setAttribute('data-id', grades[i].id);
                    scoreCell.setAttribute('data-type', grades[i].resultType);
                } else {
                    scoreCell.textContent = '';
                }
                row.appendChild(scoreCell);
            }

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

        const teacherFeedback = document.getElementById("teacherFeedback");
        teacherFeedback.innerHTML = feedbackMessages.join('<br>');

        const evaluationModal = document.getElementById("evaluationModal");
        evaluationModal.style.display = "block";
    }




    function closeEvaluationModal() {
            evaluationModal.style.display = "none";
        }

        document.getElementById("btn-evaluation-details").addEventListener("click", () => {
            fetchCourseGrades(courseId);
        });

        closeEvaluationModalBtn.addEventListener("click", closeEvaluationModal);





    });
