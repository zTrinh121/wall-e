document.addEventListener('DOMContentLoaded', async function() {
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
            console.log(response);

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

    // Start pagination
    function paginateData(data, pageSize) {
        const paginatedData = [];
        for (let i = 0; i < data.length; i += pageSize) {
            paginatedData.push(data.slice(i, i + pageSize));
        }
        return paginatedData;
    }

    function displayCurrentPage(pageNumber, paginatedData) {
        const currentPageData = paginatedData[pageNumber - 1];
        buildTable(currentPageData);
    }

    function buildPaginationButtons(paginatedData) {
        const totalPages = paginatedData.length;
        const paginationControls = document.getElementById('paginationControls');
        paginationControls.innerHTML = ''; // Clear existing pagination controls
        for (let i = 1; i <= totalPages; i++) {
            const pageButton = document.createElement('button');
            pageButton.textContent = i;
            pageButton.addEventListener('click', () => {
                displayCurrentPage(i, paginatedData);
            });
            paginationControls.appendChild(pageButton);
        }
    }

    // Fetch all users and paginate data
    const pageSize = 10; // Số lượng bản ghi trên mỗi trang
    const allUsers = await fetchUsers();
    const paginatedData = paginateData(allUsers, pageSize);
    buildPaginationButtons(paginatedData);
    displayCurrentPage(1, paginatedData); // Hiển thị trang đầu tiên mặc định
    // End pagination

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
