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

    function fetchPosts() {
        fetch("/admin-centerPosts")
            .then(response => response.json())
            .then(data => {
                console.log(data)
                allPosts = data.filter(post => post.status === "Processing");
                currentPage = 1; // Reset to first page
                renderTable(allPosts);
            })
            .catch(error => console.error("Error fetching posts:", error));
    }

    function renderTable(postList) {
        const start = (currentPage - 1) * itemsPerPage;
        const end = start + itemsPerPage;
        const postsToDisplay = postList.slice(start, end);

        displayPosts(postsToDisplay);
        renderPaginationControls(postList);
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
                        <td data-title="${post.title}" data-content="${post.content}" data-createdat="${post.createdAt}" data-centername="${post.centerName}" data-managername="${post.managerName}" data-fileurl="${post.fileUrl}">${post.title}</td>
                        <td>${post.centerName}</td>
                        <td>${new Date(post.createdAt).toLocaleDateString('en-GB')}</td>
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

    function renderPaginationControls(postList) {
        const totalPages = Math.ceil(postList.length / itemsPerPage);
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
                renderTable(postList);
            });
            paginationControls.appendChild(pageButton);
        }
    }

    function displayPostDetails(postId) {
        var postRow = document.getElementById(postId);

        var title = postRow.querySelector("td[data-title]").getAttribute("data-title");
        var content = postRow.querySelector("td[data-content]").getAttribute("data-content");
        var createdAt = postRow.querySelector("td[data-createdat]").getAttribute("data-createdat");
        var centerName = postRow.querySelector("td[data-centername]").getAttribute("data-centername");
        var managerName = postRow.querySelector("td[data-managername]").getAttribute("data-managername");
        var fileUrl = postRow.querySelector("td[data-fileurl]").getAttribute("data-fileurl");

        postTitleViewModal.textContent = `Title: ${title}`;
        postContentViewModal.innerText = `Detail description: ${content}`;
        postPublishedDateViewModal.textContent = `Date: ${new Date(createdAt).toLocaleDateString('en-GB')}`;
        postCenterNameViewModal.textContent = "Center: " + centerName;
        postManagerNameViewModal.textContent = "Manager: " + managerName;
        postFileURLViewModal.textContent = fileUrl ? `File: ${fileUrl}` : "No File";

        approvePostButton.setAttribute("data-id", postId);
        rejectPostButton.setAttribute("data-id", postId);
        viewPostModal.style.display = "block";
    }

    function updatePostStatus(id, status) {
        fetch(`/admin-${status}CenterPost/${id}`, {
            method: "PATCH"
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                // Remove the post from the allPosts array
                allPosts = allPosts.filter(post => String(post.id) !== id);
                // Re-render the table
                renderTable(allPosts);
                showToast(`Post ${status === 'approve' ? 'approved' : 'rejected'} successfully!`);
                // Check if no posts left to display
                if (allPosts.length === 0) {
                    noResultDiv.style.display = "block";
                }
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
        var filteredPosts
        if(!query.trim()){
            filteredPosts = allPosts;
        }else{
            filteredPosts = allPosts.filter(post =>
                post.title.toLowerCase().includes(query) ||
                post.centerName.toLowerCase().includes(query)
            );
        }

        currentPage = 1; // Reset to first page
        renderTable(filteredPosts)
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
    });

    rejectPostButton.addEventListener("click", function () {
        var postId = this.getAttribute("data-id");
        updatePostStatus(postId, "reject");
        viewPostModal.style.display = "none";
    });

    fetchPosts();
});