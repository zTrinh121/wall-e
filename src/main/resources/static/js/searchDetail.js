document.addEventListener("DOMContentLoaded", function () {
    const courseId = document.getElementById('courseId').innerText.trim();
    const centerId = document.getElementById('centerId').innerText.trim();
    const centerInfo = document.getElementById('center-info');
    const courseInfo = document.getElementById('course-info');
    var calendarEl = document.getElementById('calendar');
    var coursePrice;
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        events: [

        ]
    });
    calendar.render();

    console.log(courseId);
    console.log(centerId);

    if (centerId && courseId) {
        document.getElementById('registerBtn').addEventListener('click', function () {
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
        fetch(`/centers`)
            .then(response => response.json())
            .then(data => {
                const center = data.find(c => c.id === parseInt(centerId, 10));
                if (center) {
                    // Fetch courses in the specified center
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
                    coursePrice = course.courseFee

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
                // console.log(slots)
                const events = slots.map(slot => ({
                    title: 'Lịch học',
                    start: `${slot.slotDay}T${slot.slotStartTime}`,
                    end: `${slot.slotDay}T${slot.slotEndTime}`
                }));

                // Add the events to the calendar
                events.forEach(event => {
                    console.log(event)
                    calendar.addEvent(event);
                });

                calendar.render(); // Re-render the calendar to display the new events
            })
            .catch(error => console.error('Error fetching data:', error));
    }
    else if (centerId) {
        loadTeachersTable(centerId);
        console.log("Center");
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
        fetch(`/teachers-in-center/${centerId}`) // Replace with your API endpoint for fetching teachers
            .then(response => response.json())
            .then(data => {
                console
                const tableBody = document.getElementById('teachers-table-body');
                tableBody.innerHTML = ''; // Clear existing table rows

                data.forEach(teacher => {
                    const row = document.createElement('tr');

                    // Create table cells for each attribute
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
                    profileImage.alt = teacher.name; // Optional: Provide alt text for accessibility
                    profileImage.style.width = '50px'; // Adjust image size as needed
                    profileImageCell.appendChild(profileImage);
                    row.appendChild(profileImageCell);

                    tableBody.appendChild(row);
                });
            })
            .catch(error => console.error('Error fetching teachers:', error));
    }

});
