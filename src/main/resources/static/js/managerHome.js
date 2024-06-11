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

const toggle = () => classList.toggle("active");

let profileDropdownList = document.querySelector(".profile-dropdown-list");
let btn = document.querySelector(".profile-dropdown-btn");

let classList = profileDropdownList.classList;

window.addEventListener("click", function (e) {
  if (!btn.contains(e.target)) classList.remove("active");
});

document.getElementById("currentDate").innerText = getCurrentDate();

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

//ADDING CENTER
    const addCentreBtn = document.getElementById('addCentreBtn');
    const addCentreModal = document.getElementById('addCentreModal');
    const cancelAddButton = document.getElementById('cancelAdd');
    const confirmAddButton = document.getElementById('confirmAdd');
    const addCentreForm = document.getElementById('addCentreForm');

    // Modal-show-up
    addCentreBtn.addEventListener('click', () => {
        addCentreModal.style.display = 'block';
    });

    // Hide-modal
    cancelAddButton.addEventListener('click', () => {
        addCentreModal.style.display = 'none';
    });

    // Confirm button
    addCentreForm.addEventListener('submit', (event) => {
        event.preventDefault();
        const centreName = document.getElementById('centreName').value;
        // thêm luôn vào db nếu admin nhấn từ chối -> xoá khỏi db hoặc admin confirm mới thêm vào db
        // cần hiện thông báo đã được thêm thành công hay gì đó hmm chắc là gửi về manager
        addCentreModal.style.display = 'none';
        showToast(`Đã gửi yêu cầu thêm trung tâm "${centreName}" thành công!`);
    });

//MODAL UP DETAIL INFO
    var modal = document.getElementById("myModal");
    var modalButtons = document.getElementsByClassName("openModalBtn");
    var span = document.getElementsByClassName("close")[0];
    // Rà hết class 'openModalBtn'
    for (var i = 0; i < modalButtons.length; i++) {
        modalButtons[i].addEventListener("click", function () {
            modal.style.display = "block";
        });
    }
    // hide-modal
    span.onclick = function () {
        modal.style.display = "none";
    }

    //DELETE CENTER
    // get by => '.delete-user'
        const deleteButtons = document.querySelectorAll('.delete-user');
        const deleteModal = document.getElementById('deleteModal');
        const confirmDeleteButton = document.getElementById('confirmDelete');
        const cancelDeleteButton = document.getElementById('cancelDeleteButton');
        const centerNameElement = document.getElementById('centerName');
        let deleteTarget = null;

        deleteButtons.forEach(button => {
            button.addEventListener('click', (event) => {
                event.preventDefault(); // né tag của <a></a>
                const row = event.target.closest('tr');
                const centerName = row.querySelector('td:nth-child(1) p').textContent; // Adjusted to target
                centerNameElement.textContent = centerName;
                deleteModal.style.display = 'block';
                deleteTarget = row;
            });
        });


        confirmDeleteButton.addEventListener('click', () => {
            if (deleteTarget) {
                const centerName = deleteTarget.querySelector('td:nth-child(1) p').textContent;//lấy cột 1 vì on query
                deleteTarget.remove();
                deleteModal.style.display = 'none';
                showToast('Xóa thành công trung tâm ' + centerName);
            }
        });

        cancelDeleteButton.addEventListener('click', () => {
            deleteModal.style.display = 'none';
        });
        // hide modal
        var spanDelete = document.getElementsByClassName("closeD")[0];
        spanDelete.onclick = function () {
            deleteModal.style.display = "none";
        }
        window.addEventListener('click', (event) => {
            if (event.target == deleteModal) {
                deleteModal.style.display = 'none';
            }
        });
        // Show toast message
        function showToast(message) {
            const toast = document.createElement('div');
            toast.className = 'toast';
            toast.textContent = message;
            document.body.appendChild(toast);
            setTimeout(() => {
                toast.classList.add('show');
            }, 100);
            setTimeout(() => {
                toast.classList.remove('show');
                setTimeout(() => {
                    document.body.removeChild(toast);
                }, 300);
            }, 3000);
        }

