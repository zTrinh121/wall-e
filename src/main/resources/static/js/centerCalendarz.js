document.addEventListener('DOMContentLoaded', function() {
    var urlParams = window.location.href;
    console.log("Current URL:", urlParams);
    var urlCenParts = urlParams.split("centerId=");
    var centerIdz = urlCenParts.length > 1 ? urlCenParts[1].split(/[?&]/)[0] : null;
    console.log("Center ID:", centerIdz);

    const calendarEl = document.getElementById('calendar');
    const editEventModal = document.getElementById('editEventModal');
    const closeModalAdd = document.querySelector('#addSlotModal .close');
    const closeModalEdit = document.querySelector('#editEventModal .close');
    const editEventForm = document.getElementById('editEventForm');
    let calendar;

    // Function to fetch events from the server
    const fetchEvents = function(fetchInfo, successCallback, failureCallback) {
        fetch(`/manager/slots/byCenter/${centerIdz}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                let events = data.map(slot => ({
                    id: slot.slotId,
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
        events: function(fetchInfo, successCallback, failureCallback) {
            fetchEvents(fetchInfo, successCallback, failureCallback);
        },
        eventClick: function(info) {
            console.log('Event clicked:', info.event);
            openEditModal(info.event); // Call the function to open edit modal
            console.log(info);
        }
    });

    //NEEDED COMPONENTS
    function formatTime(timeString) {
        // Assuming timeString is in ISO format like 'HH:mm:ss.SSS'
        return timeString.split('.')[0]; // Remove milliseconds
    }

    var selectedRoomIdn;
    function displayNewRoomsInSelectorF(rooms) {
        var selectElement = document.getElementById("newRoom");
        selectElement.innerHTML = '<option value="">Chọn phòng học...</option>';

        rooms.forEach((room) => {
            var option = document.createElement("option");
            option.value = room.id;
            option.textContent = room.name;
            selectElement.appendChild(option);
        });
        selectElement.addEventListener("change", function () {
            selectedRoomIdn = selectElement.value;
            console.log(selectedRoomIdn);
        });
    }

    var selectedCourseIdn;
    function displayNewCoursesInSelectorF(courses) {
        var selectElement = document.getElementById("newCourse");
        selectElement.innerHTML = '<option value="">Chọn khoá học...</option>';

        courses.forEach((course) => {
            var option = document.createElement("option");
            option.value = course.id;
            option.textContent = course.name;
            selectElement.appendChild(option);
        });
        selectElement.addEventListener("change", function () {
            selectedCourseIdn = selectElement.value;
            console.log(selectedCourseIdn);
        });
    }
    var centerIdz;
    //getting centerId
    const urlParamss = window.location.href;
    console.log("Current URL:", urlParamss);
    // Extract centerId from the URL
    const urlPartss = urlParamss.split("centerId=");
    centerIdz = urlPartss.length > 1 ? urlPartss[1].split("&")[0] : null;
    console.log("Center ID:", centerIdz);
//api get courses
    function fetchCoursesF(centerIdz) {
        var URLcourse = `/manager/courses/center/${centerIdz}`;
        fetch(URLcourse)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((data) => {
                console.log("Data received from API:", data);
                if (!Array.isArray(data)) {
                    data = [data];
                }
                displayNewCoursesInSelectorF(data);
            })
            .catch((error) => console.error("Error fetching courses:", error));
    }
    fetchCoursesF(centerIdz);
    //api get rooms
    function fetchRoomsF(centerIdz) {
        var URLroom = `/manager/getRooms/${centerIdz}`;
        fetch(URLroom)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((data) => {
                console.log("Data received from API:", data);
                if (!Array.isArray(data)) {
                    data = [data];
                }
                displayNewRoomsInSelectorF(data);
            })
            .catch((error) => console.error("Error fetching rooms:", error));
    }
    fetchRoomsF(centerIdz);


    function openEditModal(info) {
        document.getElementById('editEventId').value = info._def.publicId;
        document.getElementById('editEventTitle').value = info._def.title;
        document.getElementById('editEventDescription').value = info._def.extendedProps.description;

        // Access start and end times from info._instance.range
        document.getElementById('editSlotDate').value = info.startStr.split('T')[0]; // Splitting datetime to get date
//        document.getElementById('editSlotDate').value = info._instance.range.start.toLocaleDateString().split('T')[0]; // Example format
        document.getElementById('editSlotStartTime').value = info._instance.range.start.toLocaleTimeString(); // Example format
        document.getElementById('editSlotEndTime').value = info._instance.range.end.toLocaleTimeString(); // Example format

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

    // Close add slot modal when close button is clicked
    closeModalAdd.addEventListener('click', function() {
        addSlotModal.style.display = 'none';
    });

    // Close edit event modal when close button is clicked
    closeModalEdit.addEventListener('click', function() {
        editEventModal.style.display = 'none';
    });

    // Render the calendar
    calendar.render();

    // Open add slot modal when button is clicked
    openModalBtn.addEventListener('click', function() {
        addSlotModal.style.display = 'block';
    });
    // Handle form submission to update an existing slot
    editEventForm.addEventListener('submit', function(event) {
        event.preventDefault();

        var formData = new FormData(editEventForm);
        console.log(formData)
        var slotDaten = formData.get("editSlotDate"); //+ "T00:00:00.000+00:00";
        var slotStartTimen = formatTime(formData.get("editSlotStartTime")) + ":00"; ;
        var slotEndTimen = formData.get("editSlotEndTime") + ":00";
        let eventId = formData.get('editEventId');
        console.log(eventId);
        let eventData = {
            slotDate: slotDaten,
            slotStartTime: slotStartTimen,
            slotEndTime: slotEndTimen,
            courseId: selectedCourseIdn,
            roomId: selectedRoomIdn
        };
        console.log(eventData)

        fetch(`/manager/slots/update/${eventId}`    , {
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
                if (error.response) {
                    error.response.json().then(data => {
                        console.error('Server error details:', data);
                    }).catch(err => {
                        console.error('Error parsing server response:', err);
                    });
                } else {
                    console.error('Network error:', error.message);
                }
        });
    });

    function handleDeleteClick(event) {
        let eventId = event.target.dataset.eventId;
        if (eventId) {
            if (confirm('Are you sure you want to delete this event?')) {
                deleteEvent(eventId);
            }
        }
    }

    document.addEventListener('click', function(event) {
        if (event.target && event.target.classList.contains('deleteEventBtn')) {
            handleDeleteClick(event);
        }
    });

    function deleteEvent(eventId) {
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
            console.log('Event deleted successfully:', data);
            alert('Event deleted successfully!');
            calendar.refetchEvents(); // Refresh calendar events
        })
        .catch(error => {
            console.error('Error deleting event:', error);
            alert('Failed to delete event. Please try again later.');
        });
    }
});

//ADDING A SLOT
document.addEventListener("DOMContentLoaded", () => {
    var centerIdz;
    //getting centerId
    const urlParams = window.location.href;
    console.log("Current URL:", urlParams);
    // Extract centerId from the URL
    const urlParts = urlParams.split("centerId=");
    centerIdz = urlParts.length > 1 ? urlParts[1].split("&")[0] : null;
    console.log("Center ID:", centerIdz);

    //Main
    var openModalBtn = document.getElementById("openModalBtn");
    var addSlotModal = document.getElementById("addSlotModal");
    var addSlotForm = document.getElementById("addSlotForm");
    // Hide modal
    if (addSlotModal) {
        addSlotModal.style.display = "none";
    }
    // Open modal
    if (openModalBtn) {
        openModalBtn.addEventListener("click", () => {
            console.log("Opening Add Course Modal");
            addSlotModal.style.display = "block";
        });
    }
    // Close/Cancel
    if (cancelAdd) {
        cancelAdd.addEventListener("click", () => {
            addSlotModal.style.display = "none";
        });
    }
    window.addEventListener("click", function (event) {
        if (event.target === addSlotModal) {
            addSlotModal.style.display = "none";
        }
    });
    //POST
    if (addSlotForm) {
        addSlotForm.addEventListener("submit", function (event) {
            event.preventDefault();
            var formData = new FormData(addSlotForm);
            var slotStartTimeZ = formData.get("slotStartTime") + ":00";
            var slotEndTimeZ = formData.get("slotEndTime") + ":00";
            var slotData = {
                slotDate: formData.get("slotDate"),
                slotStartTime: slotStartTimeZ,
                slotEndTime: slotEndTimeZ,
                roomId: selectedRoomId,
                courseId: selectedCourseId
            };
            console.log(slotData);
            fetch("/manager/slots/create", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(slotData)
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then(data => {
                console.log("Slot added:", data);
                addSlotModal.style.display = "none";
            })
            .catch(error => {
                console.error("Error adding a new slot:", error);
                // Ensure response handling is within the catch block
                if (error.response) {
                        error.response.json().then(data => {
                            console.error("Server error details:", data);
                        }).catch(err => {
                            console.error("Error parsing server response:", err);
                        });
                    } else {
                        console.error("Network error:", error.message);
                    }
            });

        });
    }
    //courses
    var selectedCourseId;
    function displayCoursesInSelector(courses) {
        var selectElement = document.getElementById("courseSelect");
        selectElement.innerHTML = '<option value="">Chọn khoá học...</option>';

        courses.forEach((course) => {
            var option = document.createElement("option");
            option.value = course.id;
            option.textContent = course.name;
            selectElement.appendChild(option);
        });
        selectElement.addEventListener("change", function () {
            selectedCourseId = selectElement.value;
            console.log(selectedCourseId);
        });
    }
    //api get courses
    function fetchCourses(centerIdz) {
        var URLcourse = `/manager/courses/center/${centerIdz}`;
        fetch(URLcourse)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((data) => {
                console.log("Data received from API:", data);
                if (!Array.isArray(data)) {
                    data = [data];
                }
                displayCoursesInSelector(data);
            })
            .catch((error) => console.error("Error fetching courses:", error));
    }
    fetchCourses(centerIdz);

    //rooms
    var selectedRoomId;
    function displayRoomsInSelector(rooms) {
        var selectElement = document.getElementById("roomSelect");
        selectElement.innerHTML = '<option value="">Chọn phòng học...</option>';

        rooms.forEach((room) => {
            var option = document.createElement("option");
            option.value = room.id;
            option.textContent = room.name;
            selectElement.appendChild(option);
        });
        selectElement.addEventListener("change", function () {
            selectedRoomId = selectElement.value;
            console.log(selectedRoomId);
        });
    }
    //api get rooms
    function fetchRooms(centerIdz) {
        var URLroom = `/manager/getRooms/${centerIdz}`;
        fetch(URLroom)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((data) => {
                console.log("Data received from API:", data);
                if (!Array.isArray(data)) {
                    data = [data];
                }
                displayRoomsInSelector(data);
            })
            .catch((error) => console.error("Error fetching rooms:", error));
    }
    fetchRooms(centerIdz);

});

//Adding full slots DOW
document.addEventListener("DOMContentLoaded", () => {
    var centerIdz;
    //getting centerId
    const urlParams = window.location.href;
    console.log("Current URL:", urlParams);
    // Extract centerId from the URL
    const urlParts = urlParams.split("centerId=");
    centerIdz = urlParts.length > 1 ? urlParts[1].split("&")[0] : null;
    console.log("Center ID:", centerIdz);

    //Main
    var openAddFull = document.getElementById("openAddFull");
    var addFullSlotModal = document.getElementById("addFullSlotModal");
    var addFullSlotForm = document.getElementById("addFullSlotForm");
    // Hide modal
    if (addFullSlotModal) {
        addFullSlotModal.style.display = "none";
    }
    // Open modal
    if (openAddFull) {
        openAddFull.addEventListener("click", () => {
            console.log("Opening Add Course Modal");
            addFullSlotModal.style.display = "block";
        });
    }
    // Close/Cancel
    if (cancelAddFull) {
        cancelAddFull.addEventListener("click", () => {
            addFullSlotModal.style.display = "none";
        });
    }
    window.addEventListener("click", function (event) {
        if (event.target === addFullSlotModal) {
            addFullSlotModal.style.display = "none";
        }
    });
    //POST-FULL
    if (addFullSlotForm) {
        addFullSlotForm.addEventListener("submit", function (event) {
            event.preventDefault();
            var formData = new FormData(addFullSlotForm);
            var slotStartTimeF = formData.get("slotStartTimeF") + ":00";
            var slotEndTimeF = formData.get("slotEndTimeF") + ":00";
                initializeEventListeners();
//            var slotData = {
//                dayOfWeek: selectedDayOfWeek,
//                startTime: slotStartTimeF,
//                endTime: slotEndTimeF
//            };
            var slotData = [{
                dayOfWeek: selectedDayOfWeek,
                startTime: slotStartTimeF,
                endTime: slotEndTimeF
            }];
//                            roomId: selectedRoomIdz,
//                            courseId: selectedCourseIdz

//            if (!Array.isArray(slotData)) {
//                slotData = [slotData];
//            }
            console.log(slotData);
            console.log(selectedRoomIdz);
            console.log(selectedCourseIdz);
            fetch(`/manager/overallSlots/certainCourse/insert?roomId=${selectedRoomIdz}&courseId=${selectedCourseIdz}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(slotData)
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then(data => {
                console.log("Slot added:", data);
                addFullSlotModal.style.display = "none";
            })
            .catch(error => {
                console.error("Error adding a new slot:", error);
                if (error.response) {
                    error.response.json().then(data => {
                        console.error("Server error details:", data);
                    }).catch(err => {
                        console.error("Error parsing server response:", err);
                    });
                } else {
                    console.error("Network error:", error.message);
                }
            });

        });
    }

    //get by dayOfWeek
    var selectedDayOfWeek;
    function initializeEventListeners() {
        var dayOfWeekSelect = document.getElementById("dayOfWeekSelect");

        dayOfWeekSelect.addEventListener("change", function () {
            var selectedOption = dayOfWeekSelect.options[dayOfWeekSelect.selectedIndex];
            selectedDayOfWeek = selectedOption.getAttribute("dateOfWeek");

            console.log("Selected day of week:", selectedDayOfWeek);
        });
    }
    initializeEventListeners();
    //courses
    var selectedCourseIdz;
    function displayCoursesInSelectorF(courses) {
        var selectElement = document.getElementById("courseSelectf");
        selectElement.innerHTML = '<option value="">Chọn khoá học...</option>';

        courses.forEach((course) => {
            var option = document.createElement("option");
            option.value = course.id;
            option.textContent = course.name;
            selectElement.appendChild(option);
        });
        selectElement.addEventListener("change", function () {
            selectedCourseIdz = selectElement.value;
            console.log(selectedCourseIdz);
        });
    }
    //api get courses
    function fetchCoursesF(centerIdz) {
        var URLcourse = `/manager/courses/center/${centerIdz}`;
        fetch(URLcourse)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((data) => {
                console.log("Data received from API:", data);
                if (!Array.isArray(data)) {
                    data = [data];
                }
                displayCoursesInSelectorF(data);
            })
            .catch((error) => console.error("Error fetching courses:", error));
    }
    fetchCoursesF(centerIdz);

    //rooms
    var selectedRoomIdz;
    function displayRoomsInSelectorF(rooms) {
        var selectElement = document.getElementById("roomSelectf");
        selectElement.innerHTML = '<option value="">Chọn phòng học...</option>';

        rooms.forEach((room) => {
            var option = document.createElement("option");
            option.value = room.id;
            option.textContent = room.name;
            selectElement.appendChild(option);
        });
        selectElement.addEventListener("change", function () {
            selectedRoomIdz = selectElement.value;
            console.log(selectedRoomIdz);
        });
    }
    //api get rooms
    function fetchRoomsF(centerIdz) {
        var URLroom = `/manager/getRooms/${centerIdz}`;
        fetch(URLroom)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((data) => {
                console.log("Data received from API:", data);
                if (!Array.isArray(data)) {
                    data = [data];
                }
                displayRoomsInSelectorF(data);
            })
            .catch((error) => console.error("Error fetching rooms:", error));
    }
    fetchRoomsF(centerIdz);
});


// TURN BACK CENTER DETAIL
document.addEventListener('DOMContentLoaded', function() {
    var urlParams = window.location.href;
    console.log("Current URL:", urlParams);
    var urlCenParts = urlParams.split("centerId=");
    var centerIdz = urlCenParts.length > 1 ? urlCenParts[1].split(/[?&]/)[0] : null;
    console.log("Center ID:", centerIdz);
//turnBack
    var turnBack = document.getElementById("turnBack");
    turnBack.addEventListener("click", function(event) {
        event.preventDefault();
            var url = `/manager/centerHome?centerIdn=`;
            url += encodeURIComponent(centerIdz);
            window.location.href = url;
    });
});
