document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
      initialView: 'dayGridMonth',
      headerToolbar: {
        center: 'addEventButton'
      },
      customButtons: {
        addEventButton: {
          text: 'add event...',
          click: function() {
            var dateStr = prompt('Enter a date in YYYY-MM-DD format');
            var date = new Date(dateStr + 'T00:00:00'); // will be in local time

            if (!isNaN(date.valueOf())) { // valid?
              calendar.addEvent({
                title: 'dynamic event',
                start: date,
                allDay: true
              });
              alert('Great. Now, update your database...');
            } else {
              alert('Invalid date.');
            }
          }
        }
      }
    });

    calendar.render();

});
document.addEventListener("DOMContentLoaded", () => {
    const date = document.querySelector(".date");
    const daysContainer = document.querySelector(".days");
    const prev = document.querySelector(".prev");
    const next = document.querySelector(".next");
    const todayBtn = document.querySelector(".today-btn");
    const gotoBtn = document.querySelector(".goto-btn");
    const dateInput = document.querySelector(".date-input");
    const eventDay = document.querySelector(".event-day");
    const eventDate = document.querySelector(".event-date");
    const eventsContainer = document.querySelector(".events");

    let today = new Date();
    let activeDay;
    let month = today.getMonth();
    let year = today.getFullYear();

    const months = [
        "T1",
        "T2",
        "T3",
        "T4",
        "T5",
        "T6",
        "T7",
        "T8",
        "T9",
        "T10",
        "T11",
        "T12",
    ];

    let eventsArr = [];
    getEvents();
    console.log(eventsArr);

    function fetchEvents() {
        fetch('/manager/slots/byCenter/1')
            .then(response => response.json())
            .then(data => {
                console.log(data)
                eventsArr = data.map(event => ({
                    day: new Date(event.slotDate).getDate(),
                    month: new Date(event.slotDate).getMonth() + 1,
                    year: new Date(event.slotDate).getFullYear(),
                    events: [{
                        title: event.courseName,
                        time: convertTime(event.slotStartTime) + " - " + convertTime(event.slotEndTime),
                        room: event.roomName
                    }]
                }));
                initCalendar();
            })
            .catch(error => console.error('Error fetching events:', error));
    }
    fetchEvents();
    console.log(eventsArr);

    function convertTime(time) {
        let timeArr = time.split(":");
        let timeHour = timeArr[0];
        let timeMin = timeArr[1];
        let timeFormat = timeHour >= 12 ? "PM" : "AM";
        timeHour = timeHour % 12 || 12;
        time = timeHour + ":" + timeMin + " " + timeFormat;
        return time;
    }
});
