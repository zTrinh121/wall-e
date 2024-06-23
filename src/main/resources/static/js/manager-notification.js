import { userList } from "../../../dummyData/userDemo.js";

// Start: nva
$(document).ready(function () {
    $(".nva").click(function () {
        $(".wrapper").toggleClass("collapse");
    });
});
// End: nva

// Display user data
userList.forEach((element) => {
    console.log(element);
});
//close-modal-applied-for-all
const closeButton = document.querySelector(".close");
//add event
closeButton.addEventListener("click", () => {
    // hide-modal
    addModal.style.display = "none";
});

// document.addEventListener("DOMContentLoaded", function () {
//   const roleFilter = document.getElementById("codeid");
//   const tableBody = document.getElementById("tableBody");

//   function buildTable(data) {
//     tableBody.innerHTML = ""; // Clear existing table rows
//     data.forEach((e, index) => {
//       let lock = e.C14_ACC_STATUS == 1 ? "Khóa" : "Mở khóa";
//       let row = `<tr>
//                           <td><p>${index + 1}</p></td>
//                           <td><p>${e.C14_USER_NAME}</p></td>
//                           <td><p>${e.C14_test_name}</p></td>
//                           <td><p>${e.C14_USER_CODEID}</p></td>
//                      </tr>`;
//       tableBody.innerHTML += row;
//     });
//     addEventListenersToButtons();
//   }

//   function filterTable() {
//     const selectedRole = roleFilter.value;
//     let roleEnum = 0; // Default to 'all'
//     if (selectedRole === "ABC") {
//       roleEnum = 1;
//     } else if (selectedRole === "XYZ") {
//       roleEnum = 2;
//     } else if (selectedRole === "ZZZ") {
//       roleEnum = 3;
//     } else if (selectedRole === "OOP") {
//       roleEnum = 4;
//     }

//     const filteredData =
//       roleEnum === 0
//         ? userList
//         : userList.filter((user) => user.C14_ROLE_ID === roleEnum);

//     buildTable(filteredData);
//   }

//   // Listen button
//   // function addEventListenersToButtons() {
//   //   const buttons = document.querySelectorAll(".action-button");
//   //   buttons.forEach((button) => {
//   //     button.addEventListener("click", function () {
//   //       const userName =
//   //         button.parentElement.parentElement.querySelector(
//   //           "td:first-child p"
//   //         ).innerText;
//   //       // Toggle lock status
//   //       const newStatus = button.innerText === "Khóa" ? "Mở khóa" : "Khóa";
//   //       // Update button text
//   //       button.innerText = newStatus;
//   //       showToast(newStatus, userName);
//   //     });
//   //   });
//   // }

//   function showToast(message, userName) {
//     const toast = document.getElementById("toast");
//     const toastMessage = document.getElementById("toastMessage");
//     toastMessage.innerHTML =
//      `<i class="bx bxs-calendar-check"><i class="fas fa-info"></i></i>
//       Trung tâm ${userName} đã ${message.toLowerCase()}`;
//     toast.className = "toast show";
//     setTimeout(function () {
//       toast.className = toast.className.replace("show", "");
//     }, 3000);
//   }

//   roleFilter.addEventListener("change", filterTable);

//   // Initial table build
//   buildTable(userList);
// });

const toggle = () => classList.toggle("active");

let profileDropdownList = document.querySelector(".profile-dropdown-list");
let btn = document.querySelector(".profile-dropdown-btn");

let classList = profileDropdownList.classList;

window.addEventListener("click", function (e) {
    if (!btn.contains(e.target)) classList.remove("active");
});

document.getElementById("currentDate").innerText = getCurrentDate();

// Get current date
// function getCurrentDate() {
//   var currentDate = new Date();
//   var day = String(currentDate.getDate()).padStart(2, "0");
//   var month = String(currentDate.getMonth() + 1).padStart(2, "0");
//   var year = currentDate.getFullYear();
//   return day + "-" + month + "-" + year;
// }
function getCurrentDate() {
    var currentDate = new Date();
    var dayOfWeek = ['Chủ Nhật', 'Thứ Hai', 'Thứ Ba', 'Thứ Tư', 'Thứ Năm', 'Thứ Sáu', 'Thứ Bảy'];
    var day = String(currentDate.getDate()).padStart(2, "0");
    var month = String(currentDate.getMonth() + 1).padStart(2, "0");
    var year = currentDate.getFullYear();
    var dayIndex = currentDate.getDay();
    var dayName = dayOfWeek[dayIndex];
    return dayName + ", " + day + "-" + month + "-" + year + " ";
}

// modal-up-detail-infor
document.addEventListener("DOMContentLoaded", (event) => {
    const modal = document.getElementById("myModal");
    const span = document.getElementsByClassName("close")[0];
    const modalDetail = document.getElementById("modal-detail");

    document.querySelectorAll(".open-modal-btn").forEach((button) => {
        button.addEventListener("click", () => {
            const detail = button.getAttribute("data-detail");
            console.log("Button clicked, detail:", detail);
            modalDetail.innerText = detail;
            modal.style.display = "block";
        });
    });

    span.onclick = function () {
        modal.style.display = "none";
    };

    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    };
});
//end

//Confirm delete center
// get by => '.delete-user'
const deleteButtons = document.querySelectorAll(".delete-user");
const deleteModal = document.getElementById("deleteModal");
const confirmDeleteButton = document.getElementById("confirmDelete");
const cancelDeleteButton = document.getElementById("cancelDeleteButton");
const centerNameElement = document.getElementById("centerName");
let deleteTarget = null;

deleteButtons.forEach((button) => {
    button.addEventListener("click", (event) => {
        event.preventDefault(); // né tag của <a></a>
        const row = event.target.closest("tr");
        const centerName = row.querySelector("td:nth-child(1) p").textContent; // Adjusted to target
        centerNameElement.textContent = centerName;
        deleteModal.style.display = "block";
        deleteTarget = row;
    });
});

confirmDeleteButton.addEventListener("click", () => {
    if (deleteTarget) {
        const centerName =
            deleteTarget.querySelector("td:nth-child(1) p").textContent;
        deleteTarget.remove();
        deleteModal.style.display = "none";
        showToast("Xóa thành công trung tâm " + centerName);
    }
});

cancelDeleteButton.addEventListener("click", () => {
    deleteModal.style.display = "none";
});

window.addEventListener("click", (event) => {
    if (event.target == deleteModal) {
        deleteModal.style.display = "none";
    }
});
// Show toast message
//   function showToast(message) {
//       const toast = document.createElement('div');
//       toast.className = 'toast';
//       toast.textContent = message;
//       document.body.appendChild(toast);
//       setTimeout(() => {
//           toast.classList.add('show');
//       }, 100);
//       setTimeout(() => {
//           toast.classList.remove('show');
//           setTimeout(() => {
//               document.body.removeChild(toast);
//           }, 300);
//       }, 3000);
//   }
//end-confirm

//    Adding Center JS
const addCentreBtn = document.getElementById("addCentreBtn");
const addCentreModal = document.getElementById("addCentreModal");
const cancelAddButton = document.getElementById("cancelAdd");
const confirmAddButton = document.getElementById("confirmAdd");
const addCentreForm = document.getElementById("addCentreForm");

// Modal-show-up
addCentreBtn.addEventListener("click", () => {
    addCentreModal.style.display = "block";
});

// Hide-modal
cancelAddButton.addEventListener("click", () => {
    addCentreModal.style.display = "none";
});

// Confirm button
addCentreForm.addEventListener("submit", (event) => {
    event.preventDefault();
    const centreName = document.getElementById("centreName").value;
    // Thêm code để xử lý việc thêm trung tâm vào cơ sở dữ liệu hoặc danh sách trên trang
    // Sau khi thêm xong, có thể hiển thị một thông báo hoặc làm một hành động gì đó
    // ở đây, mình chỉ ẩn modal để đơn giản
    addCentreModal.style.display = "none";
    showToast(`Đã thêm trung tâm "${centreName}" thành công!`);
});

// Show toast message
// function showToast(message) {
//     const toast = document.createElement('div');
//     toast.className = 'toast';
//     toast.textContent = message;
//     document.body.appendChild(toast);
//     setTimeout(() => {
//         toast.classList.add('show');
//     }, 100);
//     setTimeout(() => {
//         toast.classList.remove('show');
//         setTimeout(() => {
//             document.body.removeChild(toast);
//         }, 300);
//     }, 3000);
// }
//ending adding

//strt view-detail-iframe
