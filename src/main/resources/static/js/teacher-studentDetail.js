import { filteredUserList } from "../../../dummyData/userDemo.js";

// Start: nva
$(document).ready(function () {
    $(".nva").click(function () {
        $(".wrapper").toggleClass("collapse");
    });
});
// End: nva

// Display user data
filteredUserList.forEach((element) => {
    console.log(element);
});

document.addEventListener("DOMContentLoaded", function () {
    const roleFilter = document.getElementById("subject");
    const tableBody = document.getElementById("tableBody");

    function buildTable(data) {
        tableBody.innerHTML = ""; // Clear existing table rows
        data.forEach((e) => {
            let ban = e.status == 1 ? "Hoạt động" : "Đã xoá";
            let row = `<tr>
                          <td><p>${e.name}</p></td>
                          <td><p>${e.address}</p></td>
                          <td><p><button class="openModalBtn">Xem</button></p</td>
                     </tr>`;
            tableBody.innerHTML += row;
        });
        // <td><button class="action-button">${ban}</button></td>
        addEventListenersToButtons();
    }

    function filterTable() {
        const selectedRole = roleFilter.value;
        let roleEnum = 0; // Default to 'all'
        if (selectedRole === "Maths") {
            roleEnum = 1;
        } else if (selectedRole === "English") {
            roleEnum = 2;
        } else if (selectedRole === "Literature") {
            roleEnum = 3;
        } else if (selectedRole === "Physics") {
            roleEnum = 4;
        }

        const filteredData =
            roleEnum === 0
                ? filteredUserList
                : filteredUserList.filter((user) => user.id === roleEnum);

        buildTable(filteredData);
    }

    // Listen button
    function addEventListenersToButtons() {
        const buttons = document.querySelectorAll(".action-button");
        buttons.forEach((button) => {
            button.addEventListener("click", function () {
                const userName =
                    button.parentElement.parentElement.querySelector(
                        "td:first-child p"
                    ).innerText;
                // Toggle ban status
                const newStatus = button.innerText === "Xoá" ? "Hoạt động" : "Xoá";
                // Update button text
                button.innerText = newStatus;
                showToast(newStatus, userName);
            });
        });
    }

    function showToast(message, userName) {
        const toast = document.getElementById("toast");
        const toastMessage = document.getElementById("toastMessage");
        toastMessage.innerHTML = `<i class="fas fa-info"></i> Tài khoản ${userName} đã ${message.toLowerCase()}`;
        toast.className = "toast show";
        setTimeout(function () {
            toast.className = toast.className.replace("show", "");
        }, 3000);
    }

    roleFilter.addEventListener("change", filterTable);

    // Initial table build
    buildTable(filteredUserList);
});
