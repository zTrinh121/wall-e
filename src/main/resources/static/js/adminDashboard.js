// Define toast and toastInfo in the global scope
var toast = document.getElementById("toast");
var toastInfo = document.getElementById("toast-info");

//Start: Create notification form
function toggleSendToField() {
    var notificationTypeSelect = document.getElementById("notificationType");
    var notificationType = notificationTypeSelect.value; // Lấy giá trị của select

    var sendToInput = document.getElementById("notificationSendTo");
    var sendToLabel = document.getElementById("notifcationLabelSendTo");

    if (notificationType === "private") {
        sendToInput.style.display = "block";
        sendToLabel.style.display = "block";
        sendToInput.setAttribute("required", true);
    } else {
        sendToInput.style.display = "none";
        sendToLabel.style.display = "none";
        sendToInput.removeAttribute("required");
    }
}

//End: Create notification form

//Start: Modal create/delete/pdate notification
document.addEventListener("DOMContentLoaded", function () {
    var createNotificationBtn = document.getElementById("createNotificationBtn");
    var createNotificationModal = document.getElementById("createNotificationModal");
    var closeButton = createNotificationModal.querySelector(".close");
    var notificationForm = document.getElementById("notificationForm");
    var notificationTypeSelect = notificationForm.querySelector("select");

    createNotificationBtn.addEventListener("click", function () {
        createNotificationModal.style.display = "block";
    });

    closeButton.addEventListener("click", function () {
        createNotificationModal.style.display = "none";
    });

    window.addEventListener("click", function (event) {
        if (event.target == createNotificationModal) {
            createNotificationModal.style.display = "none";
        }
    });

    notificationForm.addEventListener("submit", function (event) {
        event.preventDefault();

        var notificationData = {
            title: notificationForm.querySelector("#notificationTitle").value,
            content: notificationForm.querySelector("#notificationContent").value,
            createdAt: new Date().toISOString()
        };

        var endpoint = "/admin-publicNotification/create";

        if (notificationTypeSelect.value === "private") {
            endpoint = "/admin-privateNotification/create";
        }

        fetch(endpoint, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(notificationData)
        })
            .then(function (response) {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                toastInfo.innerHTML = '<i class="fas fa-check-circle"></i> Notification created successfully';
                toast.style.backgroundColor = "#4caf50";
                toast.style.display = "block";
                setTimeout(function () {
                    toast.style.display = "none";
                }, 3000); // ẩn toast sau 3 giây
                return response.json();
            })
            .then(function (data) {
                console.log("Notification created successfully:", data);
                var newNotificationRow = `
                <tr id="${data.id}">
                    <td>${data.title}</td>
                    <td>${new Date(data.createdAt).toLocaleDateString('en-GB')}</td>
                    <td><button class="view-details" data-title="${data.title}" data-content="${data.content}" data-createdat="${data.createdAt}">Xem</button></td>
                    <td>
                        <i class="fas fa-edit" data-id="${data.id}"></i>
                        <i class="fas fa-trash" data-id="${data.id}"></i>
                    </td>
                </tr>
                `;
                notificationTableBody.insertAdjacentHTML("beforeend", newNotificationRow);
            })
            .catch(function (error) {
                console.error("Error creating notification:", error);
            });

        createNotificationModal.style.display = "none";
    });
});
//End: Modal create notification

//Start: Button notification public/private
document.addEventListener("DOMContentLoaded", function () {
    var currentPage = 0;
    var itemsPerPage = 10;
    var modal = document.getElementById('viewNotificationModal');
    var closeBtnNotification = modal.querySelector('.close');
    var viewNotificationTitle = modal.querySelector('#viewNotificationTitle');
    var viewNotificationContent = modal.querySelector('#viewNotificationContent');
    var viewNotificationCreatedAt = modal.querySelector('#viewNotificationCreatedAt');
    var notificationTableBody = document.getElementById("notificationTableBody");

    function fetchNotifications(page) {
        var url = `/admin-publicNotificationsPage?page=${page}&size=${itemsPerPage}`;
        fetch(url)
            .then(function (response) {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then(function (data) {
                var totalPages = data.totalPages;
                document.getElementById("totalPages").textContent = totalPages;
                if (currentPage >= totalPages - 1) {
                    document.getElementById("nextPageBtn").disabled = true;
                } else {
                    document.getElementById("nextPageBtn").disabled = false;
                }
                // Hiển thị dữ liệu lên giao diện
                displayNotifications(data.content);
                document.getElementById("currentPage").textContent = data.number + 1;
            })
            .catch(function (error) {
                console.error("Error fetching notifications:", error);
            });
    }

    function displayNotifications(notifications) {
        notificationTableBody.innerHTML = "";

        notifications.forEach(function (notification) {
            var row = `
            <tr id="${notification.id}">
                <td>${notification.title}</td>
                <td>${new Date(notification.createdAt).toLocaleDateString('en-GB')}</td>
                <td><button class="view-details" data-title="${notification.title}" data-content="${notification.content}" data-createdat="${notification.createdAt}">Xem</button></td>
                <td>
                    <i class="fas fa-edit" data-id="${notification.id}"></i>
                    <i class="fas fa-trash" data-id="${notification.id}"></i>
                </td>
            </tr>
        `;
            notificationTableBody.insertAdjacentHTML("beforeend", row);
        });
    }

    function displayNotificationDetails(title, content, createdAt) {
        // Set the title, content, and created date of the notification in the modal
        viewNotificationTitle.textContent = title;
        viewNotificationContent.textContent = content;
        viewNotificationCreatedAt.textContent = new Date(createdAt).toLocaleDateString('en-GB');
        // Display the modal
        modal.style.display = "block";
    }

    document.getElementById('closeViewNotification').addEventListener('click', function () {
        modal.style.display = "none";
    });


    window.addEventListener('click', function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    });

    function displayDeleteModal(notificationId, notificationTitle) {
        var deleteModal = document.getElementById("deleteModal");
        var deleteConfirmBtn = deleteModal.querySelector("#confirmDelete");
        var deleteCancelBtn = deleteModal.querySelector("#cancelDelete");
        var userName = deleteModal.querySelector("#userName");
        userName.textContent = notificationTitle;
        deleteModal.style.display = "block";
        var notificationId = notification.id;

        if(notification.sendTo){
            deleteConfirmBtn.addEventListener("click", function () {
                fetch(`/admin-privateNotification/delete/${notificationId}`, {
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
                fetch(`/admin-publicNotification/delete/${notificationId}`, {
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

            // Đóng modal sau khi xóa
            deleteModal.style.display = "none";
        });

        deleteCancelBtn.addEventListener("click", function() {
            deleteModal.style.display = "none";
        });
    }

    notificationTableBody.addEventListener("click", function(event) {
        if (event.target.classList.contains("fa-trash")) {
            var notificationId = event.target.getAttribute("data-id");
            var notificationTitle = event.target.closest("tr").querySelector("td:first-of-type").textContent;
            displayDeleteModal(notificationId, notificationTitle);
        }

        if (event.target.classList.contains("fa-edit")) {
            var notificationId = event.target.getAttribute("data-id");
            var notificationRow = event.target.closest("tr");
            var notification = {
                id: notificationId,
                title: notificationRow.querySelector("td:first-of-type").textContent,
                content: notificationRow.querySelector(".view-details").getAttribute("data-content"),
            };
            displayEditModal(notification);
        }

        if (event.target.classList.contains("view-details")) {
            var title = event.target.getAttribute("data-title");
            var content = event.target.getAttribute("data-content");
            var createdAt = event.target.getAttribute("data-createdat");
            displayNotificationDetails(title, content, createdAt);
        }
    });

    //Edit notification
    function displayEditModal(notification) {
        var editModal = document.getElementById("editModal");
        var editConfirmBtn = editModal.querySelector("#confirmEdit");
        var editCancelBtn = editModal.querySelector("#cancelEdit");
        var editTitle = editModal.querySelector("#editTitle");
        var editContent = editModal.querySelector("#editContent");

        editTitle.value = notification.title;
        editContent.value = notification.content;

        editModal.style.display = "block";

        editConfirmBtn.addEventListener("click", function () {
            var updatedNotification = {
                title: editTitle.value,
                content: editContent.value,
            };

            if(notification.sendTo){
                fetch(`/admin-privateNotification/update/${updatedNotification.id}`, {
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
                fetch(`/admin-publicNotification/update/${updatedNotification.id}`, {
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
        });

        editCancelBtn.addEventListener("click", function () {
            editModal.style.display = "none";
        });

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
                var url = "/admin-privateNotification/create";
                try {

                    const centersValid = await fetchCentersAndCompareSendTo(newData.sendTo);
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
            fetch("/admin-publicNotification/create", {
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
            if (data.type === "private" && !data.sendTo.startsWith("CENTER") && !data.sendTo.startsWith("USER")) {
                showError("notificationSendTo", "Gửi tới phải bắt đầu bằng 'CENTER' hoặc 'USER'.");
                isValid = false;
            }
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

    function fetchCentersAndCompareSendTo(sendTo) {
        return fetch("/admin-centers")
            .then(response => response.json())
            .then(centers => {
                // Lấy mã của tất cả các center và lưu vào một mảng
                const centerCodes = centers.map(center => center.code);

                // Kiểm tra giá trị "send to" của thông báo
                if (!centerCodes.includes(sendTo)) {
                    // Nếu giá trị "send to" không tồn tại trong danh sách mã của center,
                    // trả về false
                    return false;
                }
                return true;
            })
            .catch(error => {
                console.error("Error fetching centers:", error);
                return false;
            });
    }
});


