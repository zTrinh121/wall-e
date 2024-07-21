document.addEventListener('DOMContentLoaded', function() {
    const calendarEl = document.getElementById('calendar');
    const addSlotModal = document.getElementById('addSlotModal');
    const editEventModal = document.getElementById('editEventModal');
    const openModalBtn = document.getElementById('openModalBtn');
    const closeModal = document.querySelector('.close');
    const addSlotForm = document.getElementById('addSlotForm');
    const editEventForm = document.getElementById('editEventForm');
    let calendar;

    // Function to fetch events from the server
    const fetchEvents = function(fetchInfo, successCallback, failureCallback) {
        // Fetch events from the server endpoint
        fetch('/manager/slots/byCenter/1')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                // Map fetched data to FullCalendar event format
                let events = data.map(slot => ({
                    id: slot.id,
                    title: slot.courseName,
                    start: slot.slotDate + 'T' + slot.slotStartTime,
                    end: slot.slotDate + 'T' + slot.slotEndTime,
                    description: `Room: ${slot.roomName}`
                }));
                successCallback(events); // Callback to render events on the calendar
            })
            .catch(error => {
                console.error('Error fetching events:', error);
                failureCallback(error);
            });
    };

    // Initialize FullCalendar
    calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        events: function(fetchInfo, successCallback, failureCallback) {
            fetchEvents(fetchInfo, successCallback, failureCallback);
        },
        eventClick: function(info) {
            console.log('Event clicked:', info.event);
            openEditModal(info.event); // Call the function to open edit modal
        }
    });

    // Function to open edit modal and populate with event data
    function openEditModal(event) {
        // Populate edit modal with event details
        document.getElementById('editEventTitle').value = event.title;
        document.getElementById('editEventDescription').value = event.extendedProps.description;
        editEventModal.style.display = 'block'; // Display the edit modal
    }

    // Handle right-click context menu (optional)
    calendarEl.addEventListener('contextmenu', function(e) {
        e.preventDefault();
        const eventElement = e.target.closest('.fc-event');
        if (eventElement) {
            const event = calendar.getEventById(eventElement.dataset.eventId);
            if (event) {
                console.log('Right-clicked event:', event);
                // Implement context menu actions here
            }
        }
    });

    // Render the calendar
    calendar.render();

    // Open add slot modal when button is clicked
    openModalBtn.addEventListener('click', function() {
        addSlotModal.style.display = 'block';
    });

    // Close modals when close button is clicked
    closeModal.addEventListener('click', function() {
        addSlotModal.style.display = 'none';
        editEventModal.style.display = 'none';
    });

    // Close modals when clicking outside of them
    window.addEventListener('click', function(event) {
        if (event.target === addSlotModal) {
            addSlotModal.style.display = 'none';
        }
        if (event.target === editEventModal) {
            editEventModal.style.display = 'none';
        }
    });

    // Handle form submission to add a new slot
    addSlotForm.addEventListener('submit', function(event) {
        event.preventDefault();

        let formData = new FormData(addSlotForm);
        let slotData = {
            slotDate: formData.get('slotDate'),
            slotStartTime: formData.get('slotStartTime'),
            slotEndTime: formData.get('slotEndTime'),
            roomId: formData.get('roomName'), // Adjust based on your form field names
            courseId: formData.get('courseId') // Adjust based on your form field names
        };

        // Send POST request to create a new slot
        fetch('/manager/slots/create', {
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
            addSlotModal.style.display = 'none'; // Close the add slot modal
            calendar.refetchEvents(); // Refresh calendar events
        })
        .catch(error => {
            console.error('Error creating slot:', error);
            alert('Failed to create slot. Please try again later.');
        });
    });

    // Handle form submission to update an existing slot
    editEventForm.addEventListener('submit', function(event) {
        event.preventDefault();

        let formData = new FormData(editEventForm);
        let slotId = formData.get('eventId'); // Adjust based on your form field names
        let slotData = {
            slotDate: formData.get('slotDate'),
            slotStartTime: formData.get('slotStartTime'),
            slotEndTime: formData.get('slotEndTime'),
            roomId: formData.get('roomName'), // Adjust based on your form field names
            courseId: formData.get('courseId') // Adjust based on your form field names
        };

        // Send PUT request to update the slot
        fetch(`/manager/slots/update/${slotId}`, {
            method: 'PUT',
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
            console.log('Slot updated successfully:', data);
            alert('Slot updated successfully!');
            editEventModal.style.display = 'none'; // Close the edit event modal
            calendar.refetchEvents(); // Refresh calendar events
        })
        .catch(error => {
            console.error('Error updating slot:', error);
            alert('Failed to update slot. Please try again later.');
        });
    });

    // Handle form submission to delete an existing slot
    function deleteEvent(eventId) {
        // Send DELETE request to delete the slot
        fetch(`/manager/slots/delete/${eventId}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Slot deleted successfully:', data);
            alert('Slot deleted successfully!');
            calendar.refetchEvents(); // Refresh calendar events
        })
        .catch(error => {
            console.error('Error deleting slot:', error);
            alert('Failed to delete slot. Please try again later.');
        });
    }

    // Function to handle delete button click
    function handleDeleteClick(event) {
        let eventId = event.target.dataset.eventId;
        if (eventId) {
            if (confirm('Are you sure you want to delete this slot?')) {
                deleteEvent(eventId);
            }
        }
    }

    // Event delegation to handle delete button click
    document.addEventListener('click', function(event) {
        if (event.target && event.target.classList.contains('deleteEventBtn')) {
            handleDeleteClick(event);
        }
    });
});
