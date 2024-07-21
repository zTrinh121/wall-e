document.addEventListener("DOMContentLoaded", function() {
    const courseId = document.getElementById('urlCourseId').textContent;
    const date = document.getElementById('urlDate').textContent;
    renderAllStudents(courseId, date);
});

function renderAllStudents(courseId, date) {
    fetch(`/api/teacher/courses/${courseId}/studentss?date=${date}`)
        .then(response => response.json())
        .then(students => {
            const attendanceTable = document.getElementById('attendanceTable');
            attendanceTable.querySelector('tbody').innerHTML = '';

            students.forEach(student => {
                const statusClass = student.attendanceStatus === 'Có mặt' ? 'present' : 'absent';
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${student.studentId}</td>
                    <td>${student.studentName}</td>
                    <td class="status-cell ${statusClass}"
                        data-student-id="${student.studentId}"
                        data-slot-id="${student.slotId}"
                        data-status="${student.attendanceStatus}"> 
                        ${student.attendanceStatus === 'Có mặt' ? 'Có mặt' : 'Vắng'}
                    </td>
                `;
                attendanceTable.querySelector('tbody').appendChild(row);
            });

            enableAttendanceModification();
        })
        .catch(error => {
            console.error('Error fetching students:', error);
        });
}

function enableAttendanceModification() {
    const statusCells = document.querySelectorAll('.status-cell');
    statusCells.forEach(cell => {
        cell.addEventListener('click', function() {
            const studentId = cell.dataset.studentId;
            const slotId = cell.dataset.slotId;
            const currentStatus = cell.dataset.status === 'Có mặt';
            const newStatus = !currentStatus;

            fetch(`/api/teacher/attendance/updateStatus`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: new URLSearchParams({
                    studentId: studentId,
                    slotId: slotId,
                    status: newStatus
                })
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Failed to update status");
                    }
                    return response.text();  // expect text response
                })
                .then(data => {
                    console.log(data);
                    cell.textContent = newStatus ? 'Có mặt' : 'Vắng';
                    cell.dataset.status = newStatus ? 'Có mặt' : 'Vắng';
                    cell.classList.toggle('present', newStatus);
                    cell.classList.toggle('absent', !newStatus);
                })
                .catch(error => console.error('Error:', error));
        });
    });
}
