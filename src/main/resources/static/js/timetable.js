document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        timeZone: 'UTC',
        initialView: 'timeGridWeek',
        headerToolbar: {
            start:"prev,next",
            center: 'title',
            end: "AddEventDemoForManager"
        },
        customButtons:{
            AddEventDemoForManager:{
                text: "Add Event Demo for Manager",
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
        events: 'https://fullcalendar.io/api/demo-feeds/events.json?overload-day',
        eventOverlap: true,
        firstDay: 1,
    });

    calendar.render();
});