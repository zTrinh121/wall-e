document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    const userId = document.getElementById("userId").innerHTML.trim();
    const studentApiUrl = `/api/parent/studentsByParent`;
    const userRole = document.getElementById("userRole").innerHTML.trim();
    var studentId;
    const noResultDiv = document.getElementById("no-result");
    var calendarDisplay = document.getElementById("calendar");
    var eventDetailModal = document.getElementById('eventDetailModal');
    var eventDetailContent = document.getElementById('eventDetailContent');

    function renderEventContent(eventInfo) {
        const attendanceText = eventInfo.event.extendedProps.attendanceStatus === 0 ? 'Vắng' : 'Có mặt';
        const attendanceColor = eventInfo.event.extendedProps.attendanceStatus === 0 ? 'red' : 'green';

        if (userRole === 'TEACHER') {
            return {
                html: `
                    ${eventInfo.event.title}<br>
                    <b>Phòng: ${eventInfo.event.extendedProps.location}</b><br>
                    <b style="color: ${attendanceColor};">${attendanceText}</b>
                `
            };
        } else {
            return {
                html: `
                    <b>${eventInfo.event.title}</b><br>
                    <b>Phòng: ${eventInfo.event.extendedProps.location}</b><br>
                    <b style="color: ${attendanceColor};">${attendanceText}</b>
                `
            };
        }
    }

    var calendar = new FullCalendar.Calendar(calendarEl, {
        timeZone: 'UTC',
        initialView: 'timeGridWeek',
        headerToolbar: {
            start: 'prev,next',
            center: 'title',
            end: userRole === 'TEACHER' ? 'RequestChangingTimetable' : ''
        },
        customButtons: {
            RequestChangingTimetable: {
                text: "Đổi lịch",
                click: function() {
                    alert('Hehe chưa làm!!!');
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
            const attendanceText = event.extendedProps.attendanceStatus === 0 ? 'Vắng' : 'Có mặt';
            const attendanceColor = event.extendedProps.attendanceStatus === 0 ? 'red' : 'green';

            eventDetailContent.innerHTML = `
                <p>${event.title}</p>
                <p>Phòng: ${event.extendedProps.location}</p>
                <p style="color: ${attendanceColor};">${attendanceText}</p>
            `;

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
            studentId = data[0].id;
            await fetchEvents(`/api/student/${studentId}/slots`);
        } catch (error) {
            console.error("Error fetching students:", error);
            calendarDisplay.style.display = "none";
            noResultDiv.style.display = "block";
            noResultDiv.innerHTML = `Hãy <a class="mapping" href="/mapping"> kết nối </a> với con bạn để xem thời khóa biểu`;
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
                var events;
                console.log(data);
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
                                attendanceStatus: item.attendanceStatus
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
                                attendanceStatus: item.attendanceStatus
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
});
