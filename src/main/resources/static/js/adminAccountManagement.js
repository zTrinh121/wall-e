document.addEventListener('DOMContentLoaded', async function() {
    const roleFilter = document.getElementById('role');
    const tableBody = document.getElementById('tableBody');
    const paginationControls = document.getElementById('paginationControls');
    let allUsers = []; // Store all fetched users
    const itemsPerPage = 5; // Number of users per page
    let currentPage = 1; // Current page number
    let selectedRole = 0; // Selected role filter

    async function fetchUsers(roleId = 0) {
        try {
            const url = roleId ? `/api/users?role=${roleId}` : '/api/users';
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            allUsers = await response.json();
            renderTable();
        } catch (error) {
            console.error('Error fetching users:', error);
        }
    }

    function renderTable() {
        const start = (currentPage - 1) * itemsPerPage;
        const end = start + itemsPerPage;
        const usersToDisplay = allUsers.slice(start, end);

        buildTable(usersToDisplay);
        renderPaginationControls();
    }

    // Giả sử bạn có một đối tượng ánh xạ ID vai trò sang mô tả vai trò
    const roleDescriptions = {
        1: 'Quản trị viên',
        2: 'Học sinh',
        3: 'Giáo viên',
        4: 'Phụ huynh',
        5: 'Quản lý'
    };

    function buildTable(data) {
        tableBody.innerHTML = ''; // Clear existing table rows

        data.forEach((e) => {
            let lock = e.accStatus ? "Khóa" : "Mở khóa";
            let roleDescription = roleDescriptions[e.roleId] || 'Không xác định'; // Sử dụng ánh xạ để lấy mô tả vai trò
            let row = `<tr>
            <td><p>${e.userCode}</p></td>
            <td><p>${roleDescription}</p></td>
            <td><p>${e.accStatus ? 'Hoạt động' : 'Không hoạt động'}</p></td>
            <td><button class="action-button" data-user-id="${e.userId}" data-status="${!e.accStatus}">${lock}</button></td>
        </tr>`;
            tableBody.innerHTML += row;
        });
        addEventListenersToButtons();
    }


    function addEventListenersToButtons() {
        const buttons = document.querySelectorAll('.action-button');
        buttons.forEach(button => {
            button.addEventListener('click', async function () {
                const userId = button.getAttribute('data-user-id');
                const newStatus = button.getAttribute('data-status') === 'true';
                await updateUserStatus(userId, newStatus);
                fetchUsers(selectedRole); // Refresh the table after updating status with current role filter
            });
        });
    }

    function renderPaginationControls() {
        const totalPages = Math.ceil(allUsers.length / itemsPerPage);
        paginationControls.innerHTML = '';

        for (let i = 1; i <= totalPages; i++) {
            const pageButton = document.createElement('button');
            pageButton.textContent = i;
            pageButton.classList.add('page-button');
            if (i === currentPage) {
                pageButton.classList.add('active');
            }
            pageButton.addEventListener('click', () => {
                currentPage = i;
                renderTable();
            });
            paginationControls.appendChild(pageButton);
        }
    }

    function filterTable() {
        const selectedRoleValue = roleFilter.value;
        selectedRole = 0; // Default to 'all'
        if (selectedRoleValue === 'teacher') { selectedRole = 3; }
        else if (selectedRoleValue === 'student') { selectedRole = 1; }
        else if (selectedRoleValue === 'parent') { selectedRole = 2; }
        else if (selectedRoleValue === 'manager') { selectedRole = 4; }
        fetchUsers(selectedRole);
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

            // Display toast notification
            const username = document.querySelector(`button[data-user-id="${userId}"]`).parentElement.parentElement.querySelector('td p').textContent;
            showToast(`<i class="fas fa-check"></i> ${username} đã ${status ? 'mở khóa' : 'khóa'} thành công`);

        } catch (error) {
            console.error('Error updating user status:', error);
        }
    }

    // Start show toast
    function showToast(message) {
        const toastContainer = document.getElementById('toastContainer');
        const toast = document.createElement('div');
        toast.className = 'toast';
        toast.innerHTML = message;
        toastContainer.appendChild(toast);
        setTimeout(() => {
            toast.classList.add('show');
            setTimeout(() => {
                toast.classList.remove('show');
                setTimeout(() => {
                    toast.remove();
                }, 500);
            }, 3000);
        }, 100);
    }
    // End show toast

    roleFilter.addEventListener('change', filterTable);

    fetchUsers(); // Initial fetch to display all users
});