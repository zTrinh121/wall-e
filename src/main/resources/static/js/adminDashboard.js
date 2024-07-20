

document.addEventListener("DOMContentLoaded", function () {
    var toast = document.getElementById("toast");
    var toastInfo = document.getElementById("toast-info");
    var createNotificationBtn = document.getElementById("createNotificationBtn");
    var createNotificationModal = document.getElementById("createNotificationModal");
    var closeButton = createNotificationModal.querySelector(".close");
    var notificationForm = document.getElementById("notificationForm");
    var notificationTypeSelect = notificationForm.querySelector("select");
    var publicNotificationBtn = document.getElementById("publicNotificationBtn");
    var privateNotificationBtn = document.getElementById("privateNotificationBtn");
    var currentPage = 1;
    var itemsPerPage = 5;
    var modal = document.getElementById('viewNotificationModal');
    var viewNotificationTitle = modal.querySelector('#viewNotificationTitle');
    var viewNotificationContent = modal.querySelector('#viewNotificationContent');
    var viewNotificationCreatedAt = modal.querySelector('#viewNotificationCreatedAt');
    var viewNotificationSendTo = modal.querySelector('#viewNotificationSendTo');
    var notificationTableBody = document.getElementById("notificationTableBody");
    var allPublicNotifications = [];
    var allPrivateNotifications = [];
    var paginationControls = document.getElementById("paginationControls");

    publicNotificationBtn.addEventListener("click", function () {
        fetchAndDisplayPublicNotifications();
    });

    privateNotificationBtn.addEventListener("click", function () {
        fetchAndDisplayPrivateNotifications();
    });

    function fetchAndDisplayPublicNotifications() {
        fetch("/admin-systemNotifications")
            .then(response => response.json())
            .then(data => {
                allPublicNotifications = data;
                currentPage = 1;
                renderTable(allPublicNotifications);
            })
            .catch(error => console.error("Error fetching public notifications:", error));
    }

    function fetchAndDisplayPrivateNotifications() {
        fetch("/individualNotifications")
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Network response was not ok: ${response.statusText}`);
                }
                return response.json();
            })
            .then(data => {
                console.log(data);
                allPrivateNotifications = data.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt)); // Sort by date descending
                currentPage = 1;
                console.log(allPrivateNotifications)
                renderTable(allPrivateNotifications);
            })
            .catch(error => console.error("Error fetching private notifications:", error));
    }

    function renderTable(notificationsList) {
        const start = (currentPage - 1) * itemsPerPage;
        const end = start + itemsPerPage;
        const postsToDisplay = notificationsList.slice(start, end);
        displayNotifications(postsToDisplay);
        renderPaginationControls(notificationsList);
    }

    function displayNotifications(notifications) {
        notificationTableBody.innerHTML = "";
        var tableHeader = document.querySelector("#notificationTable thead tr");
        var isPrivate = notifications.length && (notifications[0].sendToUser);
        if (isPrivate) {
            tableHeader.innerHTML = `
            <th>Tiêu đề</th>
            <th>Ngày tạo</th>
            <th>Gửi tới</th>
            <th>Chi tiết</th>
            <th>Thao tác</th>
        `;
        } else {
            tableHeader.innerHTML = `
            <th>Tiêu đề</th>
            <th>Ngày tạo</th>
            <th>Chi tiết</th>
            <th>Thao tác</th>
        `;
        }

        notifications.forEach(function (notification) {
            const formattedDate = formatDate(notification.createdAt);
            console.log(notification.sendToUser);
            var row = `
            <tr id="${notification.id}">
                <td>${notification.title}</td>
                <td>${formattedDate}</td></td>
                ${isPrivate ? `<td>${notification.sendToUser.username}</td>` : ''}
                <td><button class="view-details" data-title="${notification.title}" data-content="${notification.content}" data-createdat="${notification.createdAt}" ${isPrivate ? `data-sendto="${notification.sendToUser ? notification.sendToUser.username : notification.sendToUser.username}"` : ''}>Xem</button></td>
                <td>
                    <i class="fas fa-edit" data-id="${notification.id}" ${isPrivate ? `data-sendto="${notification.sendToUser ? notification.sendToUser.username : notification.sendToUser.username}"` : ''}></i>
                    <i class="fas fa-trash" data-id="${notification.id}" ${isPrivate ? `data-sendto="${notification.sendToUser ? notification.sendToUser.username : notification.sendToUser.username}"` : ''}></i>
                </td>
            </tr>
        `;
            notificationTableBody.insertAdjacentHTML("beforeend", row);
        });
    }

    function displayNotificationDetails(title, content, createdAt, sendTo) {
        viewNotificationTitle.textContent = "Tiêu đề: " +title;
        viewNotificationContent.textContent = "Nội dung: " + content;
        const formattedDate = formatDate(createdAt);
        viewNotificationCreatedAt.textContent = "Ngày tạo: " + formattedDate;
        if(sendTo !== null){
            viewNotificationSendTo.style.display = "block";
            viewNotificationSendTo.textContent = "Gửi đến " + sendTo;
        }
        else viewNotificationSendTo.style.display = "none"
        modal.style.display = "block";
    }

    function renderPaginationControls(notificationsList) {
        const totalPages = Math.ceil(notificationsList.length / itemsPerPage);
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
                renderTable(notificationsList);
            });
            paginationControls.appendChild(pageButton);
        }
    }

    document.getElementById('closeViewNotification').addEventListener('click', function () {
        modal.style.display = "none";
    });

    window.addEventListener('click', function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    });



    function displayDeleteModal(notification) {
        var deleteModal = document.getElementById("deleteModal");
        var deleteConfirmBtn = deleteModal.querySelector("#confirmDelete");
        var deleteCancelBtn = deleteModal.querySelector("#cancelDelete");
        var userName = deleteModal.querySelector("#userName");
        userName.textContent = notification.title;
        deleteModal.style.display = "block";
        var notificationId = notification.id;
        if(notification.sendTo){
            console.log("Bao")
            deleteConfirmBtn.addEventListener("click", function () {
                fetch(`/admin-individualNotification/delete/${notificationId}`, {
                    method: "DELETE"
                })
                    .then(function (response) {
                        if (!response.ok) {
                            throw new Error("Network response was not ok");
                        }
                        showToast("Xóa thông báo cá nhân thành công!", "#4caf50", "check-circle");
                        var row = document.getElementById(notificationId);
                        if (row) {
                            row.remove();
                        }
                    })
                    .catch(function (error) {
                        console.error("Error deleting notification:", error);
                        showToast("Xóa thông báo cá nhân thất bại!", "#4caf50", "times-circle");
                    });

                deleteModal.style.display = "none";
            });

            deleteCancelBtn.addEventListener("click", function () {
                deleteModal.style.display = "none";
            });
        }else{
            deleteConfirmBtn.addEventListener("click", function () {
                fetch(`/admin-systemNotification/delete/${notificationId}`, {
                    method: "DELETE"
                })
                    .then(function (response) {
                        if (!response.ok) {
                            throw new Error("Network response was not ok");
                        }
                        showToast("Xóa thông báo chung thành công!", "#4caf50", "check-circle");
                        var row = document.getElementById(notificationId);
                        if (row) {
                            row.remove();
                        }
                    })
                    .catch(function (error) {
                        console.error("Error deleting notification:", error);
                        showToast("Xóa thông báo cá nhân thất bại!", "#4caf50", "times-circle");
                    });

                deleteModal.style.display = "none";
            });

            deleteCancelBtn.addEventListener("click", function () {
                deleteModal.style.display = "none";
            });
        }


    }

    notificationTableBody.addEventListener("click", function (event) {
        if (event.target.classList.contains("fa-trash")) {
            var notificationId = event.target.getAttribute("data-id");
            var notificationRow = event.target.closest("tr");
            var notification = {
                id: notificationId,
                title: notificationRow.querySelector("td:first-of-type").textContent,
                content: notificationRow.querySelector(".view-details").getAttribute("data-content"),
                sendTo: notificationRow.querySelector(".view-details").getAttribute("data-sendto")
            };
            console.log(notification)
            displayDeleteModal(notification);
        }

        if (event.target.classList.contains("fa-edit")) {
            var notificationId = event.target.getAttribute("data-id");
            var notificationRow = event.target.closest("tr");
            var notification = {
                id: notificationId,
                title: notificationRow.querySelector("td:first-of-type").textContent,
                content: notificationRow.querySelector(".view-details").getAttribute("data-content"),
                sendTo: notificationRow.querySelector(".view-details").getAttribute("data-sendto")
            };

            displayEditModal(notification);
        }

        if (event.target.classList.contains("view-details")) {
            var title = event.target.getAttribute("data-title");
            var content = event.target.getAttribute("data-content");
            var createdAt = event.target.getAttribute("data-createdat");
            var sendTo = event.target.getAttribute("data-sendto");
            displayNotificationDetails(title, content, createdAt, sendTo);
        }
    });

    function formatDate(dateString) {
        const dateParts = dateString.split(' '); // Split date and time
        const date = dateParts[0]; // Extract date part
        const [day, month, year] = date.split('-'); // Split into day, month, year

        // Ensure day and month are always two digits
        const formattedDay = day.padStart(2, '0');
        const formattedMonth = month.padStart(2, '0');

        return `${formattedDay}/${formattedMonth}/${year}`;
    }

    function displayEditModal(notification) {
        var editModal = document.getElementById("editModal");
        var editConfirmBtn = editModal.querySelector("#confirmEdit");
        var editCancelBtn = editModal.querySelector("#cancelEdit");
        var editTitle = editModal.querySelector("#editTitle");
        var editContent = editModal.querySelector("#editContent");
        var editSendTo = editModal.querySelector("#editSendTo");
        var editSendToLabel = editModal.querySelector("#editSendToLabel");

        editTitle.value = notification.title;
        editContent.value = notification.content;

        if (notification.title !== undefined && notification.title !== null) {
            editTitle.value = notification.title;
        }
        if (notification.content !== undefined && notification.content !== null) {
            editContent.value = notification.content;
        }

        if (notification.sendTo) {
            editSendTo.style.display = "block";
            editSendToLabel.style.display = "block";
            editSendTo.value = notification.sendTo;
        } else {
            editSendTo.style.display = "none";
            editSendToLabel.style.display = "none";
        }

        editModal.style.display = "block";

        function handleEditConfirm() {
            var updatedNotification = {
                id: notification.id,
                title: editTitle.value,
                content: editContent.value,
                sendTo: notification.sendTo
            };

            if(notification.sendTo){
                console.log(notification)
                fetch(`/admin-individualNotification/update/${updatedNotification.id}`, {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(updatedNotification)
                })
                    .then(function (response) {
                        if (!response.ok) {
                            throw new Error("Network response was not ok");
                        }
                        return response.json();
                    })
                    .then(function (data) {
                        showToast("Cập nhật thông báo cá nhân thành công!", "#4caf50", "check-circle");
                        fetchAndDisplayPrivateNotifications();
                    })
                    .catch(function (error) {
                        console.error("Error updating public notification:", error);
                        showToast("Cập nhật thông báo cá nhân thất bại!", "red", "times-circle");
                    });
            }else{
                fetch(`/admin-systemNotification/update/${updatedNotification.id}`, {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(updatedNotification)
                })
                    .then(function (response) {
                        if (!response.ok) {
                            throw new Error("Network response was not ok");
                        }
                        return response.json();
                    })
                    .then(function (data) {
                        showToast("Cập nhật thông báo chung thành công!", "#4caf50", "check-circle");
                        fetchAndDisplayPublicNotifications(); // Fetch the updated notifications list
                    })
                    .catch(function (error) {
                        console.error("Error updating public notification:", error);
                        showToast("Cập nhật thông báo cá nhân thất bại!", "red", "check-circle");
                    });
            }

            editModal.style.display = "none";
            editConfirmBtn.removeEventListener("click", handleEditConfirm); // Remove the event listener after handling
        }

        editConfirmBtn.addEventListener("click", handleEditConfirm);

        editCancelBtn.addEventListener("click", function () {
            editModal.style.display = "none";
            editConfirmBtn.removeEventListener("click", handleEditConfirm); // Ensure no duplicate listeners
        });
    }

    createNotificationBtn.addEventListener("click", function () {
        resetFormFields();
        createNotificationModal.style.display = "block";
    });

    function resetFormFields() {
        notificationForm.reset();
        clearErrors();
        toggleSendToField();
    }

    function showToast(message, backgroundColor, icon) {
        toastInfo.innerHTML = `<i class="fas fa-${icon}"></i> ${message}`;
        toast.style.backgroundColor = backgroundColor;
        toast.style.display = "block";
        setTimeout(function () {
            toast.style.display = "none";
        }, 3000);
    }

    closeButton.addEventListener("click", function () {
        createNotificationModal.style.display = "none";
    });

    window.addEventListener("click", function (event) {
        if (event.target == createNotificationModal) {
            createNotificationModal.style.display = "none";
        }
    });

    notificationForm.addEventListener("submit", async function (event) {
        event.preventDefault();
        clearErrors();
        var formData = new FormData(notificationForm);
        var data = {
            title: formData.get("title"),
            content: formData.get("content"),
            type: formData.get("notificationType"),
            sendTo: formData.get("send-to").toUpperCase()
        };

        const recipients = data.sendTo.split(',').map(email => email.trim());
        console.log(data)
        console.log(recipients)
        var isValid = validateFormData(data);

        if(data.type === 'private' && isValid){
            for (const recipient of recipients){
                const newData = { ...data, sendTo: recipient };
                console.log(newData)
                var url = "/individualNotification/create";
                try {
                    const centersValid = await fetchCentersAndUsersAndCompareSendTo(newData.sendTo);
                    console.log(centersValid)
                    if (!centersValid) {
                        showError("notificationSendTo", "Người nhận không hợp lệ.");
                        return;
                    }

                    const response = await fetch(url, {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify(newData)
                    });
                    if (!response.ok) {
                        throw new Error("Network response was not ok");
                    }
                    const responseData = await response.json();
                    showToast("Tạo thông báo cá nhân thành công!", "#4caf50", "check-circle");
                    createNotificationModal.style.display = "none";
                    fetchAndDisplayPrivateNotifications();
                } catch (error) {
                    console.error("Error creating notification:", error);
                    showToast("Lỗi khi tạo thông báo cá nhân!", "red", "times-circle");
                }
            }

        }else if(data.type === 'public' && isValid){
            fetch("/admin-systemNotification/create", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            })
                .then(function (response) {
                    if (!response.ok) {
                        throw new Error("Network response was not ok");
                    }
                    return response.json();
                })
                .then(function (data) {
                    showToast("Tạo thông báo chung thành công!", "#4caf50", "check-circle");
                    createNotificationModal.style.display = "none";
                    fetchAndDisplayPublicNotifications(); // Fetch the updated notifications list
                })
                .catch(function (error) {
                    console.error("Error creating notification:", error);
                    showToast("Lỗi khi tạo thông báo chung!", "red", "times-circle");
                });
        }
    });
    function validateFormData(data) {
        var isValid = true;
        if(!data.type.trim()){
            isValid = false;
            showError("notificationSendTo", "Gửi tới không được để trống");

        }

        return isValid;
    }
    function showError(fieldId, message) {
        var field = document.getElementById(fieldId);
        var error = document.createElement("div");
        error.className = "error-message";
        error.textContent = message;
        field.parentNode.insertBefore(error, field.nextSibling);
    }
    function clearErrors() {
        var errors = document.querySelectorAll(".error-message");
        errors.forEach(function (error) {
            error.remove();
        });
    }
    document.getElementById("notificationType").addEventListener("change", toggleSendToField);
    function toggleSendToField() {
        var notificationType = document.getElementById("notificationType").value;
        var notificationSendTo = document.getElementById("notificationSendTo");
        var notificationLabelSendTo = document.getElementById("notifcationLabelSendTo");

        if (notificationType === "private") {
            notificationSendTo.style.display = "block";
            notificationLabelSendTo.style.display = "block";
        } else {
            notificationSendTo.style.display = "none";
            notificationLabelSendTo.style.display = "none";
        }
    }

    toggleSendToField();

    function fetchCentersAndUsersAndCompareSendTo(sendTo) {
        return Promise.all([
            fetch("/admin-centers").then(response => response.json()),
            fetch("/api/users").then(response => response.json())
        ])
            .then(([centers, users]) => {

                // Extract center codes
                const centerName = centers.map(center => center.name);
                // Extract user codes
                const userName = users.map(user => user.username.toUpperCase());

                console.log(users)
                console.log(centerName)
                console.log(sendTo)
                console.log(userName.includes(sendTo))
                console.log("UserName: " + userName)
                // Check if sendTo exists in either center codes or user codes
                if (!centerName.includes(sendTo) && !userName.includes(sendTo)) {
                    // If sendTo doesn't exist in either, return false
                    return false;
                }
                return true;
            })
            .catch(error => {
                console.error("Error fetching centers and users:", error);
                return true;
            });
    }
});

