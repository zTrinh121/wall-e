document.addEventListener("DOMContentLoaded", function () {
    const courseId = document.getElementById('courseId').innerText.trim();
    const centerId = document.getElementById('centerId').innerText.trim();
    const centerInfo = document.getElementById('center-info');
    const courseInfo = document.getElementById('course-info');
    const userId = document.getElementById("userId").innerText.trim();
    const studentApiUrl = `/api/parent/studentsByParent`;
    const userRole = document.getElementById('userRole').innerText;
    var registerBtn = document.getElementById("registerBtn");
    var courseIdLS = localStorage.getItem('courseId');
    var calendarEl = document.getElementById('calendar');
    var coursePrice;
    var studentId;
    var courseChildren;

    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'timeGridWeek',
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        slotMinTime: '07:00:00',
        slotMaxTime: '22:00:00',
        slotDuration: '02:00:00',
        businessHours: {
            startTime: '07:00',
            endTime: '22:00',
            daysOfWeek: [1, 2, 3, 4, 5, 6]
        },
        hiddenDays: [0],
        events: []
    });

    calendar.render();

    async function fetchCourses(api) {
        try {
            const response = await fetch(api);
            const data = await response.json();
            console.log(data);
            return data.filter(c => c.courseId === parseInt(courseId, 10));
        } catch (error) {
            console.error("Error fetching courses:", error);
            return null;
        }
    }

    async function fetchStudents() {
        try {
            const response = await fetch(studentApiUrl);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.json();
            studentId = data[0].id;
            console.log(studentId)
            courseChildren = await fetchCourses(`/api/student/${studentId}/courses`);
        } catch (error) {
            console.error("Error fetching students:", error);
            alert("Hãy kết nối với con trước khi đăng ký khóa học")

        }
    }

    console.log(courseId);
    console.log(centerId);
    courseIdLS = localStorage.setItem('courseId', courseId);
    if (userRole === "Guest" || userRole == "TEACHER") {
        registerBtn.style.display = "none";
    }

    if (centerId && courseId) {
        if (userRole === "STUDENT") {
            fetchCourses(`/api/student/${userId}/courses`).then(course => {
                console.log(course);
                if (course && course.length > 0) {
                    registerBtn.style.display = "none";
                } else {
                    registerBtn.style.display = "block";
                    registerBtn.addEventListener('click', function () {
                        fetch('/api/v1/payment/courseId', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify({ courseId: parseInt(courseId) })
                        })
                            .then(response => response.json())
                            .then(data => {
                                console.log('CourseId saved to session:', data);
                                fetch('api/v1/payment/auth/status')
                                    .then(response => response.json())
                                    .then(isAuthenticated => {
                                        if (isAuthenticated) {
                                            
                                            Thread.sleep(2000);
                                            const paymentUrlEndpoint = `http://localhost:8080/api/v1/payment/vn-pay?amount=${coursePrice}&bankCode=NCB&courseId=${courseId}`;

                                            fetch(paymentUrlEndpoint)
                                                .then(response => response.json())
                                                .then(data => {
                                                    const paymentUrl = data.data.paymentUrl;
                                                    if (paymentUrl) {
                                                        window.location.href = paymentUrl;
                                                    } else {
                                                        console.error('Payment URL is missing in response');
                                                    }
                                                });
                                        } else {
                                            window.location.href = '/login';
                                        }
                                    });
                            })
                            .catch(error => {
                                console.error('Error saving courseId to session:', error);
                            });
                    });
                }
            });
        }else if(userRole == "PARENT"){
            fetchStudents().then(students => {
                console.log(courseChildren)
                if (courseChildren && courseChildren.length > 0) {
                    registerBtn.style.display = "none";
                } else {
                    registerBtn.style.display = "block";
                    registerBtn.addEventListener('click', function () {
                        fetch('/api/v1/payment/courseId', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify({ courseId: parseInt(courseId) })
                        })
                            .then(response => response.json())
                            .then(data => {
                                console.log('CourseId saved to session:', data);
                                fetch('api/v1/payment/auth/status')
                                    .then(response => response.json())
                                    .then(isAuthenticated => {
                                        if (isAuthenticated) {
                                            const paymentUrlEndpoint = `http://localhost:8080/api/v1/payment/vn-pay?amount=${coursePrice}&bankCode=NCB&courseId=${courseId}`;

                                            fetch(paymentUrlEndpoint)
                                                .then(response => response.json())
                                                .then(data => {
                                                    const paymentUrl = data.data.paymentUrl;
                                                    if (paymentUrl) {
                                                        window.location.href = paymentUrl;
                                                    } else {
                                                        console.error('Payment URL is missing in response');
                                                    }
                                                });
                                        } else {
                                            window.location.href = '/login';
                                        }
                                    });
                            })
                            .catch(error => {
                                console.error('Error saving courseId to session:', error);
                            });
                    });
                }
            });
        }

        fetch(`/centers`)
            .then(response => response.json())
            .then(data => {
                const center = data.find(c => c.id === parseInt(centerId, 10));
                if (center) {
                    return fetch(`/courses-in-center/${center.id}`);
                } else {
                    console.log('Center not found with the provided ID.');
                    throw new Error('Center not found');
                }
            })
            .then(response => response.json())
            .then(courses => {
                const course = courses.find(c => c.id === parseInt(courseId, 10));
                if (course) {
                    document.getElementById('course-description').innerText = course.description;
                    document.getElementById('course-time').innerText = `${course.startDate} đến ${course.endDate}`;
                    document.getElementById('course-instructor').innerText = course.teacher.name;
                    document.getElementById('course-fee').innerText = course.courseFee;
                    coursePrice = course.courseFee;

                    document.getElementById('course-amountStudent').innerText = course.amountOfStudents;
                    courseInfo.style.display = 'block';

                    return fetch(`/slots-in-course/${course.id}`);
                } else {
                    console.log('Course not found in the specified center.');
                    throw new Error('Course not found');
                }
            })
            .then(response => response.json())
            .then(slots => {
                const events = slots.map(slot => ({
                    title: 'Lịch học',
                    start: `${slot.slotDay}T${slot.slotStartTime}`,
                    end: `${slot.slotDay}T${slot.slotEndTime}`
                }));

                events.forEach(event => {
                    calendar.addEvent(event);
                });

                calendar.render();
            })
            .catch(error => console.error('Error fetching data:', error));
    } else if (centerId) {
        loadTeachersTable(centerId);
        fetch(`/centers`)
            .then(response => response.json())
            .then(data => {
                console.log('Center data:', data);
                const centerFilter = data.filter(c => c.id === parseInt(centerId, 10));
                if (centerFilter.length > 0) {
                    const center = centerFilter[0];
                    document.getElementById('center-description').innerText = center.description;
                    document.getElementById('center-address').innerText = center.address;
                    document.getElementById('center-email').innerText = center.email;
                    document.getElementById('center-regulation').innerText = center.regulation;

                    document.getElementById('manager-name').innerText = center.manager.name;
                    document.getElementById('manager-phone').innerText = center.manager.phone;
                    document.getElementById('manager-email').innerText = center.manager.email;
                    centerInfo.style.display = 'block';
                } else {
                    console.log('No center found with the provided ID.');
                }
            })
            .catch(error => console.error('Error fetching center data:', error));
    } else {
        console.log('Center ID and/or Course ID is missing.');
    }

    function loadTeachersTable(centerId) {
        fetch(`/teachers-in-center/${centerId}`)
            .then(response => response.json())
            .then(data => {
                const tableBody = document.getElementById('teachers-table-body');
                tableBody.innerHTML = '';

                data.forEach(teacher => {
                    const row = document.createElement('tr');

                    const nameCell = document.createElement('td');
                    nameCell.textContent = teacher.name;
                    row.appendChild(nameCell);

                    const emailCell = document.createElement('td');
                    emailCell.textContent = teacher.email;
                    row.appendChild(emailCell);

                    const phoneCell = document.createElement('td');
                    phoneCell.textContent = teacher.phone;
                    row.appendChild(phoneCell);

                    const profileImageCell = document.createElement('td');
                    const profileImage = document.createElement('img');
                    profileImage.src = teacher.profileImage;
                    profileImage.alt = teacher.name;
                    profileImage.style.width = '50px';
                    profileImageCell.appendChild(profileImage);
                    row.appendChild(profileImageCell);

                    tableBody.appendChild(row);
                });
            })
            .catch(error => console.error('Error fetching teachers:', error));
    }
});
