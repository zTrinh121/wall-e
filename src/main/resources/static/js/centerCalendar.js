document.addEventListener('DOMContentLoaded', function() {
    const calendarEl = document.getElementById('calendar');
    const addSlotModal = document.getElementById('addSlotModal');
    const closeModal = document.querySelector('.close');
    const addSlotForm = document.getElementById('addSlotForm');

    let calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        events: function(fetchInfo, successCallback, failureCallback) {
            fetch('/manager/slots/byCenter/1') // Replace with your endpoint to fetch events
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    let events = data.map(slot => ({
                        id: slot.id,
                        title: slot.courseName,
                        start: slot.slotDate + 'T' + slot.slotStartTime,
                        end: slot.slotDate + 'T' + slot.slotEndTime,
                        description: `Room: ${slot.roomName}`
                    }));
                    successCallback(events);
                })
                .catch(error => {
                    console.error('Error fetching events:', error);
                    failureCallback(error);
                });
        },
        eventClick: function(info) {
            // Handle event click (optional)
            console.log('Event clicked:', info.event);
            // You can implement edit or delete functionality here
        },
        // Add context menu for right-click on events
        eventContextMenus: [
            {
                text: 'Add Slot',
                click: function(info) {
                    addSlotModal.style.display = 'block';
                    const slotDate = info.event.startStr.split('T')[0];
                    const slotStartTime = info.event.startStr.split('T')[1];
                    const slotEndTime = info.event.endStr.split('T')[1];
                    document.getElementById('slotDate').value = slotDate;
                    document.getElementById('slotStartTime').value = slotStartTime;
                    document.getElementById('slotEndTime').value = slotEndTime;
                }
            }
        ]
    });

    calendar.render();

    closeModal.addEventListener('click', function() {
        addSlotModal.style.display = 'none';
    });

    window.addEventListener('click', function(event) {
        if (event.target === addSlotModal) {
            addSlotModal.style.display = 'none';
        }
    });

    addSlotForm.addEventListener('submit', function(event) {
        event.preventDefault();

        let formData = new FormData(addSlotForm);
        let slotData = {
            slotDate: formData.get('slotDate'),
            slotStartTime: formData.get('slotStartTime'),
            slotEndTime: formData.get('slotEndTime'),
            courseName: formData.get('courseName'),
            roomName: formData.get('roomName')
        };

        fetch('/manager/overallSlots/certainCourse/insert', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(slotData)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Slot created successfully:', data);
            alert('Slot created successfully!');
            addSlotModal.style.display = 'none';
            calendar.refetchEvents(); // Update the calendar after adding a slot
        })
        .catch(error => {
            console.error('Error creating slot:', error);
            alert('Failed to create slot. Please try again later.');
        });
    });
});
