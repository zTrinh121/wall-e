document.addEventListener('DOMContentLoaded', function() {
    const urlDate = document.getElementById('urlDate').textContent;
    const today = new Date().toISOString().split('T')[0]; // Get today's date in YYYY-MM-DD format
    const attendanceTable = document.getElementById('attendanceTable');
    const courseId = document.getElementById('urlCourseId').textContent;
    renderAllStudents(courseId);
    enableAttendanceModification();
    // if (urlDate === today) {
    //
    //     enableAttendanceModification();
    // } else {
    //
    //     disableAttendanceModification();
    // }
});

function enableAttendanceModification() {
    const rows = document.querySelectorAll('#attendanceTable tbody tr');
    rows.forEach(row => {
        const statusCell = row.querySelector('th:last-child');
        const currentStatus = statusCell.textContent;

        const select = document.createElement('select');
        select.innerHTML = `
            <option value="Có mặt" ${currentStatus === 'Có mặt' ? 'selected' : ''}>Có mặt</option>
            <option value="Vắng" ${currentStatus === 'Vắng' ? 'selected' : ''}>Vắng</option>
        `;

        select.addEventListener('change', function() {
            // Here you can add logic to save the changed status
            console.log('Status changed:', this.value);
        });

        statusCell.textContent = '';
        statusCell.appendChild(select);
    });
}

function disableAttendanceModification() {
    console.log('View-only mode: Attendance cannot be modified');
}

function renderAllStudents(courseId) {
    fetch(`/api/teacher/courses/${courseId}/students`)
        .then(response => response.json())
        .then(students => {
            const attendanceTable = document.getElementById('attendanceTable');

            attendanceTable.querySelector('tbody').innerHTML = '';

            students.forEach(student => {
                const row = document.createElement('tr');
                console.log(student)
                row.innerHTML = `
                    <td>${student.id}</td>
                    <td>${student.name}</td>
                    <td>${student.attendanceStatus}</td>
                `;
                attendanceTable.querySelector('tbody').appendChild(row);
            });

        })
        .catch(error => {
            console.error('Error fetching students:', error);
        });
}
