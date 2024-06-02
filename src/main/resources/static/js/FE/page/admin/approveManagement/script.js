import { postList } from "../../../dummyData/postDemo.js";

// Start: Drop down
document.addEventListener("DOMContentLoaded", function () {
  const bell = document.getElementById("bell");
  const contentNotification = document.querySelector(".content-notification");
  const profileDropdownList = document.querySelector(".profile-dropdown-list");
  const profileDropdownBtn = document.querySelector(".profile-dropdown-btn");

  function toggleProfileDropdown() {
    profileDropdownList.classList.toggle("active");
    contentNotification.style.display = "none"; // Hide notification dropdown
  }

  function toggleNotificationDropdown() {
    contentNotification.style.display =
      contentNotification.style.display === "block" ? "none" : "block";
    profileDropdownList.classList.remove("active"); // Hide profile dropdown
  }

  bell.addEventListener("click", function () {
    toggleNotificationDropdown();
  });

  profileDropdownBtn.addEventListener("click", function () {
    toggleProfileDropdown();
  });

  // Close both dropdowns if the user clicks outside of them
  window.addEventListener("click", function (event) {
    if (
      !bell.contains(event.target) &&
      !contentNotification.contains(event.target)
    ) {
      contentNotification.style.display = "none";
    }
    if (
      !profileDropdownBtn.contains(event.target) &&
      !profileDropdownList.contains(event.target)
    ) {
      profileDropdownList.classList.remove("active");
    }
  });
});

// End: Drop down

// Start modal notification detail
document.addEventListener("DOMContentLoaded", function () {
  // Function to open the modal
  function openModal(notificationText) {
    const modal = document.getElementById("notificationModal");
    const notificationDetails = document.getElementById("notificationDetails");
    notificationDetails.innerText = notificationText;
    modal.style.display = "block";
  }

  // Function to close the modal
  function closeModal() {
    const modal = document.getElementById("notificationModal");
    modal.style.display = "none";
  }

  // Event listener for notification clicks
  const notificationLinks = document.querySelectorAll(
    ".content-notification ul li a"
  );
  notificationLinks.forEach((link) => {
    link.addEventListener("click", function (event) {
      event.preventDefault(); // Prevent default anchor behavior
      const notificationText = this.getAttribute("data-notification");
      openModal(notificationText);
    });
  });

  // Event listener for close button
  const closeButton = document.querySelector(".modal .close");
  closeButton.addEventListener("click", closeModal);

  // Close modal when clicking outside of the modal content
  window.addEventListener("click", function (event) {
    const modal = document.getElementById("notificationModal");
    if (event.target === modal) {
      closeModal();
    }
  });
});
// End modal notification detail

// Start modal create post
$(document).ready(function () {
  // Mở modal khi click vào nút "Tạo bài đăng"
  $(".btn-create").click(function () {
    $("#createPostModal").css("display", "block");
  });

  // Đóng modal khi click vào nút đóng hoặc bên ngoài modal
  $(".close, .modal").click(function () {
    $("#createPostModal").css("display", "none");
  });

  // Ngăn sự kiện click lan ra khỏi modal
  $(".modal-content").click(function (event) {
    event.stopPropagation();
  });

  // Submit form khi ấn nút "Tạo"
  $("#postForm").submit(function (event) {
    event.preventDefault();
    // Thực hiện xử lý tạo bài đăng ở đây
    // Sau khi tạo xong, có thể đóng modal bằng cách:
    $("#createPostModal").css("display", "none");
  });
});

//End modal create post

// Start modal view post detail
function openViewModal(post) {
  const modal = document.getElementById("viewPostModal");
  const postTitle = document.getElementById("postTitleViewModal");
  const postContent = document.getElementById("postContentViewModal");

  postTitle.innerText = post.C12_TITLE;
  postContent.innerText = post.C12_CONTENT; 
  modal.style.display = "block";
  console.log(postTitle);
  console.log("In ra post")
}

function closeViewModal() {
  const modal = document.getElementById("viewPostModal");
  modal.style.display = "none";
}


document.addEventListener("DOMContentLoaded", function () {
  const closeButton = document.querySelector("#viewPostModal .close");
  closeButton.addEventListener("click", closeViewModal);

  window.addEventListener("click", function (event) {
    const modal = document.getElementById("viewPostModal");
    if (event.target === modal) {
      closeViewModal();
    }
  });
});
// End modal view post detail

// Start: Hamburger
$(document).ready(function () {
  $(".hamburger").click(function () {
    $(".wrapper").toggleClass("collapse");
    if ($(window).width() < 768) {
      // Nếu chiều rộng bé hơn 768px, thêm phần tìm kiếm vào
      $(".search").toggleClass("show-search");
    }
  });
});
// End: Hamburger

// Start show toast notification
function showToast(message, type) {
  const toastContainer = document.getElementById("toastContainer");
  const toast = document.createElement("div");
  toast.classList.add("toast", type);
  toast.innerHTML = `
    <i class="fas fa-check"></i>
    <span>${message}</span>
  `;
  toastContainer.appendChild(toast);

  // Show the toast
  setTimeout(() => {
    toast.classList.add("show");
  }, 100);

  // Hide the toast after 3 seconds
  setTimeout(() => {
    toast.classList.remove("show");
    // Remove the toast from the DOM
    setTimeout(() => {
      toastContainer.removeChild(toast);
    }, 500);
  }, 3000);
}
// End show toast notification

// Function to build table with data
function buildTable(data) {
  var table = document.getElementById("tableBody");
  table.innerHTML = ""; // Clear existing table data
  data.forEach((e, index) => {
    var row = document.createElement("tr");
    row.innerHTML = `
      <td><p>${e.C12_TITLE}</p></td>
      <td><p>${e.C12_PUBLISHED_DATE}</p></td>
      <td><a href="#" class="view-post" data-index="${index}">Xem</a></td>
      <td>
        <button class="approve-btn">Duyệt</button>
        <button class="reject-btn">Từ chối</button>
      </td>
    `;
    table.appendChild(row);

    // Add event listeners for buttons
    row.querySelector(".approve-btn").addEventListener("click", () => {
      data.splice(index, 1); // Remove post from data
      buildTable(data); // Rebuild table
      showToast("Duyệt thành công", "success"); // Show toast
    });

    row.querySelector(".reject-btn").addEventListener("click", () => {
      data.splice(index, 1); // Remove post from data
      buildTable(data); // Rebuild table
      showToast("Từ chối thành công", "success"); // Show toast
    });

    row.querySelector(".view-post").addEventListener("click", (event) => {
      event.preventDefault();
      const postIndex = event.target.getAttribute("data-index");
      openViewModal(data[postIndex]);
  
      
    });
  });
}

// Initial display of all posts
buildTable(postList);

// Search functionality
document.getElementById("searchForm").addEventListener("submit", function (event) {
  event.preventDefault();
  const query = document.getElementById("searchInput").value.toLowerCase();
  const filteredPosts = postList.filter(post =>
    post.C12_TITLE.toLowerCase().includes(query)
  );
  buildTable(filteredPosts);
});
