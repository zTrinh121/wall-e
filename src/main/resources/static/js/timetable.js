document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    const userId = document.getElementById("userId").innerHTML.trim();
    const studentApiUrl = `students`;
    const userRole = document.getElementById("userRole").innerHTML.trim();
    console.log(userRole === 'TEACHER');
    console.log(userId);
    var studentId;
    const noResultDiv = document.getElementById("no-result");
    var calendarDisplay = document.getElementById("calendar");

    // Custom event content function to display course name and room name
    function renderEventContent(eventInfo) {
        return {
            html: `<b>${eventInfo.event.title}</b><br><p>${eventInfo.event.extendedProps.location}</p>`
        };
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
        events: [],  // Initialize with empty array
        eventOverlap: false,
        firstDay: 1,
        eventContent: renderEventContent // Use the custom event content function
    });

    calendar.render();

    async function fetchStudents() {
        try {
            const response = await fetch(studentApiUrl);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.json();
            console.log("Fetched students: ", data);
            studentId = data[0].id;
            console.log("Id student: " + studentId);
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
                // Transform API data into FullCalendar event format
                var events = data.map(item => ({
                    title: item.courseName,
                    start: item.slotDate + 'T' + item.slotStartTime,
                    end: item.slotDate + 'T' + item.slotEndTime,
                    location: item.roomName
                }));

                // Set events to FullCalendar
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
});
