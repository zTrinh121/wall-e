document.addEventListener('DOMContentLoaded', function() {
    const calendarEl = document.getElementById('calendar');
    const addSlotModal = document.getElementById('addSlotModal');
    const openModalBtn = document.getElementById('openModalBtn');
    const closeModal = document.querySelector('.close');
    const addSlotForm = document.getElementById('addSlotForm');
    let calendar;

    // Fetch events function
    const fetchEvents = function(fetchInfo, successCallback, failureCallback) {
        fetch('/manager/slots/byCenter/1')
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
    };

    // Initialize FullCalendar
    calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        events: fetchEvents,
        eventClick: function(info) {
            console.log('Event clicked:', info.event);
            openEditModal(info.event); // Call the function to open edit modal
        }
    });
    // Function to open edit modal
//    function openEditModal(event) {
//        // Fetch api current slot by get method
//        // next the wrap those info below in fetch POST method
//        document.getElementById('editEventTitle').value = event.title;
//        document.getElementById('editEventDescription').value = event.extendedProps.description;
//
//        // display modal
//        editEventModal.style.display = 'block'; // create a modal w id="editEventModal"
//    }
    function openEditModal(event) {
        // Example: Populate modal fields with event data
        document.getElementById('editEventTitle').value = event.title;
        document.getElementById('editEventDescription').value = event.extendedProps.description;

        // For example, add `editEventModal` ID to your modal and handle its display
        editEventModal.style.display = 'block';
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
//===================================================================
//    addSlotForm.addEventListener('submit', function(event) {
//        event.preventDefault();
//
//        let formData = new FormData(addSlotForm);
//        let slotData = {
//            slotDate: formData.get('slotDate'),
//            slotStartTime: formData.get('slotStartTime'),
//            slotEndTime: formData.get('slotEndTime'),
//            courseName: formData.get('courseName'),
//            roomName: formData.get('roomName')
//        };
//
//        fetch('/manager/slots/create', {
//            method: 'POST',
//            headers: {
//                'Content-Type': 'application/json'
//            },
//            body: JSON.stringify(slotData)
//        })
//        .then(response => {
//            if (!response.ok) {
//                throw new Error('Network response was not ok');
//            }
//            return response.json();
//        })
//        .then(data => {
//            console.log('Slot created successfully:', data);
//            alert('Slot created successfully!');
//            addSlotModal.style.display = 'none';
//            calendar.refetchEvents();
//        })
//        .catch(error => {
//            console.error('Error creating slot:', error);
//            alert('Failed to create slot. Please try again later.');
//        });
//    });
//===================================================================
    deleteEventBtn.addEventListener('click', function() {
        let eventId = document.getElementById('editEventId').value;

        // Send a DELETE request to delete the event
        fetch(`/manager/slots/delete/${eventId}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(() => {
            console.log('Event deleted successfully');
            alert('Event deleted successfully!');
            editEventModal.style.display = 'none'; // Hide the edit modal
            calendar.refetchEvents(); // Refresh the calendar to reflect changes
        })
        .catch(error => {
            console.error('Error deleting event:', error);
            alert('Failed to delete event. Please try again later.');
        });
    });
    document.getElementById('deleteEventBtn').addEventListener('click', function() {
            const eventId = document.getElementById('editEventId').value;
            if (confirm('Are you sure you want to delete this event?')) {
                fetch(`/manager/slots/delete/${eventId}`, {
                    method: 'DELETE'
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response;
                })
                .then(() => {
                    console.log(`Event with ID ${eventId} deleted successfully`);
                    calendar.refetchEvents(); // Refresh the calendar after deletion
                    editEventModal.style.display = 'none'; // Close the modal after deletion
                })
                .catch(error => {
                    console.error('Error deleting event:', error);
                    alert('Failed to delete event. Please try again later.');
                });
            }
        });

//    editEventForm.addEventListener('submit', function(event) {
//        event.preventDefault();
//
//        // Gather updated event details from the form
//        let formData = new FormData(editEventForm);
//        let updatedEvent = {
//            id: formData.get('editEventId'),
//            title: formData.get('editEventTitle'),
//            description: formData.get('editEventDescription')
//        };
//
//        // Send a POST request to update the event
//        fetch(`/manager/slots/update/${updatedEvent.id}`, {
//            method: 'PUT',
//            headers: {
//                'Content-Type': 'application/json'
//            },
//            body: JSON.stringify(updatedEvent)
//        })
//        .then(response => {
//            if (!response.ok) {
//                throw new Error('Network response was not ok');
//            }
//            return response.json();
//        })
//        .then(data => {
//            console.log('Event updated successfully:', data);
//            alert('Event updated successfully!');
//            editEventModal.style.display = 'none'; // Hide the edit modal
//            calendar.refetchEvents(); // Refresh the calendar to reflect changes
//        })
//        .catch(error => {
//            console.error('Error updating event:', error);
//            alert('Failed to update event. Please try again later.');
//        });
//    });
    function openEditModal(event) {
        document.getElementById('editEventId').value = event.id;
        document.getElementById('editEventTitle').value = event.title;
        document.getElementById('editEventDescription').value = event.extendedProps.description;
        editEventModal.style.display = 'block'; // Assuming editEventModal is your modal element
    }

    function populateEditModal(event) {
        document.getElementById('editEventId').value = event.id;
        document.getElementById('editEventTitle').value = event.title;
        document.getElementById('editEventDescription').value = event.extendedProps.description;
        document.getElementById('editEventStart').value = event.startStr;
        document.getElementById('editEventEnd').value = event.endStr;

        // Display the edit modal
        editEventModal.style.display = 'block';
    }
     calendar.setOption('eventClick', function(info) {
        populateEditModal(info.event);
    });
    editEventForm.addEventListener('submit', function(event) {
            event.preventDefault();

            let formData = new FormData(editEventForm);
            let eventId = formData.get('editEventId');
            let eventData = {
                title: formData.get('editEventTitle'),
                description: formData.get('editEventDescription'),
                start: formData.get('editEventStart'),
                end: formData.get('editEventEnd')
            };

            // Fetch API for updating event details
            fetch(`/manager/slots/update/${eventId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(eventData)
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Event updated successfully:', data);
                alert('Event updated successfully!');
                editEventModal.style.display = 'none';
                calendar.refetchEvents();
            })
            .catch(error => {
                console.error('Error updating event:', error);
                alert('Failed to update event. Please try again later.');
            });
        });

     // Close edit modal button click handler
        closeEditModal.addEventListener('click', function() {
            editEventModal.style.display = 'none';
        });
        // Close edit modal when clicking outside the modal
        window.addEventListener('click', function(event) {
            if (event.target === editEventModal) {
                editEventModal.style.display = 'none';
            }
        });

        // Fetch initial events and render calendar
        fetchEvents();
        calendar.render();

//*******************************************************************

    addSlotForm.addEventListener('submit', function(event) {
        event.preventDefault();

        let formData = new FormData(addSlotForm);
        let slotData = {
            slotDate: formData.get('slotDate'),
            slotStartTime: formData.get('slotStartTime'),
            slotEndTime: formData.get('slotEndTime'),
            roomId: formData.get('roomName'),
            courseId: formData.get('courseId')
        };

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
            addSlotModal.style.display = 'none';
        })
        .catch(error => {
            console.error('Error creating slot:', error);
            alert('Failed to create slot. Please try again later.');
        });
    });
    //modal configure
     openModalBtn.addEventListener('click', function() {
        addSlotModal.style.display = 'block';
     });
     closeModal.addEventListener('click', function() {
         addSlotModal.style.display = 'none';
     });
     window.addEventListener('click', function(event) {
         if (event.target === addSlotModal) {
             addSlotModal.style.display = 'none';
         }
     });

     //inner Modal Config
     function displayCoursesInSelector(courses) {
         var selectElement = document.getElementById("courseSelect");
         selectElement.innerHTML = '<option value="">Chọn khóa học...</option>';

         courses.forEach((course) => {
             var option = document.createElement("option");
             option.value = course.id;
             option.textContent = course.name;
             selectElement.appendChild(option);
         });
     }
     // Fetch courses data
     var centerIdz = 1;
     function fetchCourses(centerId) {
         var URL = `/manager/courses/center/${centerIdz}`;
         fetch(URL)
             .then((response) => {
                 if (!response.ok) {
                     throw new Error("Network response was not ok");
                 }
                 return response.json();
             })
             .then((data) => {
                 console.log("Courses received from API:", data);
                 if (!Array.isArray(data)) {
                     data = [data];
                 }
                 displayCoursesInSelector(data);
             })
             .catch((error) => console.error("Error fetching courses:", error));
     }
     fetchCourses(centerIdz);
});
