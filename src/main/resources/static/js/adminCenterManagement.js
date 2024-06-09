document.addEventListener("DOMContentLoaded", function () {
    var tableBody = document.getElementById("tableBody");
    var viewPostModal = document.getElementById("viewPostModal");
    var closeViewPostModal = document.getElementById("closeViewPostModal");
    var approvePostButton = document.getElementById("approvePostButton");
    var rejectPostButton = document.getElementById("rejectPostButton");

    var postTitleViewModal = document.getElementById("postTitleViewModal");
    var postContentViewModal = document.getElementById("postContentViewModal");
    var postPublishedDateViewModal = document.getElementById("postPublishedDateViewModal");
    var postCenterNameViewModal = document.getElementById("postCenterNameViewModal");
    var postManagerNameViewModal = document.getElementById("postManagerNameViewModal");
    var postFileURLViewModal = document.getElementById("postFileURLViewModal");

    var searchForm = document.getElementById("searchForm");
    var searchInput = document.getElementById("searchInput");
    var noResultDiv = document.getElementById("no-result");
    var paginationControls = document.getElementById("paginationControls");

    var allPosts = [];
    var itemsPerPage = 5; // Number of posts per page
    var currentPage = 1; // Current page number

    var searchResults = [];

    function fetchPosts() {
        fetch("/admin-centers")
            .then(response => response.json())
            .then(data => {
                allPosts = data.filter(post => post.status === "Wait_to_process");
                if (searchResults.length > 0) {
                    // Nếu có, sử dụng kết quả tìm kiếm
                    allPosts = searchResults;
                }
                currentPage = 1; // Reset to first page
                renderTable();
            })
            .catch(error => console.error("Error fetching posts:", error));
    }

    function renderTable() {
        const start = (currentPage - 1) * itemsPerPage;
        const end = start + itemsPerPage;
        const postsToDisplay = allPosts.slice(start, end);

        displayPosts(postsToDisplay);
        renderPaginationControls();
    }

    function displayPosts(posts) {
        tableBody.innerHTML = "";

        if (posts.length === 0) {
            noResultDiv.style.display = "block";
        } else {
            noResultDiv.style.display = "none";
            posts.forEach(post => {
                var row = `
                    <tr id="${post.id}">
                        <td data-title="${post.name}" data-content="${post.description}" data-createdat="${post.createdAt}" data-centername="${post.name}" data-managername="${post.managerName}" data-fileurl="${post.imagePath}" data-address="${post.address}" data-phone="${post.phone}" data-email="${post.email}" data-regulation="${post.regulation}" data-managerphone="${post.managerPhone}" data-manageraddress="${post.managerAddress}" data-manageremail="${post.managerEmail}">
                            ${post.name}
                        </td>
                        <td>${post.address}</td>
                        <td>${post.managerName}</td>
                        <td><a class="view-details" data-id="${post.id}">Xem</a></td>
                        <td>
                            <button class="approve" data-id="${post.id}">Duyệt</button>
                            <button class="reject" data-id="${post.id}">Từ chối</button>
                        </td>
                    </tr>
                `;
                tableBody.insertAdjacentHTML("beforeend", row);
            });
        }
    }

    function renderPaginationControls() {
        const totalPages = Math.ceil(allPosts.length / itemsPerPage);
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

    paginationControls.addEventListener("click", function (event) {
        if (event.target.classList.contains("page-button")) {
            currentPage = parseInt(event.target.textContent); // Lấy số trang từ nội dung của nút
            renderTable(); // Render lại bảng khi chuyển trang
        }
    });

    function displayPostDetails(postId) {
        var postRow = document.getElementById(postId);

        var title = postRow.querySelector("td[data-title]").getAttribute("data-title");
        var content = postRow.querySelector("td[data-content]").getAttribute("data-content");
        var createdAt = postRow.querySelector("td[data-createdat]").getAttribute("data-createdat");
        var centerName = postRow.querySelector("td[data-centername]").getAttribute("data-centername");
        var managerName = postRow.querySelector("td[data-managername]").getAttribute("data-managername");
        var fileUrl = postRow.querySelector("td[data-fileurl]").getAttribute("data-fileurl");
        var address = postRow.querySelector("td[data-address]").getAttribute("data-address");
        var phone = postRow.querySelector("td[data-phone]").getAttribute("data-phone");
        var email = postRow.querySelector("td[data-email]").getAttribute("data-email");
        var regulation = postRow.querySelector("td[data-regulation]").getAttribute("data-regulation");
        var managerPhone = postRow.querySelector("td[data-managerphone]").getAttribute("data-managerphone");
        var managerAddress = postRow.querySelector("td[data-manageraddress]").getAttribute("data-manageraddress");
        var managerEmail = postRow.querySelector("td[data-manageremail]").getAttribute("data-manageremail");

        postTitleViewModal.textContent = `Title: ${title}`;
        postContentViewModal.innerText = `Detail description: ${content}`;
        postPublishedDateViewModal.textContent = `Date: ${new Date(createdAt).toLocaleDateString('en-GB')}`;
        postCenterNameViewModal.textContent = "Center: " + centerName;
        postManagerNameViewModal.textContent = "Manager: " + managerName;
        postFileURLViewModal.textContent = fileUrl ? `File: ${fileUrl}` : "No File";
        postFileURLViewModal.innerHTML = fileUrl ? `<a href="${fileUrl}" target="_blank">View File</a>` : "No File";

        var additionalDetails = `
            <p>Address: ${address}</p>
            <p>Phone: ${phone}</p>
            <p>Email: ${email}</p>
            <p>Regulation: ${regulation}</p>
            <p>Manager Phone: ${managerPhone}</p>
            <p>Manager Address: ${managerAddress}</p>
            <p>Manager Email: ${managerEmail}</p>
        `;
        postContentViewModal.innerHTML += additionalDetails;

        approvePostButton.setAttribute("data-id", postId);
        rejectPostButton.setAttribute("data-id", postId);
        viewPostModal.style.display = "block";
    }

    function updatePostStatus(id, status) {
        fetch(`/admin-${status}Center/${id}`, {
            method: "PATCH"
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }

                allPosts = allPosts.filter(post => String(post.id) !== id);
                console.log("POst sau khi thay doi" + allPosts.name);
                var postRow = document.getElementById(id);
                if (postRow) {
                    postRow.remove();
                }
                showToast(`Post ${status === 'approve' ? 'approved' : 'rejected'} successfully!`);
                if (allPosts.length === 0) {
                    noResultDiv.style.display = "block";
                }
                renderTable();
            })

            .catch(error => {
                console.error("Error updating post status:", error);
                showToast(`Failed to ${status} post.`);
            });
    }

    function showToast(message) {
        var toastContainer = document.getElementById("toastContainer");
        toastContainer.innerHTML = `<i class="fas fa-check-circle"></i> ${message}`;
        toastContainer.classList.add("show");
        setTimeout(() => {
            toastContainer.classList.remove("show");
        }, 3000);
    }

    searchForm.addEventListener("submit", function (event) {
        event.preventDefault();
        var query = searchInput.value.toLowerCase();
        var searchResults  = allPosts.filter(post =>
            post.name.toLowerCase().includes(query)
        );
        currentPage = 1; // Reset to first page
        displayPosts(searchResults);
    });

    tableBody.addEventListener("click", function (event) {
        if (event.target.classList.contains("view-details")) {
            var postId = event.target.getAttribute("data-id");
            displayPostDetails(postId);
        }

        if (event.target.classList.contains("approve")) {
            var postId = event.target.getAttribute("data-id");
            updatePostStatus(postId, "approve");
        }

        if (event.target.classList.contains("reject")) {
            var postId = event.target.getAttribute("data-id");
            updatePostStatus(postId, "reject");
        }
    });

    closeViewPostModal.addEventListener("click", function () {
        viewPostModal.style.display = "none";
    });

    window.addEventListener("click", function (event) {
        if (event.target == viewPostModal) {
            viewPostModal.style.display = "none";
        }
    });

    approvePostButton.addEventListener("click", function () {
        var postId = this.getAttribute("data-id");
        updatePostStatus(postId, "approve");
        viewPostModal.style.display = "none";
        renderTable();
    });

    rejectPostButton.addEventListener("click", function () {
        var postId = this.getAttribute("data-id");
        updatePostStatus(postId, "reject");
        viewPostModal.style.display = "none";
        renderTable();
    });

    fetchPosts();
});
