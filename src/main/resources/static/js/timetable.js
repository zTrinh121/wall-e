document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    const userId = document.getElementById("userId").innerHTML.trim();
    const studentApiUrl = `/api/parent/studentsByParent`;
    const userRole = document.getElementById("userRole").innerHTML.trim();
    const noResultDiv = document.getElementById("no-result");
    var calendarDisplay = document.getElementById("calendar");
    var eventDetailModal = document.getElementById('eventDetailModal');
    var eventDetailContent = document.getElementById('eventDetailContent');
    var requestModal = document.getElementById('requestModal');
    var closeRequestModal = document.getElementById('closeRequestModal');   
    var studentName = localStorage.getItem('studentName');
    var studentId = localStorage.getItem('studentId');

    function formatTimeRange(date) {
        let hours = date.getHours();
        let endHours = hours + 2; 
        return `${hours}-${endHours}`;
    }

    function renderEventContent(eventInfo) {
        const attendanceText = eventInfo.event.extendedProps.attendanceStatus === false ? 'Vắng' : 'Có mặt';
        const attendanceColor = eventInfo.event.extendedProps.attendanceStatus === false ? 'red' : 'green';

        if (userRole === 'TEACHER') {
            return {
                html: `
                    ${eventInfo.event.title}<br>
                    <b>Phòng: ${eventInfo.event.extendedProps.location}</b><br>
                `
            };
        } else {
            return {
                html: `
                    <b>${eventInfo.event.title} ở ${eventInfo.event.extendedProps.location}</b><br>
                    <b>${eventInfo.event.extendedProps.teacherName}</b><br>
                    <b style="color: ${attendanceColor};">${attendanceText}</b>
                `
            };
        }
    }

    async function displayStudentSelection(students) {
        const container = document.getElementById('studentSelectionContainer');
        container.innerHTML = '';
        console.log(students);
    
        const select = document.createElement('select');
        select.id = 'studentSelect';
    
        // Create options for the select element
        students.forEach(student => {
            const option = document.createElement('option');
            option.value = student.id;
            option.textContent = student.name;
            select.appendChild(option);
        });
    
        // Check for saved studentId and studentName in local storage
        const savedStudentId = localStorage.getItem('studentId');
        const savedStudentName = localStorage.getItem('studentName');
    
        if (savedStudentId && savedStudentName) {
            select.value = savedStudentId;
        }
    
        // Add event listener for change event
        select.addEventListener('change', async () => {
            const selectedStudentId = select.value;
            const selectedStudentName = select.options[select.selectedIndex].text;
            console.log(selectedStudentId, selectedStudentName);
            if (selectedStudentId) {
                console.log("Print at firs time")
                studentId = selectedStudentId;
                studentName = selectedStudentName;
                localStorage.setItem('studentId', studentId);
                localStorage.setItem('studentName', studentName);
                await fetchEvents(`/api/student/${studentId}/slots`);
            }
        });
    
        container.appendChild(select);
    
        // Fetch posts if a student is already selected
        if (savedStudentId && savedStudentName) {
            studentId = savedStudentId;
            studentName = savedStudentName;
            
        }
    }
    

    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'timeGridWeek',
        haeaderToolbar: {
            start: 'prev,next',
            center: 'title',
            end: userRole === 'TEACHER' ? 'RequestChangingTimetable' : ''
        },
        customButtons: {
            RequestChangingTimetable: {
                text: "Gửi yêu cầu",
                click: function() {
                    document.getElementById('requestModal').style.display = 'block';
                }
            }
        },
        editable: true,
        allDaySlot: false,
        dayMaxEvents: true,
        slotMinTime: '07:00:00',
        slotMaxTime: '22:00:00',
        slotDuration: '02:00:00',
        
        events: [],
        eventOverlap: false,
        firstDay: 1,
        eventContent: renderEventContent,
        eventClick: function(info) {

            const event = info.event;
            const attendanceText = event.extendedProps.attendanceStatus === false ? 'Vắng' : 'Có mặt';
            const attendanceColor = event.extendedProps.attendanceStatus === false ? 'red' : 'green';
            console.log(info.event.start)
            const startTime = new Date(info.event.start).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
            console.log(startTime)
            const endTime = new Date(info.event.end).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
            if(userRole !== "TEACHER"){
                eventDetailContent.innerHTML = `
                <p>${event.title}</p>
                <p>Phòng: ${event.extendedProps.location}</p>
                <p>Giáo viên: ${event.extendedProps.teacherName}</p>
                <p>Từ: ${startTime} đến ${endTime}</p>
                
                <p style="color: ${attendanceColor};">${attendanceText}</p>
            `;
            }else{
                eventDetailContent.innerHTML = `
                <p>${event.title}</p>
                <p>Phòng: ${event.extendedProps.location}</p>
                <p>Thời gian: ${startTime} - ${endTime}</p>
                <a href="course-details?userId=${userId}&courseId=${event.extendedProps.courseId}">Xem khóa học</a> 
                | <a href="attendance?courseId=${event.extendedProps.courseId}&date=${event.extendedProps.date}">Điểm danh</a>
            `;
            }
            eventDetailModal.style.display = 'block';
        }
    });



    calendar.render();

    async function fetchStudents() {
        try {
            const response = await fetch(studentApiUrl);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.json();
            console.log(data);
            if(data.length > 1){
                displayStudentSelection(data);  
            } else {
                studentId = data[0].id;
                studentName = data[0].name;
                localStorage.setItem('studentId', studentId);
                localStorage.setItem('studentName', studentName);

            }
            
            await fetchEvents(`/api/student/${studentId}/slots`);
        } catch (error) {
            console.error("Error fetching students:", error);
            header.style.display = "none";
            noResultDiv.style.display = "block";
            noResultDiv.innerHTML = `Hãy <a class="mapping" href="/mapping"> kết nối </a> với con bạn để truy cập vào khóa học`;
        }
    }

    function fetchEvents(url) {
        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log(data);
                var events;
                if (userRole !== "TEACHER") {
                    events = data.map(item => {
                        const attendanceColor = item.attendanceStatus === 0 ? 'red' : 'green';

                        return {
                            title: item.courseName,
                            start: item.slotDate + 'T' + item.slotStartTime,
                            end: item.slotDate + 'T' + item.slotEndTime,
                            location: item.roomName,
                            classNames: ['event-item'],

                            backgroundColor: attendanceColor,
                            extendedProps: {
                                attendanceStatus: item.attendanceStatus,
                                teacherName: item.teacherName,

                            }
                        };
                    });
                } else {
                    events = data.map(item => {
                        const dateObj = new Date(item.slotDate);
                        const slotDateISO = dateObj.toISOString().split('T')[0];

                        return {
                            title: item.courseName,
                            start: slotDateISO + 'T' + item.slotStartTime,
                            end: slotDateISO + 'T' + item.slotEndTime,
                            location: item.roomName,
                            extendedProps: {
                                courseId: item.courseId,
                                date: slotDateISO
                            }
                        };
                    });
                }

                calendar.setOption('events', events);
            })
            .catch(error => {
                console.error('Error fetching events:', error);
            });
    }

    // Fetch events on initial load
    switch (userRole) {
        case "PARENT":
            fetchStudents();
            break;
        case "STUDENT":
            fetchEvents(`/api/student/${userId}/slots`);
            break;
        case "TEACHER":
            fetchEvents(`/api/teacher/${userId}/schedule`);
            break;
    }

    var closeBtn = document.getElementsByClassName("close")[0];
    closeBtn.onclick = function() {
        eventDetailModal.style.display = "none";
    };

    window.onclick = function(event) {
        if (event.target == eventDetailModal) {
            eventDetailModal.style.display = "none";
        }
    };

    closeRequestModal.onclick = function() {
        requestModal.style.display = "none";
    }

    window.onclick = function(event) {
        if (event.target == requestModal) {
            requestModal.style.display = "none";
        }
    }

    document.getElementById('requestForm').onsubmit = function(e) {
        e.preventDefault();
        // Here you can add code to handle the form submission
        console.log('Form submitted');
        requestModal.style.display = "none";
    }
});
