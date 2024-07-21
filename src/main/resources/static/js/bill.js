document.addEventListener('DOMContentLoaded', async function() {
    var urlParams = new URLSearchParams(window.location.search);
    var status = urlParams.get('status');
    var courseId = urlParams.get('courseId');
    var userId = urlParams.get('userId');
    var amount = urlParams.get('amount');
    var date = urlParams.get('date');
    var userRole = document.getElementById("userRole").innerHTML;
    var backBtn = document.getElementById("back-btn");
    let courseIdLS = localStorage.getItem("courseId");
    let studentId = localStorage.getItem('studentId');
    console.log(studentId);

    // Format the date as needed
    function formatDateToDDMMYYYY(dateString) {
        var date = new Date(dateString);
        var day = String(date.getUTCDate()).padStart(2, '0');
        var month = String(date.getUTCMonth() + 1).padStart(2, '0'); // Months are zero-based
        var year = date.getUTCFullYear();
        return `${day}/${month}/${year}`;
    }

    // Call fetchPosts and enrollNewCourse
    await fetchPosts(studentId, courseId, amount);
    await enrollNewCourse(studentId, courseId);

    if (backBtn) {
        backBtn.onclick = function() {
            var url = '/login';
            if (userRole === "ADMIN") {
                url = '/admin';
            } else if (userRole === "TEACHER") {
                url = '/teacher';
            } else if (userRole === "PARENT") {
                url = '/parent-dashboard';
            } else if (userRole === "STUDENT") {
                url = '/student-dashboard';
            }
            window.location.href = url;
        };
    }
});

async function fetchPosts(studentId, courseId, amount) {
    try {
        await fetch(`/api/student/${studentId}/courses`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log(data);
                const courseDetail = data.filter(course => course.courseId == courseId);
                console.log(courseDetail);
                if (courseDetail[0]) {
                    console.log(courseDetail[0].teacherName);
                    teacherName = courseDetail[0].teacherName;
                    courseName = courseDetail[0].courseName;
                    displayBill(courseDetail[0], amount);
                } else {
                    console.log(`Course with courseId ${courseId} not found.`);
                }
            })
            .catch(error => console.error("Error fetching course details:", error));
    } catch (error) {
        console.error("Error in fetchPosts:", error);
    }
}

async function enrollNewCourse(studentId, courseId) {
    try {
        const response = await fetch(`api/student/enroll-new-course?studentId=${studentId}&courseId=${courseId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const message = await response.text();
        console.log(message);
    } catch (error) {
        console.error('Error enrolling new course:', error);
    }
}

function displayBill(data, amount) {
    console.log(data);
    console.log(amount);
    document.querySelector('.meta tr:nth-child(1) td span').textContent = data.courseName;
    document.querySelector('.meta tr:nth-child(2) td span').textContent = formatDateToDDMMYYYY(data.startTime) + ' - ' + formatDateToDDMMYYYY(data.endTime);
    document.querySelector('.meta tr:nth-child(3) td span').textContent = data.teacherName + " - " + data.centerName;
    document.querySelector('.meta tr:nth-child(4) td span').textContent = data.courseDesc;
}

function formatDateToDDMMYYYY(dateString) {
    var date = new Date(dateString);
    var day = String(date.getUTCDate()).padStart(2, '0');
    var month = String(date.getUTCMonth() + 1).padStart(2, '0'); // Months are zero-based
    var year = date.getUTCFullYear();
    return `${day}/${month}/${year}`;
}

function formatCurrency(amount) {
    // Assuming the amount is in Vietnamese dong
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount);
}
