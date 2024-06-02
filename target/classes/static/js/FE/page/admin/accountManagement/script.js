document.addEventListener('DOMContentLoaded', function() {
    const roleFilter = document.getElementById('role');
    const tableBody = document.getElementById('tableBody');

    async function fetchUsers(roleId = 0) {
        try {
            const url = roleId ? `/api/users?role=${roleId}` : '/api/users';
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            const data = await response.json();
            console.log('Fetched data:', data); // Log fetched data
            buildTable(data);
        } catch (error) {
            console.error('Error fetching users:', error);
        }
    }

    async function updateUserStatus(userId, status) {
        try {
            const response = await fetch(`/users/updateStatus`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: `userId=${userId}&status=${status}`
            });
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
        } catch (error) {
            console.error('Error updating user status:', error);
        }
    }

    function buildTable(data) {
        tableBody.innerHTML = ''; // Clear existing table rows
        data.forEach((e) => {
            let lock = e.status ? "Khóa" : "Mở khóa";
            let row = `<tr>
                    <td><p>${e.username}</p></td>
                    <td><p>${e.role.description}</p></td>
                    <td><p>${e.status ? 'Hoạt động' : 'Không hoạt động'}</p></td>
                    <td><button class="action-button" data-user-id="${e.id}" data-status="${!e.status}">${lock}</button></td>
                 </tr>`;
            tableBody.innerHTML += row;
        });
        addEventListenersToButtons();
    }

    function filterTable() {
        const selectedRole = roleFilter.value;
        let roleEnum = 0; // Default to 'all'
        if (selectedRole === 'teacher') { roleEnum = 2; }
        else if (selectedRole === 'student') { roleEnum = 1; }
        else if (selectedRole === 'parent') { roleEnum = 3; }
        else if (selectedRole === 'manager') { roleEnum = 4; }
        else if (selectedRole === 'admin') { roleEnum = 5; }

        fetchUsers(roleEnum);
    }

    function addEventListenersToButtons() {
        const buttons = document.querySelectorAll('.action-button');
        buttons.forEach(button => {
            button.addEventListener('click', async function () {
                const userId = button.getAttribute('data-user-id');
                const newStatus = button.getAttribute('data-status') === 'true';
                await updateUserStatus(userId, newStatus);
                fetchUsers(); // Refresh the table after updating status
            });
        });
    }

    roleFilter.addEventListener('change', filterTable);

    fetchUsers(); // Initial fetch to display all users
});
