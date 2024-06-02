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

// Start: Hamburger
$(document).ready(function () {
  $(".hamburger").click(function () {
    $(".wrapper").toggleClass("collapse");
  });
});
// End: Hamburger

// Start delete modal
$(document).ready(function() {
  const deleteUserLinks = $('.delete-notification');
  const modal = $('#deleteModal');
  const userNameSpan = $('#userName');
  const confirmDeleteButton = $('#confirmDelete');
  const cancelDeleteButton = $('#cancelDelete');
  const toast = $('#toast');
  let currentUserRow;

  deleteUserLinks.on('click', function(event) {
      event.preventDefault();
      currentUserRow = $(this).closest('tr');
      const title = currentUserRow.find('td p').first().text();
      userNameSpan.text(title);
      modal.show();
  });

  confirmDeleteButton.on('click', function() {
      if (currentUserRow) {
          currentUserRow.remove();
          modal.hide();
          showToast("Xóa thành công!");
      }
  });

  cancelDeleteButton.on('click', function() {
      modal.hide();
  });

  $(window).on('click', function(event) {
      if ($(event.target).is(modal)) {
          modal.hide();
      }
  });

  function showToast(message) {
      toast.find('p').text(message);
      toast.addClass('show');
      setTimeout(function() {
          toast.removeClass('show');
      }, 2000);
  }
});
// End delete modal

// Start create modal
$(document).ready(function () {
  let currentUserRow; // Declare the variable at the top to ensure it's accessible

  function showToast(message) {
      const toast = $('#toast');
      toast.find('p').text(message);
      toast.addClass('show');
      setTimeout(function () {
          toast.removeClass('show');
      }, 2000);
  }

  function openViewModal(title, content) {
      $('#viewNotificationTitle').text(title);
      $('#viewNotificationContent').text(content);
      $('#viewNotificationModal').show();
  }

  function populateTable() {
      const tableBody = $('#notificationTableBody');
      tableBody.empty();

      postList.forEach(post => {
          const row = `
              <tr data-post-id="${post.C12_POST_ID}">
                  <td><p>${post.C12_TITLE}</p></td>
                  <td>${post.C12_PUBLISHED_DATE}</td>
                  <td><a href="#" class="view-notification">Xem</a></td>
                  <td>
                      <a href="#" class="delete-notification"><i class="fas fa-trash"></i></a>
                  </td>
              </tr>`;
          tableBody.append(row);
      });
  }

  $('#createNotificationBtn').on('click', function () {
      $('#createNotificationModal').show();
  });

  $('.modal .close, #cancelCreate').on('click', function () {
      $('.modal').hide();
  });

  $(window).on('click', function (event) {
      if ($(event.target).hasClass('modal')) {
          $('.modal').hide();
      }
  });

  $('#notificationForm').on('submit', function (event) {
      event.preventDefault();
      const title = $('#notificationTitle').val();
      const content = $('#notificationContent').val();
      const newPost = {
          C12_POST_ID: postList.length + 1,
          C12_TITLE: title,
          C12_CONTENT: content,
          C12_PUBLISHED_DATE: new Date().toISOString().split('T')[0],
          C12_MANAGER_ID: 0,
          C12_CENTER_ID: 0
      };
      postList.push(newPost);
      populateTable();
      $('.modal').hide();
      showToast('Tạo thành công!');
  });

  $('#notificationTableBody').on('click', '.view-notification', function (event) {
      event.preventDefault();
      const postId = $(this).closest('tr').data('post-id');
      const post = postList.find(p => p.C12_POST_ID === postId);
      openViewModal(post.C12_TITLE, post.C12_CONTENT);
  });

  $('#notificationTableBody').on('click', '.delete-notification', function (event) {
      event.preventDefault();
      currentUserRow = $(this).closest('tr'); // Now this will refer to the correct variable
      const postId = currentUserRow.data('post-id');
      const post = postList.find(p => p.C12_POST_ID === postId);
      $('#userName').text(post.C12_TITLE);
      $('#deleteModal').data('post-id', postId).show();
  });

  $('#confirmDelete').on('click', function () {
      const postId = $('#deleteModal').data('post-id');
      const postIndex = postList.findIndex(p => p.C12_POST_ID === postId);
      if (postIndex !== -1) {
          postList.splice(postIndex, 1);
          populateTable();
          $('#deleteModal').hide();
          showToast('Xóa thành công!');
      }
  });

  $('#cancelDelete').on('click', function () {
      $('#deleteModal').hide();
  });

  populateTable();
});

// End create modal
